package com.ukraine.dc.api.service.writer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReportsWriterTest {
    private static final String TEST_CONTENT = "Test content";
    private static final String REPORT_FOLDER = "/reports";
    private final ItemWriter writer = new ReportsWriter(REPORT_FOLDER);
    private final File reports = new File(REPORT_FOLDER);

    @Test
    void testSaveReport(){
        var countFiles = Objects.requireNonNull(reports.list()).length;
        var nextCount = countFiles + 1;
        writer.saveReport(TEST_CONTENT);
        assertNotEquals(nextCount, countFiles);
    }


}