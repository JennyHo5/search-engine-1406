import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FileInputAndOutputTester {
    public static void main(String[] args) throws IOException {
        PrintWriter out;

        Crawler c = new Crawler();

        String URL = "http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html";
        String seed = "http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html";

        Page seedPage = new Page();

        seedPage.setURL(seed);

        seedPage.setHtml(WebRequester.readURL(URL));
        String html = seedPage.getHtml();

        seedPage.setTitle(c.findTitle(html));
        String title = seedPage.getTitle();

        String fullURL = seedPage.getFullURL();

        seedPage.setAllWords(c.findWords(html));
        HashMap<String, Integer> allWords = seedPage.getAllWords();

        seedPage.setAllURLs(c.findURLs(html, seed));
        HashSet<String> allURLs = seedPage.getAllURLs();

        out = new PrintWriter(new FileWriter("aPage.dat"));

        out.println(seedPage.getTitle());
        out.println(seedPage.getHtml());
        out.println(seedPage.getURL());
        out.println(seedPage.getFullURL());
        out.println(seedPage.getAllWords());
        out.println(seedPage.getAllURLs());


        out.close();
    }
}