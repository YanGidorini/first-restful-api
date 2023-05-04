package br.com.yan.domain;


import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "T_ITEM_PEDIDO")
@SequenceGenerator(name = "itemPedidoSQ", sequenceName="sq_item_pedido", allocationSize = 1)
@EqualsAndHashCode
@Getter @Setter
@ToString
@NoArgsConstructor
public class ItemPedido implements Serializable {
    
    @Id
    @GeneratedValue(generator = "itemPedidoSQ", strategy = GenerationType.SEQUENCE )
    @Column(name="id_item_pedido")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @NotNull
    private Integer quantidade;
}
