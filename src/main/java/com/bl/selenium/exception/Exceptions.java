package com.bl.selenium.exception;

import com.bl.selenium.base.SeleniumBasicBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static java.lang.Thread.sleep;

public class Exceptions extends SeleniumBasicBase {

    public void initialize() {
        webDriver.get("http://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement startButton = webDriver.findElement(By.xpath("//div[@id='start']//button"));
        startButton.click();
    }

    public void elementNotVisible() {
        initialize();
        WebElement finishElement = webDriver.findElement(By.id("finish"));
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElements(finishElement));
        String finishText = finishElement.getText();
        Assert.assertEquals(finishText, "Hello World!");
    }

    public void timeOutTest() throws InterruptedException {
        initialize();
        WebElement finishElement = webDriver.findElement(By.id("finish"));

        WebDriverWait wait = new WebDriverWait(webDriver, 2);
        try {
            wait.until(ExpectedConditions.visibilityOf(finishElement));
        } catch(TimeoutException exception) {
            System.out.println("Exception catched: " + exception.getMessage());
            sleep(3000);
        }
        String finishText = finishElement.getText();
        Assert.assertTrue(finishText.contains("Hello World!"));
    }

    public void noSuchElement() {
        webDriver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
        WebElement startButton = webDriver.findElement(By.xpath("//div[@id='start']//button"));
        startButton.click();
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        Assert.assertTrue(
                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("finish"), "Hello World!")),
                "Couldn't verify 'Hello World!'");
    }

    public void staleElement() {

        webDriver.get("http://the-internet.herokuapp.com/dynamic_controls");
        WebElement checkBox = webDriver.findElement(By.id("checkbox"));
        WebElement removeButton = webDriver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
        removeButton.click();
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkBox)),
                "Checkbox is still visible, but shouldn't be");
        WebElement addButton = webDriver.findElement(By.xpath("//button[contains(text(),'Add')]"));
        addButton.click();
        WebElement checkbox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkbox2.isDisplayed(), "Checkbox is not visible, but it should be");
    }
}
