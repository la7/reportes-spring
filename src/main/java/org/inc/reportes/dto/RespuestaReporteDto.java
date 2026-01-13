package org.inc.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespuestaReporteDto {
    Long reporteId;
    String estado;
    String mensaje;
}
