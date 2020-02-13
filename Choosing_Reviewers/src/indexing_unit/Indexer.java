package indexing_unit;

import java.io.IOException;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *@author Peter Asamoah
 */
public class Indexer {

    /**
     * Initializer
     */
    public Indexer() {
    }

    /**
     * Reads absolute path to folder and indexes files
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        try {
            //fetch paths from txt file
            List<String> paths = new ArrayList<>();
            Scanner input = new Scanner(Paths.get("corpus.txt"));
            while (input.hasNext()) {
                String path = input.nextLine().trim();
                paths.add(path);
            }

            for (String path : paths) {
                File folder = new File(path);
                rebuildIndex(folder);
            }
            System.out.println("rebuildIndexes done");
        }catch (Exception ex){
            System.out.println("Exception caught.\n");
        }

    }

    //indexwriter field
    private static IndexWriter indexWriter = null;

    /**
     * Creates index writer to index files
     * @param create
     * @return
     * @throws IOException
     */
    public static IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null) {
            Directory indexDir = FSDirectory.open(Paths.get("index-directory"));
            Analyzer analyzer = new StopAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            indexWriter = new IndexWriter(indexDir, config);
        }
        return indexWriter;
    }

    /**
     * Closes index writer
     * @throws IOException
     */
    public static void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    /**
     * Indexes papers
     * @param paper
     * @throws IOException
     */
    public static void indexPaper(ResearchPaper paper) throws IOException {

        IndexWriter writer = getIndexWriter(false);
        Document doc = new Document();
        doc.add(new StringField("author", paper.getAuthor(), Field.Store.YES));
        String fullSearchableText = paper.getPaper();
        doc.add(new TextField("content", fullSearchableText, Field.Store.NO));
        writer.addDocument(doc);
    }

    /***
     * Rebuilds index
     * @param folder
     * @throws IOException
     */
    public static void rebuildIndex(final File folder) throws IOException {
        getIndexWriter(true);
        List<ResearchPaper> papers = Corpus.buildCorpus(folder);
        for(ResearchPaper paper : papers) {
            indexPaper(paper);
        }
        closeIndexWriter();
    }

}