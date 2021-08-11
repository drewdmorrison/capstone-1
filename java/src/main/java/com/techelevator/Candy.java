package com.techelevator;


import java.math.BigDecimal;

public class Candy extends Item {

    public Candy(String slot, String name, BigDecimal price) {
        super(slot, name, price);
    }

    public String getSound() {
        return "Munch Munch, Yum!";
    }
}
