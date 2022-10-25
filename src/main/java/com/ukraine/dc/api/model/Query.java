package com.ukraine.dc.api.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Query {
    private final String query;
    private final SqlCommand command;
}
