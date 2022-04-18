package domain;

import java.io.Serializable;

public class Product implements Identifiable<Integer>, Serializable {
    private Integer ID;
    private String name,provider;
    int quantity;
    int price;
    public Integer getID(){
        return ID;
    }
    public void setID(Integer id){
        ID = id;
    }

    public Product(String name, String provider, int quantity, int price) {
        this.name = name;
        this.provider = provider;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
