package com.example.multidatasource.controller;

import com.example.multidatasource.dto.PromocionDTO;
import com.example.multidatasource.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para Promociones (MongoDB).
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar promociones.
 */
@RestController
@RequestMapping("/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    // GET /promociones - Lista todas las promociones
    @GetMapping
    public List<PromocionDTO> listarPromociones() {
        return promocionService.getAllPromociones();
    }

    // GET /promociones/{id} - Obtiene una promocion por su ID
    @GetMapping("/{id}")
    public ResponseEntity<PromocionDTO> obtenerPromocion(@PathVariable String id) {
        PromocionDTO promocion = promocionService.getPromocion(id);
        if (promocion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(promocion);
    }

    // POST /promociones - Crea una nueva promocion
    @PostMapping
    public ResponseEntity<PromocionDTO> crearPromocion(@RequestBody PromocionDTO dto) {
        try {
            PromocionDTO nuevo = promocionService.createPromocion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /promociones/{id} - Actualiza una promocion existente
    @PutMapping("/{id}")
    public ResponseEntity<PromocionDTO> actualizarPromocion(@PathVariable String id, @RequestBody PromocionDTO dto) {
        try {
            PromocionDTO actualizado = promocionService.updatePromocion(id, dto);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /promociones/{id} - Elimina una promocion por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable String id) {
        boolean eliminado = promocionService.deletePromocion(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
