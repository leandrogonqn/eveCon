/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.leanGomez.application.services.homepage;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.factory.FactoryService;

import com.leanGomez.modules.simple.dom.estado.Estado;
import com.leanGomez.modules.simple.dom.evento.Evento;
import com.leanGomez.modules.simple.dom.evento.EventoRepository;

@DomainService(
        nature = NatureOfService.DOMAIN, // trick to suppress the actions from the top-level menu
        objectType = "homepage.HomePageService"
)
public class HomePageService {

    @Action(semantics = SemanticsOf.SAFE)
    @HomePage
    public HomePageViewModel homePage() {
    	actualizarEstado();
        return factoryService.instantiate(HomePageViewModel.class);
    }

	public void actualizarEstado(){
		List<Evento> listaEventos = eventoRepository.actualizarEstado(Estado.confirmado);
		listaEventos.addAll(eventoRepository.actualizarEstado(Estado.presupuestado));
		listaEventos.addAll(eventoRepository.actualizarEstado(Estado.saldado));
		Iterator<Evento> it = listaEventos.iterator();
		while (it.hasNext()) {
			Evento lista = it.next();
			lista.getEventoEstado().actualizarEstado(lista);
		}
	}

    @javax.inject.Inject
    FactoryService factoryService;
    @Inject
    EventoRepository eventoRepository;
}
