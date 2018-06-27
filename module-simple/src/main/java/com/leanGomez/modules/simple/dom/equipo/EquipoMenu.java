package com.leanGomez.modules.simple.dom.equipo;

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

import com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipo;
import com.leanGomez.modules.simple.dom.tipodeequipo.TipoDeEquipoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.EquipoMenu", repositoryFor = Equipo.class)
@DomainServiceLayout(named = "Equipos", menuOrder = "40.1")
public class EquipoMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todos los equipos")
	@MemberOrder(sequence = "2")
	public List<Equipo> listar() {
		return equiposRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Equipo Por Nombre")
	@MemberOrder(sequence = "3")
	public List<Equipo> buscarPorNombre(@ParameterLayout(named = "Nombre") final String equipoNombre) {
		return equiposRepository.buscarPorNombre(equipoNombre);

	}

	@ActionLayout(named = "Crear Equipo")
	@MemberOrder(sequence = "1")
	public Equipo crear(@ParameterLayout(named = "Nombre") final String equipoNombre,
			@Nullable @ParameterLayout(named = "Descripcion", multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) final String equipoDescripcion,
			@ParameterLayout(named = "Tipo de Equipo") final TipoDeEquipo equipoTipoDeEquipo) {
		return equiposRepository.crear(equipoNombre, equipoDescripcion, equipoTipoDeEquipo);
	}
	
	public List<TipoDeEquipo> choices2Crear(){
		return tipoDeEquipoRepository.listarActivos();
	}

	@javax.inject.Inject
	EquipoRepository equiposRepository;
	@Inject
	TipoDeEquipoRepository tipoDeEquipoRepository;
}
