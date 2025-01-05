package org.zerock.knock.component.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class NextBtnWithCssSelector {

    private static final Logger logger = LoggerFactory.getLogger(NextBtnWithCssSelector.class);

    public static void nextBtn(WebDriver driver, String cssSelector)
    {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 최대 10초 대기

            while (true)
            {
                WebElement nextBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));

                if (nextBtn.isDisplayed())
                {
                    nextBtn.click();
                    wait.until(ExpectedConditions.stalenessOf(nextBtn));
                }
                else
                {
                    break;
                }
            }
        }
        catch (Exception e)
        {
            logger.debug("오류 발생: {}", e.getMessage());
        }
    }

}
