package com.leanGomez.modules.simple.dom.tipodeequipo;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CollectionLayout;
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

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "TipoDeEquipo")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "tipoDeEquipoId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipo "
			+ "WHERE tipoDeEquipoNombre.toLowerCase().indexOf(:tipoDeEquipoNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipo " + "WHERE tipoDeEquipoActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipo " + "WHERE tipoDeEquipoActivo == false ") })
@javax.jdo.annotations.Unique(name = "TipoDeEquipo_tipoDeEquipoNombre_UNQ", members = { "tipoDeEquipoNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class TipoDeEquipo implements Comparable<TipoDeEquipo>{

	public TipoDeEquipo(String tipoDeEquipoNombre, String tipoDeEquipoDescripcion) {
		super();
		this.tipoDeEquipoNombre = tipoDeEquipoNombre;
		this.tipoDeEquipoDescripcion = tipoDeEquipoDescripcion;
		this.tipoDeEquipoActivo = true;
	}
	
	public String cssClass() {
		return (isTipoDeEquipoActivo() == true) ? "activo" : "inactivo";
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo De Equipo")
	private String tipoDeEquipoNombre;
	public String getTipoDeEquipoNombre() {
		return tipoDeEquipoNombre;
	}
	public void setTipoDeEquipoNombre(String tipoDeEquipoNombre) {
		this.tipoDeEquipoNombre = tipoDeEquipoNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descripcion")
	private String tipoDeEquipoDescripcion;
	public String getTipoDeEquipoDescripcion() {
		return tipoDeEquipoDescripcion;
	}
	public void setTipoDeEquipoDescripcion(String tipoDeEquipoDescripcion) {
		this.tipoDeEquipoDescripcion = tipoDeEquipoDescripcion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean tipoDeEquipoActivo;
	public boolean isTipoDeEquipoActivo() {
		return tipoDeEquipoActivo;
	}
	public void setTipoDeEquipoActivo(boolean tipoDeEquipoActivo) {
		this.tipoDeEquipoActivo = tipoDeEquipoActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarTipoDeEquipo() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setTipoDeEquipoActivo(false);
	}
	
	public TipoDeEquipo modificarNombre(@ParameterLayout(named = "Nombre") final String tipoDeEquipoNombre) {
		setTipoDeEquipoNombre(tipoDeEquipoNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getTipoDeEquipoNombre();
	}

	public TipoDeEquipo modificarDescripcion(@ParameterLayout(named = "Descripcion") final String tipoDeEquipoDescripcion) {
		setTipoDeEquipoDescripcion(tipoDeEquipoDescripcion);
		return this;
	}

	public String default0ModificarDescripcion() {
		return getTipoDeEquipoDescripcion();
	}
	
	public TipoDeEquipo modificarActivo(@ParameterLayout(named = "Activo") final boolean tipoDeEquipoActivo) {
		setTipoDeEquipoActivo(tipoDeEquipoActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isTipoDeEquipoActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getTipoDeEquipoNombre();
	}

	@Override
	public int compareTo(final TipoDeEquipo tipoDeEquipo) {
		return this.tipoDeEquipoNombre.compareTo(tipoDeEquipo.tipoDeEquipoNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todos los Tipos De Equipos")
	@MemberOrder(sequence = "2")
	public List<TipoDeEquipo> listar() {
		return tipoDeEquiposRepository.listar();
	}

	@ActionLayout(named = "Listar Tipos De Equipos Activas")
	@MemberOrder(sequence = "3")
	public List<TipoDeEquipo> listarActivos() {
		return tipoDeEquiposRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Tipos De Equipos Inactivas")
	@MemberOrder(sequence = "4")
	public List<TipoDeEquipo> listarInactivos() {
		return tipoDeEquiposRepository.listarInactivos();
	}
	
//	@ActionLayout(named = "Listar equipos de este Tipo De Equipo")
//	@MemberOrder(sequence = "5")
//	public List<Localidad> listarTipoDeEquipo(){
//		return localidadRepository.buscarPorTipoDeEquipo(this);
//	}

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	TipoDeEquipoRepository tipoDeEquiposRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
}
