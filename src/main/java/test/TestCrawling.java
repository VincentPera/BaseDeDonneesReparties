package test;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

public class TestCrawling extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && (href.startsWith("http://paizo.com/pathfinderrpg/prd/bestiary/"));
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        ArrayList<String> tab_spells = new ArrayList<>();

        String sort = "pathfinderRPG/prd/coreRulebook/spells/";
        String str = new String(page.getContentData(), StandardCharsets.UTF_8);
        while(str.contains("<h1 id=\"")){
            int index = str.indexOf("<h1 id=\"");
            int index2 = str.indexOf("\"",index+8);
            String strr = str.substring(index,index2);
            strr = strr.substring(8);
            str = str.substring(index2);
            if(str.contains("<h1 id=\"")){
                while(str.contains(sort)&&(str.indexOf(sort)<str.indexOf("<h1 id=\""))){
                    int index_spell = str.indexOf(sort);
                    int index_spell2 = str.indexOf(".html",index_spell+38);
                    int index_spell3 = str.indexOf("#",index_spell+38);
                    if(index_spell3<index_spell2)index_spell2=index_spell3;
                    String spell = str.substring(index_spell,index_spell2);
                    spell = spell.substring(38);
                    str = str.substring(index_spell2);
                    tab_spells.add(spell);
                }
            }else{
                while(str.contains(sort)){
                    int index_spell = str.indexOf(sort);
                    int index_spell2 = str.indexOf(".html",index_spell+38);
                    int index_spell3 = str.indexOf("#",index_spell+38);
                    if(index_spell3<index_spell2)index_spell2=index_spell3;
                    String spell = str.substring(index_spell,index_spell2);
                    spell = spell.substring(38);
                    str = str.substring(index_spell2);
                    tab_spells.add(spell);
                }
            }

            ResultJson.getInstance().sendResults(strr,tab_spells);
            tab_spells.clear();
        }


        System.out.println("JSON : "+ResultJson.getInstance().toString());
        //System.out.println(str);
        //System.out.println(index);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
    }
}