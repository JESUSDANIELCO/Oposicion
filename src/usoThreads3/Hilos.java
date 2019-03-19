package usoThreads3;


public class Hilos {

	public static void main (String[] args) {
   
		Runnable juan =new Trabajador();	
		
		Runnable pepe =new Estudiante();	
		Thread t1= new Thread(juan);
		Thread t2= new Thread(pepe);

		t1.start();
		try {
	
			t1.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
		
	}
	
	
	
}
interface Persona{
	
	public void conteo();

	
}
class Trabajador implements Runnable,Persona{
	
@Override	
 public void conteo() {
		for (int i = 0; i <10; i++) {
			try {
		Thread.sleep(1000);
		System.out.println( Thread.currentThread() );
	
			System.out.println("Trabajador lleva "+i);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		
		}
		System.out.println("termina procesamiento");
	}

@Override
public void run() {
conteo();
	System.out.println("Fin Trabajador");
}
	
	}
class Estudiante implements Runnable,Persona {
	 
	 public void conteo() {

			for (int i = 0; i <10; i++) {
				System.out.println( Thread.currentThread() );
				if (i==5) {
			  
				}
				try {
			Thread.sleep(1000);
				System.out.println("Estudiante lleva "+i);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			
			}
			System.out.println("termina procesamiento");
		}

	@Override
	public void run() {
	conteo();
		System.out.println("Fin Estudiante");
	}
		
		}

