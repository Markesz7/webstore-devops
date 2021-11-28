package hu.bme.aut.fmb.webstore.placedpurchases;

import hu.bme.aut.fmb.webstore.Storage.Storage;
import hu.bme.aut.fmb.webstore.Storage.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacedPurchaseService {
    private final PlacedPurchaseRepository placedPurchaseRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public PlacedPurchaseService(PlacedPurchaseRepository placedPurchaseRepository, StorageRepository storageRepository) {
        this.placedPurchaseRepository = placedPurchaseRepository;
        this.storageRepository = storageRepository;
    }

    public List<PlacedPurchase> getPlacedPurchases() {
        return this.placedPurchaseRepository.findAll();
    }

    public boolean isEnoughStorage(int amount, long id)
    {
        List<Storage> storageList = storageRepository.findAll();
        for (Storage s: storageList){
            if(s.getProduct().getId().equals(id) && s.getQuantity() >= amount)
                return true;
        }
        return false;
    }

    public void addPlacedPurchase(PlacedPurchase placedPurchase) {
        List<Storage> storageList = storageRepository.findAll();
        for (Storage s: storageList){
            if (s.getProduct().getId().equals(placedPurchase.getProduct().getId()) && s.getQuantity() >= placedPurchase.getAmount()) {
                s.setQuantity(s.getQuantity()-placedPurchase.getAmount());
                placedPurchaseRepository.save(placedPurchase);
            }
        }
    }
}
