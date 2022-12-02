import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ProjectTesterImp implements ProjectTester{
    Crawler c = new Crawler();
    //Reset any existing data
    @Override
    public void initialize() {

    }

    //perform a web crawl starting at seed URL
    @Override
    public void crawl(String seedURL) throws IOException {
        //create the seedPage
        Page seedPage = new Page();
        seedPage.setURL(seedURL);

        //create a lists of URLs that we need to visit (a set does not allow duplicate elements to be added)
        ArrayList<String> queueURLs = new ArrayList<>();

        //create a set of URLs that we already visited
        HashSet<String> alreadyCrawledURLs = new HashSet<>();

        //create a set of Page that we already crawled
        HashSet<Page> crawledPages = new HashSet<>();

        //add the seedURL to the queueList
        queueURLs.add(seedURL);

        // repeat until the queue is empty
        while (queueURLs.size() != 0) {
            //get and remove the next Page from queueList
            String curURL = queueURLs.get(0);
            queueURLs.remove(0);

            //if the page has already been crawled, skip it
            if (alreadyCrawledURLs.contains(curURL))
                continue;

            //read the current page (using webdev module)

            String curHtml = WebRequester.readURL(curURL);
            String curTitle = c.findTitle(curHtml);
            HashMap<String, Integer> curWords = c.findWords(curHtml);
            HashSet<String> curURLs = c.findURLs(curHtml, seedURL);
            Page curPage = new Page(curURL, curTitle, curWords, curURLs);

            //add those URls on current page to the queue
            queueURLs.addAll(curURLs);
            //add current URL to alreadyCrawledURLs
            alreadyCrawledURLs.add(curURL);
            //add current page to crawledPages
            crawledPages.add(curPage);
        }

        //save data to files
        PrintWriter writer;
        try {
            writer = new PrintWriter(new FileWriter("crawled-URLs.dat"));
            for (Page p : crawledPages)
                writer.write(p.getURL() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.close();

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
