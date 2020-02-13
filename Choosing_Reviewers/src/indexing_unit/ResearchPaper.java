package indexing_unit;

/**
 * This class hold information on a Research paper
 * object.
 */
public class ResearchPaper
{
    //some fields
    private String author;
    private String paper;

    public ResearchPaper(String author, String paper)
    {
        this.author = author;
        this.paper  = paper;
    }

    /**
     * Return the author
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Return the paper content
     * @return paper
     */
    public String getPaper() {
        return paper;
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "author='" + author + '\'' +
                ", paper='" + paper + '\'' +
                '}';
    }
}
