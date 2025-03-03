package com.example.multidatasource.service;

import com.example.multidatasource.dto.ClienteDTO;
import com.example.multidatasource.model.Cliente;
import com.example.multidatasource.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la logica de negocio de Clientes.
 * Realiza validaciones basicas y mapea entre la entidad Cliente y ClienteDTO.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Lista todos los clientes
    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> dtos = new ArrayList<>();
        for (Cliente c : clientes) {
            dtos.add(new ClienteDTO(c.getId(), c.getNombre(), c.getEmail(), c.getSaldo()));
        }
        return dtos;
    }

    // Obtiene un cliente por ID
    public ClienteDTO getCliente(Long id) {
        Cliente c = clienteRepository.findById(id).orElse(null);
        if (c == null) {
            return null;
        }
        return new ClienteDTO(c.getId(), c.getNombre(), c.getEmail(), c.getSaldo());
    }

    // Crea un nuevo cliente
    public ClienteDTO createCliente(ClienteDTO dto) {
        // Validaciones basicas
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio");
        }
        if (dto.getSaldo() != null && dto.getSaldo() < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setSaldo(dto.getSaldo() != null ? dto.getSaldo() : 0.0);
        Cliente guardado = clienteRepository.save(cliente);
        return new ClienteDTO(guardado.getId(), guardado.getNombre(), guardado.getEmail(), guardado.getSaldo());
    }

    // Actualiza un cliente existente
    public ClienteDTO updateCliente(Long id, ClienteDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio");
        }
        if (dto.getSaldo() != null && dto.getSaldo() < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        Cliente existente = clienteRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setNombre(dto.getNombre());
        existente.setEmail(dto.getEmail());
        if (dto.getSaldo() != null) {
            existente.setSaldo(dto.getSaldo());
        }
        Cliente actualizado = clienteRepository.save(existente);
        return new ClienteDTO(actualizado.getId(), actualizado.getNombre(), actualizado.getEmail(), actualizado.getSaldo());
    }

    // Elimina un cliente
    public boolean deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }
}
