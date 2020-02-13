package datacollection_unit;

/**
 * This class is responsible to web crawling
 * and extracting pdf links of research papers.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebDocument
{
    /**
     * Keeps the format of the url
     * @param msg
     * @param args
     * @return
     */
    private static String print(String msg, Object... args) {
        return String.format(msg, args);
    }

    /**
     * Returns list of pdf download links on web page
     * @param list
     * @return
     * @throws IOException
     */
    public static List<String> getUrls(List<String> list) throws IOException
    {
        List<String> urls  = new ArrayList<>();
        for(String url : list) {
            //validate that a url has been entered
            //Validate.isTrue(url.length() == 0, "usage: supply url to fetch");

            //Connect to ur and retrive all .pdf links in the DOM
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href*=.pdf]");

            //Store all .pdf links in a List and return
            print("\nLinks: (%d)", links.size());
            for (Element link : links) {
                urls.add(print(link.attr("abs:href"), trim(link.text(), 35)));
            }
        }
        return urls;
    }

    /**
     * Trims the url to the right format
     * @param s
     * @param width
     * @return
     */
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

}
