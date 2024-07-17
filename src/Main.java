import java.io.File;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
	
	public static final String dbConString = "jdbc:mysql://127.0.0.1/MESECT_eStoreDB",
			mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
	public static Connection dbConnection;
	public static String userName, userPassword;
	public static File inventorySourceFile;
	public File inventorySourcePath;
	public File sqlFile;
	public File sqlFilePath;
 
	/*
	 * Define the path and filename of the data source,
	 * as well as the data source's type,
	 * and retrieve the data accordingly.
	 */
	public static void inventoryDefinition
	(String source, int option) 
			throws Exception {
		
		inventorySourceFile = new File( source );

		switch (option) {
		
		/*
		 * This data source is the Intcomex Jamaica
		 * web store. It is type 1
		 */
		case 1:
			IntcomexInventory inventory = new IntcomexInventory(inventorySourceFile);
			inventory.getProducts();
			break;

		default:
			System.out.println( "That option is not supported\n" );
			break;
			
		}
		
	}
	
	public static String setConnection() 
	{
		try
		{
			// Load MySQL JDBC Driver
			Class.forName( mysqlJDBCDriver );

			dbConnection = DriverManager.getConnection(
					dbConString,
					userName, 
					userPassword );

			return "Successful connection !!";
			
		} catch (Exception e)
		{
			return "!! Connection failed !!";
		}
	
	}
	
	public static Connection getConnection()
	{
		return dbConnection;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		new InventoryManagerGUI();		

	}
}
