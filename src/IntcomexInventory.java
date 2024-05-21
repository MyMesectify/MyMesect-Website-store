import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.File;

public class IntcomexInventory extends Product {

	/*
	 * Retrieve products from the INTCOMEX WEB STORE,
	 * to populate database of MESECT's Web store.
	 */
	private static String prodPrice, prodQTY, rate;
	private static StringBuilder prodDetails = new StringBuilder();
	private static Document products;
	private static Elements productListings;
	private static Product product = new Product();


	/**
	 * Inventory collection from HTML file, of INTCOMEX WEB STORE
	 * selected category
	 * @param inventoryFile
	 * 
	 * Constructor method
	 */
	IntcomexInventory(File inventoryFile) throws Exception {
		
		// Set the location of the HTML file holding the data to be web scraped		
		products = Jsoup.parse(inventoryFile, "utf-8");
		productListings = products.getElementsByTag("form");
		
		/* Get the exchange rate, and convert to the double data type,
		 * with 2 decimal places.
		 */
		rate = products.getElementsByClass("currency-menu").text();
		rate = rate.substring(rate.indexOf("="),rate.length());
		product.setFxRate(convertToMoney(rate));			

		/*
		 * Extracting the name of the supplier
		 */
		String supplier = products.getElementsByTag( "head" )
				.select( "title" )
				.text();

		product.setSupplier(supplier
				.substring(0, supplier.indexOf( "-" ))
				.trim());

		// Check if the supplier exists in the database
		product.checkSupplier();

		/*
		 * Extracting the name of the category for the product
		 */
		product.setCategory(products.getElementsByTag( "h2" ).text());

		// Check if the product category exists in the database
		product.checkCategory();

	}

	/*
	 * Convert string value of money to type double
	 */
	private static double convertToMoney(String productValue) {
		double money = 0.00;

		String dollars = "", priceStr = "";

		dollars = productValue.substring 
				( productValue.indexOf( "$" ) + 1, productValue.length())
				.trim();
		
		for ( char priceElement : dollars.toCharArray() ) 
		{ 
			if( priceElement != ',')
				{ 
					priceStr += priceElement;
				}
			money = Double.parseDouble( priceStr );
		};

		return money;
	}


	/*
	 * Get the current product quantity 
	 * and parse it as an integer.
	 */
	private static int productQuantity( String productAmount ) {
		int productsInStock = 0;

		for ( char numElement : productAmount.toCharArray() ) {
			if ( ! Character.isDigit( numElement )) {
				productAmount = productAmount.replace( numElement, ' ');						
			}
		}	

		productAmount = productAmount.trim();
		productsInStock = Integer.parseInt(productAmount);

		return productsInStock;
	}


	/*
	 *	 Get product attributes and details
	 */
	private static void productAttributes(Element productItem) {

		Elements details = productItem
				.select( ".visible-xs");

		if ( details.size() > 0 ) 
		{
			for ( Element detail : details ) 
			{
				try 
				{
					prodDetails.append( detail.text() + "; " );
					System.out.println( detail.text() + ";" );
					
				}
				catch (Exception ex) 
				{

				}				
			}
			
			product.setProduct_AdditionalInfo(prodDetails);
		} 
		else 
		{
			System.out.println( "No attributes found" );
		}
	}

	
	/**
	 * @param productFile
	 * @throws Exception
	 * Get the products from the HTML file
	 * acquired via the constructor method 
	 */
	public void getProducts() throws Exception {
		
		// Extract each product from the HTML product listing

		int prodCount = 0;

		product.resetAvailability();
		
		for ( Element productListing : productListings ) {
			if ( productListing.children().hasClass( "productArea" ) ) {

				product.setImage(productListing
						.children()
						.select("img")
						.attr( "src" ));						

				product.setModelInfo(productListing
						.children()
						.select( ".product-name" )
						.text());

				product.setDescription(productListing
						.children()
						.select( ".description-name" )
						.text());

				product.setSkuNumber( productListing
						.children()
						.select( ".value").first()
						.text());

				product.setPartNumber( productListing
						.children()
						.select( ".value").last()
						.text());					

				product.setBrand(productListing
						.children()
						.select( ".marca" )
						.text());

				prodPrice = productListing
						.children()
						.select( ".text-danger" )
						.text()
						.trim();
				
				product.setCost(convertToMoney(prodPrice));

				prodQTY = productListing
						.children()
						.select( ".availabilityGrid" )
						.text();

				product.setQuantity(productQuantity(prodQTY));
				
				Element productDetails = productListing
						.nextElementSibling();												

				prodCount++;

				System.out.println( "\n\n-------- Form "+( productListings
						.indexOf( productListing ) + 1 )+ " Product # "
						+ prodCount + " --------" );								

				System.out.println( "Product category .....:\t"+product.getCategory() );
				System.out.println( "Product Model Info ...:\t"+product.getModelInfo() );
				System.out.println( "Product Image ........:\t"+product.getImage() );
				System.out.println( "Product Brand ........:\t"+product.getBrand() );
				System.out.println( "Product Description ..:\t"+product.getDescription() );
				System.out.println( "Product Part Number ..:\t"+product.getPartNumber() );
				System.out.println( "Product SKU Number ...:\t"+product.getSkuNumber());
				System.out.println( "Product Supplier .....:\t"+product.getSupplier() );

				System.out.println("\n-------------- Product Details"
						+ " --------------");

				productAttributes( productDetails );
				prodDetails.delete(0, prodDetails.length());

				System.out.println( "\nExchange rate ........:\tJ$ " + product.getFXrate() );
				System.out.println( "\nProduct Price ........:\tJ$ " + product.getCost() );
				System.out.println( "\nQuantity in Stock ....:\t" + product.getQuantity() );

				/*
				 * Populate the database with product, 
				 * and update existing products, 
				 * with current product data received.
				 */

				// Enter code for populating and updating database here
				// ...........

				product.checkBrand();

				product.reviewProduct();
			}

			// inside the for loop, and outside the if condition
		}
		product.getDBConnection().close();
	}



	
}


