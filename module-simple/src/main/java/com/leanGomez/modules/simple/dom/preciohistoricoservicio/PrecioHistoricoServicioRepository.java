package com.leanGomez.modules.simple.dom.preciohistoricoservicio;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import com.leanGomez.modules.simple.dom.servicio.Servicio;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PrecioHistoricoServicio.class)
public class PrecioHistoricoServicioRepository {

	public List<PrecioHistoricoServicio> listar() {
		return repositoryService.allInstances(PrecioHistoricoServicio.class);
	}

	public List<PrecioHistoricoServicio> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(PrecioHistoricoServicio.class, "listarActivos"));
	}

	public List<PrecioHistoricoServicio> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(PrecioHistoricoServicio.class, "listarInactivos"));
	}
	
	public List<PrecioHistoricoServicio> listarPreciosPorServicio(final Servicio precioHistoricoServicioServicio) {
		return repositoryService
				.allMatches(new QueryDefault<>(PrecioHistoricoServicio.class, "listarPreciosPorServicio", 
						"precioHistoricoServicioServicio", precioHistoricoServicioServicio));
	}
	
	public Double mostrarPrecioPorFecha (final Servicio precioHistoricoServicioServicio, final Date fecha) {
		Double a = 0.0;
		List<PrecioHistoricoServicio> listaPrecios = listarPreciosPorServicio(precioHistoricoServicioServicio);
		Iterator<PrecioHistoricoServicio> iterar = listaPrecios.iterator();
		while(iterar.hasNext()) {
			PrecioHistoricoServicio listServicio = iterar.next();
			if (listServicio.getPrecioHistoricoServicioFechaHasta()!=null) {
				if (((fecha.compareTo(listServicio.getPrecioHistoricoServicioFechaDesde()))>=0)
						&&((fecha.compareTo(listServicio.getPrecioHistoricoServicioFechaHasta()))<0)) {
					a=listServicio.getPrecioHistoricoServicioPrecio(); 
					break;
				}
			}else {
				if ((fecha.compareTo(listServicio.getPrecioHistoricoServicioFechaDesde()))>=0){
					a=listServicio.getPrecioHistoricoServicioPrecio();
					break;
				}
			}
		}
		return a;
	}

	public PrecioHistoricoServicio crear(final Servicio precioHistoricoServicioServicio, Double precioHistoricoServicioPrecio) {
		final PrecioHistoricoServicio object = new PrecioHistoricoServicio(precioHistoricoServicioServicio, precioHistoricoServicioPrecio);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
	@Inject
	MessageService messageService;
}
