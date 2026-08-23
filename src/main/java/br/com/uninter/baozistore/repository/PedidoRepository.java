package br.com.uninter.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uninter.baozistore.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
