package services.implementations;

import dao.repositories.CatRepository;
import domain.entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.CatService;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    @Autowired
    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    @Override
    public Cat getCatById(Long id) {
        return catRepository.findById(id).orElse(null);
    }

    @Override
    public Cat addCat(Cat cat) {
        return catRepository.save(cat);
    }

    @Override
    public void deleteCat(Long id) {
        catRepository.deleteById(id);
    }

    @Override
    public Cat updateCat(Long id, Cat cat) {
        Cat existingCat = catRepository.findById(id).orElse(null);
        if (existingCat != null) {
            existingCat.setName(cat.getName());
            existingCat.setBirthdate(cat.getBirthdate());
            existingCat.setColor(cat.getColor());
            return catRepository.save(existingCat);
        }
        return null;
    }
}
