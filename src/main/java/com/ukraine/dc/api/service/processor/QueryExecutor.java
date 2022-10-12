package com.ukraine.dc.api.service.processor;

import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryResult;

import java.util.List;

public interface QueryExecutor {
    List<QueryResult> executeQuery(Query query);

    int execute(Query query);
}
