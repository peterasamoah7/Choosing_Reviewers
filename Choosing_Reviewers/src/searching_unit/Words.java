package searching_unit;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class contains most occuring words.
 * @author Peter Asamoah
 */
public class Words
{

    /**
     * Builds a list of stop words from a text file
     * @param str
     * @return
     * @throws IOException
     */
    public static List<String> addStopWords(String str) throws IOException
    {
        List<String> stopWords = new ArrayList<>();
        Scanner reader = new Scanner(Paths.get(str));
        while (reader.hasNext()){
            String word = reader.nextLine().toLowerCase().trim();
            stopWords.add(word);
        }
        return stopWords;
    }

    /**
     * Removes duplicate words
     * @param words
     * @return
     */
    private static List<String> singleWords(List<String>words)
    {
        //remove repetitions
        List<String> unique = new ArrayList<>();
        for(String s : words){
            String word = s.toLowerCase().trim();
            if(!unique.contains(word)){
                unique.add(word);
            }
        }
        return  unique;
    }

    /**
     * Removes the stop words from the list of words
     * @param words
     * @return words
     */
    public static List<String> removeStopWords(List<String> words)
    {
        //final bag of words
        List<String> wordbag = singleWords(words);
        try {
            //add stop words
            List<String> stopWords = addStopWords("stopWords.txt");

            //find index words in list
            List<String> index = new ArrayList<>();
            for (int i = 0; i < stopWords.size(); i++) {
                for (int j = 0; j < wordbag.size(); j++) {
                    if (stopWords.get(i).equals(wordbag.get(j)) && !index.contains(stopWords.get(i))) {
                        index.add(stopWords.get(i));
                    }
                }
            }

            //remove index words in list
            removeIndexWords(index, wordbag);

        }
        catch (Exception ex){
            System.out.println("Text file is empty");
        }
        return wordbag;

    }

    /**
     * Removes words based on index
     * @param words
     * @param wordbag
     */
    public static void removeIndexWords(List<String> words, List<String> wordbag)
    {
        for(int i = 0; i < words.size(); i++){
            String word = words.get(i);
            wordbag.remove(word);
        }
    }


}
