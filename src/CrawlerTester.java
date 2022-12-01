import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CrawlerTester {
    public static void main(String[] args) throws IOException {
        Crawler c = new Crawler();
        String html = WebRequester.readURL("http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html");
        String seed = "http://people.scs.carleton.ca/~davidmckenney/fruits/N-0.html";

        String title = c.findTitle(html);
        System.out.println(title);

        HashMap<String, Integer> allWords = c.findWords(html);
        System.out.println(allWords);

        HashMap<String, Integer> allLinks = c.findLinks(html, seed);
        System.out.println(allLinks);



    }
}