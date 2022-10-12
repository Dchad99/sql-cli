package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryResult;
import com.ukraine.dc.api.service.writer.ItemWriter;
import lombok.SneakyThrows;

import java.util.List;

public class HtmlReportGenerator implements ReportGenerator {
    private static final String REPORT_FOLDER = "src/main/resources/reports";
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
    private final ItemWriter writer;

    public HtmlReportGenerator(ItemWriter writer) {
        this.writer = writer;
    }

    @SneakyThrows
    @Override
    public void generateReport(List<QueryResult> data) {
        String content = REPORT_HEADER + proceedTableHeaders(data) + proceedColumnData(data) + REPORT_FOOTER;
        writer.saveReport(REPORT_FOLDER, content);
    }

    private String proceedTableHeaders(List<QueryResult> rows) {
        StringBuilder builder = new StringBuilder();
        if(!rows.isEmpty()) {
            builder.append("<tr>");
            for (QueryResult row : rows) {
                builder.append("<th>").append(row.getColumnName()).append("</th>");
            }
            builder.append("</tr>");
        }
        return builder.toString();
    }

    private String proceedColumnData(List<QueryResult> rows) {
        StringBuilder builder = new StringBuilder();

        int size = !rows.isEmpty() ? rows.get(0).getRows().size() : 0;

        int currentRowIndex = 0;
        if(size != 0) {
            while (currentRowIndex != size) {
                builder.append("<tr>");
                for (QueryResult row : rows) {
                    builder.append("<td>");
                    builder.append(row.getRows().get(currentRowIndex));
                    builder.append("</td>");
                }
                builder.append("</tr>");
                currentRowIndex++;
            }
        }
        return builder.toString();
    }

}
