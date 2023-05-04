package br.com.yan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yan.domain.ItemPedido;
import br.com.yan.domain.Pedido;
import br.com.yan.repository.ItemPedidoRepository;
import br.com.yan.repository.PedidoRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("pedido")
public class PedidoController {
    
    @Autowired
    private ItemPedidoRepository dao;

    @Autowired
    private PedidoRepository pedidoDao;

    @PostMapping("cadastro-item")
    public Pedido cadastrar(@RequestBody ItemPedido itemPedido){
        
        if(itemPedido.getPedido().getId() != null){
            itemPedido.setPedido( pedidoDao.findById(itemPedido.getPedido().getId()).get() );
        }
        dao.save(itemPedido);

        return lista(itemPedido.getPedido().getId());
    }

    @GetMapping("{id}")
    public Pedido lista(@PathVariable Integer id) {
        return pedidoDao.findById(id).get();
    }
    
}
