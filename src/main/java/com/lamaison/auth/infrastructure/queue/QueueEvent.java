package com.lamaison.auth.infrastructure.queue;

import java.time.LocalDateTime;

public class QueueEvent {

    private String tipo;
    private String payload;
    private LocalDateTime timestamp;

    public QueueEvent() {}

    public QueueEvent(String tipo, String payload, LocalDateTime timestamp) {
        this.tipo = tipo;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
