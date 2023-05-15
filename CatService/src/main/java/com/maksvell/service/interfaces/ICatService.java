package com.maksvell.service.interfaces;

import com.maksvell.dto.CatDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICatService {
    List<CatDto> getAllCats();
    CatDto getCatById(Long id);
    CatDto addCat(CatDto cat);
    void deleteCat(Long id);
    CatDto updateCat(Long id, CatDto cat);
}
