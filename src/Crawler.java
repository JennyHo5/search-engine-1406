import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Crawler {
    Page seedPage;
    String seedURL;
    ArrayList<String> queueURLs;
    ArrayList<String> alreadyCrawledURLs;
    HashSet<Page> crawledPages;

    //constructor
    public Crawler(String iSeedURL) {
        seedURL = iSeedURL;
        seedPage = new Page(seedURL);
        queueURLs = new ArrayList<>();
        queueURLs.add(seedURL);
        alreadyCrawledURLs = new ArrayList<>();
        crawledPages = new HashSet<>();
    }

    public void doTheCrawl(){
        // repeat until the queue is empty
        while (queueURLs.size() != 0) {
            //get and remove the next Page from queueList
            String curURL = queueURLs.get(0);
            queueURLs.remove(0);

            //if the page has already been crawled, skip it
            if (alreadyCrawledURLs.contains(curURL))
                continue;

            //read the current page (using webdev module)
            String curHtml = null;
            try {
                curHtml = WebRequester.readURL(curURL);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String curTitle = FindElementsKit.findTitle(curHtml);
            HashMap<String, Integer> curWords = FindElementsKit.findWords(curHtml);
            ArrayList<String> curURLs = FindElementsKit.findURLs(curHtml, seedURL); //we saved the absolute url to urlURLs
            Page curPage = new Page(curURL, curTitle, curWords, curURLs);

            //add those URls on current page to the queue
            queueURLs.addAll(curURLs);
            //add current URL to alreadyCrawledURLs
            alreadyCrawledURLs.add(curURL);
            //add current page to crawledPages
            crawledPages.add(curPage);
        }
    }

    //save crawled Pages
    public void saveCrawledPages() throws IOException {
        ObjectOutputStream writer1;
        writer1 = new ObjectOutputStream(new FileOutputStream("crawled-pages.dat"));
        writer1.writeObject(crawledPages);
        writer1.close();
    }

    //save crawled URls
    public void saveCrawledURLs() throws IOException {
        ObjectOutputStream writer2;
        writer2 = new ObjectOutputStream(new FileOutputStream("crawled-URLs.dat"));
        writer2.writeObject(alreadyCrawledURLs);
        writer2.close();
    }

    //save all words in the crawled pages (including duplicates)
    public void saveAllWords() throws IOException {
        ObjectOutputStream writer3;
        writer3 = new ObjectOutputStream(new FileOutputStream("all-words.dat"));
        HashMap<String, Integer> allWords = new HashMap<>();
        for (Page p : crawledPages) {
            HashMap<String, Integer> wordsOnThePage = p.getAllWords();
            for (Map.Entry<String, Integer> entry : wordsOnThePage.entrySet()) {
                String word = entry.getKey();
                Integer counts = entry.getValue();
                if (!allWords.containsKey(word)) {
                    allWords.put(word, counts);
                }
                allWords.put(word, allWords.get(word) + counts);
            }
        }
        writer3.writeObject(allWords);
        writer3.close();
    }

    //save all crawled words into a file (no duplicates)
    public void saveCrawledWords() throws IOException {
        //read all words from the file(using function)
        HashMap<String, Integer> allWords = FileInputAndOutputKit.readAllWords();
        ObjectOutputStream writer4;
        writer4 = new ObjectOutputStream(new FileOutputStream("crawled-words.dat"));
        ArrayList<String> crawledWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : allWords.entrySet()){
            String word = entry.getKey();
            crawledWords.add(word);
        }
        writer4.writeObject(crawledWords);
        writer4.close();
    }

    //save all pageranks for each page(URL) into a file
    public void savePageranks() throws IOException {
        HashMap<String, Double> pageranks = PageRankCalculation.calculatePageranks();
        ObjectOutputStream writer;
        writer = new ObjectOutputStream(new FileOutputStream("pageranks.dat"));
        writer.writeObject(pageranks);
        writer.close();
    }

    //save idfs for each crawled word into a file
    public void saveIDFs() {

    }
}
