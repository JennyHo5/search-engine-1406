import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Page implements Serializable, SearchResult, Comparable<Page> {
    private String URL;
    private String html;
    private String title;
    private HashMap<String, Integer> words; //all words on the page
    private HashSet<String> allURLs; //all URLs on the page
    private double score;
    private double pagerank;


    //constructors
    public Page(String iURL) {
        URL = iURL;
        html = "";
        title = "";
        words = new HashMap<>();
        allURLs = new HashSet<>();
        score = 0;
        pagerank = 0;
    }

    public Page(String iURL, String iTitle, HashMap<String, Integer> iWords,
                HashSet<String> iAllURLs) {
        URL = iURL;
        title = iTitle;
        words = iWords;
        allURLs = iAllURLs;
        score = 0;
        pagerank = 0;
    }

    //get methods
    public String getURL() {
        return URL;
    }

    public String getHtml() {
        return html;
    }

    public String getTitle() {
        return title;
    }


    public HashMap<String, Integer> getAllWords() {
        return words;
    }

    public HashSet<String> getAllURLs() {
        return allURLs;
    }

    public double getScore() {return score;}

    public Double getPagerank() {return pagerank;};

    //set methods
    public void setHtml(String html) {
        this.html = html;
    }


    public void setScore(double score) {
        this.score = score;
    }

    public void setPagerank (double pagerank) {this.pagerank = pagerank; }

    @Override
    public int compareTo(Page o) {
        return Comparator.comparing(Page :: getScore).reversed().thenComparing(Page :: getTitle).compare(this, o);
    }
}
