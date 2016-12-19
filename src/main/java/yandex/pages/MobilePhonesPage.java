package yandex.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by timur on 19.12.16.
 */
public class MobilePhonesPage {
    static private WebDriver driver;
    static private Wait wait;

    public MobilePhonesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new FluentWait(driver)
                .withTimeout(30L, SECONDS)
                .pollingEvery(3, SECONDS);
    }

    @FindBy(xpath = "//div[@class=\"n-images-set__item island i-slider__item i-slider__item-wrap i-slider__item-wrap_active_yes\"]")
    private List<WebElement> popularBrands;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div[2]/div[2]/div[1]/div/div[39]/div[2]/a")
    private WebElement allFiltersButton;

    @FindBy(xpath = "//div[@class=\"n-snippet-card snippet-card clearfix i-bem snippet-card_js_inited n-snippet-card_js_inited\"]")
    private List<WebElement> searchResultList;


    public int getPopularBrandsCount() {
        return popularBrands.size();
    }

    public FiltersPage getFiltersPage(WebDriver driver) {
        allFiltersButton.click();
        FiltersPage filtersPage = new FiltersPage(driver);
        wait.until(ExpectedConditions.titleContains("выбор по параметрам на Яндекс.Маркете"));
        return filtersPage;
    }

    public List<WebElement> getSearchResultList() {
        return getSearchResultList();
    }

    public ArrayList<String> getPhones() {
        List<String> resultList = new ArrayList<String>();
        String message;
        int position = 0;
        for (WebElement phone : searchResultList) {
            position++;
            WebElement rating = phone.findElement(By.xpath("./div/div"));
            String stringRating = rating.getAttribute("innerText");
            if (stringRating.startsWith("3.5") || stringRating.startsWith("4.0") || stringRating.startsWith("4.5")) {
                WebElement modelName = phone.findElement(By.xpath(".//span[@class=\"snippet-card__header-text\"]"));

                WebElement resultPriceFrom = phone.findElement(By.xpath(".//div[@class=\"snippet-card__price i-bem snippet-card__price_js_inited\"]/span[@class=\"price\"]"));
                String stringPriceFrom = resultPriceFrom.getAttribute("innerHTML");
                stringPriceFrom = stringPriceFrom.substring(0, stringPriceFrom.indexOf('&'));

                WebElement resultPriceTo = phone.findElement(By.xpath(".//div[@class=\"snippet-card__subprice\"]/span[@class=\"price\"]"));
                String stringPriceTo = resultPriceTo.getAttribute("innerHTML");
                stringPriceTo = stringPriceTo.substring(0, stringPriceTo.indexOf('&'));
                message = "Позиция на странице - " + position + ", наименование - " + modelName.getAttribute("innerHTML") + ", цена от: " + stringPriceFrom + ", до: " + stringPriceTo + " руб.";
                resultList.add(message);
            }
        }

        return (ArrayList) resultList;


    }


}
