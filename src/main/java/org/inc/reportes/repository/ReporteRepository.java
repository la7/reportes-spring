package org.inc.reportes.repository;

import org.inc.reportes.model.entity.ReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<ReporteEntity, Long> {
    // Spring Data crea la SQL automáticamente con solo nombrar el método
    // Ej: Buscar todos los reportes fallidos de un usuario
    // List<ReporteEntity> findByUsuarioIdAndEstado(Long usuarioId, EstadoReporte estado);
}
