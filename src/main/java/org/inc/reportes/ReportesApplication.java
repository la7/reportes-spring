package org.inc.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // ¡OJO! Esto es obligatorio para que funcione el @Async del servicio
public class ReportesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportesApplication.class, args);
    }

}
