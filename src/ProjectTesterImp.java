import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectTesterImp implements ProjectTester{
    /*
    This method must delete any existing data that has been stored from any previous crawl.
    This method should also perform any other initialization needed by your system.
    This method will be always called before executing the crawl for a new dataset
     */
    @Override
    public void initialize() {

    }

    //perform a web crawl starting at seed URL
    @Override
    public void crawl(String seedURL){
        Crawler c = new Crawler(seedURL);
        c.doTheCrawl();
        try {
            c.saveCrawledPages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveCrawledURLs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveAllWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveCrawledWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileInputAndOutputKit.readCrawledURLs();
        FileInputAndOutputKit.readAllWords();
        FileInputAndOutputKit.readCrawledWorlds();
    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        return FindElementsKit.getOutgoingLinks(url);
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        return FindElementsKit.getIncomingLinks(url);
    }

    @Override
    public double getPageRank(String url) {

        return 0;
    }

    @Override
    public double getIDF(String word) {
        return 0;
    }

    @Override
    public double getTF(String url, String word) {
        return 0;
    }

    @Override
    public double getTFIDF(String url, String word) {
        return 0;
    }

    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {
        return null;
    }
}
