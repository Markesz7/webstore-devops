package hu.bme.aut.fmb.webstore.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    private StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository){
        this.storageRepository=storageRepository;
    }

    public List<Storage> getStorage(){
        return storageRepository.findAll();
    }

    public void addNewStorage(Storage storage) {
            storageRepository.save(storage);
    }

    public void deleteStorageById(Long id) {
        boolean exists = storageRepository.existsById(id);
        if (exists){
            storageRepository.deleteById(id);
        }
        else{
            throw new IllegalStateException("no product with this id: "+id);
        }
    }

    @Transactional
    public void updateStorage(Long id,int quantoty) {

        Optional<Storage> storage=storageRepository.findById(id);
        Storage toUpdate = storageRepository.findById(storage.get().getId()).orElseThrow(()->new IllegalStateException("product with id: "+storage.get().getId()+" dont exists"));
        toUpdate.setQuantity(quantoty);
    }

    public Optional<Storage> findStorageById(Long id){
        Optional<Storage> storage = storageRepository.findById(id);
        if(storage.isEmpty()){
            return null;
        }
        else {
            return storage;
        }

    }

    public void deleteStorageByProductId(Long prod_id) {
        List<Storage> list=storageRepository.findAll();
        Long storageId = null;

        for (Storage s:list) {
            if (s.getProduct().getId().equals(prod_id)){
                storageId = s.getId();
            }
        }
        if (storageId !=null){
            storageRepository.deleteById(storageId);
        }
    }
}