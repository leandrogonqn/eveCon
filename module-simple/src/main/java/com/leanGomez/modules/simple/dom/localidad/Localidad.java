/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.leanGomez.modules.simple.dom.localidad;

import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
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
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import com.leanGomez.modules.simple.dom.provincia.Provincia;
import com.leanGomez.modules.simple.dom.provincia.ProvinciaRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Localidad")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "localidadId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
				+ "FROM com.leanGomez.modules.simple.dom.localidad.Localidad "
				+ "WHERE localidadNombre.toLowerCase().indexOf(:localidadNombre) >= 0 "),

		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.leanGomez.modules.simple.dom.localidad.Localidad " + "WHERE localidadActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.leanGomez.modules.simple.dom.localidad.Localidad " + "WHERE localidadActivo == false ") })
@javax.jdo.annotations.Unique(name = "localidad_localidadNombre_UNQ", members = { "localidadNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Localidad implements Comparable<Localidad> {
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name",
				getlocalidadNombre() + " - " + this.getLocalidadProvincia().getProvinciaNombre());
	}
	// endregion

	public String cssClass() {
		return (getLocalidadActivo() == true) ? "activo" : "inactivo";
	}

	public static final int NAME_LENGTH = 200;

	// Constructor
	public Localidad(String localidadNombre, Provincia localidadProvincia) {
		setlocalidadNombre(localidadNombre);
		setLocalidadProvincia(localidadProvincia);
		this.localidadActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String localidadNombre;

	public String getlocalidadNombre() {
		return localidadNombre;
	}

	public void setlocalidadNombre(String localidadNombre) {
		this.localidadNombre = localidadNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean localidadActivo;

	@javax.jdo.annotations.Column(allowsNull = "false", name = "provinciaId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Provincia")
	private Provincia localidadProvincia;

	public Provincia getLocalidadProvincia() {
		return localidadProvincia;
	}

	public void setLocalidadProvincia(Provincia localidadProvincia) {
		this.localidadProvincia = localidadProvincia;
	}

	// endregion

	public boolean getLocalidadActivo() {
		return localidadActivo;
	}

	public void setLocalidadActivo(boolean localidadActivo) {
		this.localidadActivo = localidadActivo;
	}

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarLocalidad() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setLocalidadActivo(false);
	}

	public Localidad actualizarProvincia(@ParameterLayout(named = "Provincia") final Provincia name) {
		setLocalidadProvincia(name);
		return this;
	}

	public List<Provincia> choices0ActualizarProvincia() {
		return provinciaRepository.listarActivos();
	}

	public Provincia default0ActualizarProvincia() {
		return getLocalidadProvincia();
	}

	public Localidad actualizarNombre(@ParameterLayout(named = "Nombre") final String localidadNombre) {
		setlocalidadNombre(localidadNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getlocalidadNombre();
	}

	public Localidad actualizarActivo(@ParameterLayout(named = "Activo") final boolean localidadActivo) {
		setLocalidadActivo(localidadActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return getLocalidadActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getlocalidadNombre();
	}

	@Override
	public int compareTo(final Localidad localidad) {
		return this.localidadNombre.compareTo(localidad.localidadNombre);
	}
	// endregion

	// acciones
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar todas las localidades")
	@MemberOrder(sequence = "2")
	public List<Localidad> listar() {
		return localidadRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar las Localidades Activas")
	@MemberOrder(sequence = "3")
	public List<Localidad> listarActivos() {
		return localidadRepository.listarActivos();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "Listar las Localidades Inactivas")
	@MemberOrder(sequence = "4")
	public List<Localidad> listarInactivos() {
		return localidadRepository.listarInactivos();
	}
	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	ProvinciaRepository provinciaRepository;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	LocalidadRepository localidadRepository;

	// endregion
}
