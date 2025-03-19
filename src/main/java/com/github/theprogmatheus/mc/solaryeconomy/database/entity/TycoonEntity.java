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
@DatabaseTable(tableName = "tycoon")
public class TycoonEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private long accountId;

    @DatabaseField
    private BigDecimal balance;

    @DatabaseField
    private long timestamp;

    @DatabaseField
    private long timestampEnd;

}
