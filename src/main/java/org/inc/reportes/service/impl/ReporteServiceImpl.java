package org.inc.reportes.service.impl;

import org.inc.reportes.model.entity.ReporteEntity;
import org.inc.reportes.model.entity.EstadoReporte;
import org.inc.reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Para logs
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Inyección de dependencias profesional
@Slf4j
public class ReporteServiceImpl {

    private final ReporteRepository repositorio;
    private final S3Client s3Client; // Inyectado desde AwsConfig

    @Value("${aws.s3.bucket}")
    private String bucketName;

    // 1. Inyectamos la bandera del YAML
    @Value("${app.features.simulacion-aws:false}")
    private boolean modoSimulacion;

    // FASE 1: Síncrona (Rápida) - Crea el registro y devuelve ID
    public ReporteEntity iniciarReporte(Long usuarioId) {
        var reporte = ReporteEntity.builder()
                .usuarioId(usuarioId)
                .estado(EstadoReporte.PENDING)
                .build();
        return repositorio.save(reporte);
    }

    // FASE 2: Asíncrona (Lenta) - Se ejecuta en otro hilo para no bloquear
    @Async("taskExecutor")
    public void generarYSubirReporte(Long reporteId) {
        log.info("Iniciando procesamiento asíncrono para reporte: {}", reporteId);

        // Recuperar entidad (Ojo: Manejar Optional en producción)
        var reporte = repositorio.findById(reporteId).orElseThrow();

        try {
            // 0. Cambiar estado a PROCESSING
            reporte.setEstado(EstadoReporte.PROCESSING);
            repositorio.save(reporte);

            // 1. Simulacion Test OK
            if (modoSimulacion) {
                log.warn(">>> MODO SIMULACIÓN ACTIVO: Saltando subida a S3 real <<<");

                // Simulamos una pausa (latencia de red)
                Thread.sleep(1000);

                // Forzamos el OK sin tocar AWS
                reporte.setS3Url("https://simulacion-bucket.s3.aws/fake.csv");
                reporte.setEstado(EstadoReporte.COMPLETED);
                repositorio.save(reporte);
                return; // ¡Salimos del método aquí!
            }

            // 2. Simular generación de CSV pesado (Lógica de Negocio)
            // En la vida real aquí harías queries complejas a la BD
            String csvContent = "ID,Monto,Fecha\n1,100,2023-01-01\n2,200,2023-01-02";

            // 3. Subir a S3 (Streaming en memoria, SIN guardar en disco local)
            String fileName = "reportes/" + UUID.randomUUID() + ".csv";

            PutObjectRequest putReq = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType("text/csv")
                    .build();

            // Usamos el SDK v2 de AWS
            s3Client.putObject(putReq, RequestBody.fromString(csvContent, StandardCharsets.UTF_8));

            // 4. Actualizar estado a COMPLETED
            String s3Url = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            reporte.setS3Url(s3Url);
            reporte.setEstado(EstadoReporte.COMPLETED);
            repositorio.save(reporte);

            log.info("Reporte {} completado con éxito", reporteId);

            // Aquí podrías llamar a la Lambda de notificaciones si no usas S3 Events

        } catch (Exception e) {
            log.error("Error procesando reporte {}", reporteId, e);
            reporte.setEstado(EstadoReporte.FAILED);
            repositorio.save(reporte);
        }
    }
}
