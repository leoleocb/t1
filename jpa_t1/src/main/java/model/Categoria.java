package model;
import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "tbl_categoria")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter@Setter
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_categoria")
	@EqualsAndHashCode.Include
	private int id;

	@Column(name="descripcion",nullable = false)
	private String descripcion;
	
}
