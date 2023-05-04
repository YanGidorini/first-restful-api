package br.com.yan.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "T_PEDIDO")
@EqualsAndHashCode
@NoArgsConstructor
@SequenceGenerator(name = "pedidoSQ", sequenceName="sq_pedido", allocationSize = 1)
@Getter @Setter
public class Pedido implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedidoSQ")
    @Column(name="id_pedido")
    private Integer id;

    @Transient
    private Map<Integer,Produto> produtos;

    @Transient
    private Double valorPedido;

    @Transient
    private Double custoPedido;

    @Transient
    private Double lucroPedido;

    @JsonIgnore
    @OneToMany(mappedBy="pedido")
    private List<ItemPedido> itens;

    public Map<Integer,Produto> getProdutos(){
        Map<Integer,Produto> produtos = new HashMap<>();
        for(ItemPedido i : this.itens){
            produtos.put(i.getQuantidade(),i.getProduto());
        }
        this.produtos = produtos;
        return produtos;
    }

    public Double getValorPedido() {
        Double total = 0.0;
        for (Map.Entry<Integer, Produto> entry : produtos.entrySet()){
            Integer qnt = entry.getKey();
            Produto p = entry.getValue();
            total += qnt * p.getValorVenda();
        }
        this.valorPedido = total;
        return total;
    }

    public Double getCustoPedido() {
        Double total = 0.0;
        for (Map.Entry<Integer, Produto> entry : produtos.entrySet()){
            Integer qnt = entry.getKey();
            Produto p = entry.getValue();
            total += qnt * p.getCusto();
        }
        this.custoPedido = total;
        return total;
    }

    public Double getLucroPedido() {
        return this.lucroPedido = valorPedido - custoPedido;
    }
}
