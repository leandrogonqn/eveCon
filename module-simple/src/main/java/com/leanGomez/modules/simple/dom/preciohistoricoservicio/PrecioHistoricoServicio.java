package com.leanGomez.modules.simple.dom.preciohistoricoservicio;

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
import com.leanGomez.modules.simple.dom.servicio.Servicio;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "PrecioHistoricoServicio")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "precioHistoricoServicioId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "listarPreciosPorServicio", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicio " + "WHERE precioHistoricoServicioServicio == :precioHistoricoServicioServicio"),
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicio " + "WHERE precioHistoricoServicioActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicio " + "WHERE precioHistoricoServicioActivo == false ") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class PrecioHistoricoServicio implements Comparable<PrecioHistoricoServicio>{

	public PrecioHistoricoServicio(Servicio precioHistoricoServicioServicio, Double precioHistoricoServicioPrecio) {
		super();
		Date h = new Date();
		Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
		this.precioHistoricoServicioServicio = precioHistoricoServicioServicio;
		this.precioHistoricoServicioFechaDesde = hoy;
		this.precioHistoricoServicioPrecio = precioHistoricoServicioPrecio;
		this.precioHistoricoServicioActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Servicio")
	private Servicio precioHistoricoServicioServicio;
	public Servicio getPrecioHistoricoServicioServicio() {
		return precioHistoricoServicioServicio;
	}
	public void setPrecioHistoricoServicioServicio(Servicio precioHistoricoServicioServicio) {
		this.precioHistoricoServicioServicio = precioHistoricoServicioServicio;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha desde")
	private Date precioHistoricoServicioFechaDesde;
	public Date getPrecioHistoricoServicioFechaDesde() {
		return precioHistoricoServicioFechaDesde;
	}
	public void setPrecioHistoricoServicioFechaDesde(Date precioHistoricoServicioFechaDesde) {
		this.precioHistoricoServicioFechaDesde = precioHistoricoServicioFechaDesde;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha hasta")
	private Date precioHistoricoServicioFechaHasta;
	public Date getPrecioHistoricoServicioFechaHasta() {
		return precioHistoricoServicioFechaHasta;
	}
	public void setPrecioHistoricoServicioFechaHasta(Date precioHistoricoServicioFechaHasta) {
		this.precioHistoricoServicioFechaHasta = precioHistoricoServicioFechaHasta;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Precio")
	private Double precioHistoricoServicioPrecio;
	public Double getPrecioHistoricoServicioPrecio() {
		return precioHistoricoServicioPrecio;
	}
	public void setPrecioHistoricoServicioPrecio(Double precioHistoricoServicioPrecio) {
		this.precioHistoricoServicioPrecio = precioHistoricoServicioPrecio;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean precioHistoricoServicioActivo;
	public boolean isPrecioHistoricoServicioActivo() {
		return precioHistoricoServicioActivo;
	}
	public void setPrecioHistoricoServicioActivo(boolean precioHistoricoServicioActivo) {
		this.precioHistoricoServicioActivo = precioHistoricoServicioActivo;
	}
	
	// region > toString, compareTo
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return "Precio: " + getPrecioHistoricoServicioPrecio().toString() + " Desde: " + 
		sdf.format(getPrecioHistoricoServicioFechaDesde());
	}

	@Override
	public int compareTo(final PrecioHistoricoServicio precioHistoricoServicio) {
		return this.precioHistoricoServicioFechaDesde.compareTo(precioHistoricoServicio.precioHistoricoServicioFechaDesde);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las PrecioHistoricoServicios")
	@MemberOrder(sequence = "2")
	public List<PrecioHistoricoServicio> listar() {
		return precioHistoricoServiciosRepository.listar();
	}

	@ActionLayout(named = "Listar PrecioHistoricoServicio Activas")
	@MemberOrder(sequence = "3")
	public List<PrecioHistoricoServicio> listarActivos() {
		return precioHistoricoServiciosRepository.listarActivos();
	}

	@ActionLayout(named = "Listar PrecioHistoricoServicios Inactivas")
	@MemberOrder(sequence = "4")
	public List<PrecioHistoricoServicio> listarInactivos() {
		return precioHistoricoServiciosRepository.listarInactivos();
	}
	
	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	PrecioHistoricoServicioRepository precioHistoricoServiciosRepository;
	
}
