package com.leanGomez.modules.simple.dom.ocupacion;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import com.leanGomez.modules.simple.dom.empleado.Empleado;

@DomainObject(nature = Nature.VIEW_MODEL, objectType="OcupacionConPrecio")
public class OcupacionConPrecio {

	public OcupacionConPrecio(Ocupacion ocupacion, Empleado empleado, Double precio) {
		// TODO Auto-generated constructor stub
		this.ocupacion = ocupacion;
		this.empleado = empleado;
		this.precio = precio;
	}

	private Ocupacion ocupacion;
	public Ocupacion getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}

	private Empleado empleado;
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	private Double precio;
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

}
