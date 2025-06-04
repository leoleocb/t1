package model;

import java.time.LocalDate;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name = "tbl_solicitud")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@DynamicInsert

public class Solicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
	@Column(name="nro_solicitud")
	private int id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;
    
    @Column(name="estado", nullable =false,length = 2)
    private String estado;
    
    @Column(name="archivo_adjunto", nullable=false ,length=200)
    private String archivo_adjunto;
    
    @Column(name="fecha_reg",nullable=false,columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDate fecha_reg;

	
	
	
	
}
