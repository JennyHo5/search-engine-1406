import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
        List<String> incomingLinks = new ArrayList<>();
        Page curPage = new Page(url);
        HashSet<String> crawledURLs = FileInputAndOutputKit.readCrawledURLs();
        //if the URL was not found during the crawling process then return Null
        if (!crawledURLs.contains(url))
            return null;
        //read crawledPages
        HashSet<Page> crawledPages = FileInputAndOutputKit.readCrawledPages();
        //get curPage by URL
        for (Page p : crawledPages) {
            if (Objects.equals(p.getURL(), url)) {
                curPage = p;
                break;
            }
        }
        //get through crawledPages again to find outgoing links
        for (Page p : crawledPages) {
            //if it is the same page, skip
            if (Objects.equals(p.getURL(), url))
                continue;
            //if the links on the page include the current URL, store the page's URL
            if (p.getAllURLs().contains(url))
                incomingLinks.add(p.getURL());
        }
        return incomingLinks;
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
