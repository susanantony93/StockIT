package com.example.android_group_project;



public class ItemList {
    private int id;
    private String itemName;
    private String itemDesc;
    private int itemStock;
    private double itemPrice;
    private int itemImage;

    public ItemList(int id, String itemName, String itemDesc, int itemStock, double itemPrice, int itemImage) {
        this.id = id;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemStock = itemStock;
        this.itemPrice = itemPrice;
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