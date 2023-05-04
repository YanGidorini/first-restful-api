package br.com.yan.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "produtos")
@Table(name = "T_MATERIAL")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SequenceGenerator(name = "materialSQ", sequenceName = "sq_material", allocationSize = 1)
public class Material implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialSQ")
    @Column(name = "ID_MATERIAL")
    @Getter @Setter private int id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "NM_MATERIAL", unique = true)
    @Getter @Setter private String nome;
    
    @Min(value = 0, message = "Custo não pode ser negativo")
    @Column(name = "CUSTO_MATERIAL")
    @Getter @Setter private Double custo;

    @JsonIgnore
    @ManyToMany(mappedBy = "materiais")
    @Getter private List<Produto> produtos;

}
