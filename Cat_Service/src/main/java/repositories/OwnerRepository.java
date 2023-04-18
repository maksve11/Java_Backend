package repositories;

import domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    public List<Cat> getAllCatsByOwnerId(Long id);
    public List<Cat> getAllCatsByName(String name);
}
