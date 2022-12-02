import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ProjectTesterImp implements ProjectTester{
    //Reset any existing data
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
        List<String> outgoingLinks = new ArrayList<>();
        Page curPage = new Page(url);
        HashSet<String> crawledURLs = FileInputAndOutputKit.readCrawledURLs();
        //if the URL was not found during the crawling process then return Null
        if (!crawledURLs.contains(url))
            return null;

        //read crawledPages
        HashSet<Page> crawledPages = FileInputAndOutputKit.readCrawledPages();
        for (Page p : crawledPages) {
            if (Objects.equals(p.getURL(), url)) {
                curPage = p;
                break;
            }
        }

        outgoingLinks = curPage.getAllURLs();
        return outgoingLinks;
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        return null;
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
