package hu.bme.aut.fmb.webstore.purchases;

import hu.bme.aut.fmb.webstore.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;
    Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> getPurchases() {
        List<Purchase> list= purchaseService.getPurchases();
        logger.info("GET request for listing all the Purchases, there are " +  list.size() + " items.");
        return list;
    }

    @Secured(User.ROLE_USER)
    @PostMapping
    public void addPurchase(@RequestBody Purchase purchase){
        logger.info("POST request for posting a new Purchase.\n ");
        purchaseService.addPurchase(purchase);
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void exportToCsv( ) throws IOException {
        String filename="purchase.csv";
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append("id");
        csvWriter.append(";");
        csvWriter.append("username");
        csvWriter.append("\n");
        List<Purchase> list = purchaseService.getPurchases();


        for (Purchase p: list){
            csvWriter.append(p.getId().toString());
            csvWriter.append(";");
            csvWriter.append(p.getUser().getUsername());
        }
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Exported purchases");
        logger.info("Backup save purchases");
    }
}
