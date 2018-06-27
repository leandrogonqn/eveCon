package com.leanGomez.modules.simple.dom.empleadoocupacion;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.leanGomez.modules.simple.dom.empleado.Empleado;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = EmpleadoOcupacion.class)
public class EmpleadoOcupacionRepository {

	public EmpleadoOcupacion crear(final Empleado empleado, Ocupacion ocupacion) {
		final EmpleadoOcupacion object = new EmpleadoOcupacion(empleado, ocupacion);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
	
}
