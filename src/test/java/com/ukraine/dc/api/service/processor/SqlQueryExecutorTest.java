package com.ukraine.dc.api.service.processor;

import com.ukraine.dc.api.db.ConnectionFactory;
import com.ukraine.dc.api.db.DataSource;
import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.SqlCommand;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SqlQueryExecutorTest {
    private final DataSource factory = mock(ConnectionFactory.class);
    private final Connection connection = mock(Connection.class);
    private final Statement statement = mock(Statement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private final ResultSetMetaData resultSetMetaData = mock(ResultSetMetaData.class);
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
    void testUpdateQuery() {
        var query = buildUpdateQuery();
        when(statement.executeUpdate(query.getQuery())).thenReturn(1);
        executor.executeUpdate(query);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(statement).executeUpdate(captor.capture());
        var executedQuery = captor.getValue();
        assertEquals(query.getQuery(), executedQuery);
    }

    @Test
    @SneakyThrows
    void testSelectQuery() {
        var query = buildSelectQuery();
        when(statement.executeQuery(query.getQuery())).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSetMetaData.getColumnCount()).thenReturn(1);
        executor.execute(query);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(statement).executeQuery(captor.capture());
        var executedQuery = captor.getValue();
        assertEquals(query.getQuery(), executedQuery);
    }

    private Query buildSelectQuery() {
        return new Query("SELECT * FROM test.company;", SqlCommand.SELECT);
    }

    private Query buildUpdateQuery() {
        return new Query("UPDATE test.company SET age=11 WHERE id=1;", SqlCommand.UPDATE);
    }

}