package com.leanGomez.modules.simple.dom.servicio;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;

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
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicio;
import com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicioRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Servicio")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "servicioId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.servicio.Servicio "
			+ "WHERE servicioNombre.toLowerCase().indexOf(:servicioNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.servicio.Servicio " + "WHERE servicioActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.servicio.Servicio " + "WHERE servicioActivo == false ") })
@javax.jdo.annotations.Unique(name = "Servicio_servicioNombre_UNQ", members = { "servicioNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Servicio implements Comparable<Servicio>{

	public Servicio(String servicioNombre, String servicioObservaciones) {
		super();
		this.servicioNombre = servicioNombre;
		this.servicioObservaciones = servicioObservaciones;
		this.servicioActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Servicio")
	private String servicioNombre;
	public String getServicioNombre() {
		return servicioNombre;
	}
	public void setServicioNombre(String servicioNombre) {
		this.servicioNombre = servicioNombre;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Observaciones",hidden=Where.ALL_TABLES)
	private String servicioObservaciones;
	public String getServicioObservaciones() {
		return servicioObservaciones;
	}
	public void setServicioObservaciones(String servicioObservaciones) {
		this.servicioObservaciones = servicioObservaciones;
	}
	
	public Double getPrecioActualServicio() {
		Date hoy = new Date();
		Double a = precioHistoricoServicioRepository.mostrarPrecioPorFecha(this, hoy);
		return a;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo",hidden=Where.ALL_TABLES)
	private boolean servicioActivo;
	public boolean isServicioActivo() {
		return servicioActivo;
	}
	public void setServicioActivo(boolean servicioActivo) {
		this.servicioActivo = servicioActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarServicio() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setServicioActivo(false);
	}
	
	public Servicio modificarNombre(@ParameterLayout(named = "Nombre") final String servicioNombre) {
		setServicioNombre(servicioNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getServicioNombre();
	}
	
	public Servicio modificarObservaciones(@ParameterLayout(named = "Observaciones") final String servicioObservaciones) {
		setServicioObservaciones(servicioObservaciones);
		return this;
	}

	public String default0ModificarObservaciones() {
		return getServicioObservaciones();
	}
	
	//MODIFICAR PRECIO
	public Servicio modificarPrecio(@ParameterLayout(named = "Precio") final Double servicioPrecio) {
		Date h = new Date();
		Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
		List<PrecioHistoricoServicio> p = precioHistoricoServicioRepository.listarPreciosPorServicio(this);
		Iterator<PrecioHistoricoServicio> it = p.iterator();
		while (it.hasNext()) {
			PrecioHistoricoServicio lista = it.next();
			if (lista.getPrecioHistoricoServicioFechaHasta()==null)
				lista.setPrecioHistoricoServicioFechaHasta(hoy);
		}
		precioHistoricoServicioRepository.crear(this, servicioPrecio);
		return this;
	}
	
	public Double default0ModificarPrecio() {
		return getPrecioActualServicio();
	}

	public Servicio modificarActivo(@ParameterLayout(named = "Activo") final boolean servicioActivo) {
		setServicioActivo(servicioActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isServicioActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getServicioNombre();
	}

	@Override
	public int compareTo(final Servicio servicio) {
		return this.servicioNombre.compareTo(servicio.servicioNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las Servicios")
	@MemberOrder(sequence = "2")
	public List<Servicio> listar() {
		return serviciosRepository.listar();
	}

	@ActionLayout(named = "Listar Servicio Activas")
	@MemberOrder(sequence = "3")
	public List<Servicio> listarActivos() {
		return serviciosRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Servicios Inactivas")
	@MemberOrder(sequence = "4")
	public List<Servicio> listarInactivos() {
		return serviciosRepository.listarInactivos();
	}
	
	@ActionLayout(named = "Listar Precios de este Servicio")
	@MemberOrder(sequence = "5")
	public List<PrecioHistoricoServicio> listarPreciosPorServicio(){
		return precioHistoricoServicioRepository.listarPreciosPorServicio(this);
	}

	
	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	ServicioRepository serviciosRepository;
	
	@Inject
	PrecioHistoricoServicioRepository precioHistoricoServicioRepository;
}
