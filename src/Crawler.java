import java.io.*;
import java.util.*;

public class Crawler {
    private String seedURL;
    private TreeSet<String> queueURLs;
    private HashSet<String> alreadyCrawledURLs;
    private HashSet<Page> crawledPages;

    //constructor
    public Crawler(String iSeedURL) {
        seedURL = iSeedURL;
        queueURLs = new TreeSet<>();
        queueURLs.add(seedURL);
        alreadyCrawledURLs = new HashSet<>();
        crawledPages = new HashSet<>();
    }

    public void doTheCrawl(){
        // repeat until the queue is empty
        while (queueURLs.size() != 0) {
            //get and remove the next Page from queueList
            String curURL = queueURLs.first(); //O(1)
            queueURLs.pollFirst(); //O(1)

            //if the page has already been crawled, skip it
            if (alreadyCrawledURLs.contains(curURL)) //O(1)
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
            HashSet<String> curURLs = FindElementsKit.findURLs(curHtml, seedURL); //we saved the absolute url to urlURLs
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
    public void saveCrawledURLsArray() throws IOException {
        ObjectOutputStream writer2;
        writer2 = new ObjectOutputStream(new FileOutputStream("crawled-URLs-array.dat"));
        List<String> crawledURLsArray = new ArrayList<>(alreadyCrawledURLs); //save the hashset into an arraylist
        writer2.writeObject(crawledURLsArray);
        writer2.close();
    }

    public void saveCrawledURlsHash() throws IOException {
        ObjectOutputStream writer;
        writer = new ObjectOutputStream(new FileOutputStream("crawled-URLs-hash.dat"));
        HashSet<String> crawledURlsHash = alreadyCrawledURLs;
        writer.writeObject(crawledURlsHash);
        writer.close();
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
    public void saveIDFs() throws IOException {
        HashMap<String, Double> wordIDF = TfIdfCalculation.calculateIDFs();
        ObjectOutputStream writer;
        writer = new ObjectOutputStream((new FileOutputStream("word-idf.dat")));
        writer.writeObject(wordIDF);
        writer.close();
    }

    //save tfs for each url into a file
    public void saveTFs() throws IOException {
        HashMap<String, HashMap<String, Double>> urlWordTF = TfIdfCalculation.calculateTFs();
        ObjectOutputStream writer;
        writer = new ObjectOutputStream((new FileOutputStream("url-word-tf.dat")));
        writer.writeObject(urlWordTF);
        writer.close();
    }

    //map each URL to a specific int, and create a HashMap to contain those ints and URLs
    public void saveMapIntWithUrl() throws IOException {
        HashMap<Integer, String> intWithURL = new HashMap<>();
        ArrayList<String> crawledURLsArray = FileInputAndOutputKit.readCrawledURLsArray();
        for (int i = 0; i < crawledURLsArray.size(); i++) //O(n)
            intWithURL.put(i, crawledURLsArray.get(i));
        //save it to a file
        ObjectOutputStream writer;
        writer = new ObjectOutputStream(new FileOutputStream("int-url.dat"));
        writer.writeObject(intWithURL);
        writer.close();
    }

    //if two URL are connected
    public static boolean isConnected(String url2, HashSet<String> url1OutgoingLinksHash, HashSet<String> url1IncomingLinksHash) {
        return url1OutgoingLinksHash.contains(url2) || url1IncomingLinksHash.contains(url2); //O(1)
    }

    public static HashMap<String, HashMap<String, Boolean>> getUrl1Url2Connect() {
        HashMap<String, HashMap<String, Boolean>> url1url2IfConnect = new HashMap<>();
        ArrayList<String> crawledURLsArray = FileInputAndOutputKit.readCrawledURLsArray();
        for (String url1 : crawledURLsArray) {
            HashSet<String> url1OutgoingLinksHash = FindElementsKit.getOutgoingLinksHash(url1);
            HashSet<String> url2IncomingLinksHash = FindElementsKit.getIncomingLinksHash(url1);
            url1url2IfConnect.put(url1, new HashMap<>());
            for (String url2 : crawledURLsArray) {
                if (Objects.equals(url2, url1))
                    continue;
                if (isConnected(url2, url1OutgoingLinksHash, url2IncomingLinksHash))
                    url1url2IfConnect.get(url1).put(url2, true);
                else url1url2IfConnect.get(url1).put(url2, false);
            }
        }
        return url1url2IfConnect;
    }


    public void saveConnect() throws IOException {
        HashMap<String, HashMap<String, Boolean>> connect = getUrl1Url2Connect();
        //save it to a file
        ObjectOutputStream writer;
        writer = new ObjectOutputStream(new FileOutputStream("connect.dat"));
        writer.writeObject(connect);
        writer.close();
    }
}
