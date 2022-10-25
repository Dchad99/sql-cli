package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryColumnResult;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConsoleReportGenerator implements ReportGenerator {
    private final AsciiTable tableGenerator;

    public ConsoleReportGenerator() {
        this.tableGenerator = new AsciiTable();
    }

    @Override
    public void generateReport(QueryColumnResult data) {
        displayColumnHeaders(data.getColumnNames());
        proceedTableData(data);
        System.out.println(tableGenerator.render());
    }

    private void displayColumnHeaders(List<String> queryColumnResults) {
        tableGenerator.addRule();
        var columnNames = new ArrayList<>();
        columnNames.addAll(queryColumnResults);
        tableGenerator.addRow(columnNames);
    }

    private void proceedTableData(QueryColumnResult result) {
        tableGenerator.addRule();
        if (Objects.nonNull(result.getData()) && !result.getData()
                .isEmpty()) {
            for (List<String> row : result.getData()) {
                tableGenerator.addRow(row);
                tableGenerator.addRule();
            }
        }
    }
}
