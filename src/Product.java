
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Product {

	/*
	 * Product Attributes
	 */
	private String product_Category;
	private String product_Brand;
	private String product_ModelInfo;
	private String product_Description;
	private StringBuilder product_AdditionalInfo = new StringBuilder();
	private String product_Supplier;
	private String product_Image;
	private String product_PartNumber;
	private String product_skuNumber;
	private int product_Qty;
	private double product_Cost;
	private double current_FXRate;
	private boolean isAvailable;	
	static String userName, currentPassword;

	// Not accessible externally.
	// Used internally for the conversion of the StringBuilder data type,
	// to a string data type
	private String product_MoreInfo;

	/*
	 * Connection to product inventory database
	 */
	private Connection product_dbConnection = null;

	/*
	 * SQL query & instructions pointer
	 */
	private PreparedStatement sqlCmd;


	/*
	 * The dataset from a SQL executeQuery statement
	 */
	private ResultSet sqlResult;

	/*
	 * Use these SQL strings for prepared SQL statements,
	 * to execute searches, insert records, update
	 * existing records, and remove irrelevant records.
	 */
	private String findProduct_SQL = "SELECT * FROM ProductInfo "
			+ "WHERE sku = ? AND Part_number = ? ";

	private String addProduct_SQL = "INSERT INTO ProductInfo "
			+ "( Category_ID, "
			+ "		Brand_ID, "
			+ "		Supplier_ID, "
			+ "		Model_Info, "
			+ "		Description, "
			+ "		Product_details, "
			+ "		SKU, "
			+ "		Part_number, "
			+ "		Quantity, "
			+ "		Cost, "
			+ "		FX_Rate, "
			+ "		Pic, "
			+ "		Available ) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	private String updateProduct_SQL = "UPDATE ProductInfo SET "
			+ "Model_Info = ?, "
			+ "Description = ?, "
			+ "Product_details = ?, "
			+ "Quantity = ?, "
			+ "Cost = ?, "
			+ "FX_Rate = ?, "
			+ "Pic = ?, "
			+ "Available = ? "
			+ "WHERE SKU = ? "
			+ "AND Part_number = ?";

	private String findBrand_SQL = "SELECT * FROM Brand "
			+ "WHERE Brand = ?";

	private String addBrand_SQL = "INSERT INTO Brand (Brand) "
			+ "VALUES (?) ";

	private String findCategory_SQL = "SELECT * FROM ProductCategory "
			+ "WHERE Category = ?";

	private String addCategory_SQL = "INSERT INTO ProductCategory (Category) "
			+ "VALUES (?) ";

	private String findSupplier_SQL = "SELECT * FROM Supplier "
			+ "WHERE Supplier = ? ";

	private String addSupplier_SQL = "INSERT INTO Supplier (Supplier) "
			+ "VALUES (?) ";

	private String updateAvailability_SQL = "UPDATE ProductInfo SET "
			+ "Available = ? WHERE Category_ID = ? AND Supplier_ID = ? ";


	/*
	 * Constructs a blank product object
	 */
	Product() {

		// Establishing the connection to the product inventory database
		// Password = !!P3d3lm@r2!!
		try {
			// Load MySQL JDBC Driver
			Class.forName( "com.mysql.cj.jdbc.Driver" );

			product_dbConnection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/MESECT_eStoreDB",
					userName, 
					currentPassword );
		}
		catch (Exception e){

		}
	}


	/*
	 * Constructs a product object with values and attributes
	 * that were acquired from the inventory source document
	 */
	Product (ArrayList<Object> productItem) {

		try {
			// Load MySQL JDBC Driver
			Class.forName( "com.mysql.cj.jdbc.Driver" );

			product_dbConnection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/MESECT_eStoreDB",
					userName, 
					currentPassword );

		}
		catch (Exception e){

		}

		product_Category = (String) productItem.get(0);
		product_Brand = (String) productItem.get(1);
		product_ModelInfo = (String) productItem.get(2);
		product_Description = (String) productItem.get(3);
		product_AdditionalInfo = (StringBuilder) productItem.get(4);
		product_Supplier = (String) productItem.get(5);
		product_Image = (String) productItem.get(6);
		product_PartNumber = (String) productItem.get(7);
		product_skuNumber = (String) productItem.get(8);
		product_Qty = (int) productItem.get(9);
		product_Cost = (double) productItem.get(10);
		current_FXRate = (double) productItem.get(11);
		isAvailable = (boolean) productItem.get(12);
	}

	public void setCategory(Object category) {
		product_Category = (String) category;
	}

	public void setBrand(Object brand) {
		product_Brand = (String) brand;
	}

	public void setModelInfo(Object model) {
		product_ModelInfo = (String) model;
	}

	public void setDescription(Object describeProduct) {
		product_Description = (String) describeProduct;
	}

	public void setProduct_AdditionalInfo(Object moreInfo) {
		product_AdditionalInfo.append(moreInfo);
	}

	public void setSupplier(Object supplier) {
		product_Supplier = (String) supplier;
	}

	public void setImage(Object productImage) {
		product_Image = (String) productImage;
	}

	public void setPartNumber(Object partNumber) {
		product_PartNumber = (String) partNumber;
	}

	public void setSkuNumber(Object sku) {
		product_skuNumber = (String) sku;
	}

	public void setFxRate(Object fxRate) {
		current_FXRate = (double) fxRate;
	}

	public void setCost(Object cost) {
		product_Cost = (double) cost;
	}

	public void setQuantity(int qty) {
		product_Qty = (int) qty;
	}

	public void setAvailability(boolean available) {
		isAvailable = (boolean) available;
	}

	public String getCategory() {
		return product_Category;
	}

	public String getBrand() {
		return product_Brand;
	}

	public String getModelInfo() {
		return product_ModelInfo;
	}

	public String getDescription() {
		return product_Description;
	}

	public StringBuilder getProduct_AdditionalInfo() {
		return product_AdditionalInfo;
	}

	public String getSupplier() {
		return product_Supplier;
	}

	public String getImage() {
		return product_Image;
	}

	public String getPartNumber() {
		return product_PartNumber;
	}

	public String getSkuNumber() {
		return product_skuNumber;
	}

	public double getFXrate() {
		return current_FXRate;
	}

	public double getCost() {
		return product_Cost;
	}

	public int getQuantity() {
		return product_Qty;
	}

	public boolean getAvailability() {
		return isAvailable;
	}

	public Connection getDBConnection() {
		return product_dbConnection;
	}


	public void reviewProduct() throws Exception {

		product_MoreInfo = "";

		product_dbConnection = DriverManager.getConnection(
				"jdbc:mysql://security/MESECT_eStoreDB",
				userName, 
				currentPassword);

		sqlCmd = product_dbConnection.prepareStatement( findProduct_SQL );
		sqlCmd.setString( 1, product_skuNumber );
		sqlCmd.setString( 2, product_PartNumber );
		sqlResult = sqlCmd.executeQuery();

		for ( int i = 0; i < product_AdditionalInfo.length() ; i++ ) {
			if ( product_AdditionalInfo.charAt(i) == ';' ) {
				product_MoreInfo += product_AdditionalInfo.substring(0,i);
				product_AdditionalInfo.delete( 0,  i + 1 );
				i = 0;
			}
		}

		if ( sqlResult.next() ) {

			System.out.println("\nThe product with SKU # "+ product_skuNumber 
					+" and part # "+ product_PartNumber +" is in the database");

			sqlCmd = product_dbConnection.prepareStatement( updateProduct_SQL );
			sqlCmd.setObject( 1, product_ModelInfo );
			sqlCmd.setObject( 2, product_Description );
			sqlCmd.setObject( 3, product_MoreInfo );
			sqlCmd.setObject( 4, product_Qty );
			sqlCmd.setObject( 5, product_Cost );
			sqlCmd.setObject( 6, current_FXRate );
			sqlCmd.setObject( 7, product_Image );
			sqlCmd.setObject( 8, true );
			sqlCmd.setObject( 9, product_skuNumber );
			sqlCmd.setObject( 10, product_PartNumber );
			int updatedRecords = sqlCmd.executeUpdate();

			System.out.println("\n"+ updatedRecords +" record(s) updated" );
		}
		else {

			int product_CategoryID, product_BrandID, product_SupplierID;

			product_dbConnection = DriverManager.getConnection(
					"jdbc:mysql://security/MESECT_eStoreDB",
					userName, 
					currentPassword);

			sqlCmd = product_dbConnection.prepareStatement( findCategory_SQL );
			sqlCmd.setObject( 1, product_Category );
			sqlResult = sqlCmd.executeQuery();
			sqlResult.next();
			product_CategoryID = sqlResult.getInt( 1 );

			sqlCmd = product_dbConnection.prepareStatement( findBrand_SQL );
			sqlCmd.setObject( 1, product_Brand );
			sqlResult = sqlCmd.executeQuery();
			sqlResult.next();
			product_BrandID = sqlResult.getInt( 1 );

			sqlCmd = product_dbConnection.prepareStatement( findSupplier_SQL );
			sqlCmd.setObject( 1, product_Supplier );
			sqlResult = sqlCmd.executeQuery();
			sqlResult.next();
			product_SupplierID = sqlResult.getInt( 1 );

			System.out.println("\nThe product with SKU # "+ product_skuNumber 
					+" and part # "+ product_PartNumber +" is NOT in the database");

			sqlCmd = product_dbConnection.prepareStatement( addProduct_SQL );
			sqlCmd.setObject( 1, product_CategoryID );
			sqlCmd.setObject( 2, product_BrandID );
			sqlCmd.setObject( 3, product_SupplierID );
			sqlCmd.setObject( 4, product_ModelInfo );
			sqlCmd.setObject( 5, product_Description );
			sqlCmd.setObject( 6, product_MoreInfo );
			sqlCmd.setObject( 7, product_skuNumber );
			sqlCmd.setObject( 8, product_PartNumber );
			sqlCmd.setObject( 9, product_Qty );
			sqlCmd.setObject( 10, product_Cost );
			sqlCmd.setObject( 11, current_FXRate );
			sqlCmd.setObject( 12, product_Image );
			sqlCmd.setObject( 13, 1 );

			int addedRecords = sqlCmd.executeUpdate();

			System.out.println("\n"+ addedRecords +" record(s) added" );
		}
	}

	public void resetAvailability() throws Exception {

		int categoryID, supplierID;

		sqlCmd = product_dbConnection.prepareStatement( findCategory_SQL );
		sqlCmd.setObject( 1, product_Category );
		sqlResult = sqlCmd.executeQuery();
		sqlResult.next();
		categoryID = sqlResult.getInt( 1 );

		sqlCmd = product_dbConnection.prepareStatement( findSupplier_SQL );
		sqlCmd.setObject( 1, product_Supplier );
		sqlResult = sqlCmd.executeQuery();
		sqlResult.next();
		supplierID = sqlResult.getInt( 1 );

		sqlCmd = product_dbConnection.prepareStatement( updateAvailability_SQL );
		sqlCmd.setObject( 1, false );
		sqlCmd.setObject( 2, categoryID );
		sqlCmd.setObject( 3, supplierID );
		sqlCmd.executeUpdate();

	}

	public void checkSupplier() throws Exception {
		
		product_dbConnection = DriverManager.getConnection(
				"jdbc:mysql://security/MESECT_eStoreDB",
				userName, 
				currentPassword );
		
		sqlCmd = product_dbConnection.prepareStatement( findSupplier_SQL );
		sqlCmd.setObject( 1, product_Supplier );
		sqlResult = sqlCmd.executeQuery();
		
		if ( ! sqlResult.next() ) {
			sqlCmd = product_dbConnection.prepareStatement( addSupplier_SQL );
			sqlCmd.setObject( 1, product_Supplier );
			sqlCmd.executeUpdate();
			
			System.out.println( "\n"+ product_Supplier +" added to database\n" );
		}
		else {
			System.out.println( "\n"+ product_Supplier +" exists in database\n" );
		}
	}

	
	public void checkCategory() throws Exception {
		
		product_dbConnection = DriverManager.getConnection(
				"jdbc:mysql://security/MESECT_eStoreDB",
				userName, 
				currentPassword );
		
		sqlCmd = product_dbConnection.prepareStatement( findCategory_SQL );
		sqlCmd.setObject( 1, product_Category );
		sqlResult = sqlCmd.executeQuery();
		
		if ( ! sqlResult.next() ) {
			sqlCmd = product_dbConnection.prepareStatement( addCategory_SQL );
			sqlCmd.setObject( 1, product_Category );
			sqlCmd.executeUpdate();
			
			System.out.println( "\n"+ product_Category +" added to database\n" );
		}
		else {
			System.out.println( "\n"+ product_Category +" exists in database\n" );
		}
	}
	
	public void checkBrand() throws Exception {
		
		product_dbConnection = DriverManager.getConnection(
				"jdbc:mysql://security/MESECT_eStoreDB",
				userName, 
				currentPassword );
		
		sqlCmd = product_dbConnection.prepareStatement( findBrand_SQL );
		sqlCmd.setObject( 1, product_Brand );
		sqlResult = sqlCmd.executeQuery();
		
		if ( ! sqlResult.next() ) {
			sqlCmd = product_dbConnection.prepareStatement( addBrand_SQL );
			sqlCmd.setObject( 1, product_Brand );
			sqlCmd.executeUpdate();
			
			System.out.println( "\n"+ product_Brand +" added to database\n" );
		}
		else {
			System.out.println( "\n"+ product_Brand +" exists in database\n" );
		}
		
	}
	
}
