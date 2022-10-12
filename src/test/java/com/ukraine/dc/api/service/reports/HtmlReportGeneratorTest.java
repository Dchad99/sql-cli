package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryResult;
import com.ukraine.dc.api.service.writer.ItemWriter;
import com.ukraine.dc.api.service.writer.ResourceWriter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HtmlReportGeneratorTest {
    private static final String HTML_REPORT_CONTENT = " <!doctype html>\n" +
            "                <html lang=\"en\">\n" +
            "                <head>\n" +
            "                <title>Second</title>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                <table><tr><th>id</th><th>name</th></tr><tr><td>1</td><td>A</td></tr><tr><td>2</td><td>B</td></tr> </table>\n" +
            "                    </body>\n" +
            "                </html>";
    private static final String EMPTY_TABLE_REPORT = " <!doctype html>\n" +
            "                <html lang=\"en\">\n" +
            "                <head>\n" +
            "                <title>Second</title>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                <table> </table>\n" +
            "                    </body>\n" +
            "                </html>";
    private final ItemWriter writer = mock(ResourceWriter.class);
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new HtmlReportGenerator(writer);
    }

    @Test
    @SneakyThrows
    void shouldGenerateReport() {
        reportGenerator.generateReport(buildQueryList());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(writer).saveReport(any(), argumentCaptor.capture());

        var content = argumentCaptor.getValue();
        assertEquals(HTML_REPORT_CONTENT, content);
    }

    @Test
    @SneakyThrows
    void test() {
        reportGenerator.generateReport(Collections.emptyList());

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(writer).saveReport(any(), argumentCaptor.capture());

        var content = argumentCaptor.getValue();
        assertEquals(EMPTY_TABLE_REPORT, content);
    }


    private List<QueryResult> buildQueryList() {
        return List.of(
                new QueryResult("id", List.of("1", "2")),
                new QueryResult("name", List.of("A", "B"))
        );
    }

}

