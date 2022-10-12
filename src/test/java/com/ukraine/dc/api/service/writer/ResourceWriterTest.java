package com.ukraine.dc.api.service.writer;

import com.ukraine.dc.manager.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ResourceWriterTest {
    private static final String TEST_CONTENT = "Test content";
    private static final String REPORT_FOLDER = "src/test/resources/reports";
    private final ItemWriter writer = new ResourceWriter();
    private final File reports = new File(REPORT_FOLDER);

    @BeforeEach
    void setUp() {
        reports.mkdir();
    }

    @AfterEach
    void tearDown() {
        FileManager.deleteFiles(reports);
    }

    @Test
    void testSaveReport() throws IOException{
        assertEquals(0, reports.list().length);
        writer.saveReport(REPORT_FOLDER, TEST_CONTENT);
        assertNotEquals(0, reports.list().length);
    }
        

}