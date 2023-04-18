package repositories;

import domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Flea> getAllFleasByCatId(Long catId);
    List<Flea> getAllFleasByName(String catName);
}
