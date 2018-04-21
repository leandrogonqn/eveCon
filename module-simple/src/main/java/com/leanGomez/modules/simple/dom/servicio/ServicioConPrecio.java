package com.leanGomez.modules.simple.dom.servicio;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.ParameterLayout;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="ServicioConPrecio")
public class ServicioConPrecio{
	
	public ServicioConPrecio(Servicio servicio, Double precio) {
		// TODO Auto-generated constructor stub
		this.servicio = servicio;
		this.precio = precio;
	}

	private Servicio servicio;

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	
	private Double precio;
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
