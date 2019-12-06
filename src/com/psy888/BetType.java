package com.psy888;

import java.util.Objects;

public class BetType {
    private String description;
    private int ratio;

    public BetType(String description, int ratio) {
        this.description = description;
        this.ratio = ratio;
    }

    public int getRatio() {
        return ratio;
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().contentEquals(this.toString());
//        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
