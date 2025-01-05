package org.zerock.knock.service.crawling;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zerock.knock.component.util.WebDriverUtil;

import java.time.Duration;
import java.util.Objects;

@Service
public interface CrawlingInterface {

    Logger logger = LoggerFactory.getLogger(CrawlingInterface.class);

    void addNewBrands();

    class ElementExtractor implements Runnable {

        private final String urlPath;
        private final String cssQuery;
        private final ThreadLocal<WebDriver> driverThreadLocal = ThreadLocal.withInitial(WebDriverUtil::getChromeDriver);

        @Getter
        private Elements elements;
        @Getter
        private WebDriver driver;

        public ElementExtractor(String urlPath, String cssQuery) {
            this.urlPath = urlPath;
            this.cssQuery = cssQuery;
        }

        public void setUpDriver()
        {
            driver = driverThreadLocal.get();
            if (urlPath == null) {
                logger.error("[{}]", "urlPath is null");
                return;
            }

            driver.navigate().to(urlPath);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        }

        @Override
        public void run() {

            Document urlDoc;

            synchronized (this) {
                urlDoc = Jsoup.parse(Objects.requireNonNull(driver.getPageSource()));
            }

            synchronized (this) {
                elements = urlDoc.select(cssQuery);
            }
        }
    }
}
