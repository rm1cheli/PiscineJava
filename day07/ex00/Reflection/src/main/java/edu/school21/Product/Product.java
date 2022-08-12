package edu.school21.Product;

public class Product {
    private Long id;
    private String name;
    private Long price;

    public Product(){

        this.id = 0L;
        this.name = "null";
        this.price = 0L;
    }

    public Product(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void priceIncrease(Long newPrice){
        price += 10L;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                "}";
    }
}