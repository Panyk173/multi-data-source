package com.example.multidatasource.service;

import com.example.multidatasource.dto.PromocionDTO;
import com.example.multidatasource.model.Promocion;
import com.example.multidatasource.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para la logica de negocio de Promociones (MongoDB).
 * Realiza validaciones basicas y mapea entre Promocion y PromocionDTO.
 */
@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    // Lista todas las promociones
    public List<PromocionDTO> getAllPromociones() {
        List<Promocion> promociones = promocionRepository.findAll();
        List<PromocionDTO> dtos = new ArrayList<>();
        for (Promocion p : promociones) {
            dtos.add(new PromocionDTO(p.getId(), p.getNombre(), p.getDescuento()));
        }
        return dtos;
    }

    // Obtiene una promocion por ID
    public PromocionDTO getPromocion(String id) {
        Promocion promo = promocionRepository.findById(id).orElse(null);
        if (promo == null) {
            return null;
        }
        return new PromocionDTO(promo.getId(), promo.getNombre(), promo.getDescuento());
    }

    // Crea una nueva promocion
    public PromocionDTO createPromocion(PromocionDTO dto) {
        // Validaciones basicas
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la promocion no puede estar vacio");
        }
        if (dto.getDescuento() != null && dto.getDescuento() < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo");
        }
        Promocion promo = new Promocion();
        String nuevoId = (dto.getId() == null || dto.getId().isEmpty()) ? UUID.randomUUID().toString() : dto.getId();
        promo.setId(nuevoId);
        promo.setNombre(dto.getNombre());
        promo.setDescuento(dto.getDescuento() != null ? dto.getDescuento() : 0.0);
        Promocion guardada = promocionRepository.save(promo);
        return new PromocionDTO(guardada.getId(), guardada.getNombre(), guardada.getDescuento());
    }

    // Actualiza una promocion existente
    public PromocionDTO updatePromocion(String id, PromocionDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la promocion no puede estar vacio");
        }
        if (dto.getDescuento() != null && dto.getDescuento() < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo");
        }
        Promocion existente = promocionRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setNombre(dto.getNombre());
        if (dto.getDescuento() != null) {
            existente.setDescuento(dto.getDescuento());
        }
        Promocion actualizado = promocionRepository.save(existente);
        return new PromocionDTO(actualizado.getId(), actualizado.getNombre(), actualizado.getDescuento());
    }

    // Elimina una promocion
    public boolean deletePromocion(String id) {
        if (!promocionRepository.existsById(id)) {
            return false;
        }
        promocionRepository.deleteById(id);
        return true;
    }
}
