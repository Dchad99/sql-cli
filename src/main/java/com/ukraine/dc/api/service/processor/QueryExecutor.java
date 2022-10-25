package com.ukraine.dc.api.service.processor;

import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryColumnResult;

public interface QueryExecutor {
    QueryColumnResult execute(Query query);

    int executeUpdate(Query query);
}
