package com.ukraine.dc.api.service.processor;

import com.ukraine.dc.api.db.DataSource;
import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryResult;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlQueryExecutor implements QueryExecutor {
    private final DataSource factory;

    public SqlQueryExecutor(DataSource dataSource) {
        this.factory = dataSource;
    }

    @Override
    public List<QueryResult> executeQuery(Query query) {
        try (var connection = factory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(query.getQuery())) {

            List<String> columns = getColumnNames(resultSet.getMetaData());
            return getQueries(resultSet, columns);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int execute(Query query) {
        try (var connection = factory.getConnection();
             var statement = connection.createStatement()) {
            return statement.executeUpdate(query.getQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<QueryResult> getQueries(ResultSet resultSet, List<String> columns) throws SQLException {
        List<QueryResult> rows = new ArrayList<>();
        for (String columnName : columns) {
            rows.add(new QueryResult(columnName, new ArrayList<>()));
        }

        while (resultSet.next()) {
            for (QueryResult row : rows) {
                row.getRows().add(resultSet.getObject(row.getColumnName()));
            }
        }
        return rows;
    }

    private List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        List<String> columns = new ArrayList<>();
        if (metaData != null && metaData.getColumnCount() != 0) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnName(i));
            }
        }
        return columns;
    }

}
