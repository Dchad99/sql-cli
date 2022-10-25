package com.ukraine.dc.api.service.reports;

import com.ukraine.dc.api.model.QueryColumnResult;

public interface ReportGenerator {
    void generateReport(QueryColumnResult data);
}
