package com.leanGomez.modules.simple.dom.evento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;

import com.leanGomez.modules.simple.dom.cliente.Cliente;
import com.leanGomez.modules.simple.dom.cliente.ClienteRepository;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;
import com.leanGomez.modules.simple.dom.pagodecliente.PagoDeCliente;
import com.leanGomez.modules.simple.dom.pagodecliente.PagoDeClienteRepository;
import com.leanGomez.modules.simple.dom.salon.Salon;
import com.leanGomez.modules.simple.dom.salon.SalonRepository;
import com.leanGomez.modules.simple.dom.servicio.Servicio;
import com.leanGomez.modules.simple.dom.servicio.ServicioRepository;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEventoRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Evento")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "eventoId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.evento.Evento " + "WHERE eventoActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.evento.Evento " + "WHERE eventoActivo == false ") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Evento implements Comparable<Evento>{

	// region > title
	public TranslatableString title() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return TranslatableString.tr("Fecha: " + sdf.format(getEventoFechaDelEvento()) + " - " + this.getEventoTipoDeEvento()
		+ " - Cliente: " + this.getEventoCliente().getPersonaNombre());
	}
	// endregion

	public Evento(Date eventoFechaPresupuesto ,Date eventoFechaDelEvento, String eventoNombreAgasajado, Cliente eventoCliente, TipoDeEvento eventoTipoDeEvento,
			Salon eventoSalon, Integer eventoCantidadPersonas, String eventoHoraComienzo, String eventoHoraFinalizacion,
			String eventoHoraComienzoArmado, String eventoEleccionMusica) {
		super();
		this.eventoFechaPresupuesto = eventoFechaPresupuesto;
		this.eventoFechaDelEvento = eventoFechaDelEvento;
		this.eventoNombreAgasajado = eventoNombreAgasajado;
		this.eventoCliente = eventoCliente;
		this.eventoTipoDeEvento = eventoTipoDeEvento;
		this.eventoSalon = eventoSalon;
		this.eventoCantidadPersonas= eventoCantidadPersonas;
		this.eventoHoraComienzo= eventoHoraComienzo;
		this.eventoHoraFinalizacion= eventoHoraFinalizacion;
		this.eventoHoraComienzoArmado= eventoHoraComienzoArmado;
		this.eventoEleccionMusica=eventoEleccionMusica;
		this.eventoActivo = true;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha Presupuesto")
	private Date eventoFechaPresupuesto;
	public Date getEventoFechaPresupuesto() {
		return eventoFechaPresupuesto;
	}
	public void setEventoFechaPresupuesto(Date eventoFechaPresupuesto) {
		this.eventoFechaPresupuesto = eventoFechaPresupuesto;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha del Evento")
	private Date eventoFechaDelEvento;
	public Date getEventoFechaDelEvento() {
		return eventoFechaDelEvento;
	}
	public void setEventoFechaDelEvento(Date eventoFechaDelEvento) {
		this.eventoFechaDelEvento = eventoFechaDelEvento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre del agasajado")
	private String eventoNombreAgasajado;
	public String getEventoNombreAgasajado() {
		return eventoNombreAgasajado;
	}
	public void setEventoNombreAgasajado(String eventoNombreAgasajado) {
		this.eventoNombreAgasajado = eventoNombreAgasajado;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Cliente")
	private Cliente eventoCliente;
	public Cliente getEventoCliente() {
		return eventoCliente;
	}
	public void setEventoCliente(Cliente eventoCliente) {
		this.eventoCliente = eventoCliente;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo de evento")
	private TipoDeEvento eventoTipoDeEvento;
	public TipoDeEvento getEventoTipoDeEvento() {
		return eventoTipoDeEvento;
	}
	public void setEventoTipoDeEvento(TipoDeEvento eventoTipoDeEvento) {
		this.eventoTipoDeEvento = eventoTipoDeEvento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Salon")
	private Salon eventoSalon;
	public Salon getEventoSalon() {
		return eventoSalon;
	}
	public void setEventoSalon(Salon eventoSalon) {
		this.eventoSalon = eventoSalon;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Cantidad de personas")
	private Integer eventoCantidadPersonas;
	public Integer getEventoCantidadPersonas() {
		return eventoCantidadPersonas;
	}
	public void setEventoCantidadPersonas(Integer eventoCantidadPersonas) {
		this.eventoCantidadPersonas = eventoCantidadPersonas;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Hora comienzo")
	private String eventoHoraComienzo;
	public String getEventoHoraComienzo() {
		return eventoHoraComienzo;
	}
	public void setEventoHoraComienzo(String eventoHoraComienzo) {
		this.eventoHoraComienzo = eventoHoraComienzo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Hora finalizacion")
	private String eventoHoraFinalizacion;
	public String getEventoHoraFinalizacion() {
		return eventoHoraFinalizacion;
	}
	public void setEventoHoraFinalizacion(String eventoHoraFinalizacion) {
		this.eventoHoraFinalizacion = eventoHoraFinalizacion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Hora comienzo armado")
	private String eventoHoraComienzoArmado;
	public String getEventoHoraComienzoArmado() {
		return eventoHoraComienzoArmado;
	}
	public void setEventoHoraComienzoArmado(String eventoHoraComienzoArmado) {
		this.eventoHoraComienzoArmado = eventoHoraComienzoArmado;
	}
	
	@Join
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
	private List<Servicio> listaServicio;
	public List<Servicio> getListaServicio() {
		return listaServicio;
	}
	public void setListaServicio(List<Servicio> listaServicio) {
		this.listaServicio = listaServicio;
	}
	
	public Double getEventoPrecioServicios() {
		return 100.0;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descuento")
	private Double eventoDescuento;
	public Double getEventoDescuento() {
		return eventoDescuento;
	}
	public void setEventoDescuento(Double eventoDescuento) {
		this.eventoDescuento = eventoDescuento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Recargo")
	private Double eventoRecargo;
	public Double getEventoRecargo() {
		return eventoRecargo;
	}
	public void setEventoRecargo(Double eventoRecargo) {
		this.eventoRecargo = eventoRecargo;
	}
	
	public Double getEventoPrecioFinal() {
		return 100.0;
	}
		
	@Join
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Lista de Pagos")
	private List<PagoDeCliente> eventoPagoDeCliente;
	public List<PagoDeCliente> getEventoPagoDeCliente() {
		return eventoPagoDeCliente;
	}
	public void setEventoPagoDeCliente(List<PagoDeCliente> eventoPagoDeCliente) {
		this.eventoPagoDeCliente = eventoPagoDeCliente;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Eleccion de musica")
	private String eventoEleccionMusica;
	public String getEventoEleccionMusica() {
		return eventoEleccionMusica;
	}
	public void setEventoEleccionMusica(String eventoEleccionMusica) {
		this.eventoEleccionMusica = eventoEleccionMusica;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean eventoActivo;
	public boolean isEventoActivo() {
		return eventoActivo;
	}
	public void setEventoActivo(boolean eventoActivo) {
		this.eventoActivo = eventoActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarEvento() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setEventoActivo(false);
	}
	
	public Evento modificarEventoFechaDelEvento(@ParameterLayout(named = "Fecha del Evento") final Date eventoFechaDelEvento) {
		setEventoFechaDelEvento(eventoFechaDelEvento);
		return this;
	}

	public Date default0ModificarEventoFechaDelEvento() {
		return getEventoFechaDelEvento();
	}

	public Evento modificarAgasajado(@ParameterLayout(named = "Agasajado") final String eventoNombreAgasajado) {
		setEventoNombreAgasajado(eventoNombreAgasajado);
		return this;
	}

	public String default0ModificarAgasajado() {
		return getEventoNombreAgasajado();
	}

	public Evento modificarEventoCliente(@ParameterLayout(named = "Cliente") final Cliente eventoCliente) {
		setEventoCliente(eventoCliente);
		return this;
	}

	public Cliente default0ModificarEventoCliente() {
		return getEventoCliente();
	}
	
	public List<Cliente> choices0ModificarEventoCliente() {
		return clienteRepository.listarActivos();
	}

	public Evento modificarEventoTipoDeEvento(@ParameterLayout(named = "Tipo De Evento") final TipoDeEvento eventoTipoDeEvento) {
		setEventoTipoDeEvento(eventoTipoDeEvento);
		return this;
	}

	public TipoDeEvento default0ModificarEventoTipoDeEvento() {
		return getEventoTipoDeEvento();
	}
	
	public List<TipoDeEvento> choices0ModificarEventoTipoDeEvento() {
		return tipoDeEventoRepository.listarActivos();
	}
	
	public Evento modificarEventoSalon(@ParameterLayout(named = "Salon") final Salon eventoSalon) {
		setEventoSalon(eventoSalon);
		return this;
	}

	public Salon default0ModificarEventoSalon() {
		return getEventoSalon();
	}

	public List<Salon> choices0ModificarEventoSalon() {
		return salonRepository.listarActivos();
	}
	
	public Evento modificarCantidadPersonas(@ParameterLayout(named = "Cantidad Personas") final int eventoCantidadPersonas) {
		setEventoCantidadPersonas(eventoCantidadPersonas);
		return this;
	}

	public int default0ModificarCantidadPersonas() {
		return getEventoCantidadPersonas();
	}
	
	public Evento modificarEventoHoraComienzo(@ParameterLayout(named = "Hora Comienzo") final String eventoHoraComienzo) {
		setEventoHoraComienzo(eventoHoraComienzo);
		return this;
	}

	public String default0ModificarEventoHoraComienzo() {
		return getEventoHoraComienzo();
	}
	
	public Evento modificarEventoHoraFinalizacion(@ParameterLayout(named = "Hora Finalizacion") final String eventoHoraFinalizacion) {
		setEventoHoraFinalizacion(eventoHoraFinalizacion);
		return this;
	}

	public String default0ModificarEventoHoraFinalizacion() {
		return getEventoHoraFinalizacion();
	}
	
	public Evento modificarEventoHoraComienzoArmado(@ParameterLayout(named = "Hora Comienzo Armado") final String eventoHoraComienzoArmado) {
		setEventoHoraComienzoArmado(eventoHoraComienzoArmado);
		return this;
	}

	public String default0ModificarEventoHoraComienzoArmado() {
		return getEventoHoraComienzoArmado();
	}
	
	public Evento modificarEventoDescuento(@ParameterLayout(named = "Descuento") final Double eventoDescuento) {
		setEventoDescuento(eventoDescuento);
		return this;
	}

	public Double default0ModificarEventoDescuento() {
		return getEventoDescuento();
	}
	
	public Evento modificarEventoRecargo(@ParameterLayout(named = "Recargo") final Double eventoRecargo) {
		setEventoRecargo(eventoRecargo);
		return this;
	}

	public Double default0ModificarEventoRecargo() {
		return getEventoRecargo();
	}

	public Evento modificarEventoEleccionMusica(@ParameterLayout(named = "Eleccion Musica") final String eventoEleccionMusica) {
		setEventoEleccionMusica(eventoEleccionMusica);
		return this;
	}

	public String default0ModificarEventoEleccionMusica() {
		return getEventoEleccionMusica();
	}
	
	public Evento modificarActivo(@ParameterLayout(named = "Activo") final boolean eventoActivo) {
		setEventoActivo(eventoActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isEventoActivo();
	}
	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getEventoCliente().toString()+getEventoTipoDeEvento().toString()+getEventoFechaDelEvento();
	}

	@Override
	public int compareTo(final Evento evento) {
		return this.eventoCliente.toString().compareTo(evento.eventoCliente.toString());
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las Eventos")
	@MemberOrder(sequence = "2")
	public List<Evento> listar() {
		return eventosRepository.listar();
	}

	@ActionLayout(named = "Listar Evento Activas")
	@MemberOrder(sequence = "3")
	public List<Evento> listarActivos() {
		return eventosRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Eventos Inactivas")
	@MemberOrder(sequence = "4")
	public List<Evento> listarInactivos() {
		return eventosRepository.listarInactivos();
	}
	
	@ActionLayout(named = "Agregar Servicio")
	public Evento agregarServicio(@ParameterLayout(named = "Servicio") final Servicio servicio) {
		if (this.getListaServicio().contains(servicio)) {
			messageService.warnUser("ERROR: El servicio ya está agregado en la lista");
		} else {
			this.getListaServicio().add(servicio);
			this.setListaServicio(this.getListaServicio());
		}
		return this;
	}

	public List<Servicio> choices0AgregarServicio() {
		return servicioRepository.listarActivos();
	}

	@ActionLayout(named = "Quitar Servicio")
	public Evento quitarServicio(@ParameterLayout(named = "Servicio") Servicio servicio) {
		Iterator<Servicio> it = getListaServicio().iterator();
		while (it.hasNext()) {
			Servicio lista = it.next();
			if (lista.equals(servicio))
				it.remove();
		}
		return this;
	}

	public List<Servicio> choices0QuitarServicio() {
		return getListaServicio();
	}
	
	@ActionLayout(named="Crear Pago")
	public Evento crearPago(@ParameterLayout(named = "Fecha") Date pagoDeClienteFecha,
								@ParameterLayout(named = "Monto") Double pagoDeClienteMonto,
								@Nullable @ParameterLayout(named = "Observaciones",multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) String pagoDeClienteObservaciones) {
		this.getEventoPagoDeCliente().add(pagoDeClienteRepository.crear(this, pagoDeClienteMonto, 
				pagoDeClienteFecha, pagoDeClienteObservaciones));
		this.setEventoPagoDeCliente(this.getEventoPagoDeCliente());
		return this;
	}
	
//	@ActionLayout(named = "Listar Localidades de esta Evento")
//	@MemberOrder(sequence = "5")
//	public List<Localidad> listarEvento(){
//		return localidadRepository.buscarPorEvento(this);
//	}

	
	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	EventoRepository eventosRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
	@Inject
	ClienteRepository clienteRepository;
	
	@Inject
	TipoDeEventoRepository tipoDeEventoRepository;
	
	@Inject
	SalonRepository salonRepository;
	
	@Inject
	ServicioRepository servicioRepository;
	
	@Inject
	PagoDeClienteRepository pagoDeClienteRepository;
	
}
