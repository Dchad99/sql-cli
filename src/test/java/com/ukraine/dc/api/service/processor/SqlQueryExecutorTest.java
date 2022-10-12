package com.ukraine.dc.api.service.processor;

import com.ukraine.dc.api.db.ConnectionFactory;
import com.ukraine.dc.api.db.DataSource;
import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SqlQueryExecutorTest {
    private final DataSource factory = mock(ConnectionFactory.class);
    private final Connection connection = mock(Connection.class);
    private final Statement statement = mock(Statement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private QueryExecutor executor;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        when(factory.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
        executor = new SqlQueryExecutor(factory);
    }

    @Test
    @SneakyThrows
    void testExecuteMethod() {
        var query = buildQuery();
        when(statement.executeUpdate(query.getQuery())).thenReturn(1);
        executor.execute(query);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(statement).executeUpdate(captor.capture());
        var executedQuery = captor.getValue();
        assertEquals(query.getQuery(), executedQuery);
    }

    @Test
    @SneakyThrows
    void testExecuteQuery() {
        var query = buildQuery();
        when(statement.executeQuery(query.getQuery())).thenReturn(resultSet);
        executor.executeQuery(query);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(statement).executeQuery(captor.capture());
        var a = captor.getValue();
        assertEquals(query.getQuery(), captor.getValue());
    }

    private Query buildQuery() {
        Query query = new Query();
        query.setQuery("SELECT * FROM test.company;");
        query.setQueryType("SELECT");
        return query;
    }

}