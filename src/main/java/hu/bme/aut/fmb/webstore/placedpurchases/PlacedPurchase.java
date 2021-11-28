package hu.bme.aut.fmb.webstore.placedpurchases;

import hu.bme.aut.fmb.webstore.product.Product;
import hu.bme.aut.fmb.webstore.purchases.Purchase;

import javax.persistence.*;

@Entity
@Table(name = "placed_purchases")
public class PlacedPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    int amount;

    public PlacedPurchase() {}

    public PlacedPurchase(Purchase purchase, Product product, int amount) {
        this.product = product;
        this.purchase = purchase;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}