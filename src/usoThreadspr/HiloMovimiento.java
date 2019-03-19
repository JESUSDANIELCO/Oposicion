package usoThreadspr;

public class HiloMovimiento implements Runnable {

	Thread hilo;
	boolean suspender;	//--Variable para suspender el hilo--
 	boolean pausar;		//--VAriable para pausar el hilo--
 	
 	HiloMovimiento (String nombre){
 		
 		hilo = new Thread(this, nombre);
 		suspender = false;
 		pausar = false; 		
 		
 	}
 	
 	public static HiloMovimiento crearEstInicial(String nombre) {
 		
 		HiloMovimiento miHilo = new HiloMovimiento(nombre);
 		miHilo.hilo.start(); 	//---Iniciamos el Hilo--
 		return miHilo;
 		
 	}
 		
	@Override
	public void run() {
	
		System.out.println(hilo.getName() + " Iniciamos el hilo.");
		try {
			
			for (int i = 1; i < 1000; i++) {
				
				System.out.print(i + " ");
				if((i%10) == 0){
					System.out.println();
					Thread.sleep(250);
				}
				synchronized (this) {
					while (suspender) {
						wait();
					}
					if(pausar) break;
				}
			}
			
		} catch (InterruptedException e) {
			System.out.println(hilo.getName() + " hilo interrumpido.");
		}
		System.out.println(hilo.getName() + "hilo finalizado");
		
	}
	
	synchronized void pausarhilo() {
		
		pausar = true;
		
		suspender = false; //--aseguramos que un hilo detenido se puede detener --
		notify();		
	}
	
	synchronized void suspenderhilo() {
		
		suspender = true;
	}
	
	synchronized void reanudarhilo() {
		
		suspender = false;
		notify();
	}	
}

class Suspender {
	
	public static void main(String[] args) {
		
		HiloMovimiento hm1 = HiloMovimiento.crearEstInicial("Mi Hilo");
		try {
			
			Thread.sleep(1000); //--- ejecutamos el hilo principal--
			
			hm1.suspenderhilo();
			System.out.println("SUSPENDIENDO HILO.");
			Thread.sleep(1000);
			
			hm1.reanudarhilo();
			System.out.println("REANUDANDO HILO.");
			Thread.sleep(1000);
			
			hm1.suspenderhilo();
			System.out.println("suspendiendo hilo.");
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