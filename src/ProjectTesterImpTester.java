import java.io.IOException;

public class ProjectTesterImpTester {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ProjectTesterImp imp = new ProjectTesterImp();
        imp.crawl("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
        System.out.println(imp.getOutgoingLinks("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-6.html"));
    }
}
