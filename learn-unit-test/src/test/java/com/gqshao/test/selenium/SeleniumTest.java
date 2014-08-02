package com.gqshao.test.selenium;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {
    private static WebDriver driver;

    @BeforeClass
    public static void loginAsAdmin() {
        // Instantiate a webDriver implementation
        driver = new FirefoxDriver();
        driver.get("http://localhost/test");
        WebElement loginNameElement = driver.findElement(By.id("loginName"));
        loginNameElement.clear();
        loginNameElement.sendKeys("admin");
        WebElement passwordNameElement = driver.findElement(By.id("password"));
        passwordNameElement.clear();
        passwordNameElement.sendKeys("admin");

        WebElement btn = driver.findElement(By.name("loginBtn"));
        btn.submit();
    }

    @AfterClass
    public static void logout() {
        driver.get("http://localhost/test/logout");
        driver.close();
    }

    @Test
    public void test2() {
        WebElement sysBtn = driver.findElement(By.linkText("系统管理"));
        sysBtn.click();
        WebElement userBtn = driver.findElement(By.linkText("用户管理"));
        userBtn.click();
        WebElement addBtn = driver.findElement(By.xpath("//td[@id='pager_right']/table/tbody/tr/td[2]/div"));
        // WebElement addBtn = driver.findElement(By.xpath("//td[@id='pager_right']/table/tbody/tr/td[2]/div"));

        addBtn.click();
    }

    public void test() {
        driver.get("http://localhost/web/ahm/fleetStatus");
        String begin = driver.getWindowHandle();
        System.out.println(begin);
        WebElement e1 = driver.findElement(By.cssSelector("#grid tbody tr:nth-of-type(2) td button"));
        Actions builder = new Actions(driver);
        Actions hoverOverRegistrar = builder.moveToElement(e1);
        hoverOverRegistrar.perform();
        WebElement e2 = driver.findElement(By.cssSelector("#tip_fleetstatus ul li:nth-of-type(2) a"));
        e2.getText();
        e2.click();
        Set<String> handles = driver.getWindowHandles();
        String newPage = null;
        for (String handle : handles) {
            if (!begin.equals(handle)) {
                newPage = handle;
            }
        }

        driver.switchTo().window(newPage);
        Select select1 = new Select(driver.findElement(By.id("numberOfFlights")));
        select1.selectByValue("500");

        Select select2 = new Select(driver.findElement(By.id("timeFrame")));
        select2.selectByValue("-6");

        WebElement btn = driver.findElement(By.id("btnQuery"));
        btn.click();
    }

    public void type(By by, String text) {
        WebElement element = driver.findElement(by);
        element.clear();
        element.sendKeys(text);
    }

}
