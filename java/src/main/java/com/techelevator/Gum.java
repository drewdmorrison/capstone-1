package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Item {

    public Gum(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    public String getSound() {
        return "Chew Chew, Yum!";
    }
}
