package com.adidev.bakersbiz.model;
import java.io.Serializable;
import java.util.List;

public class MenuItem implements Serializable {
    public MenuItem(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    private String name;
    private int price;
    private String description;
    private List<RecipeIngredient> ingredients;
    private String menuItemImageID;
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
}
