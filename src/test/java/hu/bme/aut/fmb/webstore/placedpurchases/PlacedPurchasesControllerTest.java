package hu.bme.aut.fmb.webstore.placedpurchases;


import hu.bme.aut.fmb.webstore.product.ProductService;
import hu.bme.aut.fmb.webstore.purchases.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@AutoConfigureMockMvc
public class PlacedPurchasesControllerTest<PlacedPurchasesController> {
         PlacedPurchaseService placedPurchaseService;
         PurchaseService purchaseService;
         ProductService productService;

         PlacedPurchasesController placedPurchasesController;

    //@Test
    public void getProductTest_set() {

        placedPurchaseService= Mockito.mock(PlacedPurchaseService.class);
        purchaseService = Mockito.mock(PurchaseService.class);
        productService = Mockito.mock(ProductService.class);

       // placedPurchasesController = new PlacedPurchasesController(placedPurchaseService,purchaseService,productService);
       // product= new Product();
    }

}
