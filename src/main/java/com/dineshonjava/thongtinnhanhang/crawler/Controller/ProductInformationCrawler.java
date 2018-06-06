/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler.Controller;

import com.dineshonjava.thongtinnhanhang.crawler.POJO.ProductInformation;
import com.dineshonjava.thongtinnhanhang.crawler.ProductInformationCrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import static java.lang.System.out;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Huy Thinh
 */
public class ProductInformationCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(ProductInformationCrawler.class);

    private static final Pattern FILTERS = Pattern.compile(".*(\\.(" +
            "css|js" +
            "|bmp|gif|jpe?g|JPE?G|png|tiff?|ico|nef|raw" +
            "|mid|mp2|mp3|mp4|wav|wma|flv|mpe?g" +
            "|avi|mov|mpeg|ram|m4v|wmv|rm|smil" +
            "|pdf|doc|docx|pub|xls|xlsx|vsd|ppt|pptx" +
            "|swf" +
            "|zip|rar|gz|bz2|7z|bin" +
            "))$");
    
    private static final String DOMAIN = "https://phongvu.vn/";
    private static final Pattern NEWS_PAGE = Pattern.compile(".*/\\w+\\.html");
//    private static final Pattern TAGS_PAGES = Pattern.compile(".*phongvu\\.vn\\/(catelogy)\\/(product).*");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        //out.print("checking "+href);
        boolean shouldBe = !FILTERS.matcher(href).matches() && (href.startsWith(DOMAIN));
        return shouldBe;
    }

    @Override
    public void visit(Page page) {
        
        String url = page.getWebURL().getURL();
        
        out.print("Visiting "+url);
        if (!NEWS_PAGE.matcher(url).matches() /*|| TAGS_PAGES.matcher(url).matches()*/) {
            out.println(" -- skipped");
            return;
        }
       
        if (page.getParseData() instanceof HtmlParseData) {
            ProductInformation productInformation;
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            ProductInformationParser productInformationParser = new ProductInformationParser();
            productInformation = productInformationParser.parseFromHTML(html);
            productInformation.setProductUrl(url);
            ProductInformationCrawlController.mongoDBService.insertFeed(productInformation);
            out.println(" -- parsed");
        }
        else
            out.println("Instance error");

    }    
}
