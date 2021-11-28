package hu.bme.aut.fmb.webstore.purchases;

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
public class PurchaseTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Test
    public void getPurchasesIDTest() {
        // given

        Purchase purchase= new Purchase();
        entityManager.persist(purchase);
        entityManager.flush();


        // when
        Optional<Purchase> foundopt = purchaseRepository.findById(purchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getId())
                .isEqualTo(purchase.getId()));
    }

    @Test
    public void getPurchasesUserTest() {
        // given
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();


        // when
        Optional<Purchase> foundopt = purchaseRepository.findById(purchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getUser())
                .isEqualTo(purchase.getUser()));
    }


    @Test
    public void getPurchasesUserTest_set() {
        // given
        User user=new User();
        user.setUsername("tesztuser");
        User newUser=new User();
        newUser.setUsername("tesztNewUser");
        Purchase purchase= new Purchase(user);
        purchase.setUser(newUser);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.persist(newUser);
        entityManager.flush();
        entityManager.persist(purchase);
        entityManager.flush();


        // when
        Optional<Purchase> foundopt = purchaseRepository.findById(purchase.getId());

        // then
        foundopt.ifPresent(value -> assertThat(value.getUser())
                .isEqualTo(purchase.getUser()));
    }

}
