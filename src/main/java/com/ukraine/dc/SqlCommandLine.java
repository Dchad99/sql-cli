package com.ukraine.dc;

import com.ukraine.dc.api.db.ConnectionFactory;
import com.ukraine.dc.api.service.QueryHandler;
import com.ukraine.dc.api.service.SqlQueryHandler;
import com.ukraine.dc.api.service.parser.SqlQueryParser;
import com.ukraine.dc.api.service.processor.SqlQueryExecutor;
import com.ukraine.dc.api.service.reports.ConsoleReportGenerator;
import com.ukraine.dc.api.service.reports.HtmlReportGenerator;
import com.ukraine.dc.api.service.writer.ResourceWriter;

import java.util.Scanner;

public class SqlCommandLine {
    public static void main(String[] args) {

        var factory = new ConnectionFactory();
        var parser = new SqlQueryParser();
        var executor = new SqlQueryExecutor(factory);
        var writer = new ResourceWriter();
        var consoleReport = new ConsoleReportGenerator();
        var htmlReport = new HtmlReportGenerator(writer);

        QueryHandler queryHandler = new SqlQueryHandler(parser, executor, consoleReport, htmlReport);
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String query = scanner.nextLine();
            queryHandler.handleQuery(query);
        }
    }
}
