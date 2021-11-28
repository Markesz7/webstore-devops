package hu.bme.aut.fmb.webstore.product;

import com.sun.istack.NotNull;
import hu.bme.aut.fmb.webstore.placedpurchases.PlacedPurchase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) @NotNull
    private Long id;
    private String Name;
    private String Manufacturer;
    private String Description;
    private int Price;
    @OneToMany(mappedBy = "product")
    List<PlacedPurchase> placedPurchases=new ArrayList<>();


    public Product() { }

    public Product(Long id,String name,String Manufacturer,String Description,int price) {
        this.id=id;
        this.Name=name;
        this.Manufacturer=Manufacturer;
        this.Description=Description;
        this.Price=price;
    }
    public Product(String name,String Manufacturer,String Description,int price) {
        this.Name=name;
        this.Manufacturer=Manufacturer;
        this.Description=Description;
        this.Price=price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

}
