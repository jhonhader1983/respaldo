package com.lamaison.auth.infrastructure.scheduled;

import com.lamaison.auth.infrastructure.controller.repository.PasswordResetTokenRepository;
import com.lamaison.auth.infrastructure.controller.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final PasswordResetTokenRepository tokenRepository;
    private final PedidoRepository pedidoRepository;

    public ScheduledTasks(PasswordResetTokenRepository tokenRepository,
                          PedidoRepository pedidoRepository) {
        this.tokenRepository = tokenRepository;
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Cada 5 minutos: elimina tokens de recuperación que ya expiraron o fueron usados.
     */
    @Scheduled(fixedRate = 300_000)
    public void limpiarTokensExpirados() {
        log.info("[CRON] Iniciando limpieza de tokens de recuperación expirados...");
        LocalDateTime ahora = LocalDateTime.now();

        tokenRepository.findAll()
                .filter(t -> t.isUsado() || t.getExpiracion().isBefore(ahora))
                .flatMap(t -> tokenRepository.deleteById(t.getId()))
                .count()
                .subscribe(
                        count -> log.info("[CRON] Tokens eliminados: {}", count),
                        err  -> log.error("[CRON] Error limpiando tokens: {}", err.getMessage())
                );
    }

    /**
     * Cada hora: cuenta y reporta pedidos en estado pendiente.
     */
    @Scheduled(fixedRate = 3_600_000)
    public void revisarPedidosPendientes() {
        log.info("[CRON] Revisando pedidos pendientes...");

        pedidoRepository.findAll()
                .filter(p -> "pendiente".equals(p.getEstado()))
                .count()
                .subscribe(
                        count -> log.info("[CRON] Pedidos con estado 'pendiente': {}", count),
                        err  -> log.error("[CRON] Error revisando pedidos: {}", err.getMessage())
                );
    }

    /**
     * Todos los días a las 02:00 AM: log de mantenimiento diario.
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void mantenimientoDiario() {
        log.info("[CRON] Mantenimiento diario ejecutado → {}", LocalDateTime.now());
    }
}
