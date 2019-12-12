package com.rohov.tagsoft.configuration.db;

import com.rohov.tagsoft.model.Country;

public class DBContextHolder {

    private static final ThreadLocal<DBType> contextHolder = new ThreadLocal<>();

    public static void setCurrentDB(DBType type) {
        contextHolder.set(type);
    }

    public static void setCurrentDB(String country) {
        for (Country value : Country.values()) {
            if (value.toString().equalsIgnoreCase(country)) {
                switch (value) {
                    case USA: {
                        contextHolder.set(DBType.USA);
                        break;
                    }
                    case Canada: {
                        contextHolder.set(DBType.CANADA);
                    }
                    default: {
                        contextHolder.set(DBType.DEFAULT);
                    }
                }
            }
        }

    }

    public static DBType getCurrentDB() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
