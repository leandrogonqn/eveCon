package com.leanGomez.modules.simple.dom.empleadoocupacion;

import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;

import com.leanGomez.modules.simple.dom.empleado.Empleado;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "EmpleadoOcupacion")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "empleadoOcupacionId")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class EmpleadoOcupacion {

	public EmpleadoOcupacion(Empleado empleado, Ocupacion ocupacion) {
		// TODO Auto-generated constructor stub
		this.empleado = empleado;
		this.ocupacion = ocupacion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Empleado")
	private Empleado empleado;
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Ocupacion")
	private Ocupacion ocupacion;
	public Ocupacion getOcupacion() {
		return ocupacion;
	}
	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}
	
	@Override
	public String toString() {
		return getEmpleado().toString()+" "+getOcupacion().toString();
	}
}
