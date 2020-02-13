package searching_unit;
/**
 * This class builds essential words
 * from an essay or research paper context
 */

import java.util.*;

public class KeyWordBuilder
{
    //initializer
    public KeyWordBuilder()
    {

    }

    /**
     * Build a list of words from a string of words
     * @param str
     * @return allWords
     */
    public static List<String> buildWordList(String str)
    {
        String[] essayWords = str.split("[\\p{Punct}\\s]+");
        List<String> allWords = new ArrayList<String>();
        for(String s : essayWords){
            String word = s.toLowerCase().trim();
            allWords.add(word);
        }
        return allWords;
    }

    /**
     * Build a list of words pair ranking from highest to lowest
     * @param strs
     * @param strs1
     * @return
     */
    public static List<WordPair> buildWordPair(List<String> strs,List<String>strs1)
    {
        List<WordPair> wordPair = new ArrayList<>();
        for(int i = 0; i < strs.size(); i++){
            String word1 = strs.get(i);
            int count = 0;
            for(int j = 0; j < strs1.size(); j++){
                String word2 = strs1.get(j);
                if(word1.equals(word2)){
                    count++;
                }
            }
            wordPair.add(new WordPair(word1, count));
        }
        Collections.sort(wordPair, Collections.reverseOrder());
        return wordPair;
    }
}