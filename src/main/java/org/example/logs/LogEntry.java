package org.example.logs;

import java.time.LocalDateTime;

public class LogEntry {
    private LocalDateTime timestamp;
    private LogLevel level;
    private String message;

    public LogEntry(LocalDateTime timestamp, LogLevel level, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public LogLevel getLevel() { return level; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return timestamp + " [" + level + "] " + message;
    }
}
