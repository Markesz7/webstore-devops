package hu.bme.aut.fmb.webstore.purchases;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getPurchases() {
        return purchaseRepository.findAll();
    }

    public void addPurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public Long getId(Purchase purchase) {
        Optional<Purchase> p = purchaseRepository.findById(purchase.id);
        if (p.isPresent()){
            return purchase.getId();
        }
        else {
            throw new IllegalStateException("Easter egg!");
        }
    }
}
