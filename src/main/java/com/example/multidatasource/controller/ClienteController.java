package com.example.multidatasource.controller;

import com.example.multidatasource.dto.ClienteDTO;
import com.example.multidatasource.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para Clientes (MariaDB).
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar clientes.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // GET /clientes - Lista todos los clientes
    @GetMapping
    public List<ClienteDTO> listarClientes() {
        return clienteService.getAllClientes();
    }

    // GET /clientes/{id} - Obtiene un cliente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerCliente(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.getCliente(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    // POST /clientes - Crea un nuevo cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO dto) {
        try {
            ClienteDTO nuevo = clienteService.createCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /clientes/{id} - Actualiza un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        try {
            ClienteDTO actualizado = clienteService.updateCliente(id, dto);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /clientes/{id} - Elimina un cliente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        boolean eliminado = clienteService.deleteCliente(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
