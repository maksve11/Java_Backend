package com.maksvell.repository;


import com.maksvell.entity.Cat;
import com.maksvell.entity.Flea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Flea> getAllFleasByName(String catName);
}
