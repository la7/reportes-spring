package org.inc.reportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);   // Hilos mínimos activos (ideal para t3.micro)
        executor.setMaxPoolSize(5);    // Máximo de hilos simultáneos
        executor.setQueueCapacity(500); // Cola de espera si los 5 hilos están ocupados
        executor.setThreadNamePrefix("ReporteThread-");
        executor.initialize();
        return executor;
    }
}