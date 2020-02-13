package searching_unit;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;


public class SearchEngine {


    //some fields
    private static IndexSearcher searcher = null;
    private static QueryParser parser = null;

    /**
     * Starts the search engine
     * @throws IOException
     */
    public  SearchEngine() throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("index-directory")));
        searcher = new IndexSearcher(reader);
        parser = new QueryParser("content", new StopAnalyzer());
    }

    /**
     * Perform search with a string query
     * @param queryString
     * @param n
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static TopDocs performSearch(String queryString, int n)
            throws IOException, ParseException {
        Query query = parser.parse(queryString);
        return searcher.search(query, n);
    }

    /**
     * Return the document matching the doc ID
     * @param docId
     * @return
     * @throws IOException
     */
    public static Document getDocument(int docId)
            throws IOException {
        return searcher.doc(docId);
    }
}