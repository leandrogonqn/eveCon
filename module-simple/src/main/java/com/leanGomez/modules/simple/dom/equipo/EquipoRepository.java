package com.leanGomez.modules.simple.dom.equipo;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipo;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Equipo.class)
public class EquipoRepository {

	public List<Equipo> listar() {
		return repositoryService.allInstances(Equipo.class);
	}

	public List<Equipo> buscarPorNombre(final String equipoNombre) {
		return repositoryService.allMatches(new QueryDefault<>(Equipo.class, "buscarPorNombre", "equipoNombre",
				equipoNombre.toLowerCase()));
	}

	public List<Equipo> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Equipo.class, "listarActivos"));
	}

	public List<Equipo> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Equipo.class, "listarInactivos"));
	}

	public Equipo crear(final String equipoNombre, final String equipoDescripcion, final TipoDeEquipo equipoTipoDeEquipo) {
		final Equipo object = new Equipo(equipoNombre, equipoDescripcion, equipoTipoDeEquipo);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
