package org.example.logs;

import java.time.LocalDateTime;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        LogAnalyzer analyzer = new LogAnalyzer();

        LogFilter filter = new LogFilter()
                .setLevels(Set.of(LogLevel.ERROR, LogLevel.WARN))
                .setFrom(LocalDateTime.of(2024, 1, 1, 0, 0))
                .setSortOrder(LogFilter.Order.DESC);

        System.out.println("Запуск анализатора...");
        
        // Анализируем архив, который лежит в корне проекта
        analyzer.analyze("logs.zip", filter);
    }
}
