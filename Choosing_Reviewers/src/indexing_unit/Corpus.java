package indexing_unit;

import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * This class contains information about
 * the corpus.
 */
public class Corpus
{
     // field to hold file names
    private  static HashMap<String,String> papers = new HashMap<>();

    /**
     * List all the file names in a folder
     * @param folder
     * @return
     */
    public static HashMap<String,String> listAllFiles(final File folder)
    {
        for(final File fileEntry : folder.listFiles()){
            if(fileEntry.isDirectory()){
                listAllFiles(fileEntry);
            }
            else if(fileEntry.isFile() && fileEntry.getName().endsWith(".pdf")){
                papers.put(fileEntry.getName(),fileEntry.getPath());
            }
        }
        return papers;
    }

    /**
     * Extracts keyowrds from pdf and builds list of Research Paper obejcts
     * @param folder
     * @return
     * @throws IOException
     */
    public static List<ResearchPaper> buildCorpus(final File folder) throws IOException
    {
        List<ResearchPaper> allPapers = new ArrayList<>();
        HashMap<String,String> papers = listAllFiles(folder);
        Set<String> fileNames = papers.keySet();
        for(String key : fileNames){
            PDFManager pdfManager = new PDFManager();
            pdfManager.setFilePath(papers.get(key));
            allPapers.add(new ResearchPaper(key,pdfManager.ToText()));
        }
        return allPapers;
    }



}
