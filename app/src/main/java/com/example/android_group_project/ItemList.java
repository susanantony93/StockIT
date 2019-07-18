package com.example.android_group_project;



public class ItemList {
    private int id;
    private String itemName;
    private String itemDesc;
    private int itemStock;
    private double itemPrice;
    private int itemImage;

    public ItemList() {

    }

    public ItemList(int id, String itemName, String itemDesc, int itemStock, double itemPrice, int itemImage) {
        this.id = id;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemStock = itemStock;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public void setItemStock(int itemStock) {
        this.itemStock = itemStock;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemImage(int itemImage) {
        this.itemImage = itemImage;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public int getItemStock() {
        return itemStock;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemImage() {
        return itemImage;
    }
}