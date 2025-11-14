package com.redeskyller.bukkit.solaryeconomy.util;

import lombok.Data;

@Data
public class IntFlag {
    private int flags;

    public IntFlag(int flags) {
        this.flags = flags;
    }

    public boolean hasFlag(int flag) {
        return (flags & flag) == flag;
    }

    public void addFlag(int flag) {
        this.flags |= flag;
    }

    public void removeFlag(int flag) {
        this.flags &= ~flag;
    }
}