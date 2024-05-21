import java.io.File;

public class Main {
	
	private static String sourceFile;
	private static File file;
	private static Object inventory;
	
	private static void inventoryDefinition
	(String source, int option) 
			throws Exception {
		
		sourceFile = source;
		file = new File( sourceFile );

		switch (option) {

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

		String srcFile = "C:\\Users\\peter_000\\"
				+ "Downloads\\Monitors.htm";
		
		int sourceType = 1;
		
		inventoryDefinition(srcFile, sourceType);
		
		

		// Extract each product from the HTML product listing


	}
}
