package com.leanGomez.modules.simple.dom.persona;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import com.leanGomez.modules.simple.dom.localidad.Localidad;

@DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="personaId")
@PersistenceCapable(identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public abstract class Persona {
	
	public String cssClass() {
		return (isPersonaActivo() == true) ? "activo" : "inactivo";
	}
	
	@Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(named="Nombre y apellido / Razon Social")
	private String personaNombre;
	public String getPersonaNombre() {
		return personaNombre;
	}
	public void setPersonaNombre(String personaNombre) {
		this.personaNombre = personaNombre;
	}
	
	@Column(allowsNull = "false", name="localidadId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Localidad")
	private Localidad personaLocalidad;
	public Localidad getPersonaLocalidad() {
		return personaLocalidad;
	}
	public void setPersonaLocalidad(Localidad personaLocalidad) {
		this.personaLocalidad = personaLocalidad;
	}
	
	@Column(allowsNull = "true")
	@Property(
	            editing = Editing.DISABLED
	    )
	@PropertyLayout(named="Direccion")
	private String personaDireccion;
	public String getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}
	
	@Column(allowsNull = "true")
	@Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Telefono")
	private String personaTelefono;
	public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}
	
	@Column(allowsNull = "true")
	@Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Mail")
	private String personaMail;
	public String getPersonaMail() {
		return personaMail;
	}
	public void setPersonaMail(String personaMail) {
		this.personaMail = personaMail;
	}
	
	@Column(allowsNull = "false")
	@Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Activo")
	private boolean personaActivo;
	public boolean isPersonaActivo() {
		return personaActivo;
	}
	public void setPersonaActivo(boolean personaActivo) {
		this.personaActivo = personaActivo;
	}
	
	@Column(allowsNull = "true")
	@Property(
            editing = Editing.DISABLED
    )
	@PropertyLayout(named="Cuit/Cuil")
	private String personaCuitCuil;
	public String getPersonaCuitCuil() {
		return personaCuitCuil;
	}
	public void setPersonaCuitCuil(String personaCuitCuil) {
		this.personaCuitCuil = personaCuitCuil;
	}
	
}
