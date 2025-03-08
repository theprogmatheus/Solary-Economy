package com.github.theprogmatheus.mc.solaryeconomy.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "seco_bank")
public class BankEntity {

    public static final transient String DEFAULT_BANK_NAME = "default";
    public static final transient String DEFAULT_BANK_OWNER = "server";

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(unique = true)
    private String name;

    @DatabaseField
    private String owner;

}
