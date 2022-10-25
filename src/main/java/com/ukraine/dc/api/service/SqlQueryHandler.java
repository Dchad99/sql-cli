package com.ukraine.dc.api.service;

import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryColumnResult;
import com.ukraine.dc.api.model.SqlCommand;
import com.ukraine.dc.api.service.parser.QueryParser;
import com.ukraine.dc.api.service.processor.QueryExecutor;
import com.ukraine.dc.api.service.reports.ReportGenerator;

import java.util.Arrays;

public class SqlQueryHandler implements QueryHandler {
    private static final String QUERY_MESSAGE = "The quantity of affected rows: %s";
    private final QueryParser queryParser;
    private final QueryExecutor queryExecutor;
    private final ReportGenerator[] reports;

    public SqlQueryHandler(QueryParser queryParser, QueryExecutor executor,
                           ReportGenerator... reportGenerators) {
        this.queryParser = queryParser;
        this.queryExecutor = executor;
        this.reports = reportGenerators;
    }

    @Override
    public void handleQuery(String queryString) {
        Query query = queryParser.parseQuery(queryString);
        var queryCommand = SqlCommand.getQueryCommand(query.getCommand().getSqlCommand());
        if (queryCommand.isResultContainsData()) {
            QueryColumnResult data = queryExecutor.execute(query);
            Arrays.stream(reports).forEach(generator -> generator.generateReport(data));
        } else {
            int affectedRows = queryExecutor.executeUpdate(query);
            //TODO create PrintWriter for example that will stands fpr presenting status
            System.out.println(String.format(QUERY_MESSAGE, affectedRows));
        }
    }


}
