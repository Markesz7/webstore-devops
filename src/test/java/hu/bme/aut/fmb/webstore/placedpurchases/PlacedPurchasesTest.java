package hu.bme.aut.fmb.webstore.placedpurchases;
import hu.bme.aut.fmb.webstore.product.Product;
import hu.bme.aut.fmb.webstore.purchases.Purchase;
import hu.bme.aut.fmb.webstore.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlacedPurchasesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlacedPurchaseRepository placedPurchaseRepository;

    @Test
    public void getPlacedPurchasesTest() {
        // given
        PlacedPurchase placedPurchase = new PlacedPurchase();
        entityManager.persist(placedPurchase);
        entityManager.flush();


        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getPurchase())
                .isEqualTo(placedPurchase.getPurchase()));
    }
    @Test
    public void getIDPlacedPurchasesTest() {
        // given
        Product product=new Product("teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        entityManager.persist(product);
        entityManager.flush();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();
        entityManager.persist(placedPurchase);
        entityManager.flush();


        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getId())
                .isEqualTo(placedPurchase.getId()));
    }
    @Test
    public void getProductTest() {
        // given
        Product product=new Product("teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        entityManager.persist(product);
        entityManager.flush();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();
        entityManager.persist(placedPurchase);
        entityManager.flush();


        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getProduct())
                .isEqualTo(placedPurchase.getProduct()));
    }
    @Test
    public void getAmountTest() {
        // given
        Product product=new Product("teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        entityManager.persist(product);
        entityManager.flush();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();
        entityManager.persist(placedPurchase);
        entityManager.flush();


        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getAmount())
                .isEqualTo(placedPurchase.getAmount()));
    }

    @Test
    public void getPlacedPurchasesTest_set() {
        // given
        PlacedPurchase placedPurchase = new PlacedPurchase();
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        placedPurchase.setPurchase(purchase);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();
        entityManager.persist(placedPurchase);
        entityManager.flush();



        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getPurchase())
                .isEqualTo(placedPurchase.getPurchase()));
    }

    @Test
    public void getProductTest_set() {
        // given
        Product product=new Product("teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        Product newproduct=new Product("teszt2","man","desc",1);
        placedPurchase.setProduct(newproduct);
        entityManager.persist(product);
        entityManager.flush();
        entityManager.persist(newproduct);
        entityManager.flush();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();
        entityManager.persist(placedPurchase);
        entityManager.flush();


        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getProduct())
                .isEqualTo(placedPurchase.getProduct()));
    }
    @Test
    public void getAmountTest_set() {
        // given
        Product product=new Product("teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        placedPurchase.setAmount(5);
        entityManager.persist(product);
        entityManager.flush();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();
        entityManager.persist(placedPurchase);
        entityManager.flush();


        // when
        Optional<PlacedPurchase> foundopt = placedPurchaseRepository.findById(placedPurchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getAmount())
                .isEqualTo(placedPurchase.getAmount()));
    }


}
