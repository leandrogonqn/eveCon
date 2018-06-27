package com.leanGomez.modules.simple.dom.estado;

import java.util.Date;
import com.leanGomez.modules.simple.dom.evento.Evento;

public enum Estado implements IEstado {
	presupuestado{

		@Override
		public void agregarPago(Evento evento) {
			// TODO Auto-generated method stub
			if (evento.getEventoPagoDeCliente().size()>0){
				if (evento.getSaldoRestante()>0) {
					evento.setEventoEstado(confirmado);
				} else {
					evento.setEventoEstado(saldado);
				}	
			}
		}

		@Override
		public void actualizarEstado(Evento evento) {
			// TODO Auto-generated method stub
			if(evento.getSaldoRestante()>0){
				evento.setEventoEstado(realizado_No_Cancelado);
			}else{
				evento.setEventoEstado(realizado);
			}
		}
	},
	confirmado{

		@Override
		public void agregarPago(Evento evento) {
			// TODO Auto-generated method stub
			if (evento.getSaldoRestante()<=0) {
				evento.setEventoEstado(saldado);
			}
		}

		@Override
		public void quitarPago(Evento evento) {
			// TODO Auto-generated method stub
			if (evento.getEventoPagoDeCliente().isEmpty()) {
				evento.setEventoEstado(presupuestado);
			}
		}

		@Override
		public void actualizarEstado(Evento evento) {
			// TODO Auto-generated method stub
			if(evento.getSaldoRestante()>0){
				evento.setEventoEstado(realizado_No_Cancelado);
			}else{
				evento.setEventoEstado(realizado);
			}
		}

	},
	saldado{

		@Override
		public void quitarPago(Evento evento) {
			// TODO Auto-generated method stub
			if(evento.getEventoPagoDeCliente().isEmpty()) {
				evento.setEventoEstado(presupuestado);
			}else {
				if(evento.getSaldoRestante()>0) {
					evento.setEventoEstado(confirmado);
				}
			}
		}

		@Override
		public void actualizarEstado(Evento evento) {
			// TODO Auto-generated method stub
			if(evento.getSaldoRestante()>0){
				evento.setEventoEstado(realizado_No_Cancelado);
			}else{
				evento.setEventoEstado(realizado);
			}
		}
		
		@Override
		public void cambiarFechaEvento(Evento evento) {
			// TODO Auto-generated method stub
			Date h = new Date();
			Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
			if(evento.getEventoFechaDelEvento().compareTo(hoy)<0) {
				evento.setEventoEstado(realizado);
			}
		}

	},
	realizado{

		@Override
		public void quitarPago(Evento evento) {
			// TODO Auto-generated method stub
			if(evento.getSaldoRestante()>0) {
				evento.setEventoEstado(realizado_No_Cancelado);
			}
		}
		
		@Override
		public void cambiarFechaEvento(Evento evento) {
			// TODO Auto-generated method stub
			Date h = new Date();
			Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
			if(evento.getEventoFechaDelEvento().compareTo(hoy)>=0) {
				evento.setEventoEstado(saldado);
			}
		}

	},
	realizado_No_Cancelado{

		@Override
		public void agregarPago(Evento evento) {
			// TODO Auto-generated method stub
			if (evento.getSaldoRestante()<=0) {
				evento.setEventoEstado(realizado);
			}
		}

		@Override
		public void cambiarFechaEvento(Evento evento) {
			// TODO Auto-generated method stub
			Date h = new Date();
			Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
			if(evento.getEventoFechaDelEvento().compareTo(hoy)>=0) {
				if(evento.getEventoPagoDeCliente().isEmpty()) {
					evento.setEventoEstado(presupuestado);
				}else {
					evento.setEventoEstado(confirmado);
				}
			}
		}

	},
	anulado{

		@Override
		public void anulacion(Evento evento) {
			// TODO Auto-generated method stub
			Date h = new Date();
			Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
			if((evento.getEventoFechaDelEvento().compareTo(hoy))>=0) {
				if(evento.getEventoPagoDeCliente().isEmpty()) {
					evento.setEventoEstado(presupuestado);
				}else {
					if(evento.getSaldoRestante()>0) {
						evento.setEventoEstado(confirmado);
					}else {
						evento.setEventoEstado(saldado);	
					}
				}
			}else {
				if(evento.getSaldoRestante()>0) {
					evento.setEventoEstado(realizado_No_Cancelado);
				}else {
					evento.setEventoEstado(realizado);
				}
			}
		}

		@Override
		public void cambiarFechaEvento(Evento evento) {
			// TODO Auto-generated method stub
			//no hace nada
		}

	}
	;
	
	@Override
	public void agregarPago(Evento evento) {
		// TODO Auto-generated method stub
		//no hace nada
	}

	@Override
	public void quitarPago(Evento evento) {
		// TODO Auto-generated method stub
		//no hace nada
	}

	@Override
	public void actualizarEstado(Evento evento) {
		// TODO Auto-generated method stub
		//no hacer nada
	}

	@Override
	public void anulacion(Evento evento) {
		// TODO Auto-generated method stub
		evento.setEventoEstado(anulado);
	}
	
	@Override
	public void cambiarFechaEvento(Evento evento) {
		// TODO Auto-generated method stub
		Date h = new Date();
		Date hoy = new Date(h.getYear(), h.getMonth(), h.getDate());
		if(evento.getEventoFechaDelEvento().compareTo(hoy)<0) {
			evento.setEventoEstado(realizado_No_Cancelado);
		}
	}

}
