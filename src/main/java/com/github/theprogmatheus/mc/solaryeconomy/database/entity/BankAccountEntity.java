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
@DatabaseTable(tableName = "seco_bank_account")
public class BankAccountEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String bank;

    @DatabaseField
    private String owner;

    @DatabaseField
    private double balance;


}
