package com.example.firstDbProject.Repository;
import com.example.firstDbProject.Models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Persona findByDni(Integer dni);
}

