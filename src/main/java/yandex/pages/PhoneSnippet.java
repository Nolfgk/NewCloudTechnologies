package yandex.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by timur on 18.12.16.
 */
public class PhoneSnippet {
    WebDriver driver;

    public PhoneSnippet(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = ".//div/noindex/ul/li[@data-department=\"Электроника\"]")
    private WebElement phoneRating;

    @FindBy(xpath = "//div/noindex/ul/li[@data-department=\"Электроника\"]")
    private WebElement phoneName;

    @FindBy(xpath = "//div/noindex/ul/li[@data-department=\"Электроника\"]")
    private WebElement priceFrom;

    @FindBy(xpath = "//div/noindex/ul/li[@data-department=\"Электроника\"]")
    private WebElement priceTo;


}
