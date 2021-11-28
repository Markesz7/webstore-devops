package hu.bme.aut.fmb.webstore.purchases;


import hu.bme.aut.fmb.webstore.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;



@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {
    private PurchaseController purchaseController;
    private PurchaseService purchaseService;


    @Test
    public void addPurchaseTest() {

        purchaseService = Mockito.mock(PurchaseService.class);
        purchaseController = new PurchaseController(purchaseService);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);


        purchaseController.addPurchase(purchase);

        verify(purchaseService, times(1)).addPurchase(purchase);

    }

    @Test
    public void getPurchaseTest() {

        purchaseService = Mockito.mock(PurchaseService.class);
        purchaseController = new PurchaseController(purchaseService);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        List<Purchase> purchaselist= Arrays.asList(purchase);
        when(purchaseService.getPurchases()).thenReturn(purchaselist);

        purchaseController.addPurchase(purchase);
        List<Purchase> purchaselistfound= purchaseController.getPurchases();

        assertThat(purchaselistfound).isEqualTo(purchaselist);
        verify(purchaseService, times(1)).getPurchases();

    }
}
