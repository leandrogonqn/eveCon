package com.leanGomez.modules.simple.dom.evento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
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
import com.leanGomez.modules.simple.dom.estado.Estado;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;
import com.leanGomez.modules.simple.dom.pagodecliente.PagoDeCliente;
import com.leanGomez.modules.simple.dom.pagodecliente.PagoDeClienteRepository;
import com.leanGomez.modules.simple.dom.preciohistoricoservicio.PrecioHistoricoServicioRepository;
import com.leanGomez.modules.simple.dom.salon.Salon;
import com.leanGomez.modules.simple.dom.salon.SalonRepository;
import com.leanGomez.modules.simple.dom.servicio.Servicio;
import com.leanGomez.modules.simple.dom.servicio.ServicioConPrecio;
import com.leanGomez.modules.simple.dom.servicio.ServicioRepository;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEventoRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Evento")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "eventoId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "actualizarEstado", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.evento.Evento " + "WHERE eventoFechaDelEvento >= :hoy AND eventoEstado == :eventoEstado")})
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Evento implements Comparable<Evento>{

	// region > title
	public TranslatableString title() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return TranslatableString.tr("Fecha: " + sdf.format(getEventoFechaDelEvento()) + " - " + this.getEventoTipoDeEvento()
		+ " - Cliente: " + this.getEventoCliente().getPersonaNombre());
	}
	// endregion
	
	public String cssClass() {
		String a;
		a = eventoEstado.toString();
		return a;
	}

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
		this.eventoDescuento = 0.0;
		this.eventoRecargo = 0.0;
		this.eventoEleccionMusica=eventoEleccionMusica;
		this.eventoEstado = Estado.presupuestado;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha Presupuesto",hidden=Where.ALL_TABLES)
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
	@PropertyLayout(named = "Hora finalizacion",hidden=Where.ALL_TABLES)
	private String eventoHoraFinalizacion;
	public String getEventoHoraFinalizacion() {
		return eventoHoraFinalizacion;
	}
	public void setEventoHoraFinalizacion(String eventoHoraFinalizacion) {
		this.eventoHoraFinalizacion = eventoHoraFinalizacion;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Hora comienzo armado",hidden=Where.ALL_TABLES)
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
	@CollectionLayout(hidden=Where.EVERYWHERE)
	private List<Servicio> listaServicio;
	public List<Servicio> getListaServicio() {
		return listaServicio;
	}
	public void setListaServicio(List<Servicio> listaServicio) {
		this.listaServicio = listaServicio;
	}
	
	@ActionLayout(named="Precio Servicios", hidden=Where.ALL_TABLES)	 
	public Double getEventoPrecioServicios() {
		Double a = 0.0;
		for(int indice = 0;indice<listaServicio.size();indice++)
		{
			Servicio s = listaServicio.get(indice);
			Double p = precioHistoricoServicioRepository.mostrarPrecioPorFecha(s, this.eventoFechaPresupuesto);
			a = a + p;
		}
		return a;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descuento",hidden=Where.ALL_TABLES)
	private Double eventoDescuento;
	public Double getEventoDescuento() {
		return eventoDescuento;
	}
	public void setEventoDescuento(Double eventoDescuento) {
		this.eventoDescuento = eventoDescuento;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Recargo",hidden=Where.ALL_TABLES)
	private Double eventoRecargo;
	public Double getEventoRecargo() {
		return eventoRecargo;
	}
	public void setEventoRecargo(Double eventoRecargo) {
		this.eventoRecargo = eventoRecargo;
	}
	
	@ActionLayout(named="Precio Final")	
	public Double getEventoPrecioFinal() {
		Double a;
		a = getEventoPrecioServicios()-getEventoDescuento()+getEventoRecargo();
		return a;
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
	
	@ActionLayout(named="Saldo Restante")	
	public Double getSaldoRestante() {
		Double a = getEventoPrecioFinal();
		for(int indice = 0;indice<getEventoPagoDeCliente().size();indice++)
		{
			a = a - getEventoPagoDeCliente().get(indice).getPagoDeClienteMonto();
		}
		if(a<0) {
			messageService.informUser("ALERTA: el monto pagado es mayor al Precio Final");
		}
		return a;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Eleccion de musica",hidden=Where.ALL_TABLES)
	private String eventoEleccionMusica;
	public String getEventoEleccionMusica() {
		return eventoEleccionMusica;
	}
	public void setEventoEleccionMusica(String eventoEleccionMusica) {
		this.eventoEleccionMusica = eventoEleccionMusica;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Estado")
	private Estado eventoEstado;
	public Estado getEventoEstado() {
		return eventoEstado;
	}
	public void setEventoEstado(Estado eventoEstado) {
		this.eventoEstado = eventoEstado;
	}
	
	public List<ServicioConPrecio> getListaServicioConPrecio(){
		List<ServicioConPrecio> listaServicioConPrecio = new ArrayList<>();
		for(int indice = 0;indice<listaServicio.size();indice++)
		{
			Servicio s = listaServicio.get(indice);
			Double p = precioHistoricoServicioRepository.mostrarPrecioPorFecha(s, this.eventoFechaPresupuesto);
			listaServicioConPrecio.add(new ServicioConPrecio(s, p));
		    
		}

		return listaServicioConPrecio;
	}

	public Evento modificarEventoFechaPresupuesto(@ParameterLayout(named = "Fecha Presupuesto") final Date eventoFechaPresupuesto) {
		setEventoFechaPresupuesto(eventoFechaPresupuesto);
		return this;
	}

	public Date default0ModificarEventoFechaPresupuesto() {
		return getEventoFechaPresupuesto();
	}
	
	public Evento modificarEventoFechaDelEvento(@ParameterLayout(named = "Fecha del Evento") final Date eventoFechaDelEvento) {
		setEventoFechaDelEvento(eventoFechaDelEvento);
		eventoEstado.cambiarFechaEvento(this);
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
	@ActionLayout(named = "Listar todos los Eventos")
	@MemberOrder(sequence = "2")
	public List<Evento> listar() {
		return eventosRepository.listar();
	}
	
	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Anular/Desanular Evento")
	public Evento anulacion() {
		eventoEstado.anulacion(this);
		messageService.warnUser("El evento quedo en estado "+this.eventoEstado);
		return this;
	}

	@ActionLayout(named = "Agregar Servicio")
	public Evento agregarServicio(@ParameterLayout(named = "Servicio") final Servicio servicio) {
		if (this.getListaServicio().contains(servicio)) {
			messageService.warnUser("ERROR: El servicio ya está agregado en la lista");
		} else {
			this.getListaServicio().add(servicio);
			this.setListaServicio(this.getListaServicio());
		}
		eventoEstado.quitarPago(this);
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
		eventoEstado.agregarPago(this);
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
		eventoEstado.agregarPago(this);
		return this;
	}
	
	@ActionLayout(named = "Quitar Pago")
	public Evento quitarPago(@ParameterLayout(named = "Pago") PagoDeCliente pagoDeCliente) {
		Iterator<PagoDeCliente> it = getEventoPagoDeCliente().iterator();
		while (it.hasNext()) {
			PagoDeCliente lista = it.next();
			if (lista.equals(pagoDeCliente))
				it.remove();
		}
		eventoEstado.quitarPago(this);
		return this;
	}
	
	public List<PagoDeCliente> choices0QuitarPago() {
		return getEventoPagoDeCliente();
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
	
	@Inject
	PrecioHistoricoServicioRepository precioHistoricoServicioRepository;
	
}
