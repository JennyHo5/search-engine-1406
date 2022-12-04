import java.io.*;
import java.util.*;

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
        try {
            c.savePageranks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public double getPageRank(String url){
        ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(new FileInputStream("pageranks.dat"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Double> pageranks = null;
        try {
            pageranks = (HashMap<String, Double>) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return pageranks.get(url);
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
