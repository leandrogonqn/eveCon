package com.leanGomez.modules.simple.dom.salon;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.value.Blob;

import com.leanGomez.modules.simple.dom.adjunto.Adjunto;
import com.leanGomez.modules.simple.dom.adjunto.AdjuntoRepository;
import com.leanGomez.modules.simple.dom.cliente.Cliente;
import com.leanGomez.modules.simple.dom.localidad.Localidad;
import com.leanGomez.modules.simple.dom.localidad.LocalidadRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Salon")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "salonId")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.salon.Salon "
			+ "WHERE salonNombre.toLowerCase().indexOf(:salonNombre) >= 0 "),

	@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.salon.Salon " + "WHERE salonActivo == true "),
	@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
			+ "FROM com.leanGomez.modules.simple.dom.salon.Salon " + "WHERE salonActivo == false ") })
@javax.jdo.annotations.Unique(name = "Salon_salonNombre_UNQ", members = { "salonNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Salon implements Comparable<Salon>{
	
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("Salon: {salonNombre}", "salonNombre",	getSalonNombre());
	}
	// endregion

	public Salon(String salonNombre, String salonDireccion, Localidad salonLocalidad, String salonTelefono, String salonResponsable) {
		super();
		this.salonNombre = salonNombre;
		this.salonDireccion = salonDireccion;
		this.salonLocalidad = salonLocalidad;
		this.salonTelefono = salonTelefono;
		this.salonResponsable = salonResponsable;
		this.salonActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String salonNombre;
	public String getSalonNombre() {
		return salonNombre;
	}
	public void setSalonNombre(String salonNombre) {
		this.salonNombre = salonNombre;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Direccion")
	private String salonDireccion;
	public String getSalonDireccion() {
		return salonDireccion;
	}
	public void setSalonDireccion(String salonDireccion) {
		this.salonDireccion = salonDireccion;
	}
	
	@Column(allowsNull = "false", name="localidadId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Localidad")
	private Localidad salonLocalidad;
	public Localidad getSalonLocalidad() {
		return salonLocalidad;
	}
	public void setSalonLocalidad(Localidad salonLocalidad) {
		this.salonLocalidad = salonLocalidad;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Telefono")
	private String salonTelefono;
	public String getSalonTelefono() {
		return salonTelefono;
	}
	public void setSalonTelefono(String salonTelefono) {
		this.salonTelefono = salonTelefono;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Responsable")
	private String salonResponsable;
	public String getSalonResponsable() {
		return salonResponsable;
	}
	public void setSalonResponsable(String salonResponsable) {
		this.salonResponsable = salonResponsable;
	}
	
	//LISTA DE ADJUNTOS
	@Join
	@Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@CollectionLayout(named = "Lista de imagenes")
	private List<Adjunto> salonAdjunto;
	public List<Adjunto> getSalonAdjunto() {
		return salonAdjunto;
	}
	public void setSalonAdjunto(List<Adjunto> salonAdjunto) {
		this.salonAdjunto = salonAdjunto;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean salonActivo;
	public boolean isSalonActivo() {
		return salonActivo;
	}
	public void setSalonActivo(boolean salonActivo) {
		this.salonActivo = salonActivo;
	}

	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarSalon() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setSalonActivo(false);
	}
	
	public Salon modificarNombre(@ParameterLayout(named = "Nombre") final String salonNombre) {
		setSalonNombre(salonNombre);
		return this;
	}

	public String default0ModificarNombre() {
		return getSalonNombre();
	}
	
	public Salon modificarDireccion(@ParameterLayout(named = "Direccion") final String salonDireccion) {
		setSalonDireccion(salonDireccion);
		return this;
	}

	public String default0ModificarDireccion() {
		return getSalonDireccion();
	}
	
	public Salon modificarLocalidad(@ParameterLayout(named = "Localidad") final Localidad name) {
		setSalonLocalidad(name);
		return this;
	}

	public List<Localidad> choices0ModificarLocalidad() {
		return localidadRepository.listarActivos();
	}

	public Localidad default0ModificarLocalidad() {
		return getSalonLocalidad();
	}
	
	public Salon modificarTelefono(@ParameterLayout(named = "Telefono") final String salonTelefono) {
		setSalonTelefono(salonTelefono);
		return this;
	}

	public String default0ModificarTelefono() {
		return getSalonTelefono();
	}
	
	public Salon modificarResponsable(@ParameterLayout(named = "Responsable") final String salonResponsable) {
		setSalonResponsable(salonResponsable);
		return this;
	}

	public String default0ModificarResponsable() {
		return getSalonResponsable();
	}
	
	//Modificar Adjunto

	public Salon modificarActivo(@ParameterLayout(named = "Activo") final boolean salonActivo) {
		setSalonActivo(salonActivo);
		return this;
	}

	public boolean default0ModificarActivo() {
		return isSalonActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getSalonNombre();
	}

	@Override
	public int compareTo(final Salon salon) {
		return this.salonNombre.compareTo(salon.salonNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar todas las Salons")
	@MemberOrder(sequence = "2")
	public List<Salon> listar() {
		return salonsRepository.listar();
	}

	@ActionLayout(named = "Listar Salon Activas")
	@MemberOrder(sequence = "3")
	public List<Salon> listarActivos() {
		return salonsRepository.listarActivos();
	}

	@ActionLayout(named = "Listar Salons Inactivas")
	@MemberOrder(sequence = "4")
	public List<Salon> listarInactivos() {
		return salonsRepository.listarInactivos();
	}
	
//	@ActionLayout(named = "Listar todos los eventos de este Salon")
//	@MemberOrder(sequence = "5")
//	public List<Localidad> listarSalon(){
//		return localidadRepository.buscarPorSalon(this);
//	}
	
	@ActionLayout(named = "Agregar Imagen")
	public Salon crearAdjunto(
			@ParameterLayout(named = "Descripcion") final String salonAdjuntoDescripcion,
			@ParameterLayout(named = "Imagen") final Blob salonAdjunto) {
		this.getSalonAdjunto()
				.add(adjuntoRepository.crear(salonAdjuntoDescripcion, salonAdjunto, this));
		this.setSalonAdjunto(this.getSalonAdjunto());
		return this;
	}

	@ActionLayout(named = "Quitar Imagen")
	public Salon quitarAdjunto(@ParameterLayout(named = "Imagen") Adjunto adjunto) {
		Iterator<Adjunto> it = getSalonAdjunto().iterator();
		while (it.hasNext()) {
			Adjunto lista = it.next();
			if (lista.equals(adjunto))
				it.remove();
		}
		return this;
	}

	public List<Adjunto> choices0QuitarAdjunto() {
		return getSalonAdjunto();
	}

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	SalonRepository salonsRepository;
	
	@Inject
	LocalidadRepository localidadRepository;
	
	@Inject
	AdjuntoRepository adjuntoRepository;
	
}
