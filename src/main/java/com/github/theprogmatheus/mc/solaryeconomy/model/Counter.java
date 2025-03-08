package com.github.theprogmatheus.mc.solaryeconomy.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@DatabaseTable(tableName = "seco_counter")
public class Counter {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private int count;

    public int increment() {
        return this.count++;
    }
}
