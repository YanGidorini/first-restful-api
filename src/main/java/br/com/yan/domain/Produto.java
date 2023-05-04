package br.com.yan.domain;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name="T_PRODUTO")
@SequenceGenerator(name="produtoSQ", sequenceName="sq_produto", allocationSize=1)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Produto implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="produtoSQ")
	@Column(name="ID_PRODUTO")
	@Getter @Setter private int id;
	
    @NotBlank(message = "Nome obrigatório")
	@Size(max = 64)
	@Column(name="NM_PRODUTO", unique = true)
	@Getter @Setter private String nome;
	
	@NotNull
	@Column(name="VL_VENDA_PRODUTO")
	@Min(value = 0, message = "Valor de venda não pode ser negativo")
	@Getter @Setter private Double valorVenda;
	
	@Transient
	@Min(value = 0, message = "Custo não pode ser negativo")
	private Double custo = 0.0;
	
	@Transient
	private Double lucro = 0.0;
	
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name="id_produto"), inverseJoinColumns = @JoinColumn(name="id_material"), name="T_PRODUTO_MATERIAL")
	@Fetch(FetchMode.JOIN)
	@Getter @Setter private List<Material> materiais;

	public Double getCusto() {
		Double custoTotal = 0.0;
		for (Material m : this.getMateriais()) {
			custoTotal += m.getCusto();
		}
		this.custo = custoTotal;
		return custoTotal;
	}
	
	public Double getLucro() {
		return this.lucro = this.valorVenda - this.custo;
	}
	
	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", valorVenda=" + valorVenda + ", custo=" + getCusto() + ", lucro="
				+ getLucro() + ", materiais=" + materiais + "]";
	}

}