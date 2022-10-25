package com.ukraine.dc.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryColumnResult {
    private List<String> columnNames;
    private List<List<String>> data;
}

