//g00330821-Sarah Carroll

package ie.gmit.sw;
import java.util.Scanner;
/**
 * 
 * @author Sarah Carroll -G00330821
 * @version 1.0
 */

public class menu {

	public void show() throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("*** Document Comparison Service ***");
		
		String fileName1;
		String fileName2;
		Scanner console=new Scanner(System.in);
		
		// Get name of book or URL to process
		System.out.print("\nEnter File Name / URL 1: ");
		fileName1 = console.nextLine();
		
		System.out.print("\nEnter File Name / URL 2: ");
		fileName2 = console.nextLine();
		
		new Launcher(fileName1,fileName2, 100);
		console.close();
	}

	public menu() {
		//super();
		// TODO Auto-generated constructor stub
	}

}


