package com.ukraine.dc.api.service.writer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ReportsWriter implements ItemWriter {
    private final String destinationPath;

    @Override
    @SneakyThrows
    public String saveReport(String content) {
        String filePath = "\\report_" + LocalDateTime.now().hashCode() + ".html";
        var rootPath = Paths.get(destinationPath).toAbsolutePath().toFile();
        var file = new File(rootPath, filePath);
        try (Writer writer = new BufferedWriter(new FileWriter((file)))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save report", e);
        }
        return file.getPath();
    }


}
