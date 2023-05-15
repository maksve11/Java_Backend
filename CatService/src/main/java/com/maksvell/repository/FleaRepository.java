package com.maksvell.repository;


import com.maksvell.entity.Flea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FleaRepository extends JpaRepository<Flea, Long> {
    List<Flea> findByCatId(Long catId);
    List<Flea> findAllByName(String name);
}
