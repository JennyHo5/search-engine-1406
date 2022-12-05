import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Page implements Serializable, SearchResult {
    private String URL;
    private String html;
    private String title;
    private HashMap<String, Integer> words; //all words on the page
    private ArrayList<String> allURLs; //all URLs on the page
    private double score;
    private double pagerank;


    //constructors
    public Page(String iURL) {
        URL = iURL;
        html = "";
        title = "";
        words = new HashMap<>();
        allURLs = new ArrayList<>();
        score = 0;
        pagerank = 0;
    }

    public Page(String iURL, String iTitle, HashMap<String, Integer> iWords,
                ArrayList<String> iAllURLs) {
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

    public ArrayList<String> getAllURLs() {
        return allURLs;
    }

    public double getScore() {return score;}

    public Double getPagerank() {return pagerank;};

    //set methods
    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAllWords(HashMap<String, Integer> words) {
        this.words = words;
    }

    public void setAllURLs(ArrayList<String> allURLs) {
        this.allURLs = allURLs;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setPagerank (double pagerank) {this.pagerank = pagerank; }

}
