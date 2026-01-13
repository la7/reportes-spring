package org.inc.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SolicitudReporteDto {
    Long usuarioId;
    String tipoReporte;
}
