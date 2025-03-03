package com.example.multidatasource.repository;

import com.example.multidatasource.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA para MariaDB
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
