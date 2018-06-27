package com.leanGomez.modules.simple.dom.preciohistoricoocupacion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "PrecioHistoricoOcupacion")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "precioHistoricoOcupacionId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "listarPreciosPorOcupacion", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.preciohistoricoocupacion.PrecioHistoricoOcupacion " + "WHERE precioHistoricoOcupacionOcupacion == :precioHistoricoOcupacionOcupacion"),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.preciohistoricoocupacion.PrecioHistoricoOcupacion " + "WHERE precioHistoricoOcupacionActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.preciohistoricoocupacion.PrecioHistoricoOcupacion " + "WHERE precioHistoricoOcupacionActivo == false ") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class PrecioHistoricoOcupacion implements Comparable<PrecioHistoricoOcupacion>{

	public PrecioHistoricoOcupacion(Ocupacion precioHistoricoOcupacionOcupacion, Double precioHistoricoOcupacionPrecio) {
		super();
		Date h = new Date();
		Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
		this.precioHistoricoOcupacionOcupacion = precioHistoricoOcupacionOcupacion;
		this.precioHistoricoOcupacionFechaDesde = hoy;
		this.precioHistoricoOcupacionPrecio = precioHistoricoOcupacionPrecio;
		this.precioHistoricoOcupacionActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Ocupacion")
	private Ocupacion precioHistoricoOcupacionOcupacion;
	public Ocupacion getPrecioHistoricoOcupacionOcupacion() {
		return precioHistoricoOcupacionOcupacion;
	}
	public void setPrecioHistoricoOcupacionOcupacion(Ocupacion precioHistoricoOcupacionOcupacion) {
		this.precioHistoricoOcupacionOcupacion = precioHistoricoOcupacionOcupacion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha desde")
	private Date precioHistoricoOcupacionFechaDesde;
	public Date getPrecioHistoricoOcupacionFechaDesde() {
		return precioHistoricoOcupacionFechaDesde;
	}
	public void setPrecioHistoricoOcupacionFechaDesde(Date precioHistoricoOcupacionFechaDesde) {
		this.precioHistoricoOcupacionFechaDesde = precioHistoricoOcupacionFechaDesde;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha hasta")
	private Date precioHistoricoOcupacionFechaHasta;
	public Date getPrecioHistoricoOcupacionFechaHasta() {
		return precioHistoricoOcupacionFechaHasta;
	}
	public void setPrecioHistoricoOcupacionFechaHasta(Date precioHistoricoOcupacionFechaHasta) {
		this.precioHistoricoOcupacionFechaHasta = precioHistoricoOcupacionFechaHasta;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Precio")
	private Double precioHistoricoOcupacionPrecio;
	public Double getPrecioHistoricoOcupacionPrecio() {
		return precioHistoricoOcupacionPrecio;
	}
	public void setPrecioHistoricoOcupacionPrecio(Double precioHistoricoOcupacionPrecio) {
		this.precioHistoricoOcupacionPrecio = precioHistoricoOcupacionPrecio;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean precioHistoricoOcupacionActivo;
	public boolean isPrecioHistoricoOcupacionActivo() {
		return precioHistoricoOcupacionActivo;
	}
	public void setPrecioHistoricoOcupacionActivo(boolean precioHistoricoOcupacionActivo) {
		this.precioHistoricoOcupacionActivo = precioHistoricoOcupacionActivo;
	}
	
	// region > toString, compareTo
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return "Precio: " + getPrecioHistoricoOcupacionPrecio().toString() + " Desde: " + 
		sdf.format(getPrecioHistoricoOcupacionFechaDesde());
	}

	@Override
	public int compareTo(final PrecioHistoricoOcupacion precioHistoricoOcupacion) {
		return this.precioHistoricoOcupacionFechaDesde.compareTo(precioHistoricoOcupacion.precioHistoricoOcupacionFechaDesde);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las PrecioHistoricoOcupacions")
	@MemberOrder(sequence = "2")
	public List<PrecioHistoricoOcupacion> listar() {
		return precioHistoricoOcupacionsRepository.listar();
	}

	@ActionLayout(named = "Listar PrecioHistoricoOcupacion Activas")
	@MemberOrder(sequence = "3")
	public List<PrecioHistoricoOcupacion> listarActivos() {
		return precioHistoricoOcupacionsRepository.listarActivos();
	}

	@ActionLayout(named = "Listar PrecioHistoricoOcupacions Inactivas")
	@MemberOrder(sequence = "4")
	public List<PrecioHistoricoOcupacion> listarInactivos() {
		return precioHistoricoOcupacionsRepository.listarInactivos();
	}
	
	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	PrecioHistoricoOcupacionRepository precioHistoricoOcupacionsRepository;
	
}
