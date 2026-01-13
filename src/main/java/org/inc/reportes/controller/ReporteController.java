package org.inc.reportes.controller;

import org.inc.reportes.dto.RespuestaReporteDto;
import org.inc.reportes.dto.SolicitudReporteDto;
import org.inc.reportes.service.impl.ReporteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteServiceImpl servicio;

    @PostMapping
    public ResponseEntity<RespuestaReporteDto> solicitarReporte(@RequestBody SolicitudReporteDto solicitud) {

        // 1. Crear el tracking en BD
        var reporte = servicio.iniciarReporte(solicitud.getUsuarioId());

        // 2. Disparar el proceso "Fire and Forget"
        servicio.generarYSubirReporte(reporte.getId());

        // 3. Responder rápido al cliente
        return ResponseEntity.accepted().body(new RespuestaReporteDto(
                reporte.getId(),
                "PENDING",
                "La solicitud fue recibida. Te notificaremos cuando termine."
        ));
    }
}
