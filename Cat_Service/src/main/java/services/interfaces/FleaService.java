package services.interfaces;

import domain.entities.Flea;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FleaService {
    List<Flea> getAllFleas();
    Flea getFleaById(Long id);
    Flea addFlea(Flea flea);
    void deleteFlea(Long id);
    Flea updateFlea(Long id, Flea flea);
}
