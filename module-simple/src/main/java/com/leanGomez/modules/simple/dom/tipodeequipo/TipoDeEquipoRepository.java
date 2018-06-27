package com.leanGomez.modules.simple.dom.tipodeequipo;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = TipoDeEquipo.class)
public class TipoDeEquipoRepository {

	public List<TipoDeEquipo> listar() {
		return repositoryService.allInstances(TipoDeEquipo.class);
	}

	public List<TipoDeEquipo> buscarPorNombre(final String tipoDeEquipoNombre) {
		return repositoryService.allMatches(new QueryDefault<>(TipoDeEquipo.class, "buscarPorNombre", "tipoDeEquipoNombre",
				tipoDeEquipoNombre.toLowerCase()));
	}

	public List<TipoDeEquipo> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(TipoDeEquipo.class, "listarActivos"));
	}

	public List<TipoDeEquipo> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(TipoDeEquipo.class, "listarInactivos"));
	}

	public TipoDeEquipo crear(final String tipoDeEquipoNombre, final String tipoDeEquipoDescripcion) {
		final TipoDeEquipo object = new TipoDeEquipo(tipoDeEquipoNombre, tipoDeEquipoDescripcion);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
