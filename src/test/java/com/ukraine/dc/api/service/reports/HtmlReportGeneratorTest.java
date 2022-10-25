package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryColumnResult;
import com.ukraine.dc.api.service.writer.ItemWriter;
import com.ukraine.dc.api.service.writer.ReportsWriter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HtmlReportGeneratorTest {
    private static final String HTML_REPORT_CONTENT = " <!doctype html>\n" +
            "                <html lang=\"en\">\n" +
            "                <head>\n" +
            "                <title>Second</title>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                <table><tr><th>id</th><th>name</th></tr><tr><th>1</th><th>A</th></tr><tr><th>2</th><th>B</th></tr> </table>\n" +
            "                    </body>\n" +
            "                </html>";
    private static final String EMPTY_TABLE_REPORT = " <!doctype html>\n" +
            "                <html lang=\"en\">\n" +
            "                <head>\n" +
            "                <title>Second</title>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                <table><tr><th>id</th><th>name</th></tr> </table>\n" +
            "                    </body>\n" +
            "                </html>";
    private final ItemWriter writer = mock(ReportsWriter.class);
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new HtmlReportGenerator(writer::saveReport);
    }

    @Test
    @SneakyThrows
    void shouldGenerateHtmlReport_whenTableDataIsNotEmpty() {
        reportGenerator.generateReport(buildQueryWithData());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(writer).saveReport(argumentCaptor.capture());

        var content = argumentCaptor.getValue();
        assertEquals(HTML_REPORT_CONTENT, content);
    }

    @Test
    @SneakyThrows
    void shouldGenerateHtmlReport_whenTableDataIsEmpty() {
        reportGenerator.generateReport(buildQueryWithoutData());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(writer).saveReport(argumentCaptor.capture());

        var content = argumentCaptor.getValue();
        assertEquals(EMPTY_TABLE_REPORT, content);
    }


    private QueryColumnResult buildQueryWithData() {
        return QueryColumnResult.builder()
                .columnNames(List.of("id", "name"))
                .data(List.of(List.of("1", "A"), List.of("2", "B")))
                .build();
    }

    private QueryColumnResult buildQueryWithoutData() {
        return QueryColumnResult.builder()
                .columnNames(List.of("id", "name"))
                .build();
    }

}

