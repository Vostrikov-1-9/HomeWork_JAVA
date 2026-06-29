package org.example.logs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LogAnalyzer {

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart().appendPattern("[.SSS][.SS][.S]").optionalEnd()
            .toFormatter();

    public void analyze(String filePath, LogFilter filter) {
        Path path = Paths.get(filePath);

        try (Stream<String> lines = getLinesStream(path)) {
            Stream<LogEntry> stream = lines.map(this::parseLine).filter(Objects::nonNull);

            if (filter.getFrom() != null) stream = stream.filter(log -> !log.getTimestamp().isBefore(filter.getFrom()));
            if (filter.getTo() != null) stream = stream.filter(log -> !log.getTimestamp().isAfter(filter.getTo()));
            if (filter.getLevels() != null && !filter.getLevels().isEmpty()) stream = stream.filter(log -> filter.getLevels().contains(log.getLevel()));
            if (filter.getMessageContains() != null) stream = stream.filter(log -> log.getMessage().contains(filter.getMessageContains()));

            Comparator<LogEntry> comparator = Comparator.comparing(LogEntry::getTimestamp);
            if (filter.getSortOrder() == LogFilter.Order.DESC) comparator = comparator.reversed();
            stream = stream.sorted(comparator);

            List<LogEntry> results = stream.collect(Collectors.toList());

            printAnalytics(results);
            System.out.println("\n--- Результаты поиска ---");
            results.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private void printAnalytics(List<LogEntry> logs) {
        System.out.println("\n--- Аналитика ---");
        System.out.println("Общее количество логов (подходящих под фильтр): " + logs.size());
        Map<LogLevel, Long> countsByLevel = logs.stream().collect(Collectors.groupingBy(LogEntry::getLevel, Collectors.counting()));
        System.out.println("Количество по уровням:");
        countsByLevel.forEach((level, count) -> System.out.println(level + ": " + count));
    }

    private LogEntry parseLine(String line) {
        try {
            String[] parts = line.split("\\s+", 4);
            if (parts.length < 4) return null;
            LocalDateTime timestamp = LocalDateTime.parse(parts[0] + " " + parts[1], FORMATTER);
            return new LogEntry(timestamp, LogLevel.valueOf(parts[2].toUpperCase()), parts[3]);
        } catch (Exception e) { return null; }
    }

    private Stream<String> getLinesStream(Path path) throws IOException {
        if (path.toString().endsWith(".zip")) {
            Path tempFile = Files.createTempFile("extracted_log_", ".log");
            tempFile.toFile().deleteOnExit();
            try (InputStream is = Files.newInputStream(path); ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".log")) {
                        Files.copy(zis, tempFile, StandardCopyOption.REPLACE_EXISTING);
                        break;
                    }
                }
            }
            return Files.lines(tempFile);
        } else {
            return Files.lines(path);
        }
    }
}
