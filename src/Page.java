import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Page {
    private String URL;
    private String html;
    private String title;
    private String fullURL; //full URL of the page
    private HashMap<String, Integer> allWords; //all words on the page
    private HashSet<String> allURLs; //all URLs on the page

    //constructors
    public Page() {
        URL = "";
        html = "";
        title = "";
        fullURL = "";
        allWords = new HashMap<>();
        allURLs = new HashSet<>();
    }

    public Page(String iURL, String iTitle, String iFullURL, HashMap<String, Integer> iAllWords,
                HashSet<String> iAllURLs) {
        URL = iURL;
        title = iTitle;
        fullURL = iFullURL;
        allWords = iAllWords;
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

    public String getFullURL() {
        return fullURL;
    }

    public HashMap<String, Integer> getAllWords() {
        return allWords;
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

    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }

    public void setAllWords(HashMap<String, Integer> allWords) {
        this.allWords = allWords;
    }

    public void setAllURLs(HashSet<String> allURLs) {
        this.allURLs = allURLs;
    }
}
