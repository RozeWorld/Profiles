package com.roseworld.Profiles.profiles.GUI;

public enum Rank {
    OWNER("\uDAFF\uDF8E\uEA51"),
    ADMIN("\uDAFF\uDF8E\uEA52"),
    MOD("\uDAFF\uDF8E\uEA53"),
    ARTIST("\uDAFF\uDF8E\uEA54"),
    DEFAULT("\uDAFF\uDF8E\uEA55");

    public final String prefix;
    Rank(String prefix) {
        this.prefix = prefix;
    }
}
