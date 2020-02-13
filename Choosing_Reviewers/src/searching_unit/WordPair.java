package searching_unit;

/**
 *
 * @author Peter Asamoah
 *
 */

public class WordPair implements Comparable<WordPair>
{
    //some fields
    private String word;
    private int rank;

    //Initializer
    public WordPair(String word, int rank)
    {
        this.word = word;
        this.rank = rank;
    }

    /**
     * Returns word
     * @return
     */
    public String getWord()
    {
        return word;
    }

    /**
     * Return rank
     * @return
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Compares words based on rank
     * @param pair
     * @return
     */
    public int compareTo(WordPair pair)
    {
        if(rank > pair.getRank()){
            return 1;
        }
        else if(rank < pair.getRank()){
            return -1;
        }
        else{
            return 0;
        }
    }





}
