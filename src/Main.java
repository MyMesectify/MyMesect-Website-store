import java.io.File;

public class Main {
	
	private static File file;
	private static Object inventory;	
	
	/*
	 * Define the path and filename of the data source,
	 * as well as the data source's type,
	 * and retrieve the data accordingly.
	 */
	public static void inventoryDefinition
	(String source, int option) 
			throws Exception {
		
		file = new File( source );

		switch (option) {
		
		/*
		 * This data source is the Intcomex Jamaica
		 * web store. It is type 1
		 */
		case 1:
			inventory = new IntcomexInventory(file);
			((IntcomexInventory) inventory).getProducts();
			break;

		default:
			System.out.println( "That option is not supported\n" );
			break;
			
		}
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new InventoryManagerGUI();		
		

	}
}
