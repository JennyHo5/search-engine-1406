import java.util.HashMap;

public class Page {
    private Link URL;
    private String title;
    private String fullURL; //full URL of the page
    private HashMap<String, Integer> allWords; //all words on the page
    private HashMap<String, Integer> allLinks; //all links on the page

    //constructors
    public Page() {
        URL = new Link();
        title = "";
        fullURL = "";
        allWords = new HashMap<>();
        allLinks = new HashMap<>();
    }

    public Page(Link iURL, String iTitle, String iFullURL, HashMap<String, Integer> iAllWords, HashMap<String,
            Integer> iAllLinks) {
        URL = iURL;
        title = iTitle;
        fullURL = iFullURL;
        allWords = iAllWords;
        allLinks = iAllLinks;
    }

    //get methods
    public Link getURL() {
        return URL;
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

    public HashMap<String, Integer> getAllLinks() {
        return allLinks;
    }

    //set methods
    public void setURL(Link URL) {
        this.URL = URL;
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

    public void setAllLinks(HashMap<String, Integer> allLinks) {
        this.allLinks = allLinks;
    }
}
