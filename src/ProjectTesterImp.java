import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.io.File;

public class ProjectTesterImp implements ProjectTester{
    /*
    This method must delete any existing data that has been stored from any previous crawl.
    This method should also perform any other initialization needed by your system.
    This method will be always called before executing the crawl for a new dataset
     */
    @Override
    public void initialize() {
        Path cwd = Path.of(".").toAbsolutePath(); //get current working directory
        //delete all the .dat files in the current directory
        File dir = new File(cwd.toString());
        File[] dirList = dir.listFiles();
        if (dirList != null) {
            for (File f : dirList) {
                if (f.getName().endsWith("dat"))
                    f.delete();
                if (f.getName().endsWith("txt") && !f.getName().endsWith("results.txt"))
                    f.delete();
            }
        }
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
            c.saveCrawledURLsArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveCrawledURlsHash();
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
            c.saveMapIntWithUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveConnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.savePageranks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveIDFs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            c.saveTFs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        HashSet<String> crawledURLs = FileInputAndOutputKit.readCrawledURLsHash();
        //if the URL was not found during the crawling process then return Null
        if (!crawledURLs.contains(url)) //O(1)
            return null;
        //read crawledPages
        HashSet<Page> crawledPages = FileInputAndOutputKit.readCrawledPages();
        Page curPage = new Page(url);
        for (Page p : crawledPages) {
            if (Objects.equals(p.getURL(), url)) {
                curPage = p;
                break;
            }
        }
        HashSet<String> outgoingLinksHash = curPage.getAllURLs();
        List<String> outgoingLinks = new ArrayList<>(outgoingLinksHash);
        return outgoingLinks;
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        List<String> incomingLinks = new ArrayList<>();
        HashSet<String> crawledURLs = FileInputAndOutputKit.readCrawledURLsHash();
        //if the URL was not found during the crawling process then return Null
        if (!crawledURLs.contains(url))
            return null;
        //read crawledPages
        HashSet<Page> crawledPages = FileInputAndOutputKit.readCrawledPages();
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
        if (!pageranks.containsKey(url))
            return -1.0;
        return pageranks.get(url);
    }

    @Override
    public double getIDF(String word)  {
        HashMap<String, Double> wordIDF =  FileInputAndOutputKit.readWordIDF();
        if (!wordIDF.containsKey(word))
            return 0;
        return wordIDF.get(word);
    }

    @Override
    public double getTF(String url, String word) {
        HashMap<String, HashMap<String, Double>> urlWordTF = FileInputAndOutputKit.readUrlWordTF();
        if (!urlWordTF.containsKey(url))
            return 0;
        if (!urlWordTF.get(url).containsKey(word))
            return 0;
        return urlWordTF.get(url).get(word);
    }

    @Override
    public double getTFIDF(String url, String word) {
        double tf = getTF(url, word);
        double idf = getIDF(word);
        double tfidf = Math.log(1 + tf) / Math.log(2) * idf;
        return tfidf;
    }

    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {
        List<Page> result = new ArrayList<>();
        //turn the phrase user entered into a query
        ArrayList<String> queryList = Search.getSearchQuery(query);
        //get the query vector
        ArrayList<Double> queVector = Search.getQueVector(query, queryList);

        //measure the left-denom
        double qSum = 0;
        for (double i : queVector) //O(n), n = # words in query
            qSum += i*i;
        double leftDenom = Math.sqrt(qSum);

        //for each document(page), measure the similarity
        HashSet<Page> crawledPages = FileInputAndOutputKit.readCrawledPages();
        for (Page p : crawledPages) {
            String url = p.getURL();
            p.setPagerank(FileInputAndOutputKit.readPageranks().get(url));

            //get the document vector for each document
            ArrayList<Double> docVector = Search.getDocVector(url, queryList);
            //measure the numerator
            double num = 0;
            for (int i = 0; i < queVector.size(); i++)
                num += queVector.get(i) * docVector.get(i);
            //measure the right_denom
            double dSum = 0;
            for (double i : docVector)
                dSum += i*i;
            double rightDenom = Math.sqrt(dSum);

            //calculate the cosine of the page
            //if none of terms in the query vector exist in the page (right_denom == 0), pagescore = 0;
            if (rightDenom == 0)
                p.setScore(0);
            else {
                double cosine = num / (leftDenom * rightDenom);
                p.setScore((double) Math.round(cosine * 1000d) / 1000d); //round the result to 3dp
            }
            result.add(p);
        }

        //if boost = True, boost by PageRank value
        if (boost) {
            for (Page p : result) {
                double pagerank = p.getPagerank();
                double score = p.getScore();
                p.setScore((double)Math.round(pagerank * score * 1000d) / 1000d); //round the result to 3dp
            }
        }

        List<Page> sortedResult = new ArrayList<>();
        //sort the result from the top to low and add the top X to searchResult
        while (sortedResult.size() < 10) {
            double highestScore = result.get(0).getScore();
            Page highestScorePage = result.get(0);
            for (Page p : result) {
                if (p.getScore() > highestScore) {
                    highestScore = p.getScore();
                    highestScorePage = p;
                }
            }
            sortedResult.add(highestScorePage);
            result.remove(highestScorePage);
        }

        //sort lexicographically
        Collections.sort(sortedResult);

        List<SearchResult> searchResult = new ArrayList<>(sortedResult);

        return searchResult;
    }
}
