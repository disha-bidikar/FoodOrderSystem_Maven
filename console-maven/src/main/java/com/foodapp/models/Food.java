package com.foodapp.models;

import java.math.BigDecimal;

public class Food {
    private int foodId;
    private String name;
    private String description;
    private BigDecimal price;

    public Food(int foodId, String name, String description, BigDecimal price) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getFoodId() { return foodId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }

    @Override
    public String toString() {
        return foodId + " | " + name + " | " + description + " | $" + price;
    }
}
