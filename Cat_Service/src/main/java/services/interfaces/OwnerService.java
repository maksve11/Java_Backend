package services.interfaces;

import domain.entities.Owner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OwnerService {
    List<Owner> getAllOwners();
    Owner getOwnerById(Long id);
    Owner addOwner(Owner owner);
    void deleteOwner(Long id);
    Owner updateOwner(Long id, Owner owner);
}
