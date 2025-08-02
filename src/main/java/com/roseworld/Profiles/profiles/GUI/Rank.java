package com.roseworld.Profiles.profiles.GUI;

public enum Rank {
    OWNER("\uDAFF\uDF60\uEA51"),
    ADMIN("\uDAFF\uDF60\uEA52"),
    MOD("\uDAFF\uDF66\uEA53"),
    ARTIST("\uDAFF\uDF5B\uEA54"),
    DEFAULT("\uDAFF\uDF5A\uEA55");

    public final String prefix;
    Rank(String prefix) {
        this.prefix = prefix;
    }
}
