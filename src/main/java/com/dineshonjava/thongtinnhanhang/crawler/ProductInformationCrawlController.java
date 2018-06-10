/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dineshonjava.thongtinnhanhang.crawler;

import com.dineshonjava.thongtinnhanhang.crawler.Controller.ProductInformationCrawler;
import com.dineshonjava.thongtinnhanhang.crawler.DAO.MongoDBService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import static java.lang.System.out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Huy Thinh
 */
public class ProductInformationCrawlController {
    private static final Logger logger = LoggerFactory.getLogger(ProductInformationCrawlController.class);
    public static final MongoDBService mongoDBService = MongoDBService.getInstance();

    public static void main(String[] args) throws Exception {
        /*
         *ThÆ° má»¥c Ä‘á»ƒ lÆ°u cache crawl
         */
        String crawlStorageFolder = System.getProperty("java.io.tmpdir");

         /*
         *Số lượng thread khởi tạo để chạy crawl
         */
        int numberOfCrawlers = 5;

        if (args.length != 2) {

            logger.info("Needed parameters: ");
            logger.info("\t rootFolder (it will contain intermediate crawl data)");
            logger.info("\t numberOfCralwers (number of concurrent threads)");
            out.println("Wrong parameters! Will start with default storage folder and 5 threads.");
        }
        else {
            try {
                crawlStorageFolder = args[0];
                numberOfCrawlers = Integer.parseInt(args[1]);
            } catch (Exception e) {
                logger.error("Error: ", e);
            }
        }



        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Thời gian delay giữa các lần send request
         */
        config.setPolitenessDelay(1000);

        /*
         * Độ sâu khi crawl, mặc định -1 là unlimited
         */
        config.setMaxDepthOfCrawling(-1);

        /*
         * Set số lượng pages tối đa crawl, mặc định -1 là unlimited
         */
        config.setMaxPagesToFetch(-1);

        /*
         * Thiết lập crawl các tập tin nhị phân
         */
        config.setIncludeBinaryContentInCrawling(false);

        /*
         * Thiết lập này cho phép crawl tiếp tục tiến trình cũ
         * Nếu muốn crawl mới phải xóa dữ liệu thư mục lưu trữ đi 
         */
        config.setResumableCrawling(true);

        /*
         * Các biến cần thiết
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        int attempCount = 0;

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * Seed URL, link URL duyệt để crawl
         */
        controller.addSeed(crawlStorageFolder);
        
        /*
         * Bắt đầu crawl
         */
        controller.start(ProductInformationCrawler.class, numberOfCrawlers);

    }
}
