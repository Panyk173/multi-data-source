package com.example.multidatasource.repository;

import com.example.multidatasource.model.Promocion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Repositorio MongoDB para Promociones
@Repository
public interface PromocionRepository extends MongoRepository<Promocion, String> {
}
