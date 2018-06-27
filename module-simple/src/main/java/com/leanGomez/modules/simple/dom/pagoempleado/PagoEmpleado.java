package com.leanGomez.modules.simple.dom.pagoempleado;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;
import com.leanGomez.modules.simple.dom.empleado.Empleado;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "PagoEmpleado")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "pagoEmpleadoId")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class PagoEmpleado implements Comparable<PagoEmpleado>{
	
	public TranslatableString title() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return TranslatableString.tr("Fecha: " + sdf.format(getPagoEmpleadoFecha()) + " - " + this.getPagoEmpleadoMonto());
	}

	public PagoEmpleado(Empleado pagoEmpleadoEmpleado, double pagoEmpleadoMonto, Date pagoEmpleadoFecha, String pagoEmpleadoObservaciones) {
		super();
		this.pagoEmpleadoEmpleado=pagoEmpleadoEmpleado;
		this.pagoEmpleadoMonto = pagoEmpleadoMonto;
		this.pagoEmpleadoFecha = pagoEmpleadoFecha;
		this.pagoEmpleadoObservaciones = pagoEmpleadoObservaciones;
		this.pagoEmpleadoActivo = true;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Empleado",hidden=Where.ALL_TABLES)
	private Empleado pagoEmpleadoEmpleado;
	public Empleado getPagoEmpleadoEmpleado() {
		return pagoEmpleadoEmpleado;
	}
	public void setPagoEmpleadoEmpleado(Empleado pagoEmpleadoEmpleado) {
		this.pagoEmpleadoEmpleado = pagoEmpleadoEmpleado;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Monto")
	private double pagoEmpleadoMonto;
	public double getPagoEmpleadoMonto() {
		return pagoEmpleadoMonto;
	}
	public void setPagoEmpleadoMonto(double pagoEmpleadoMonto) {
		this.pagoEmpleadoMonto = pagoEmpleadoMonto;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha")
	private Date pagoEmpleadoFecha;
	
	public Date getPagoEmpleadoFecha() {
		return pagoEmpleadoFecha;
	}
	public void setPagoEmpleadoFecha(Date pagoEmpleadoFecha) {
		this.pagoEmpleadoFecha = pagoEmpleadoFecha;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Observaciones")
	private String pagoEmpleadoObservaciones;
	public String getPagoEmpleadoObservaciones() {
		return pagoEmpleadoObservaciones;
	}
	public void setPagoEmpleadoObservaciones(String pagoEmpleadoObservaciones) {
		this.pagoEmpleadoObservaciones = pagoEmpleadoObservaciones;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo",hidden=Where.ALL_TABLES)
	private boolean pagoEmpleadoActivo;

	public boolean isPagoEmpleadoActivo() {
		return pagoEmpleadoActivo;
	}
	public void setPagoEmpleadoActivo(boolean pagoEmpleadoActivo) {
		this.pagoEmpleadoActivo = pagoEmpleadoActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarPagoEmpleado() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPagoEmpleadoActivo(false);
	}
	
	public PagoEmpleado modificarMonto(@ParameterLayout(named = "Monto") final double pagoEmpleadoMonto) {
		setPagoEmpleadoMonto(pagoEmpleadoMonto);
		return this;
	}

	public double default0ModificarMonto() {
		return getPagoEmpleadoMonto();
	}
	
	public PagoEmpleado modificarFecha(@ParameterLayout(named = "Fecha") final Date pagoEmpleadoFecha) {
		setPagoEmpleadoFecha(pagoEmpleadoFecha);
		return this;
	}

	public Date default0ModificarFecha() {
		return getPagoEmpleadoFecha();
	}
	
	public PagoEmpleado modificarObservaciones(@ParameterLayout(named = "Observaciones") final String pagoEmpleadoObservaciones) {
		setPagoEmpleadoObservaciones(pagoEmpleadoObservaciones);
		return this;
	}

	public String default0ModificarObservaciones() {
		return getPagoEmpleadoObservaciones();
	}

	public PagoEmpleado modificarActivo(@ParameterLayout(named = "Activo") final boolean pagoEmpleadoActivo) {
		setPagoEmpleadoActivo(pagoEmpleadoActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isPagoEmpleadoActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return null; //getPagoEmpleadoEmpleado()+getPagoEmpleadoMonto();
	}

	@Override
	public int compareTo(final PagoEmpleado pagoEmpleado) {
		return 0; //this.pagoEmpleadoEmpleado.compareTo(pagoEmpleado.pagoEmpleadoEmpleado);
	}

	// endregion

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	PagoEmpleadoRepository pagoEmpleadosRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
}
