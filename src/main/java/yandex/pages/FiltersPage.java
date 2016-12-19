package yandex.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by timur on 19.12.16.
 */
public class FiltersPage {
    static private WebDriver driver;
    static private Wait wait;

    public FiltersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new FluentWait(driver)
                .withTimeout(30L, SECONDS)
                .pollingEvery(3, SECONDS);
    }

    @FindBy(id = "glf-pricefrom-var")
    private WebElement priceFrom;

    @FindBy(id = "glf-priceto-var")
    private WebElement priceTo;

    @FindBy(xpath = "//label[@class=\"checkbox__label\" and @for=\"glf-onstock-select\"]")
    private WebElement inSaleCheckbox;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div/div[1]/div[1]/div[2]/div[2]/div/div[2]/button")
    private WebElement moreVendorsButton;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div/div[1]/div[1]/div[2]/div[2]/div/span/span/input")
    private WebElement vendorChooseInput;

    @FindBy(xpath = "/html/body/div[1]/div[4]/div/div[1]/div[1]/div[2]/div[2]/div/div[1]/div/div[1]/span")
    private WebElement vendorCheckbox;

    @FindBy(xpath = "//a[@class=\"button button_size_l button_theme_pseudo i-bem button_action_show-filtered n-filter-panel-extend__controll-button_size_big button_js_inited\"]")
    private WebElement showResults;

    public boolean chooseVendor(String vendor) {

        vendorChooseInput.clear();
        vendorChooseInput.sendKeys(vendor);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vendorCheckbox.click();
        return vendorCheckbox.isEnabled();
    }


    public String getPriceFrom() {
        return priceFrom.getAttribute("value");
    }

    public String getPriceTo() {
        return priceTo.getAttribute("value");
    }

    public FiltersPage clickMoreVendorsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreVendorsButton));
        moreVendorsButton.click();
        return this;
    }

    public boolean chooseInSaleCheckbox() {
        inSaleCheckbox.click();
        if (inSaleCheckbox.isSelected()) return true;
        else return false;
    }

    public FiltersPage setPriceFrom(String price) {
        priceFrom.clear();
        priceFrom.sendKeys(price);
        return this;
    }

    public FiltersPage setPriceTo(String price) {
        priceTo.clear();
        priceTo.sendKeys(price);
        return this;
    }

    public MobilePhonesPage showResultsPage() {
        showResults.click();
        MobilePhonesPage mobilePhonesPage = new MobilePhonesPage(driver);
        wait.until(ExpectedConditions.titleContains("Мобильные телефоны"));
        return mobilePhonesPage;
    }
}
