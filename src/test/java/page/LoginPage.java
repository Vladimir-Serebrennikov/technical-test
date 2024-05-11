package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends BasePage {

    private final By loginAsCustomerButton = By.xpath("//button[@ng-click='customer()']");
    private final By yourNameSelectField = By.id("userSelect");
    private final By loginButton = By.xpath("//button[text()='Login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    @Step("Открыть страницу сайта")
    public LoginPage open(String url) {
        driver.get(url);
        return this;
    }
    @Step("Нажать кнопку \"Customer Login\"")
    public LoginPage clickOnLoginAsCustomer() {
        driver.findElement(loginAsCustomerButton).click();
        return this;
    }
    @Step("Выбрать пункт {value}")
    public LoginPage selectOption(String value) {
        WebElement selectElement = driver.findElement(yourNameSelectField);
        Select select = new Select(selectElement);
        select.selectByVisibleText(value);
        return this;
    }
    @Step("Кликнуть на кнопку \"Login\"")
    public AccountPage clickLoginButton() {
        driver.findElement(loginButton).click();
        return new AccountPage(driver);
    }

    @Step("Авторизоваться под именем \"{userName}\"")
    public AccountPage loginAs(String userName) {
        clickOnLoginAsCustomer();
        selectOption(userName);
        clickLoginButton();
        return new AccountPage(driver);
    }
}
