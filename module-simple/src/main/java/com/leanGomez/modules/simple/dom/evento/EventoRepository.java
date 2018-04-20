package com.leanGomez.modules.simple.dom.evento;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.cliente.Cliente;
import com.leanGomez.modules.simple.dom.salon.Salon;
import com.leanGomez.modules.simple.dom.tipodeevento.TipoDeEvento;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Evento.class)
public class EventoRepository {

	public List<Evento> listar() {
		return repositoryService.allInstances(Evento.class);
	}

//	public List<Evento> buscarPorNombre(final String provinciasNombre) {
//
//		return repositoryService.allMatches(new QueryDefault<>(Evento.class, "buscarPorNombre", "provinciaNombre",
//				provinciasNombre.toLowerCase()));
//
//	}

	public List<Evento> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Evento.class, "listarActivos"));
	}

	public List<Evento> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Evento.class, "listarInactivos"));
	}

	public Evento crear(final Date eventoPresupuesto, final Date eventoFechaDelEvento, final String eventoNombreAgasajado, final Cliente eventoCliente, 
			final TipoDeEvento eventoTipoDeEvento, final Salon eventoSalon, final Integer eventoCantidadPersonas, final String eventoHoraComienzo, 
			final String eventoHoraFinalizacion, final String eventoHoraComienzoArmado, final String eventoEleccionMusica) {
		final Evento object = new Evento(eventoPresupuesto, eventoFechaDelEvento, eventoNombreAgasajado, eventoCliente, 
				eventoTipoDeEvento, eventoSalon, eventoCantidadPersonas, eventoHoraComienzo, 
				eventoHoraFinalizacion, eventoHoraComienzoArmado, eventoEleccionMusica);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
