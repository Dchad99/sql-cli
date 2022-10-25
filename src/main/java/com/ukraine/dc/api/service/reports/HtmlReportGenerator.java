package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryColumnResult;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class HtmlReportGenerator implements ReportGenerator {
    private static final String REPORT_HEADER = " <!doctype html>\n" +
            "                <html lang=\"en\">\n" +
            "                <head>\n" +
            "                <title>Second</title>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                <table>";
    private static final String REPORT_FOOTER = " </table>\n" +
            "                    </body>\n" +
            "                </html>";
    private static final String DATA_TEMPLATE = "<th>%s</th>";
    private static final String ROW_START = "<tr>";
    private static final String ROW_END = "</tr>";
    private final Consumer<String> writer;

    public HtmlReportGenerator(Consumer<String> writer) {
        this.writer = writer;
    }

    @SneakyThrows
    @Override
    public void generateReport(QueryColumnResult data) {
        String content = REPORT_HEADER + proceedTableHeaders(data) + proceedColumnData(data) + REPORT_FOOTER;
        writer.accept(content);
    }

    private String proceedTableHeaders(QueryColumnResult rows) {
        StringBuilder builder = new StringBuilder();
        if(!rows.getColumnNames().isEmpty()) {
            builder.append(ROW_START);
            for (String row : rows.getColumnNames()) {
                builder.append(String.format(DATA_TEMPLATE, row));
            }
            builder.append(ROW_END);
        }
        return builder.toString();
    }

    private String proceedColumnData(QueryColumnResult result) {
        StringBuilder builder = new StringBuilder();

        if (Objects.nonNull(result.getData()) && !result.getData().isEmpty()) {
            for (List<String> row : result.getData()) {
                builder.append(ROW_START);
                var rowBuilder = new StringBuilder();
                for (String field : row) {
                    rowBuilder.append(String.format(DATA_TEMPLATE, field));
                }
                builder.append(rowBuilder);
                builder.append(ROW_END);
            }
        }
        return builder.toString();
    }

}
