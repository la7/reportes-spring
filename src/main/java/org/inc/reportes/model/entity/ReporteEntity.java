package org.inc.reportes.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data // Lombok: Genera Getters/Setters
@Builder // Lombok: Patrón Builder para crear objetos fácil
@NoArgsConstructor @AllArgsConstructor
public class ReporteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    private EstadoReporte estado; // Enum: PENDING, PROCESSING, COMPLETED, FAILED

    private String s3Url;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;
}
