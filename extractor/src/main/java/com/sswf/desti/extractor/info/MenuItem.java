package com.sswf.desti.extractor.info;

/**
 * @author Alexey Grigorev
 */
public class MenuItem {

    private final String category;
    private final String name;
    private final String price;

    public MenuItem(String category, String name, String price) {
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

}
