package com.ukraine.dc.api.service.parser;

import com.ukraine.dc.api.exception.ValidationException;
import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.SqlCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlQueryParserTest {
    private static final String SELECT_QUERY = "select * from test.tab;";
    private static final String SQL_QUERY = "drop table test.tab;";
    private static final String INVALID_QUERY = "select * from test.tab";

    private final QueryParser parser = new SqlQueryParser();

    @Test
    void shouldReturnSelectQueryInstance_whenQueryIsValid() {
        var expectedQuery = new Query(SELECT_QUERY, SqlCommand.SELECT.getSqlCommand());
        var actual = parser.parseQuery(SELECT_QUERY);
        assertEquals(expectedQuery.getQueryType(), actual.getQueryType());
    }

    @Test
    void shouldReturnSqlQueryInstance_whenSqlQueryIsValid() {
        var expectedQuery = new Query(SQL_QUERY, SqlCommand.DROP.getSqlCommand());
        var actual = parser.parseQuery(SQL_QUERY);
        assertEquals(expectedQuery.getQueryType(), actual.getQueryType());
    }

    @Test
    void shouldThrowValidationException_whenQueryIsNotValid() {
        Exception exception = assertThrows(ValidationException.class,
                () -> parser.parseQuery(INVALID_QUERY));
        assertEquals("Query is not valid", exception.getMessage());
    }

}