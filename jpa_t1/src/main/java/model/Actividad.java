package model;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name="tbl_actividad")
@DynamicInsert
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Getter@Setter
public class Actividad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_actividad")
	private int id;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "id_categoria", nullable = false)
	private Categoria categoria;
	
	@Column(name = "fecha_inicio", nullable = false) 
    private LocalDate fechaInicio;
	
	@Column(name = "nro_vacantes,")
	private int nroVacantes = 100;
	
	
}
