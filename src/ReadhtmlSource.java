import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class ReadhtmlSource {

	private StringBuffer fileContent = new StringBuffer();
	

	/**
     * Construct an instance to read from a HTML file
     */
    public ReadhtmlSource(File htmlSourceFile) throws IOException {
    	try 
    	(
    			BufferedReader reader = new BufferedReader
    			(new FileReader(htmlSourceFile))
    			) 
    	{
    		String currentLine;
    		while ((currentLine = reader.readLine()) != null) 
    		{
    			fileContent.append(currentLine).append("\n");
    		}
    	}
    }

	/**
     * Construct an instance to read from a HTML web page
     */
    public ReadhtmlSource(String htmlSourcePage) throws IOException {
        Document doc = Jsoup.connect(htmlSourcePage).get();
        String sourceHtml = doc.outerHtml();
        fileContent.append(sourceHtml);
    }
		

	public StringBuffer getHTMLFileContent()
	{
		return fileContent;
	}
}
