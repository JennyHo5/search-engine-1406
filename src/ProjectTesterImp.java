import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ProjectTesterImp implements ProjectTester{
    Crawler c = new Crawler();
    //Reset any existing data
    @Override
    public void initialize() {

    }

    //perform a web crawl starting at seed URL
    @Override
    public void crawl(String seedURL) {
        //create the seedPage
        Page seedPage = new Page();
        seedPage.setURL(seedURL);

        //create a set of pages that we need to visit (a set does not allow duplicate elements to be added)
        HashSet<Page> queueList = new HashSet<>();

        //create a set of pages that we already visited
        HashSet<Page> alreadyCrawledList = new HashSet<>();

        //add the seed page to the queueList
        queueList.add(seedPage);

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
