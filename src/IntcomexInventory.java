import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class IntcomexInventory extends Product {

	/*
	 * Retrieve products from the INTCOMEX WEB STORE,
	 * to populate database of MESECT's Web store.
	 */
	private static String prodPrice, prodQTY, rate;
	private static StringBuilder prodDetails = new StringBuilder();
	private static Document products;
	private static Elements productListings;
	//private static Product product = new Product();


	/**
	 * Inventory collection from HTML file, of INTCOMEX WEB STORE
	 * selected category
	 * @param inventoryFile
	 * 
	 * Constructor method
	 */
	IntcomexInventory(File inventoryFile) throws Exception {
		
		ReadhtmlSource htmlReader = new ReadhtmlSource(inventoryFile);
		String content = htmlReader.getHTMLFileContent().toString();	
		products = Jsoup.parse(content);
		productListings = products.getElementsByTag("form");
		
		// price of product is to be extracted,
		// and stored in rate
		rate = products.select("span[id=lblTicker].text-black")
				.text()
				.trim();
		rate = rate.substring(rate.indexOf("=") + 1, rate.indexOf(".") + 3).trim();
		rate = rate.substring(rate.indexOf("$") + 1, rate.length()).trim();

		
		/* Get the exchange rate, and convert to the double data type,
		 * with 2 decimal places.
		 */
			setFxRate(convertToMoney(rate));	

		/*
		 * Extracting the name of the supplier
		 */
		String supplier = products.getElementsByTag( "head" )
				.select( "title" )
				.text();

		setSupplier(supplier
				.substring(0, supplier.indexOf( "-" ))
				.trim());

		// Check if the supplier exists in the database
		checkSupplier();

		/*
		 * Extracting the name of the category for the product
		 */
		setCategory(products.getElementsByTag( "h2" ).text());

		// Check if the product category exists in the database
		checkCategory();

	}

	/*
	 * Convert string value of money to type double
	 */
	private double convertToMoney(String productValue) {
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
	private int productQuantity( String productAmount ) {
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
	private void productAttributes(Element productItem) {

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
			
			setProduct_AdditionalInfo(prodDetails);
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

		resetAvailability();
		
		for ( Element productListing : productListings ) {
			if ( productListing.children().hasClass( "productArea" ) ) {

				setImage(getProductImage(productListing));					

				setModelInfo(productListing
						.children()
						.select( ".product-name" )
						.text());

				setDescription(productListing
						.children()
						.select( ".description-name" )
						.text());

				setSkuNumber( productListing
						.children()
						.select( ".value").first()
						.text());

				setPartNumber( productListing
						.children()
						.select( ".value").last()
						.text());					

				setBrand(productListing
						.children()
						.select( ".marca" )
						.text());

				prodPrice = productListing
						.children()
						.select( ".text-danger" )
						.text()
						.trim();
				
				double usCost = convertToMoney(prodPrice);
				double jmdCost = usCost * getFXrate();
				
				setCost( jmdCost );

				prodQTY = productListing
						.children()
						.select( ".availabilityGrid" )
						.text();

				setQuantity(productQuantity(prodQTY));
				
				Element productDetails = productListing
						.nextElementSibling();												

				prodCount++;

				System.out.println( "\n\n-------- Form "+( productListings
						.indexOf( productListing ) + 1 )+ " Product # "
						+ prodCount + " --------" );								

				System.out.println( "Product category .....:\t"+ getCategory() );
				System.out.println( "Product Model Info ...:\t"+ getModelInfo() );
				System.out.println( "Product Image ........:\t"+ getImage() );
				System.out.println( "Product Brand ........:\t"+ getBrand() );
				System.out.println( "Product Description ..:\t"+ getDescription() );
				System.out.println( "Product Part Number ..:\t"+ getPartNumber() );
				System.out.println( "Product SKU Number ...:\t"+ getSkuNumber());
				System.out.println( "Product Supplier .....:\t"+ getSupplier() );

				System.out.println("\n-------------- Product Details"
						+ " --------------");

				productAttributes( productDetails );
				prodDetails.delete(0, prodDetails.length());

				System.out.println( "\nExchange rate ........:\tJ$ " + getFXrate() );
				System.out.println( "\nProduct Price ........:\tJ$ " + getCost() );
				System.out.println( "\nQuantity in Stock ....:\t" + getQuantity() );

				/*
				 * Populate the database with product, 
				 * and update existing products, 
				 * with current product data received.
				 */

				// Enter code for populating and updating database here
				// ...........

				checkBrand();

				reviewProduct();
			}

			// inside the for loop, and outside the if condition
		}
		
		//setDBConnection().close();
	}

	 private String getProductImage(Element productListing) throws IOException {
	        String imageName, imageLink = null;

	        // Get the URL where the image can be found
	        String imageSource = productListing
	                .children()
	                .select("div a")
	                .attr("href");

	        // Get the name of the product image file to search for
	        imageName = productListing
	                .children()
	                .select("img")
	                .attr("src");

	        // Extract only the necessary part of the product image file
	        if (imageName != null && !imageName.isEmpty()) {
	            imageName = imageName.substring(imageName.lastIndexOf("/") + 1);
	            if ( imageName.equalsIgnoreCase("noimage.jpg"))
	            {
	            	return "https://store.intcomex.com/Content/Images/" + imageName;
	            }
//	            System.out.println("This is the name of the product: " + imageName);
	        } else {
//	            System.out.println("No image found in the product listing.");
	            return null;
	        }

	        // Connect with the URL where the image can be found, and store the content as a document
	        ReadhtmlSource sourcePageHtml;
	        try 
	        {
	            sourcePageHtml = new ReadhtmlSource(imageSource);
	        } 
	        catch (IOException e) 
	        {
	            JOptionPane.showMessageDialog(null, e);
	            return null;
	        }

	        String pageHtml = sourcePageHtml.getHTMLFileContent().toString();
	        Document doc = Jsoup.parse(pageHtml);

	        // Print the HTML code for debugging purposes
	        // Capture the HTML source code for the URL where the image can be found
	        // Parse the HTML code, and store as a different Elements
	        Elements links = doc.select("span+a");

	        for (Element link : links) {
	            String href = link.attr("href");
	            if (href.contains(imageName)) {
	                imageLink = href;
	                break;
	            }
	        }

	        if (imageLink != null) {
	            return imageLink;
	        } else {
	            return "https://store.intcomex.com/images/products/" + imageName;
	        }

	    }



	
}


