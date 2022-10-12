package com.ukraine.dc.api.service.writer;

import java.io.IOException;

public interface ItemWriter {
    void saveReport(String destinationPath, String content) throws IOException;
}
