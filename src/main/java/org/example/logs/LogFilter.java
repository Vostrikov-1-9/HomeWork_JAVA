package org.example.logs;

import java.time.LocalDateTime;
import java.util.Set;

public class LogFilter {
    public enum Order { ASC, DESC }

    private LocalDateTime from;
    private LocalDateTime to;
    private Set<LogLevel> levels;
    private String messageContains;
    private Order sortOrder = Order.ASC;

    public LogFilter setFrom(LocalDateTime from) { this.from = from; return this; }
    public LogFilter setTo(LocalDateTime to) { this.to = to; return this; }
    public LogFilter setLevels(Set<LogLevel> levels) { this.levels = levels; return this; }
    public LogFilter setMessageContains(String messageContains) { this.messageContains = messageContains; return this; }
    public LogFilter setSortOrder(Order sortOrder) { this.sortOrder = sortOrder; return this; }

    public LocalDateTime getFrom() { return from; }
    public LocalDateTime getTo() { return to; }
    public Set<LogLevel> getLevels() { return levels; }
    public String getMessageContains() { return messageContains; }
    public Order getSortOrder() { return sortOrder; }
}
