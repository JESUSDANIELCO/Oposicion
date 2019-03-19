package usoThreads4;



public class HilosEjercicio {

	
	public static void main(String[] args) {
						
		Runnable juan = new Trabajador();	//--3 INSTANCIAR LA CLASE CREADA TIPO RUNNABLE
		Runnable oscar = new Estudiante();  //--3 
		
		Thread t1 = new Thread(juan);		//--4 INSTANCIAR CLASE THREAD Y PASAMOS COMO PARAMETRO EL TIPO RUNNABLE
		Thread t2 = new Thread(oscar);		//--4 
		
		t1.start(); 						//--5 METODO PARA INICIAR EL HILO CREADO
		
		try {
			
			t1.join(); 						//-- METODO PARA HACER ESPERAR A QUE TERNIME UN HILO----
		} catch (Exception e) {
			e.printStackTrace();
		}
		t2.start();
			
	}			
}
class Trabajador implements Runnable {    //-- 1 CREAR CLASE
 	
	@Override
	public void run() {						//--ESCRIBIR TAREA
		
		for (int i = 1; i <= 10; i++) {
			
			try {
				Thread.sleep(500);
				//----- METODO PARA SABER QUE HILO ESTA CORRIENDO-------
				System.out.println("EL HILO QUE ESTA CORRIENDO ES :" + Thread.currentThread().getName());	//-- referencia del hilo en ejecucion actual y NOMBRE DEL  HILO
				System.out.println("EL TRABAJADOR LLEVA EL MENSAJE NUMERO :  " + i);
				System.out.println("Es un cambio de ejemplo");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("TERMINA EL PROCESO PARA TRABAJADOR");
	}
	
}
class Estudiante implements Runnable{

	@Override
	public void run() {
		
		for (int i = 1; i <= 10; i++) {
			
			try {
				Thread.sleep(1000); //--- METODO PARA DORMIR EL HILO POR SEGUNDOS-----
				System.out.println("EL HILO QUE ESTA CORRIENDO ES :" + Thread.currentThread().getName()); //--- METODO PARA SABER QUE HILO ESTA CORRIENDO---
				System.out.println("EL ESTUDIANTE LLEVA EL MENSAJE NUMERO:  " + i);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("TERMINA EL PROCESO PARA ESTUDIANTE");
	}
}


