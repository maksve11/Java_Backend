package com.maksvell.repository;

import com.maksvell.entity.Cat;
import com.maksvell.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    public List<Cat> getAllCatsByName(String name);
}
