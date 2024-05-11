package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountPage extends BasePage {

    private final By transactionsButton = By.xpath("//button[@ng-click='transactions()']");
    private final By depositTabButton = By.xpath("//button[@ng-click='deposit()']");
    private final By withdrawlTabButton = By.xpath("//button[@ng-click='withdrawl()']");
    private final By amountField = By.xpath("//input[@ng-model='amount']");
    private final By addToDepositButton = By.xpath("//button[text()='Deposit']");
    private final By addToWithdrawButton = By.xpath("//button[text()='Withdraw']");
    private final By ownerNameSpan = By.xpath("//span[contains(@class,'fontBig')]");
    private final By successfulMessage = By.xpath("//span[@ng-show='message']");
    private final By balanceDiv = By.xpath("//strong[@class='ng-binding'][2]");
    private final By formGroupLabel = By.xpath("//div[@class='form-group']/label");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Переключиться на таб \"Deposit\"")
    private AccountPage clickOnDepositTabButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(depositTabButton));
        driver.findElement(depositTabButton).click();
        wait.until(ExpectedConditions.textToBe(formGroupLabel, "Amount to be Deposited :"));
        return this;
    }

    @Step("Установить значение {value} в поле \"amount\"")
    private AccountPage setValueInAmountField(int value) throws InterruptedException {
        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(amountField));
        inputField.sendKeys(String.valueOf(value));
        return this;
    }

    @Step("Клик на кнопку \"Deposit\" для добавления суммы в депозит")
    private AccountPage clickOnAddToDepositButton() {
        WebElement addToDeposit = wait.until(ExpectedConditions.presenceOfElementLocated(addToDepositButton));
        addToDeposit.click();
        return this;
    }

    @Step("Внести сумму, равную {value} на депозит")
    public AccountPage addToDeposit(int value) throws InterruptedException {
        clickOnDepositTabButton();
        setValueInAmountField(value);
        clickOnAddToDepositButton();
        return this;
    }

    @Step("Переключиться на таб \"Withdrawl\"")
    private AccountPage clickOnWithdrawlTabButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(withdrawlTabButton));
        driver.findElement(withdrawlTabButton).click();
        wait.until(ExpectedConditions.textToBe(formGroupLabel, "Amount to be Withdrawn :"));
        return this;
    }

    @Step("Клик на кнопку \"Withdrawl\" для списания с депозита")
    private AccountPage clickOnWithdrawlButton() {
        driver.findElement(addToWithdrawButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(successfulMessage));
        return this;
    }

    @Step("Списать сумму, равную {value} с депозита")
    public AccountPage withdrawlFromDeposit(int value) throws InterruptedException {
        clickOnWithdrawlTabButton();
        setValueInAmountField(value);
        clickOnWithdrawlButton();
        return this;
    }

    @Step("Переключиться на таб \"Transactions\"")
    public TransactionsPage clickOnTransactionTabButton() {
        driver.findElement(transactionsButton).click();
        return new TransactionsPage(driver);
    }

    public String getOwnerName() {
        return driver.findElement(ownerNameSpan).getText();
    }

    public String getMessage() {
        return driver.findElement(successfulMessage).getText();
    }

    public String getBalance() {
        return driver.findElement(balanceDiv).getText();
    }

}
