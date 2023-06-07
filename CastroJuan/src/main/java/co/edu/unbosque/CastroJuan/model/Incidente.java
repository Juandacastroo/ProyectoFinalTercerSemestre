	package co.edu.unbosque.CastroJuan.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Incidente {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private String empresaAfectada;
	private String tiempoAfectado;
	private int personasAfectadas;
	private String fecha;
	private String lugar;
	@ManyToOne
	@JoinColumn(name = "policia_id")
	private Policia policia;


	public Incidente() {
	}

	public String getEmpresaAfectada() {
		return empresaAfectada;
	}

	public void setEmpresaAfectada(String empresaAfectada) {
		this.empresaAfectada = empresaAfectada;
	}

	public String getTiempoAfectado() {
		return tiempoAfectado;
	}

	public void setTiempoAfectado(String tiempoAfectado) {
		this.tiempoAfectado = tiempoAfectado;
	}

	public int getPersonasAfectadas() {
		return personasAfectadas;
	}

	public void setPersonasAfectadas(int personasAfectadas) {
		this.personasAfectadas = personasAfectadas;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Policia getPolicia() {
		return policia;
	}

	public void setPolicia(Policia policia) {
		this.policia = policia;
	}
	

	
	
	
	

}
