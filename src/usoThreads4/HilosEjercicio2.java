package usoThreads4;




public class HilosEjercicio2 {

	
public static void main(String[] args) {
		
		HiloMovimiento2 hm1 = HiloMovimiento2.crearEstInicial("Mi Hilo"); //INSTANCIAMOS EL METODO
		
						
		try {
			
			
			Thread.sleep(2000); //--- ejecutamos el hilo principal--
			
			hm1.suspenderhilo();
			System.out.println("PAUSANDO HILO.");
			Thread.sleep(1000);
								
			hm1.reanudarhilo();
			System.out.println("REANUDANDO HILO.");
			Thread.sleep(1000);
			
			hm1.suspenderhilo();
			System.out.println("pausando hilo.");
			Thread.sleep(1000);
			
			hm1.reanudarhilo();
			System.out.println("reanudando hilo.");
			Thread.sleep(1000);
			
			hm1.suspenderhilo();
			System.out.println("SUSPENDIENDO HILO.");
			hm1.pausarhilo();
						
		} catch (InterruptedException e) {
			System.out.println("HILO PRINCIPAL INTERRUMPIDO.");
		}
		System.out.println("HILO PRINCIPAL FINALIZADO.");		
	}	
}
class HiloMovimiento2 implements Runnable {			//--1 CREAMOS CLASE RUNNABLE

	Thread hilo;		//--Variable hilo--
	boolean suspender;	//--Variable indicador para suspender el hilo--
 	boolean pausar;		//--VAriable indicador para pausar el hilo--
 	
 	HiloMovimiento2 (String nombre){				//--2 CREAMOS CONSTRUCTOR DE CLASE
 		
 		hilo = new Thread(this, nombre);
 		suspender = false;
 		pausar = false; 		
 		
 	}
 	public static HiloMovimiento2 crearEstInicial(String nombre) { //--CREAMOS METODO PARA INICIAR EL HILO
 		
 		HiloMovimiento2 miHilo = new HiloMovimiento2(nombre);
 		miHilo.hilo.start(); 	//---Iniciamos el Hilo--
 		return miHilo;
 		
 	}

	@Override
	public void run() {												//--ESCRIBIR EL CODIGO DE LA TAREA
		System.out.println(hilo.getName() + " Iniciamos el hilo.");
		try {
			
			for (int i = 1; i < 1000; i++) {
				
				System.out.print(i + " ");
				if((i%10) == 0){		//-- SALTO DE LINEA CADA 10 NUMEROS
					System.out.println();
					Thread.sleep(250);//-- LAPSO PARA QUE EL HILO SE LLEVE LA INFORMACION
				}
				synchronized (this) {		//-- OCUPAR EL HILO Y SABER EL ESTADO DE NUESTRO HILO
					while (suspender) {
						wait();		//--BLOQUEA Y DEJA EN LISTA DE ESPERA EL HILO 
					}
					if(pausar) break;
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println(hilo.getName() + " hilo interrumpido.");
		}
		System.out.println(hilo.getName() + " hilo finalizado.");
				
	}
	synchronized void pausarhilo() {
		
		pausar = true;
		
		suspender = false; //-- aseguramos que un hilo detenido se puede detener --
		notify();			//-- reanuda la continuidad del hilo
	}
	
	synchronized void suspenderhilo() {
		
		suspender = true;
	}
	
	synchronized void reanudarhilo() {
		
		suspender = false;
		notify();
	}
}
