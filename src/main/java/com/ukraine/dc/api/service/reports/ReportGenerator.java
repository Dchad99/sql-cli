package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryResult;
import java.util.List;

public interface ReportGenerator {
    void generateReport(List<QueryResult> data);
}
