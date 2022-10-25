package com.ukraine.dc.api.service;

import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryColumnResult;
import com.ukraine.dc.api.model.SqlCommand;
import com.ukraine.dc.api.service.parser.QueryParser;
import com.ukraine.dc.api.service.parser.SqlQueryParser;
import com.ukraine.dc.api.service.processor.QueryExecutor;
import com.ukraine.dc.api.service.processor.SqlQueryExecutor;
import com.ukraine.dc.api.service.reports.ConsoleReportGenerator;
import com.ukraine.dc.api.service.reports.HtmlReportGenerator;
import com.ukraine.dc.api.service.reports.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SqlQueryHandlerTest {
    private final QueryParser queryParser = mock(SqlQueryParser.class);
    private final QueryExecutor queryExecutor = mock(SqlQueryExecutor.class);
    private final ReportGenerator consoleGenerator = mock(ConsoleReportGenerator.class);
    private final ReportGenerator htmlGenerator = mock(HtmlReportGenerator.class);

    private QueryHandler queryHandler;

    @BeforeEach
    void setUp() {
        queryHandler = new SqlQueryHandler(queryParser, queryExecutor,
                new ReportGenerator[]{consoleGenerator, htmlGenerator});
    }

    @Test
    void testHandleQuery() {
        var query = buildQuery();
        var queryColumnResult = buildQueryWithData();
        when(queryParser.parseQuery(any())).thenReturn(query);
        when(queryExecutor.execute(query)).thenReturn(queryColumnResult);
        queryHandler.handleQuery(query.getQuery());

        verify(queryParser).parseQuery(any());
        verify(queryExecutor).execute(query);
        ArgumentCaptor<QueryColumnResult> resultArgumentCaptor = ArgumentCaptor.forClass(QueryColumnResult.class);
        verify(consoleGenerator).generateReport(resultArgumentCaptor.capture());
        assertEquals(queryColumnResult, resultArgumentCaptor.getValue());
    }

    @Test
    void shouldReturnAffectedRows_whenQueryExecuted() {
        var query = buildUpdateQuery();
        when(queryParser.parseQuery(any())).thenReturn(query);
        when(queryExecutor.executeUpdate(query)).thenReturn(1);
        queryHandler.handleQuery(query.getQuery());

        verify(queryParser).parseQuery(any());
        verify(queryExecutor).executeUpdate(query);
    }

    private QueryColumnResult buildQueryWithData() {
        return QueryColumnResult.builder()
                .columnNames(List.of("id", "name"))
                .data(List.of(List.of("1", "A"), List.of("2", "B")))
                .build();
    }

    private Query buildQuery() {
        return new Query("SELECT * FROM test.company;", SqlCommand.SELECT);
    }

    private Query buildUpdateQuery() {
        return new Query("UPDATE test.company SET age=1 where id=1;", SqlCommand.UPDATE);
    }

}