package com.example.firstDbProject.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "personas")
public class Persona {
    @Id
    @Column(name = "dni")
    private Integer dni;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "ciudad")
    private String city;
    @Column(name = "edad")
    private Integer age;

    public Persona() {
    }

    public Persona(Integer dni, String name, String lastname, String city, Integer age) {
        this.dni = dni;
        this.name =  name;
        this.lastName = lastname;
        this.city = city;
        this.age = age;
    }

}
