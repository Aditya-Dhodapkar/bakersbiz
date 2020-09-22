package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Ingredient implements Serializable {
    private String name;
    private int totalUnitsInInventory;
    private int pricePerUnit;
    private LocalDate lastRefreshed;
    private int noOfUnitsInLastRefresh;
    private int totalUnitsOrderedSoFar;
    private int totalSpent;
    private int thresholdsForRefresh;
}
