package com.leanGomez.modules.simple.dom.preciohistoricoocupacion;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;
import com.leanGomez.modules.simple.dom.ocupacion.OcupacionRepository;
import com.leanGomez.modules.simple.dom.ocupacion.Ocupacion;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.PrecioHistoricoOcupacionMenu", repositoryFor = Ocupacion.class)
@DomainServiceLayout(named = "Eventos", menuOrder = "30.3")
public class PrecioHistoricoOcupacionMenu {
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar historial completo")
	@MemberOrder(sequence = "2")
	public List<PrecioHistoricoOcupacion> listar() {
		return precioHistoricoOcupacionRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Mostrar precio de Ocupacion por fecha")
	@MemberOrder(sequence = "1")
	public Double mostrarPrecioPorFecha(@ParameterLayout(named = "Ocupacion") final Ocupacion precioHistoricoOcupacionOcupacion,
			@ParameterLayout(named = "fecha") final Date fecha) {
		return precioHistoricoOcupacionRepository.mostrarPrecioPorFecha(precioHistoricoOcupacionOcupacion, fecha);
	}
	
	public List<Ocupacion> choices0MostrarPrecioPorFecha() {
		return ocupacionsRepository.listarActivos();
	}

	@Inject
	PrecioHistoricoOcupacionRepository precioHistoricoOcupacionRepository;
	
	@javax.inject.Inject
	OcupacionRepository ocupacionsRepository;
	
}
