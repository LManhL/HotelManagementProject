package com.example.hotelmangementproject.models;

public class MenuBill extends Menu{
    private int count;
    public MenuBill(){

    }

    public MenuBill(Menu menu, int count){
        super(menu.getId(), menu.getImportPrice(), menu.getName(),menu.getPrice(),menu.getType());
        this.count = count;
    }

    public MenuBill(String id, double importPrice, String name, double price, String type, int count) {
        super(id, importPrice, name, price, type);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + getId() + '\'' +
                ", importPrice=" + getImportPrice() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", count='" + getCount() + '\'' +
                '}';
    }
}
