package com.leanGomez.modules.simple.dom.equipo;

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
import com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipo;
import com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipoMenu;
import com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipoRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Equipo")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "equipoId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.equipo.Equipo "
			+ "WHERE equipoNombre.toLowerCase().indexOf(:equipoNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.equipo.Equipo " + "WHERE equipoActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.equipo.Equipo " + "WHERE equipoActivo == false ") })
@javax.jdo.annotations.Unique(name = "Equipo_equipoNombre_UNQ", members = { "equipoNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Equipo implements Comparable<Equipo>{

	public Equipo(String equipoNombre, String equipoDescripcion, TipoDeEquipo equipoTipoDeEquipo) {
		super();
		this.equipoNombre = equipoNombre;
		this.equipoDescripcion = equipoDescripcion;
		this.equipoTipoDeEquipo = equipoTipoDeEquipo;
		this.equipoActivo = true;
	}
	
	public String cssClass() {
		return (isEquipoActivo() == true) ? "activo" : "inactivo";
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Equipo")
	private String equipoNombre;
	public String getEquipoNombre() {
		return equipoNombre;
	}
	public void setEquipoNombre(String equipoNombre) {
		this.equipoNombre = equipoNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descripcion")
	private String equipoDescripcion;
	public String getEquipoDescripcion() {
		return equipoDescripcion;
	}
	public void setEquipoDescripcion(String equipoDescripcion) {
		this.equipoDescripcion = equipoDescripcion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo de Equipo")
	private TipoDeEquipo equipoTipoDeEquipo;
	public TipoDeEquipo getEquipoTipoDeEquipo() {
		return equipoTipoDeEquipo;
	}
	public void setEquipoTipoDeEquipo(TipoDeEquipo equipoTipoDeEquipo) {
		this.equipoTipoDeEquipo = equipoTipoDeEquipo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean equipoActivo;
	public boolean isEquipoActivo() {
		return equipoActivo;
	}
	public void setEquipoActivo(boolean equipoActivo) {
		this.equipoActivo = equipoActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarEquipo() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setEquipoActivo(false);
	}
	
	public Equipo modificarNombre(@ParameterLayout(named = "Nombre") final String equipoNombre) {
		setEquipoNombre(equipoNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getEquipoNombre();
	}

	public Equipo modificarDescripcion(@ParameterLayout(named = "Descripcion") final String equipoDescripcion) {
		setEquipoDescripcion(equipoDescripcion);
		return this;
	}

	public String default0ModificarDescripcion() {
		return getEquipoDescripcion();
	}
	
	public Equipo modificarTipoDeEquipo(@ParameterLayout(named = "Tipo De Equipo") final TipoDeEquipo equipoTipoDeEquipo) {
		setEquipoTipoDeEquipo(equipoTipoDeEquipo);
		return this;
	}
	
	public List<TipoDeEquipo> choices0ModificarTipoDeEquipo(){
		return tipoDeEquipoRepository.listarActivos();
	}

	public TipoDeEquipo default0ModificarTipoDeEquipo() {
		return getEquipoTipoDeEquipo();
	}
	
	public Equipo modificarActivo(@ParameterLayout(named = "Activo") final boolean equipoActivo) {
		setEquipoActivo(equipoActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isEquipoActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getEquipoNombre();
	}

	@Override
	public int compareTo(final Equipo equipo) {
		return this.equipoNombre.compareTo(equipo.equipoNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todos los Tipos De Eventos")
	@MemberOrder(sequence = "2")
	public List<Equipo> listar() {
		return equiposRepository.listar();
	}

	@ActionLayout(named = "Listar Tipos De Eventos Activas")
	@MemberOrder(sequence = "3")
	public List<Equipo> listarActivos() {
		return equiposRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Tipos De Eventos Inactivas")
	@MemberOrder(sequence = "4")
	public List<Equipo> listarInactivos() {
		return equiposRepository.listarInactivos();
	}
	
//	@ActionLayout(named = "Listar eventos de este Tipo De Evento")
//	@MemberOrder(sequence = "5")
//	public List<Localidad> listarEquipo(){
//		return localidadRepository.buscarPorEquipo(this);
//	}

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	EquipoRepository equiposRepository;
	
	@Inject
	TipoDeEquipoRepository tipoDeEquipoRepository;
	
}
