package com.ukraine.dc;

import com.ukraine.dc.api.db.ConnectionFactory;
import com.ukraine.dc.api.db.PropertiesReader;
import com.ukraine.dc.api.service.QueryHandler;
import com.ukraine.dc.api.service.SqlQueryHandler;
import com.ukraine.dc.api.service.parser.SqlQueryParser;
import com.ukraine.dc.api.service.processor.SqlQueryExecutor;
import com.ukraine.dc.api.service.reports.ConsoleReportGenerator;
import com.ukraine.dc.api.service.reports.HtmlReportGenerator;
import com.ukraine.dc.api.service.reports.ReportGenerator;
import com.ukraine.dc.api.service.writer.ReportsWriter;

import java.util.Scanner;

public class SqlCommandLine {
    public static void main(String[] args) {
        var propsReader = new PropertiesReader();
        var props = propsReader.readPropertiesFile();
        var reportFolder = props.getProperty("reports.path");

        var factory = new ConnectionFactory(props);
        var parser = new SqlQueryParser();
        var executor = new SqlQueryExecutor(factory);
        var writer = new ReportsWriter(reportFolder);
        var consoleReport = new ConsoleReportGenerator();
        var htmlReport = new HtmlReportGenerator(content -> {
            var reportPath = writer.saveReport(content);
            System.out.println("Report is available by path: " + reportPath);
        });

        QueryHandler queryHandler = new SqlQueryHandler(parser, executor, new ReportGenerator[]{consoleReport, htmlReport});
        System.out.println("Enter a query: ");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String query = scanner.nextLine();
            queryHandler.handleQuery(query);
        }

    }
}
