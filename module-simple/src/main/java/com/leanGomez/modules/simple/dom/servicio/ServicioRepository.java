package com.leanGomez.modules.simple.dom.servicio;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Servicio.class)
public class ServicioRepository {

	public List<Servicio> listar() {
		return repositoryService.allInstances(Servicio.class);
	}

	public List<Servicio> buscarPorNombre(final String serviciosNombre) {

		return repositoryService.allMatches(new QueryDefault<>(Servicio.class, "buscarPorNombre", "servicioNombre",
				serviciosNombre.toLowerCase()));

	}

	public List<Servicio> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Servicio.class, "listarActivos"));
	}

	public List<Servicio> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Servicio.class, "listarInactivos"));
	}

	public Servicio crear(String servicioNombre, String servicioObservaciones) {
		final Servicio object = new Servicio(servicioNombre, servicioObservaciones);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
