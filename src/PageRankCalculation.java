import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class PageRankCalculation {

    //if two URL are connected
    public static boolean isConnected(String url1, String url2) {
        //get url1's all outgoing links
        HashSet<String> url1OutgoingLinks = new HashSet<>();
        //read crawled pages
        HashSet<Page> crawledPages = FileInputAndOutputKit.readCrawledPages();
        for (Page p : crawledPages) {
            if (Objects.equals(p.getURL(), url1))
                url1OutgoingLinks.addAll(p.getAllURLs());
        }
        //get url2's incoming links
        HashSet<String> url2IncomingLinks = new HashSet<>();
        //get through crawledPages to find outgoing links
        for (Page p : crawledPages) {
            //if it is the same page, skip
            if (Objects.equals(p.getURL(), url2))
                continue;
            //if the links on the page include the current URL, store the page's URL
            if (p.getAllURLs().contains(url2))
                url2IncomingLinks.add(p.getURL());
        }
        return url1OutgoingLinks.contains(url2) || url2IncomingLinks.contains(url1);
    }

    //calculate the pageranks for all pages
    public static void calculatePageRanks() {
        double a = 0.1;
        double threshold = 0.0001;

        //create a N*N matrix
    }
}
