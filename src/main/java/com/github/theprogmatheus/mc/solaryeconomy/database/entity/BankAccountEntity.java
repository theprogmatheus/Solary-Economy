package com.github.theprogmatheus.mc.solaryeconomy.database.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "bank_accounts")
public class BankAccountEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "bank_id")
    private BankEntity bank;

    @DatabaseField(columnName = "owner_id")
    private String ownerId;

    @DatabaseField(columnName = "owner_name")
    private String ownerName;

    @DatabaseField
    private BigDecimal balance;
}
