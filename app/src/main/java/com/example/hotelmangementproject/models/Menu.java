package com.example.hotelmangementproject.models;

public class Menu {
    private String id;
    private double importPrice;
    private String name;
    private double price;
    private String type;

    public Menu(){
        this.id = "";
        this.importPrice = 0;
        this.name = "";
        this.price = 0;
        this.type = "";
    }
    public Menu(String id, double importPrice, String name, double price, String type) {
        this.id = id;
        this.importPrice = importPrice;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
