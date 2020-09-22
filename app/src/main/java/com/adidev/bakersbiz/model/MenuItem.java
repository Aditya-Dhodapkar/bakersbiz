package com.adidev.bakersbiz.model;
import java.io.Serializable;
import java.util.List;

public class MenuItem implements Serializable {
    private String name;
    private float price;
    private List<RecipeIngredient> ingredients;
    private String menuItemImageID;
}
