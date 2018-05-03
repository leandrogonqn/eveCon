package com.leanGomez.modules.simple.dom.tipodeevento;

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

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "TipoDeEvento")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "tipoDeEventoId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento "
			+ "WHERE tipoDeEventoNombre.toLowerCase().indexOf(:tipoDeEventoNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento " + "WHERE tipoDeEventoActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento " + "WHERE tipoDeEventoActivo == false ") })
@javax.jdo.annotations.Unique(name = "TipoDeEvento_tipoDeEventoNombre_UNQ", members = { "tipoDeEventoNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class TipoDeEvento implements Comparable<TipoDeEvento>{

	public TipoDeEvento(String tipoDeEventoNombre, String tipoDeEventoDescripcion) {
		super();
		this.tipoDeEventoNombre = tipoDeEventoNombre;
		this.tipoDeEventoDescripcion = tipoDeEventoDescripcion;
		this.tipoDeEventoActivo = true;
	}
	
	public String cssClass() {
		return (isTipoDeEventoActivo() == true) ? "activo" : "inactivo";
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo De Evento")
	private String tipoDeEventoNombre;
	public String getTipoDeEventoNombre() {
		return tipoDeEventoNombre;
	}
	public void setTipoDeEventoNombre(String tipoDeEventoNombre) {
		this.tipoDeEventoNombre = tipoDeEventoNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descripcion")
	private String tipoDeEventoDescripcion;
	public String getTipoDeEventoDescripcion() {
		return tipoDeEventoDescripcion;
	}
	public void setTipoDeEventoDescripcion(String tipoDeEventoDescripcion) {
		this.tipoDeEventoDescripcion = tipoDeEventoDescripcion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean tipoDeEventoActivo;
	public boolean isTipoDeEventoActivo() {
		return tipoDeEventoActivo;
	}
	public void setTipoDeEventoActivo(boolean tipoDeEventoActivo) {
		this.tipoDeEventoActivo = tipoDeEventoActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarTipoDeEvento() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setTipoDeEventoActivo(false);
	}
	
	public TipoDeEvento modificarNombre(@ParameterLayout(named = "Nombre") final String tipoDeEventoNombre) {
		setTipoDeEventoNombre(tipoDeEventoNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getTipoDeEventoNombre();
	}

	public TipoDeEvento modificarDescripcion(@ParameterLayout(named = "Descripcion") final String tipoDeEventoDescripcion) {
		setTipoDeEventoDescripcion(tipoDeEventoDescripcion);
		return this;
	}

	public String default0ModificarDescripcion() {
		return getTipoDeEventoDescripcion();
	}
	
	public TipoDeEvento modificarActivo(@ParameterLayout(named = "Activo") final boolean tipoDeEventoActivo) {
		setTipoDeEventoActivo(tipoDeEventoActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isTipoDeEventoActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getTipoDeEventoNombre();
	}

	@Override
	public int compareTo(final TipoDeEvento tipoDeEvento) {
		return this.tipoDeEventoNombre.compareTo(tipoDeEvento.tipoDeEventoNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todos los Tipos De Eventos")
	@MemberOrder(sequence = "2")
	public List<TipoDeEvento> listar() {
		return tipoDeEventosRepository.listar();
	}

	@ActionLayout(named = "Listar Tipos De Eventos Activas")
	@MemberOrder(sequence = "3")
	public List<TipoDeEvento> listarActivos() {
		return tipoDeEventosRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Tipos De Eventos Inactivas")
	@MemberOrder(sequence = "4")
	public List<TipoDeEvento> listarInactivos() {
		return tipoDeEventosRepository.listarInactivos();
	}
	
//	@ActionLayout(named = "Listar eventos de este Tipo De Evento")
//	@MemberOrder(sequence = "5")
//	public List<Localidad> listarTipoDeEvento(){
//		return localidadRepository.buscarPorTipoDeEvento(this);
//	}

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	TipoDeEventoRepository tipoDeEventosRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
}
