package com.ukraine.dc.api.db;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection();
}
