package com.leanGomez.modules.simple.dom.preciohistoricoocupacion;

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

import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PrecioHistoricoOcupacion.class)
public class PrecioHistoricoOcupacionRepository {

	public List<PrecioHistoricoOcupacion> listar() {
		return repositoryService.allInstances(PrecioHistoricoOcupacion.class);
	}

	public List<PrecioHistoricoOcupacion> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(PrecioHistoricoOcupacion.class, "listarActivos"));
	}

	public List<PrecioHistoricoOcupacion> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(PrecioHistoricoOcupacion.class, "listarInactivos"));
	}
	
	public List<PrecioHistoricoOcupacion> listarPreciosPorOcupacion(final Ocupacion precioHistoricoOcupacionOcupacion) {
		return repositoryService
				.allMatches(new QueryDefault<>(PrecioHistoricoOcupacion.class, "listarPreciosPorOcupacion", 
						"precioHistoricoOcupacionOcupacion", precioHistoricoOcupacionOcupacion));
	}
	
	public Double mostrarPrecioPorFecha (final Ocupacion precioHistoricoOcupacionOcupacion, final Date fecha) {
		Double a = 0.0;
		List<PrecioHistoricoOcupacion> listaPrecios = listarPreciosPorOcupacion(precioHistoricoOcupacionOcupacion);
		Iterator<PrecioHistoricoOcupacion> iterar = listaPrecios.iterator();
		while(iterar.hasNext()) {
			PrecioHistoricoOcupacion listOcupacion = iterar.next();
			if (listOcupacion.getPrecioHistoricoOcupacionFechaHasta()!=null) {
				if (((fecha.compareTo(listOcupacion.getPrecioHistoricoOcupacionFechaDesde()))>=0)
						&&((fecha.compareTo(listOcupacion.getPrecioHistoricoOcupacionFechaHasta()))<0)) {
					a=listOcupacion.getPrecioHistoricoOcupacionPrecio(); 
					break;
				}
			}else {
				if ((fecha.compareTo(listOcupacion.getPrecioHistoricoOcupacionFechaDesde()))>=0){
					a=listOcupacion.getPrecioHistoricoOcupacionPrecio();
					break;
				}
			}
		}
		return a;
	}

	public PrecioHistoricoOcupacion crear(final Ocupacion precioHistoricoOcupacionOcupacion, Double precioHistoricoOcupacionPrecio) {
		final PrecioHistoricoOcupacion object = new PrecioHistoricoOcupacion(precioHistoricoOcupacionOcupacion, precioHistoricoOcupacionPrecio);
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
