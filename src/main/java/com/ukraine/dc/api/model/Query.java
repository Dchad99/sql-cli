package com.ukraine.dc.api.model;

public class Query {
    private String query;
    private String queryType;

    public Query() {
    }

    public Query(String query, String queryType) {
        this.query = query;
        this.queryType = queryType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
