package hu.bme.aut.fmb.webstore.purchases;

import hu.bme.aut.fmb.webstore.placedpurchases.PlacedPurchase;
import hu.bme.aut.fmb.webstore.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @OneToMany(mappedBy = "purchase")
    List<PlacedPurchase> placedPurchases=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "username")
    User user;

    public Purchase() {}

    public Purchase(User user){
        this.user=user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
