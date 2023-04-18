package services.implementations;

import dao.repositories.FleaRepository;
import domain.entities.Flea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.FleaService;

import java.util.List;

@Service
public class FleaServiceImpl implements FleaService {

    private final FleaRepository fleaRepository;

    @Autowired
    public FleaServiceImpl(FleaRepository fleaRepository) {
        this.fleaRepository = fleaRepository;
    }

    @Override
    public List<Flea> getAllFleas() {
        return fleaRepository.findAll();
    }

    @Override
    public Flea getFleaById(Long id) {
        return fleaRepository.findById(id).orElse(null);
    }

    @Override
    public Flea addFlea(Flea flea) {
        return fleaRepository.save(flea);
    }

    @Override
    public void deleteFlea(Long id) {
        fleaRepository.deleteById(id);
    }

    @Override
    public Flea updateFlea(Long id, Flea flea) {
        Flea existingFlea = fleaRepository.findById(id).orElse(null);
        if (existingFlea != null) {
            existingFlea.setName(flea.getName());
            return fleaRepository.save(existingFlea);
        }
        return null;
    }
}
