package br.com.yan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yan.domain.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}