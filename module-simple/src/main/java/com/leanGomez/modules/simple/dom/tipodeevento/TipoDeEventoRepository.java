package com.leanGomez.modules.simple.dom.tipodeevento;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = TipoDeEvento.class)
public class TipoDeEventoRepository {

	public List<TipoDeEvento> listar() {
		return repositoryService.allInstances(TipoDeEvento.class);
	}

	public List<TipoDeEvento> buscarPorNombre(final String tipoDeEventoNombre) {
		return repositoryService.allMatches(new QueryDefault<>(TipoDeEvento.class, "buscarPorNombre", "tipoDeEventoNombre",
				tipoDeEventoNombre.toLowerCase()));
	}

	public List<TipoDeEvento> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(TipoDeEvento.class, "listarActivos"));
	}

	public List<TipoDeEvento> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(TipoDeEvento.class, "listarInactivos"));
	}

	public TipoDeEvento crear(final String tipoDeEventoNombre, final String tipoDeEventoDescripcion) {
		final TipoDeEvento object = new TipoDeEvento(tipoDeEventoNombre, tipoDeEventoDescripcion);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
