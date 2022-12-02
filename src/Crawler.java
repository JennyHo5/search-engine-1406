import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Crawler {
    Page seedPage;
    String seedURL;
    ArrayList<String> queueURLs;
    HashSet<String> alreadyCrawledURLs;
    HashSet<Page> crawledPages;

    //constructor
    public Crawler(String iSeedURL) {
        seedURL = iSeedURL;
        seedPage = new Page(seedURL);
        queueURLs = new ArrayList<>();
        queueURLs.add(seedURL);
        alreadyCrawledURLs = new HashSet<>();
        crawledPages = new HashSet<>();
    }

    public void doTheCrawl() throws IOException {
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
            String curTitle = findTitle(curHtml);
            HashMap<String, Integer> curWords = findWords(curHtml);
            HashSet<String> curURLs = findURLs(curHtml, seedURL);
            Page curPage = new Page(curURL, curTitle, curWords, curURLs);

            //add those URls on current page to the queue
            queueURLs.addAll(curURLs);
            //add current URL to alreadyCrawledURLs
            alreadyCrawledURLs.add(curURL);
            //add current page to crawledPages
            crawledPages.add(curPage);
        }
    }

    public void saveData() throws IOException {
        //save crawled Pages
        ObjectOutputStream writer1;
        writer1 = new ObjectOutputStream(new FileOutputStream("crawled-pages.dat"));
        writer1.writeObject(crawledPages);
        writer1.close();

        //save crawled URls
        ObjectOutputStream writer2;
        writer2 = new ObjectOutputStream(new FileOutputStream("crawled-URLs.dat"));
        writer2.writeObject(alreadyCrawledURLs);
        writer2.close();
    }

    public void readData() throws IOException, ClassNotFoundException {
        ObjectInputStream reader1;
        reader1 = new ObjectInputStream(new FileInputStream("crawled-pages.dat"));
        HashSet<Page> crawledPagesList = new HashSet<>();
        crawledPagesList = (HashSet<Page>) reader1.readObject();
        for (Page p : crawledPagesList) {
            System.out.println(p);
        }
    }

    //find the title of the current page
    public String findTitle(String html) {
        int startTitleTagIndex = html.indexOf("<title");
        int endTitleTagIndex = html.indexOf("</title>");
        String titleTag = html.substring(startTitleTagIndex + 6, endTitleTagIndex);
        int startTitleIndex = titleTag.indexOf(">") + 1;
        String title = titleTag.substring(startTitleIndex);
        return title;
    }

    //find all words on the current page
    public HashMap<String, Integer> findWords(String html) {
        HashMap<String, Integer> allWords = new HashMap<>(); //key: specific word, value: appear times
        while (html.contains("<p")) {
            int startPIndex = html.indexOf("<p");
            int endPIndex = html.indexOf("</p>");
            String p = html.substring(startPIndex, endPIndex);
            int startWordsIndex = p.indexOf(">") + 1;
            String words = p.substring(startWordsIndex);
            words = words.trim(); //get rid of whitespaces (spaces, new lines etc.) from the beginning and end of the string
            String[] wordsList = words.split("[\\n\\s]"); //split a string by newline \n and space \s characters
            for (String word : wordsList) {
                if (!allWords.containsKey(word)) {
                    allWords.put(word, 0);
                }
                allWords.put(word, allWords.get(word) + 1); //update the value by +1
            }
            html = html.substring(0,startPIndex) + html.substring(endPIndex + 4);
        }
        return allWords;
    }

    //find all links on the current page
    public HashSet<String> findURLs(String html, String seed) {
        HashSet<String> allURLs = new HashSet<>();
        while (html.contains("<a href=\"")) {
            int startAIndex = html.indexOf("<a href=\"");
            int endAIndex = html.indexOf("</a>");
            String a = html.substring(startAIndex + 9, endAIndex);
            int endQuotationIndex = a.indexOf("\">");
            String URL = a.substring(0, endQuotationIndex);
            String absURL = LinkConverter.convert(URL, seed);
            //add the fullLink to the HashSet
            allURLs.add(absURL);
            html = html.substring(0, startAIndex) + html.substring(endAIndex + 5);
        }
        return allURLs;
    }

}
