package com.leanGomez.modules.simple.dom.tipodeevento;

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

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.TipoDeEventoMenu", repositoryFor = TipoDeEvento.class)
@DomainServiceLayout(named = "Eventos", menuOrder = "10.5")
public class TipoDeEventoMenu {
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las tipoDeEventos")
	@MemberOrder(sequence = "2")
	public List<TipoDeEvento> listar() {
		return tipoDeEventosRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Tipos De Evento Por Nombre")
	@MemberOrder(sequence = "5")
	public List<TipoDeEvento> buscarPorNombre(@ParameterLayout(named = "Nombre") final String tipoDeEventoNombre) {
		return tipoDeEventosRepository.buscarPorNombre(tipoDeEventoNombre);

	}

	@ActionLayout(named = "Crear Tipo De Evento")
	@MemberOrder(sequence = "1.2")
	public TipoDeEvento crear(@ParameterLayout(named = "Nombre") final String tipoDeEventoNombre,
			@Nullable @ParameterLayout(named = "Descripcion", multiLine=10) @Parameter(optionality=Optionality.OPTIONAL) final String tipoDeEventoDescripcion) {
		return tipoDeEventosRepository.crear(tipoDeEventoNombre, tipoDeEventoDescripcion);
	}

	@javax.inject.Inject
	TipoDeEventoRepository tipoDeEventosRepository;
}
