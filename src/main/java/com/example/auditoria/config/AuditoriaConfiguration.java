package com.example.auditoria.config;

import com.example.auditoria.usecase.*;
import com.example.auditoria.usecase.impl.*;
import com.example.auditoria.usecase.port.HallazgoRepositoryPort;
import com.example.auditoria.usecase.port.HistorialAuditoriaPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Composition Root (Frameworks & Drivers): cablea los casos de uso (clases
// planas) con el adaptador de repositorio que Spring provee como bean.
@Configuration
public class AuditoriaConfiguration {

    @Bean
    public RegistrarHallazgoUseCase registrarHallazgoUseCase(HallazgoRepositoryPort repo) {
        return new RegistrarHallazgoService(repo);
    }

    @Bean
    public IniciarRemediacionUseCase iniciarRemediacionUseCase(HallazgoRepositoryPort repo,
                                                               HistorialAuditoriaPort historial) {
        return new IniciarRemediacionService(repo, historial);
    }

    @Bean
    public CerrarHallazgoUseCase cerrarHallazgoUseCase(HallazgoRepositoryPort repo,
                                                       HistorialAuditoriaPort historial) {
        return new CerrarHallazgoService(repo, historial);
    }

    @Bean
    public ReabrirHallazgoUseCase reabrirHallazgoUseCase(HallazgoRepositoryPort repo,
                                                         HistorialAuditoriaPort historial) {
        return new ReabrirHallazgoService(repo, historial);
    }

    @Bean
    public ConsultarHallazgoUseCase consultarHallazgoUseCase(HallazgoRepositoryPort repo) {
        return new ConsultarHallazgoService(repo);
    }

    @Bean
    public ConsultarHistorialUseCase consultarHistorialUseCase(HistorialAuditoriaPort historial) {
        return new ConsultarHistorialService(historial);
    }
}
