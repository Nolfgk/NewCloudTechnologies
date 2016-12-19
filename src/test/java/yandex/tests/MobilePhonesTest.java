package yandex.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import yandex.pages.FiltersPage;
import yandex.pages.MainPage;
import yandex.pages.MobilePhonesPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by timur on 18.12.16.
 */
public class MobilePhonesTest {

    WebDriver driver;
    Logger log;

    @Before
    public void before(){
    log = Logger.getLogger(MobilePhonesTest.class.getName());
    System.setProperty("webdriver.chrome.driver", "/home/timur/Java/testwork/src/main/resources/chromedriver");
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().deleteAllCookies();
    }

    @After
    public void after(){
        log.info("Test Finished");
        driver.close();
    }

    @Test
    public void cheskSearch() {
        //Переходим на страницу яндекс-маркета.
        driver.get("https://market.yandex.ru/");
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue("Current URL is YM", currentURL.equals("https://market.yandex.ru/"));

        //идем в раздел мобильных телефонов
        MainPage mainPage = new MainPage(driver);
        MobilePhonesPage mobilePhonesPage = mainPage.getMobilePhonesPage(driver);
        int popularBrands = mobilePhonesPage.getPopularBrandsCount();
        Assert.assertTrue("ThreePopular Brands displayed", popularBrands==3);
        Assert.assertEquals(driver.getTitle(), ("Мобильные телефоны — купить на Яндекс.Маркете"));

        //переходим на страницу полных фильтров
        FiltersPage filtersPage = mobilePhonesPage.getFiltersPage(driver);
        Assert.assertEquals(driver.getTitle(), ("- выбор по параметрам на Яндекс.Маркете"));

        //выставляем нижнюю границу цены
        filtersPage.setPriceFrom("4512");
        Assert.assertTrue("From price setted ", filtersPage.getPriceFrom().equals("4512"));


        //выставляем верхнюю границу цены
        filtersPage.setPriceTo("7350");
        Assert.assertTrue("To price setted ", filtersPage.getPriceTo().equals("7350"));

        //кликаем на чекбокс [в продаже]
        Boolean chooseInSaleCheckbox = filtersPage.chooseInSaleCheckbox();
        Assert.assertFalse(chooseInSaleCheckbox);

        //раскрываем меню производителей
        filtersPage.clickMoreVendorsButton();
        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

        //выбираем три производителя
        String vendor = "ZTE";
        Boolean vendorCheckbox =filtersPage.chooseVendor(vendor);
        Assert.assertTrue(vendorCheckbox);
        vendorCheckbox = false;

        vendor = "Sony";
        vendorCheckbox=filtersPage.chooseVendor(vendor);
        Assert.assertTrue(vendorCheckbox);
        vendorCheckbox = false;

        vendor = "Samsung";
        vendorCheckbox=filtersPage.chooseVendor(vendor);
        Assert.assertTrue(vendorCheckbox);

        //показать результаты поиска
        mobilePhonesPage = filtersPage.showResultsPage();
        Assert.assertEquals(driver.getTitle(), ("Мобильные телефоны — купить на Яндекс.Маркете"));


        //Выбираем три случайных телефона с рейтингом от 3.5 до 4.5
        List<String> suitablePhones = mobilePhonesPage.getPhones();
        int count =0;
        for (int i = 0; count < 3; i++) {
            Random random = new Random();
            int randNumber = random.nextInt(suitablePhones.size());
            log.info(suitablePhones.get(randNumber));
            suitablePhones.remove(randNumber);
            count +=1;
        }
        Assert.assertTrue("Found three random phones ", count==3);
    }

}
