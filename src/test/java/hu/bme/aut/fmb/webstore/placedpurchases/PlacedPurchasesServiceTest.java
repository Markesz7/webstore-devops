package hu.bme.aut.fmb.webstore.placedpurchases;

import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageRepository;
import hu.bme.aut.fmb.webstore.product.Product;
import hu.bme.aut.fmb.webstore.purchases.Purchase;
import hu.bme.aut.fmb.webstore.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PlacedPurchasesServiceTest {

    PlacedPurchaseRepository placedPurchaseRepository;
    StorageRepository storageRepositoryy;
    PlacedPurchaseService placedPurchaseService;

    @Test
    public void addNewPlacedPurchase_and_findByID_Test() {
        placedPurchaseRepository=Mockito.mock(PlacedPurchaseRepository.class);
        storageRepositoryy=Mockito.mock(StorageRepository.class);
        placedPurchaseService= new PlacedPurchaseService(placedPurchaseRepository,storageRepositoryy);
        Product product=new Product(2L,"teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        List<PlacedPurchase> placedPurchaselist= Arrays.asList(placedPurchase);
        Storage storages=new Storage(1L,new Product(2L,"Proba", "TestMan", "TesztDesc", 100),10);
        List<Storage> storagelist= Arrays.asList(storages);
        when(placedPurchaseRepository.findById(placedPurchase.getId()))
                .thenReturn(Optional.of(placedPurchase));
        when(placedPurchaseRepository.findAll()).thenReturn(placedPurchaselist);
                storageRepositoryy.save(storages);//);
        when(storageRepositoryy.findAll()).thenReturn(storagelist);

        placedPurchaseService.addPlacedPurchase(placedPurchase);

        verify(placedPurchaseRepository, times(1)).save(placedPurchase);
        verify(storageRepositoryy, times(1)).findAll();
    }

    @Test
    public void getPlacedPurchasesTest() {
        placedPurchaseRepository=Mockito.mock(PlacedPurchaseRepository.class);
        storageRepositoryy=Mockito.mock(StorageRepository.class);
        placedPurchaseService= new PlacedPurchaseService(placedPurchaseRepository,storageRepositoryy);
        Product product=new Product(2L,"teszt","man","desc",1);
        User user=new User();
        user.setUsername("tesztuser");
        Purchase purchase= new Purchase(user);
        PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, 3);
        List<PlacedPurchase> placedPurchaselist= Arrays.asList(placedPurchase);
        Storage storages=new Storage(1L,new Product(2L,"Proba", "TestMan", "TesztDesc", 100),10);
        List<Storage> storagelist= Arrays.asList(storages);
        when(placedPurchaseRepository.findById(placedPurchase.getId()))
                .thenReturn(Optional.of(placedPurchase));
        when(placedPurchaseRepository.findAll()).thenReturn(placedPurchaselist);

        storageRepositoryy.save(storages);
        when(storageRepositoryy.findAll()).thenReturn(storagelist);
        placedPurchaseService.addPlacedPurchase(placedPurchase);
        List<PlacedPurchase> placedPurchaselistfound=placedPurchaseService.getPlacedPurchases();

        assertThat(placedPurchaselistfound).isEqualTo(placedPurchaselist);
        verify(placedPurchaseRepository, times(1)).findAll();

    }
}
