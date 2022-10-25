package com.ukraine.dc.api.service.processor;

import com.ukraine.dc.api.db.DataSource;
import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryColumnResult;

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
    public QueryColumnResult execute(Query query) {
        try (var connection = factory.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(query.getQuery())) {
            return getQueries(resultSet);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int executeUpdate(Query query) {
        try (var connection = factory.getConnection();
             var statement = connection.createStatement()) {
            return statement.executeUpdate(query.getQuery());
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private QueryColumnResult getQueries(ResultSet resultSet) throws SQLException {
        var columnNames = getColumnNames(resultSet.getMetaData());
        var rows = new ArrayList<List<String>>();
        if (resultSet != null) {
            while (resultSet.next()) {
                var row = new ArrayList<String>();
                for (String column : columnNames) {
                    row.add(resultSet.getString(column));
                }
                rows.add(row);
            }
        }
        return QueryColumnResult.builder()
                .columnNames(columnNames)
                .data(rows)
                .build();
    }

    private List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
        var columns = new ArrayList<String>();
        final var columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columns.add(metaData.getColumnName(i));
        }
        return columns;
    }

}
