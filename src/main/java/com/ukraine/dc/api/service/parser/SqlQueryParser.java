package com.ukraine.dc.api.service.parser;

import com.ukraine.dc.api.exception.ValidationException;
import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.SqlCommand;

public class SqlQueryParser implements QueryParser {

    public Query parseQuery(String queryString) {
        validateQuery(queryString);
        String queryType = queryString.split("\\s")[0];
        if (queryType.equalsIgnoreCase(SqlCommand.SELECT.getSqlCommand())) {
            return new Query(queryString, SqlCommand.SELECT);
        }
        return new Query(queryString, SqlCommand.getQueryCommand(queryType));
    }

    private void validateQuery(String query) {
        if (!query.endsWith(";")) {
            throw new ValidationException("Query is not valid");
        }
    }

}
