package com.leanGomez.modules.simple.dom.ocupacion;

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

import com.leanGomez.modules.simple.dom.preciohistoricoocupacion.PrecioHistoricoOcupacionRepository;
import com.leanGomez.modules.simple.dom.servicio.Servicio;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.OcupacionMenu", repositoryFor = Ocupacion.class)
@DomainServiceLayout(named = "Empleados", menuOrder = "40.2")
public class OcupacionMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las ocupaciones")
	@MemberOrder(sequence = "2")
	public List<Ocupacion> listar() {
		return ocupacionRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Ocupacion Por Nombre")
	@MemberOrder(sequence = "3")
	public List<Ocupacion> buscarPorNombre(@ParameterLayout(named = "Nombre") final String ocupacionNombre) {
		return ocupacionRepository.buscarPorNombre(ocupacionNombre);

	}

	@ActionLayout(named = "Crear Ocupacion")
	@MemberOrder(sequence = "1")
	public Ocupacion crear(@ParameterLayout(named = "Nombre") final String ocupacionNombre,
			@Nullable @ParameterLayout(named = "Descripcion", multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) final String ocupacionDescripcion,
			@ParameterLayout(named="Precio") final Double servicioPrecio) {
		Ocupacion o = new Ocupacion(ocupacionNombre, ocupacionDescripcion);
		precioHistoricoEmpleadoRepository.crear(o, servicioPrecio);
		return o;
	}

	@javax.inject.Inject
	OcupacionRepository ocupacionRepository;
	
	@Inject
	PrecioHistoricoOcupacionRepository precioHistoricoEmpleadoRepository;
}
