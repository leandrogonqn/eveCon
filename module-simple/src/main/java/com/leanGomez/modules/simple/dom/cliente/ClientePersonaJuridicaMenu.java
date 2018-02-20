package com.leanGomez.modules.simple.dom.cliente;

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

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.ClientePersonaJuridicaMenu", repositoryFor = ClientePersonaJuridica.class)
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
public class ClientePersonaJuridicaMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Empresa Por CUIT")
	@MemberOrder(sequence = "6")
	public List<ClientePersonaJuridica> buscarPorCuit(@ParameterLayout(named = "CUIT") final String personaCuitCuil) {
		return clientePersonaJuridicaRepository.buscarPorCUIT(personaCuitCuil);
	}

	public List<Localidad> choices2Crear() {
		return localidadesRepository.listarActivos();
	}

	@ActionLayout(named = "Crear Empresa")
	@MemberOrder(sequence = "1")
	public Cliente crear(@ParameterLayout(named = "Razon Social") final String personaNombre,
			@ParameterLayout(named = "Cuit") final String personaCuitCuil,
			@ParameterLayout(named = "Localidad") final Localidad personaLocalidad,
			@ParameterLayout(named = "Dirección") final String personaDireccion,
			@Nullable @ParameterLayout(named = "Teléfono") @Parameter(optionality = Optionality.OPTIONAL) final String personaTelefono,
			@Nullable @ParameterLayout(named = "E-Mail") @Parameter(optionality = Optionality.OPTIONAL) final String personaMail) {
		return clientePersonaJuridicaRepository.crear(personaNombre, personaLocalidad, personaDireccion, personaTelefono, personaMail, personaCuitCuil);
				
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Todos las Empresas")
	@MemberOrder(sequence = "2")
	public List<ClientePersonaJuridica> listar() {
		return clientePersonaJuridicaRepository.listar();
	}

//	public String validateCrear(final String clienteNombre, final String clienteApellido,
//			final TipoDocumento clienteTipoDocumento, final int clienteDni, final Sexo clienteSexo,
//			final Localidad personaLocalidad, final String personaDireccion, final String personaTelefono,
//			final String personaMail, final Date clienteFechaNacimiento, final boolean clienteNotificacionCumpleanios) {
//		if ((clienteFechaNacimiento == null) & (clienteNotificacionCumpleanios == true)) {
//			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
//		}
//		return "";
//	}

	@javax.inject.Inject
	ClientePersonaJuridicaRepository clientePersonaJuridicaRepository;

	@javax.inject.Inject
	LocalidadRepository localidadesRepository;
	
}
