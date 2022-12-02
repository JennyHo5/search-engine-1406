import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Page implements Serializable {
    private String URL;
    private String html;
    private String title;
    private HashMap<String, Integer> words; //all words on the page
    private HashSet<String> allURLs; //all URLs on the page

    //constructors
    public Page() {
        URL = "";
        html = "";
        title = "";
        words = new HashMap<>();
        allURLs = new HashSet<>();
    }

    public Page(String iURL) {
        URL = iURL;
        html = "";
        title = "";
        words = new HashMap<>();
        allURLs = new HashSet<>();
    }

    public Page(String iURL, String iTitle, HashMap<String, Integer> iWords,
                HashSet<String> iAllURLs) {
        URL = iURL;
        title = iTitle;
        words = iWords;
        allURLs = iAllURLs;
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

    public void setAllURLs(HashSet<String> allURLs) {
        this.allURLs = allURLs;
    }

}
