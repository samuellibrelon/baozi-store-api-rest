package br.com.uninter.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uninter.baozistore.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
