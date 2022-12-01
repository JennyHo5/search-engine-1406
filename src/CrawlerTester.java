import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CrawlerTester {
    public static void main(String[] args) throws IOException {
        Crawler c = new Crawler();

        String URL = "http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html";
        String seed = "http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html";

        Page seedPage = new Page();

        seedPage.setURL(seed);

        seedPage.setHtml(WebRequester.readURL(URL));
        String html = seedPage.getHtml();

        seedPage.setTitle(c.findTitle(html));
        String title = seedPage.getTitle();
        System.out.println(title);

        seedPage.setAllWords(c.findWords(html));
        HashMap<String, Integer> allWords = seedPage.getAllWords();
        System.out.println(allWords);

        seedPage.setAllURLs(c.findURLs(html, seed));
        HashSet<String> allURLs = seedPage.getAllURLs();
        System.out.println(allURLs);



    }
}