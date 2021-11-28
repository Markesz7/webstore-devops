package hu.bme.aut.fmb.webstore.placedpurchases;

import hu.bme.aut.fmb.webstore.EmailSendingService;
import hu.bme.aut.fmb.webstore.product.Product;
import hu.bme.aut.fmb.webstore.product.ProductService;
import hu.bme.aut.fmb.webstore.purchases.Purchase;
import hu.bme.aut.fmb.webstore.purchases.PurchaseService;
import hu.bme.aut.fmb.webstore.user.User;
import hu.bme.aut.fmb.webstore.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/placedpurchase")
public class PlacedPurchaseController {
    private final PlacedPurchaseService placedPurchaseService;
    private final PurchaseService purchaseService;
    private final ProductService productService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(PlacedPurchase.class);

    @Autowired
    public PlacedPurchaseController(PlacedPurchaseService placedPurchaseService, PurchaseService purchaseService, ProductService productService) {
        this.placedPurchaseService = placedPurchaseService;
        this.purchaseService = purchaseService;
        this.productService = productService;
    }

    @GetMapping
    public List<PlacedPurchase> getPlacedPurchases() {
        List<PlacedPurchase> list = placedPurchaseService.getPlacedPurchases();
        logger.info("GET request for listing all placed purchases, with " + list.size() + " items");
        return list;
    }

    @Secured(User.ROLE_USER)
    @PostMapping
    public void addPlacedPurchase(@RequestParam(name = "amount", defaultValue = "1") List<Integer> amount,
                                  @RequestParam(name = "product_id") List<Long> product_id,
                                  @RequestParam(name = "purchase_id", required = false) Long purchase_id,
                                  @RequestParam(name = "username", required = false, defaultValue = "Noname Nick") String username) {
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty())
            return;
        boolean hasBeen=false;
        Purchase purchase = new Purchase(user.get());
        for (int i = 0; i < amount.size(); i++) {
            Product product = new Product();
            List<Product> productList = productService.getProducts();
            boolean productExsist = false;

            if(placedPurchaseService.isEnoughStorage(amount.get(i), product_id.get(i))) {
                if(!hasBeen) {
                    purchaseService.addPurchase(purchase);
                    hasBeen=true;
                }
                for (Product p : productList) {
                    if (p.getId().equals(product_id.get(i))) {
                        productExsist = true;
                        product = p;
                    }
                }
                PlacedPurchase placedPurchase = new PlacedPurchase(purchase, product, amount.get(i));
                placedPurchaseService.addPlacedPurchase(placedPurchase);
            }
        }

        logger.info(String.format("POST request for a placed purchase where product_id: %s amount: %s purchase_id: %s",product_id,amount,purchase_id));

        try {
            logger.error("Could not connect to the gmail smtp because of my ISP-s security policy");
            emailSendingService.sendSimpleMessage(user.get().getEmail(),"Rendeles rogzitve!","FMBWebshop rendeles");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void exportToCsv( ) throws IOException {
        String filename="placedpurchase.csv";
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append("id");
        csvWriter.append(";");
        csvWriter.append("purchase_id");
        csvWriter.append(";");
        csvWriter.append("product_id");
        csvWriter.append(";");
        csvWriter.append("amount");
        csvWriter.append("\n");
        List<PlacedPurchase> list = placedPurchaseService.getPlacedPurchases();


        for (PlacedPurchase p: list){
            csvWriter.append(p.getId().toString());
            csvWriter.append(";");
            csvWriter.append(p.getPurchase().getId().toString());
            csvWriter.append(";");
            csvWriter.append(p.getProduct().getId().toString());
            csvWriter.append(";");
            csvWriter.append(String.valueOf(p.getAmount()));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Exported Placed purchases");
        logger.info("Backup save Placed purchases");
    }
}
