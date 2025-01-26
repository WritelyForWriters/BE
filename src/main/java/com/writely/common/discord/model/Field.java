package com.writely.common.discord.model;

import lombok.Getter;

@Getter
public class Field {

    private final String name;
    private final String value;
    private final boolean inline;

    public Field(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }
}
