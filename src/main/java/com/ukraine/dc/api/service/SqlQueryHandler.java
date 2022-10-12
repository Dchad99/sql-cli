package com.ukraine.dc.api.service;

import com.ukraine.dc.api.model.Query;
import com.ukraine.dc.api.model.QueryResult;
import com.ukraine.dc.api.model.SqlCommand;
import com.ukraine.dc.api.service.parser.QueryParser;
import com.ukraine.dc.api.service.processor.QueryExecutor;
import com.ukraine.dc.api.service.reports.ReportGenerator;

import java.util.List;

public class SqlQueryHandler implements QueryHandler {
    private static final String QUERY_MESSAGE = "The quantity of affected rows: %s";
    private final QueryParser queryParser;
    private final QueryExecutor queryExecutor;
    private final ReportGenerator consoleReportGenerator;
    private final ReportGenerator htmlReportGenerator;

    public SqlQueryHandler(QueryParser queryParser, QueryExecutor executor,
                           ReportGenerator console, ReportGenerator html) {
        this.queryParser = queryParser;
        this.queryExecutor = executor;
        this.consoleReportGenerator = console;
        this.htmlReportGenerator = html;
    }

    @Override
    public void handleQuery(String queryString) {
        Query query = queryParser.parseQuery(queryString);

        if (!SqlCommand.getQueryCommand(query.getQueryType()).isResultContainsData()) {
            int affectedRows = queryExecutor.execute(query);
            System.out.println(String.format(QUERY_MESSAGE, affectedRows));
        } else {
            List<QueryResult> data = queryExecutor.executeQuery(query);
            consoleReportGenerator.generateReport(data);
            htmlReportGenerator.generateReport(data);
        }
    }


}
