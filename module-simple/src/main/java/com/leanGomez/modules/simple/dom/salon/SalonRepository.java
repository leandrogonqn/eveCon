package com.leanGomez.modules.simple.dom.salon;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.localidad.Localidad;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Salon.class)
public class SalonRepository {

	public List<Salon> listar() {
		return repositoryService.allInstances(Salon.class);
	}

	public List<Salon> buscarPorNombre(final String salonesNombre) {

		return repositoryService.allMatches(new QueryDefault<>(Salon.class, "buscarPorNombre", "salonNombre",
				salonesNombre.toLowerCase()));

	}

	public List<Salon> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Salon.class, "listarActivos"));
	}

	public List<Salon> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Salon.class, "listarInactivos"));
	}

	public Salon crear(String salonNombre, String salonDireccion, Localidad salonLocalidad, String salonTelefono, String salonResponsable) {
		final Salon object = new Salon(salonNombre, salonDireccion, salonLocalidad, salonTelefono, salonResponsable);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
