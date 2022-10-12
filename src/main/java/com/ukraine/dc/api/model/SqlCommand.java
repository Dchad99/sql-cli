package com.ukraine.dc.api.model;

import java.util.Arrays;

public enum SqlCommand {
    CREATE("CREATE", false),
    ALTER("ALTER", false),
    TRUNCATE("TRUNCATE", false),
    DROP("DROP", false),
    RENAME("RENAME", false),
    SELECT("SELECT", true),
    INSERT("INSERT", false),
    UPDATE("UPDATE", false),
    DELETE("DELETE", false);

    private final String command;
    private final boolean isResultContainsData;

    SqlCommand(String command, boolean isResultContainsData) {
        this.command = command;
        this.isResultContainsData = isResultContainsData;
    }

    public String getSqlCommand() {
        return command;
    }

    public boolean isResultContainsData() {
        return isResultContainsData;
    }

    public static SqlCommand getQueryCommand(String sqlCommand) {
        return Arrays.stream(SqlCommand.values())
                .filter(s -> s.command.equalsIgnoreCase(sqlCommand))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The SqlCommand " + sqlCommand + " doesn't exist"));
    }
}

