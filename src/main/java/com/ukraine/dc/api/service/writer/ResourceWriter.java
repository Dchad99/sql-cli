package com.ukraine.dc.api.service.writer;

import lombok.SneakyThrows;

import java.io.*;
import java.time.LocalDateTime;

public class ResourceWriter implements ItemWriter {

    @Override
    @SneakyThrows
    public void saveReport(String destinationPath, String content) {
        String filePath = "/report_" + LocalDateTime.now().getSecond() + ".html";
        File file = new File(destinationPath + filePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (Writer writer = new BufferedWriter(new FileWriter((file)))) {
            writer.write(content);
        } catch (IOException exception) {
            throw new RuntimeException("Couldn't save report", exception);
        }
    }


}
