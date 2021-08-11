package com.techelevator;

import java.math.BigDecimal;

public class Chip extends Item {

    public Chip(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    public String getSound() {
        return "Crunch Crunch, Yum!";
    }
}
