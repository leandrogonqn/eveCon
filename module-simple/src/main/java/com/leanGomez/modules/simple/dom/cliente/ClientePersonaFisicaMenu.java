package com.leanGomez.modules.simple.dom.cliente;

import java.util.Date;
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

import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;
import com.leanGomez.modules.simple.dom.persona.Sexo;
import com.leanGomez.modules.simple.dom.persona.TipoDeDocumento;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.ClientePersonaFisicaMenu", repositoryFor = ClientePersonaFisica.class)
@DomainServiceLayout(named = "Clientes", menuOrder = "10.1")
public class ClientePersonaFisicaMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Por DNI")
	@MemberOrder(sequence = "1.3")
	public List<ClientePersonaFisica> buscarPorDNI(@ParameterLayout(named = "DNI") final int clienteDni) {
		return clientePersonaFisicaRepository.buscarPorDNI(clienteDni);
	}

	public List<Localidad> choices4Crear() {
		return localidadesRepository.listarActivos();
	}

	@ActionLayout(named = "Crear Cliente")
	@MemberOrder(sequence = "1.2")
	public Cliente crear(@ParameterLayout(named = "Nombre y apellido") final String personaNombre,
			@ParameterLayout(named = "Tipo de Documento") final TipoDeDocumento clienteTipoDocumento,
			@ParameterLayout(named = "Numero de Documento") final int clienteNumeroDocumento,
			@ParameterLayout(named = "Sexo") final Sexo clienteSexo,
			@ParameterLayout(named = "Localidad") final Localidad personaLocalidad,
			@Nullable @ParameterLayout(named = "Dirección") @Parameter(optionality=Optionality.OPTIONAL) final String personaDireccion,
			@Nullable @ParameterLayout(named = "Teléfono") @Parameter(optionality = Optionality.OPTIONAL) final String personaTelefono,
			@Nullable @ParameterLayout(named = "E-Mail") @Parameter(optionality = Optionality.OPTIONAL) final String personaMail,
			@Nullable @ParameterLayout(named = "Fecha de Nacimiento") @Parameter(optionality = Optionality.OPTIONAL) final Date clienteFechaNacimiento,
			@ParameterLayout(named = "Notif. Cumpleaños") final boolean clienteNotificacionCumpleanios) {
		return clientePersonaFisicaRepository.crear(personaNombre, clienteTipoDocumento, clienteNumeroDocumento, clienteSexo, personaLocalidad, personaDireccion, personaTelefono, personaMail, clienteFechaNacimiento, clienteNotificacionCumpleanios);
				
	}
	
	public String validateCrear(final String clienteNombre, final TipoDeDocumento clienteTipoDocumento, 
			final int clienteNumeroDocumento, final Sexo clienteSexo,
			final Localidad personaLocalidad, final String personaDireccion, final String personaTelefono,
			final String personaMail, final Date clienteFechaNacimiento, final boolean clienteNotificacionCumpleanios) {
		if ((clienteFechaNacimiento == null) & (clienteNotificacionCumpleanios == true)) {
			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
		}
		Date hoy = new Date();
		if (clienteFechaNacimiento!=null) {
			if (clienteFechaNacimiento.after(hoy)) {
				return "La fecha de nacimiento es mayor a la fecha actual";
			}
		}

		return "";
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Todos los Clientes")
	@MemberOrder(sequence = "2")
	public List<ClientePersonaFisica> listar() {
		return clientePersonaFisicaRepository.listar();
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-search", named = "Buscar Cliente Por Nombre")
	@MemberOrder(sequence = "1.1")
	public List<Cliente> buscarPorNombre(@ParameterLayout(named = "Nombre") final String personaNombre) {
		return clienteRepository.buscarPorNombre(personaNombre);
	}


	@Inject
	ClienteRepository clienteRepository;
	
	@javax.inject.Inject
	ClientePersonaFisicaRepository clientePersonaFisicaRepository;

	@javax.inject.Inject
	LocalidadRepository localidadesRepository;
	
}
