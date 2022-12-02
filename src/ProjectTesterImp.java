import java.io.*;
import java.util.List;

public class ProjectTesterImp implements ProjectTester{
    //Reset any existing data
    @Override
    public void initialize() {

    }

    //perform a web crawl starting at seed URL
    @Override
    public void crawl(String seedURL) throws IOException, ClassNotFoundException {
        Crawler c = new Crawler(seedURL);
        c.doTheCrawl();
        c.saveData();
        c.readData();

    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        return null;
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
