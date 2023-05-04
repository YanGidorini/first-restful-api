package br.com.yan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.yan.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}

    