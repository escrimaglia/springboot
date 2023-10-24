package com.example.firstDbProject.Repository;

import com.example.firstDbProject.Models.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    List<Auto> findByPropietario(Integer propietario);
}
