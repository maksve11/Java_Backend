package services.interfaces;

import domain.entities.Cat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatService {
    List<Cat> getAllCats();
    Cat getCatById(Long id);
    Cat addCat(Cat cat);
    void deleteCat(Long id);
    Cat updateCat(Long id, Cat cat);
}
