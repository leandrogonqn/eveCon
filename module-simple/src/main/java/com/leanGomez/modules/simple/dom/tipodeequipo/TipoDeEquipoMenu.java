package com.leanGomez.modules.simple.dom.tipodeequipo;

import java.util.List;

import javax.annotation.Nullable;

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

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.TipoDeEquipoMenu", repositoryFor = TipoDeEquipo.class)
@DomainServiceLayout(named = "Equipos", menuOrder = "40.2")
public class TipoDeEquipoMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las tipoDeEquipos")
	@MemberOrder(sequence = "2")
	public List<TipoDeEquipo> listar() {
		return tipoDeEquiposRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Tipos De Equipo Por Nombre")
	@MemberOrder(sequence = "3")
	public List<TipoDeEquipo> buscarPorNombre(@ParameterLayout(named = "Nombre") final String tipoDeEquipoNombre) {
		return tipoDeEquiposRepository.buscarPorNombre(tipoDeEquipoNombre);

	}

	@ActionLayout(named = "Crear Tipo De Equipo")
	@MemberOrder(sequence = "1")
	public TipoDeEquipo crear(@ParameterLayout(named = "Nombre") final String tipoDeEquipoNombre,
			@Nullable @ParameterLayout(named = "Descripcion", multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) final String tipoDeEquipoDescripcion) {
		return tipoDeEquiposRepository.crear(tipoDeEquipoNombre, tipoDeEquipoDescripcion);
	}

	@javax.inject.Inject
	TipoDeEquipoRepository tipoDeEquiposRepository;
}
