package com.ukraine.dc.api.service.parser;

import com.ukraine.dc.api.model.Query;

public interface QueryParser {
    Query parseQuery(String query);
}
