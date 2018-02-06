package com.leanGomez.modules.simple.dom.provincia;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Provincia")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "provinciaId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.provincia.Provincia "
			+ "WHERE provinciaNombre.toLowerCase().indexOf(:provinciaNombre) >= 0 "),

	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.provincia.Provincia " + "WHERE provinciaActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.provincia.Provincia " + "WHERE provinciaActivo == false ") })
@javax.jdo.annotations.Unique(name = "Provincia_provinciaNombre_UNQ", members = { "provinciaNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Provincia implements Comparable<Provincia>{

	public Provincia(String nombre) {
		super();
		this.provinciaNombre = nombre;
		this.provinciaActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String provinciaNombre;
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean provinciaActivo;
	public String getProvinciaNombre() {
		return provinciaNombre;
	}
	public void setProvinciaNombre(String provinciaNombre) {
		this.provinciaNombre = provinciaNombre;
	}
	public boolean isProvinciaActivo() {
		return provinciaActivo;
	}
	public void setProvinciaActivo(boolean provinciaActivo) {
		this.provinciaActivo = provinciaActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarProvincia() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setProvinciaActivo(false);
	}
	
	public Provincia actualizarNombre(@ParameterLayout(named = "Nombre") final String provinciaNombre) {
		setProvinciaNombre(provinciaNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getProvinciaNombre();
	}

	public Provincia actualizarActivo(@ParameterLayout(named = "Activo") final boolean provinciaActivo) {
		setProvinciaActivo(provinciaActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return isProvinciaActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getProvinciaNombre();
	}

	@Override
	public int compareTo(final Provincia provincia) {
		return this.provinciaNombre.compareTo(provincia.provinciaNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las Provincias")
	@MemberOrder(sequence = "2")
	public List<Provincia> listar() {
		return provinciasRepository.listar();
	}

	@ActionLayout(named = "Listar Provincia Activas")
	@MemberOrder(sequence = "3")
	public List<Provincia> listarActivos() {
		return provinciasRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Provincias Inactivas")
	@MemberOrder(sequence = "4")
	public List<Provincia> listarInactivos() {
		return provinciasRepository.listarInactivos();
	}

	
	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	ProvinciaRepository provinciasRepository;
	
}
