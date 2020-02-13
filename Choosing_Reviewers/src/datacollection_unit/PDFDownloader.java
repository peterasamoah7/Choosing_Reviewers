package datacollection_unit;

/**
 * This class creates a directory and downloads
 * all the PDF to form the corpus
 */
import java.util.*;
import java.nio.file.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class PDFDownloader
{

    /**
     * Reads url from text file and downloads pdfs
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
         List<String> links = new ArrayList<>();
         Scanner input = new Scanner(Paths.get("urls.txt"));
         while(input.hasNext()){
            String url = input.nextLine();
            links.add(url);
         }

         List<String> urls = WebDocument.getUrls(links);
         for(String url : urls){
            PDFDownloader.download(url);
         }
    }

    /**
     * Download a pdf and save to file
     * @param link
     * @throws IOException
     */
    public static void download(String link) throws IOException
    {
        try {
            URL url = new URL(link);
            File destination = new File(link);
            FileUtils.copyURLToFile(url, destination);
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
        catch (ConnectException ex){
            System.out.println("Connection refused");
        }

    }

}
