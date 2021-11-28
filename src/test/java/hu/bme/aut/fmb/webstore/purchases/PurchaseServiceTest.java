package hu.bme.aut.fmb.webstore.purchases;


import hu.bme.aut.fmb.webstore.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PurchaseServiceTest {
    PurchaseRepository purchaseRepository;
    PurchaseService purchaseService;

    @Test
    public void addNewPurchaseTest() {
        purchaseRepository=Mockito.mock(PurchaseRepository.class);
        purchaseService= new PurchaseService(purchaseRepository);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        List<Purchase> purchaselist= Arrays.asList(purchase);
        when(purchaseRepository.findById(purchase.getId()))
                .thenReturn(Optional.of(purchase));
        when(purchaseRepository.findAll()).thenReturn(purchaselist);

        purchaseService.addPurchase(purchase);

        verify(purchaseRepository, times(1)).save(purchase);

    }

    @Test
    public void getPurchasesTest() {
        purchaseRepository=Mockito.mock(PurchaseRepository.class);
        purchaseService= new PurchaseService(purchaseRepository);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        List<Purchase> purchaselist= Arrays.asList(purchase);
        when(purchaseRepository.findById(purchase.getId()))
                .thenReturn(Optional.of(purchase));
        when(purchaseRepository.findAll()).thenReturn(purchaselist);


        purchaseService.addPurchase(purchase);
        List<Purchase> purchaselistfound= purchaseService.getPurchases();

        assertThat(purchaselistfound).isEqualTo(purchaselist);
        verify(purchaseRepository, times(1)).findAll();

    }

    @Test
    public void getIDPurchasesTest() {
        purchaseRepository=Mockito.mock(PurchaseRepository.class);
        purchaseService= new PurchaseService(purchaseRepository);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        List<Purchase> purchaselist= Arrays.asList(purchase);
        when(purchaseRepository.findById(purchase.getId()))
                .thenReturn(Optional.of(purchase));
        when(purchaseRepository.findAll()).thenReturn(purchaselist);


        purchaseService.addPurchase(purchase);
        Long foundID=purchaseService.getId(purchase);

        assertThat(foundID).isEqualTo(purchase.getId());
        verify(purchaseRepository, times(1)).findById(purchase.getId());


    }
    @Test
    public void getIDPurchasesTest_withException() {
        purchaseRepository=Mockito.mock(PurchaseRepository.class);
        purchaseService= new PurchaseService(purchaseRepository);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        User user2=new User();
        user2.setUsername("tesztuser2");
        Purchase purchase2= new Purchase(user2);
        purchase2.setId(23L);
        List<Purchase> purchaselist= Arrays.asList(purchase);
        when(purchaseRepository.findById(purchase.getId()))
                .thenReturn(Optional.of(purchase));
        when(purchaseRepository.findAll()).thenReturn(purchaselist);


        purchaseService.addPurchase(purchase);

        Throwable exception = assertThrows(IllegalStateException.class, () -> purchaseService.getId(purchase2));
        assertEquals("Easter egg!", exception.getMessage());
        verify(purchaseRepository, times(1)).findById(purchase2.getId());

    }

}
