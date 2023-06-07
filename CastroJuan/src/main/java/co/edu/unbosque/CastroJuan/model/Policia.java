package co.edu.unbosque.CastroJuan.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Policia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String fechaNacimiento;
	private String sexo;
	private String fechaEntrada;
	private String rangoActual;
	private int numeroCasosAtendidos;
	
	public Policia() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getRangoActual() {
		return rangoActual;
	}

	public void setRangoActual(String rangoActual) {
		this.rangoActual = rangoActual;
	}

	public int getNumeroCasosAtendidos() {
		return numeroCasosAtendidos;
	}

	public void setNumeroCasosAtendidos(int numeroCasosAtendidos) {
		this.numeroCasosAtendidos = numeroCasosAtendidos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	

}
