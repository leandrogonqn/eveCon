package com.leanGomez.modules.simple.dom.pagodecliente;

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

import com.leanGomez.modules.simple.dom.evento.Evento;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "PagoDeCliente")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "pagoDeClienteId")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class PagoDeCliente implements Comparable<PagoDeCliente>{
	
	public TranslatableString title() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return TranslatableString.tr("Fecha: " + sdf.format(getPagoDeClienteFecha()) + " - " + this.getPagoDeClienteMonto());
	}

	public PagoDeCliente(Evento pagoDeClienteEvento, double pagoDeClienteMonto, Date pagoDeClienteFecha, String pagoDeClienteObservaciones) {
		super();
		this.pagoDeClienteEvento=pagoDeClienteEvento;
		this.pagoDeClienteMonto = pagoDeClienteMonto;
		this.pagoDeClienteFecha = pagoDeClienteFecha;
		this.pagoDeClienteObservaciones = pagoDeClienteObservaciones;
		this.pagoDeClienteActivo = true;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Evento",hidden=Where.ALL_TABLES)
	private Evento pagoDeClienteEvento;
	public Evento getPagoDeClienteEvento() {
		return pagoDeClienteEvento;
	}
	public void setPagoDeClienteEvento(Evento pagoDeClienteEvento) {
		this.pagoDeClienteEvento = pagoDeClienteEvento;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Monto")
	private double pagoDeClienteMonto;
	public double getPagoDeClienteMonto() {
		return pagoDeClienteMonto;
	}
	public void setPagoDeClienteMonto(double pagoDeClienteMonto) {
		this.pagoDeClienteMonto = pagoDeClienteMonto;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha")
	private Date pagoDeClienteFecha;
	
	public Date getPagoDeClienteFecha() {
		return pagoDeClienteFecha;
	}
	public void setPagoDeClienteFecha(Date pagoDeClienteFecha) {
		this.pagoDeClienteFecha = pagoDeClienteFecha;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Observaciones")
	private String pagoDeClienteObservaciones;
	public String getPagoDeClienteObservaciones() {
		return pagoDeClienteObservaciones;
	}
	public void setPagoDeClienteObservaciones(String pagoDeClienteObservaciones) {
		this.pagoDeClienteObservaciones = pagoDeClienteObservaciones;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo",hidden=Where.ALL_TABLES)
	private boolean pagoDeClienteActivo;

	public boolean isPagoDeClienteActivo() {
		return pagoDeClienteActivo;
	}
	public void setPagoDeClienteActivo(boolean pagoDeClienteActivo) {
		this.pagoDeClienteActivo = pagoDeClienteActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarPagoDeCliente() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setPagoDeClienteActivo(false);
	}
	
	public PagoDeCliente modificarMonto(@ParameterLayout(named = "Monto") final double pagoDeClienteMonto) {
		setPagoDeClienteMonto(pagoDeClienteMonto);
		return this;
	}

	public double default0ModificarMonto() {
		return getPagoDeClienteMonto();
	}
	
	public PagoDeCliente modificarFecha(@ParameterLayout(named = "Fecha") final Date pagoDeClienteFecha) {
		setPagoDeClienteFecha(pagoDeClienteFecha);
		return this;
	}

	public Date default0ModificarFecha() {
		return getPagoDeClienteFecha();
	}
	
	public PagoDeCliente modificarObservaciones(@ParameterLayout(named = "Observaciones") final String pagoDeClienteObservaciones) {
		setPagoDeClienteObservaciones(pagoDeClienteObservaciones);
		return this;
	}

	public String default0ModificarObservaciones() {
		return getPagoDeClienteObservaciones();
	}

	public PagoDeCliente modificarActivo(@ParameterLayout(named = "Activo") final boolean pagoDeClienteActivo) {
		setPagoDeClienteActivo(pagoDeClienteActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isPagoDeClienteActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return null; //getPagoDeClienteEvento()+getPagoDeClienteMonto();
	}

	@Override
	public int compareTo(final PagoDeCliente pagoDeCliente) {
		return 0; //this.pagoDeClienteEvento.compareTo(pagoDeCliente.pagoDeClienteEvento);
	}

	// endregion

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	PagoDeClienteRepository pagoDeClientesRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
}
