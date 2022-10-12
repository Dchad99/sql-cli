package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryResult;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.List;

public class ConsoleReportGenerator implements ReportGenerator {
    private final AsciiTable tableGenerator;

    public ConsoleReportGenerator() {
        this.tableGenerator = new AsciiTable();
    }

    @Override
    public void generateReport(List<QueryResult> data) {
        displayColumnHeaders(data);
        proceedTableData(data);
        if (data.isEmpty()) {
            throw new RuntimeException("No data to display");
        }
        System.out.println(tableGenerator.render());
    }

    private void displayColumnHeaders(List<QueryResult> queryResults) {
        tableGenerator.addRule();
        var columnNames = new ArrayList<>();
        for (QueryResult queryResult : queryResults) {
            columnNames.add(queryResult.getColumnName());
        }
        tableGenerator.addRow(columnNames);
    }

    private void proceedTableData(List<QueryResult> queryResults) {
        tableGenerator.addRule();

        int size = 0;
        int current = 0;
        for (QueryResult queryResult : queryResults) {
            size = queryResult.getRows().size();
        }

        if (size != 0) {
            while (current != size) {
                var res = new ArrayList<>();
                for (QueryResult queryResult : queryResults) {
                    res.add(queryResult.getRows().get(current));
                }
                tableGenerator.addRow(res);
                tableGenerator.addRule();
                current++;
            }
        }
    }
}
