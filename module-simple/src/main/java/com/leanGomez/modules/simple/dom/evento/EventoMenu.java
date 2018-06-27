package com.leanGomez.modules.simple.dom.evento;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

import com.leanGomez.modules.simple.dom.cliente.Cliente;
import com.leanGomez.modules.simple.dom.cliente.ClienteRepository;
import com.leanGomez.modules.simple.dom.estado.Estado;
import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;
import com.leanGomez.modules.simple.dom.provincia.Provincia;
import com.leanGomez.modules.simple.dom.salon.Salon;
import com.leanGomez.modules.simple.dom.salon.SalonRepository;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEventoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.EventoMenu", repositoryFor = Evento.class)
@DomainServiceLayout(named = "Eventos", menuOrder = "30.1")
public class EventoMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las eventos")
	@MemberOrder(sequence = "2")
	public List<Evento> listar() {
		return eventosRepository.listar();
	}
	
	@ActionLayout(named = "Crear Evento")
	@MemberOrder(sequence = "1")
	public Evento crear(@ParameterLayout(named = "Fecha presupuesto") final Date eventoFechaPresupuesto,
			@Nullable@ParameterLayout(named = "Fecha del evento") @Parameter(optionality=Optionality.OPTIONAL)final Date eventoFechaDelEvento, 
			@Nullable @ParameterLayout(named = "Agasajado") @Parameter(optionality=Optionality.OPTIONAL) final String eventoNombreAgasajado, 
			@ParameterLayout(named = "Cliente") final Cliente eventoCliente, 
			@ParameterLayout(named = "Tipo de Evento") final TipoDeEvento eventoTipoDeEvento, 
			@ParameterLayout(named = "Salon") final Salon eventoSalon, 
			@Nullable @ParameterLayout(named = "Cantidad de Personas") @Parameter(optionality=Optionality.OPTIONAL) final Integer eventoCantidadPersonas,
			@Nullable @ParameterLayout(named = "Hora de Comienzo") @Parameter(optionality=Optionality.OPTIONAL) final String eventoHoraComienzo, 
			@Nullable @ParameterLayout(named = "Hora de Finalizacion") @Parameter(optionality=Optionality.OPTIONAL) final String eventoHoraFinalizacion, 
			@Nullable @ParameterLayout(named = "Hora de Comienzo de Armado") @Parameter(optionality=Optionality.OPTIONAL) final String eventoHoraComienzoArmado, 
			@Nullable @ParameterLayout(named = "Eleccion de Musica", multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) final String eventoEleccionMusica) {
		return eventosRepository.crear(eventoFechaPresupuesto, eventoFechaDelEvento, eventoNombreAgasajado, eventoCliente, 
				eventoTipoDeEvento, eventoSalon, eventoCantidadPersonas, eventoHoraComienzo, 
				eventoHoraFinalizacion, eventoHoraComienzoArmado, eventoEleccionMusica);
	}
	
	public Date default0Crear() {
		Date hoy = new Date();
		return hoy;
	}
	
	public List<Cliente> choices3Crear() {
		return clienteRepository.listarActivos();
	}
	
	public List<TipoDeEvento> choices4Crear() {
		return tipoDeEventoRepository.listarActivos();
	}
	
	public List<Salon> choices5Crear() {
		return salonRepository.listarActivos();
	}
	
	public String validateCrear(@ParameterLayout(named = "Fecha presupuesto") final Date eventoFechaPresupuesto,
			@Nullable@ParameterLayout(named = "Fecha del evento") @Parameter(optionality=Optionality.OPTIONAL)final Date eventoFechaDelEvento, 
			@Nullable @ParameterLayout(named = "Agasajado") @Parameter(optionality=Optionality.OPTIONAL) final String eventoNombreAgasajado, 
			@ParameterLayout(named = "Cliente") final Cliente eventoCliente, 
			@ParameterLayout(named = "Tipo de Evento") final TipoDeEvento eventoTipoDeEvento, 
			@ParameterLayout(named = "Salon") final Salon eventoSalon, 
			@Nullable @ParameterLayout(named = "Cantidad de Personas") @Parameter(optionality=Optionality.OPTIONAL) final Integer eventoCantidadPersonas,
			@Nullable @ParameterLayout(named = "Hora de Comienzo") @Parameter(optionality=Optionality.OPTIONAL) final String eventoHoraComienzo, 
			@Nullable @ParameterLayout(named = "Hora de Finalizacion") @Parameter(optionality=Optionality.OPTIONAL) final String eventoHoraFinalizacion, 
			@Nullable @ParameterLayout(named = "Hora de Comienzo de Armado") @Parameter(optionality=Optionality.OPTIONAL) final String eventoHoraComienzoArmado, 
			@Nullable @ParameterLayout(named = "Eleccion de Musica", multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) final String eventoEleccionMusica) {
		Date hoy = new Date();
		if (eventoFechaDelEvento.before(hoy)) {
			return "La fecha del evento no puede ser antes a la fecha de hoy";
		}

		return "";
	}

	@Inject
	Evento e;
	@javax.inject.Inject
	EventoRepository eventosRepository;
	@Inject
	ClienteRepository clienteRepository;
	@Inject
	TipoDeEventoRepository tipoDeEventoRepository;
	@Inject
	SalonRepository salonRepository;
}
