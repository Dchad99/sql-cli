package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryResult;
import de.vandermeer.asciitable.AsciiTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleReportGeneratorTest {
    private static final String TABLE =
            "        ┌───────────────────────────────────────┬──────────────────────────────────────┐\n" +
            "        │id                                     │name                                  │\n" +
            "        ├───────────────────────────────────────┼──────────────────────────────────────┤\n" +
            "        │1                                      │A                                     │\n" +
            "        ├───────────────────────────────────────┼──────────────────────────────────────┤\n" +
            "        │2                                      │B                                     │\n" +
            "        └───────────────────────────────────────┴──────────────────────────────────────┘";
    private final AsciiTable generator = mock(AsciiTable.class);
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ConsoleReportGenerator();
    }

    @Test
    void shouldThrowExceptionWhenGenerateReport() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> reportGenerator.generateReport(anyList()));
        assertEquals("No data to display", exception.getMessage());
    }

    @Test
    void shouldRenderTableWithTwoColumns() {
        when(generator.render()).thenReturn(TABLE);
        reportGenerator.generateReport(buildQueryList());

        assertEquals(TABLE, generator.render());
    }

    private List<QueryResult> buildQueryList() {
        return List.of(
                new QueryResult("id", List.of("1", "2")),
                new QueryResult("name", List.of("A", "B"))
        );
    }

}