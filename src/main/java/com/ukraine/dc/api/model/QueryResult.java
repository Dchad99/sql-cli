package com.ukraine.dc.api.model;

import java.util.List;

public class QueryResult {
    private String columnName;
    private List<Object> rows;

    public QueryResult() {
    }

    public QueryResult(String columnName, List<Object> rows) {
        this.columnName = columnName;
        this.rows = rows;
    }


    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

    public String getColumnName() {
        return columnName;
    }

    public List<Object> getRows() {
        return rows;
    }
}

