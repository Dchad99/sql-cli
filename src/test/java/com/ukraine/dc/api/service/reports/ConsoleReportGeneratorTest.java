package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryColumnResult;
import de.vandermeer.asciitable.AsciiTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleReportGeneratorTest {
    private static final String TABLE_WITH_DATA = """
               ┌───────────────────────────────────────┬──────────────────────────────────────┐
               │id                                     │name                                  │
               ├───────────────────────────────────────┼──────────────────────────────────────┤
               │1                                      │A                                     │
               ├───────────────────────────────────────┼──────────────────────────────────────┤
               │2                                      │B                                     │
               └───────────────────────────────────────┴──────────────────────────────────────┘
               """;

    private static final String TABLE_WITHOUT_DATA = """
               ┌───────────────────────────────────────┬──────────────────────────────────────┐
               │id                                     │name                                  │
               └───────────────────────────────────────┴──────────────────────────────────────┘
            """;

    private final AsciiTable generator = mock(AsciiTable.class);
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ConsoleReportGenerator();
    }

    @Test
    void shouldDisplayOnlyColumnNames_whenTableIsEmpty() {
        when(generator.render()).thenReturn(TABLE_WITHOUT_DATA);
        reportGenerator.generateReport(buildQueryWithoutData());

        assertEquals(TABLE_WITHOUT_DATA, generator.render());
    }

    @Test
    void shouldRenderTableWithTwoColumnsAndTwoRecords() {
        when(generator.render()).thenReturn(TABLE_WITH_DATA);
        reportGenerator.generateReport(buildQueryWithData());

        assertEquals(TABLE_WITH_DATA, generator.render());
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