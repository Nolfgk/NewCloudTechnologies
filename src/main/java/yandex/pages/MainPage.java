package yandex.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by timur on 18.12.16.
 */
public class MainPage {
    Actions builder;
    static private WebDriver driver;
    static private Wait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "(//div/noindex/ul/li[@data-department=\"Электроника\"])[1]")
    private WebElement electronicsMenuButton;

    @FindBy(xpath = "//div[@class=\"topmenu__sublist\"]/a[text()[contains(.,\"Мобильные телефоны\")]]")
    private WebElement mobilePhonesSubmenuButton;


    public MobilePhonesPage getMobilePhonesPage(WebDriver driver) {
        wait = new FluentWait(driver)
                .withTimeout(30L, SECONDS)
                .pollingEvery(3, SECONDS);
        builder = new Actions(driver);
        builder.moveToElement(electronicsMenuButton).perform();
        wait.until(ExpectedConditions.visibilityOf(mobilePhonesSubmenuButton));
        mobilePhonesSubmenuButton.click();
        MobilePhonesPage mobilePhonesPage = new MobilePhonesPage(driver);
        return mobilePhonesPage;
    }
}
