package com.leanGomez.modules.simple.dom.ocupacion;

import java.util.Date;
import java.util.Iterator;
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
import com.leanGomez.modules.simple.dom.preciohistoricoocupacion.PrecioHistoricoOcupacion;
import com.leanGomez.modules.simple.dom.preciohistoricoocupacion.PrecioHistoricoOcupacionRepository;
import com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicio;
import com.leanGomez.modules.simple.dom.servicio.Servicio;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Ocupacion")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "ocupacionId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.ocupacion.Ocupacion "
			+ "WHERE ocupacionNombre.toLowerCase().indexOf(:ocupacionNombre) >= 0 "),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.ocupacion.Ocupacion " + "WHERE ocupacionActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.ocupacion.Ocupacion " + "WHERE ocupacionActivo == false ") })
@javax.jdo.annotations.Unique(name = "Ocupacion_ocupacionNombre_UNQ", members = { "ocupacionNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Ocupacion implements Comparable<Ocupacion>{

	public Ocupacion(String ocupacionNombre, String ocupacionDescripcion) {
		super();
		this.ocupacionNombre = ocupacionNombre;
		this.ocupacionDescripcion = ocupacionDescripcion;
		this.ocupacionActivo = true;
	}
	
	public String cssClass() {
		return (isOcupacionActivo() == true) ? "activo" : "inactivo";
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Ocupacion")
	private String ocupacionNombre;
	public String getOcupacionNombre() {
		return ocupacionNombre;
	}
	public void setOcupacionNombre(String ocupacionNombre) {
		this.ocupacionNombre = ocupacionNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descripcion")
	private String ocupacionDescripcion;
	public String getOcupacionDescripcion() {
		return ocupacionDescripcion;
	}
	public void setOcupacionDescripcion(String ocupacionDescripcion) {
		this.ocupacionDescripcion = ocupacionDescripcion;
	}
	
	public Double getPrecioActualOcupacion() {
		Date hoy = new Date();
		Double a = precioHistoricoEmpleadoRepository.mostrarPrecioPorFecha(this, hoy);
		return a;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean ocupacionActivo;
	public boolean isOcupacionActivo() {
		return ocupacionActivo;
	}
	public void setOcupacionActivo(boolean ocupacionActivo) {
		this.ocupacionActivo = ocupacionActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarOcupacion() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setOcupacionActivo(false);
	}
	
	public Ocupacion modificarNombre(@ParameterLayout(named = "Nombre") final String ocupacionNombre) {
		setOcupacionNombre(ocupacionNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getOcupacionNombre();
	}

	public Ocupacion modificarDescripcion(@ParameterLayout(named = "Descripcion") final String ocupacionDescripcion) {
		setOcupacionDescripcion(ocupacionDescripcion);
		return this;
	}

	public String default0ModificarDescripcion() {
		return getOcupacionDescripcion();
	}
	
	//MODIFICAR PRECIO
	public Ocupacion modificarPrecio(@ParameterLayout(named = "Precio") final Double servicioPrecio) {
		Date h = new Date();
		Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
		List<PrecioHistoricoOcupacion> p = precioHistoricoEmpleadoRepository.listarPreciosPorOcupacion(this);
		Iterator<PrecioHistoricoOcupacion> it = p.iterator();
		while (it.hasNext()) {
			PrecioHistoricoOcupacion lista = it.next();
			if (lista.getPrecioHistoricoOcupacionFechaHasta()==null)
				lista.setPrecioHistoricoOcupacionFechaHasta(hoy);
		}
		precioHistoricoEmpleadoRepository.crear(this, servicioPrecio);
		return this;
	}
	
	public Double default0ModificarPrecio() {
		return getPrecioActualOcupacion();
	}
	
	public Ocupacion modificarActivo(@ParameterLayout(named = "Activo") final boolean ocupacionActivo) {
		setOcupacionActivo(ocupacionActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isOcupacionActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getOcupacionNombre();
	}

	@Override
	public int compareTo(final Ocupacion ocupacion) {
		return this.ocupacionNombre.compareTo(ocupacion.ocupacionNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todos los Tipos De Equipos")
	@MemberOrder(sequence = "2")
	public List<Ocupacion> listar() {
		return ocupacionsRepository.listar();
	}

	@ActionLayout(named = "Listar Tipos De Equipos Activas")
	@MemberOrder(sequence = "3")
	public List<Ocupacion> listarActivos() {
		return ocupacionsRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Tipos De Equipos Inactivas")
	@MemberOrder(sequence = "4")
	public List<Ocupacion> listarInactivos() {
		return ocupacionsRepository.listarInactivos();
	}
	
//	@ActionLayout(named = "Listar equipos de este Tipo De Equipo")
//	@MemberOrder(sequence = "5")
//	public List<Localidad> listarOcupacion(){
//		return localidadRepository.buscarPorOcupacion(this);
//	}
	
	@ActionLayout(named = "Listar Precios de este Servicio")
	@MemberOrder(sequence = "5")
	public List<PrecioHistoricoOcupacion> listarPreciosPorEmpleado(){
		return precioHistoricoEmpleadoRepository.listarPreciosPorOcupacion(this);
	}

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	OcupacionRepository ocupacionsRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
	@Inject
	PrecioHistoricoOcupacionRepository precioHistoricoEmpleadoRepository;
	
}
