package hu.bme.aut.fmb.webstore.Storage;

import hu.bme.aut.fmb.webstore.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "Storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Product product;
    private int quantity;

    public Storage(){

    }

    public Storage(Long id, Product product, int quantity){
        this.quantity =quantity;
        this.product=product;
        this.id=id;
    }

    public Storage(Product product) {
        this.product = product;
        this.quantity = 0;
    }

    public Storage(Long prodId, int quantity) {

        this.quantity = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity>=0)
            this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if(product!=null)
            this.product = product;
    }
}