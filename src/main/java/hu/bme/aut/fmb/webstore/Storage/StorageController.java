package hu.bme.aut.fmb.webstore.Storage;

import hu.bme.aut.fmb.webstore.product.Product;
import hu.bme.aut.fmb.webstore.purchases.Purchase;
import hu.bme.aut.fmb.webstore.user.User;
import org.apache.juli.logging.Log;
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
@RequestMapping(path = "api/storage")
public class StorageController {


    private final StorageService storageService;
    Logger logger = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    public StorageController(StorageService storageService){
        this.storageService=storageService;
    }

    @GetMapping
    public List<Storage> getProducts() { //TODO: getProducts? nem inkább Storage?
        List<Storage> list=storageService.getStorage();
        logger.info(String.format("GET request for storage with %s items", list.size()));
        return list;
    }

    @Secured(User.ROLE_MODERATOR)
    @PostMapping
    public void addNewStorage(@RequestBody Storage storage) {
        logger.info(String.format("POST request for adding a new storage to database: amount: %s product_id: %s",storage.getQuantity(),storage.getProduct().getId()));
        storageService.addNewStorage(storage);
    }

    @Secured(User.ROLE_MODERATOR)
    @DeleteMapping(path = "{storageId}")
    public void deleteStorage(@PathVariable("storageId") Long id)
    {
        logger.info("DELETE request for storage with id: "+id);
        storageService.deleteStorageById(id);
    }

    @Secured(User.ROLE_MODERATOR)
    @PutMapping(path = "{storageId}")
    public void updateProduct(@PathVariable("storageId") Long id, //TODO: updateProducts? nem inkább Storage? amúgy a függvény is elég szegényesnek tűnik
                              @RequestParam(required = false) Long product_id,
                              @RequestParam(required = false) Product product,
                              @RequestParam(required = false) int quantity)
    {
        logger.info(String.format("PUT mapping for storage: %s %s %s %s",id,product_id,product,quantity));


        storageService.updateStorage(id,quantity);
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    public void exportToCsv( ) throws IOException {
        String filename="storage.csv";
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append("id");
        csvWriter.append(";");
        csvWriter.append("quantity");
        csvWriter.append(";");
        csvWriter.append("product_id");
        csvWriter.append("\n");
        List<Storage> list = storageService.getStorage();


        for (Storage s: list){
            csvWriter.append(s.getId().toString());
            csvWriter.append(";");
            csvWriter.append(String.valueOf(s.getQuantity()));
            csvWriter.append(";");
            csvWriter.append(s.getProduct().getId().toString());
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        System.out.println("Exported storage");
        logger.info("Backup save storage");
    }
}