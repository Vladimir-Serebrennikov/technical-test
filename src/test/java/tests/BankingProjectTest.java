package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.AccountPage;
import page.LoginPage;
import page.TransactionsPage;

import java.util.ResourceBundle;

import static helpers.Helpers.fibbonachiCalc;

public class BankingProjectTest extends BaseTest {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    String link = resourceBundle.getString("url");

    @Test
    @DisplayName("Проверка информации о транзакциях")
    public void openTransactionsTest() throws InterruptedException {
        String accountOwner = "Harry Potter";
        int fibbonachiNum = fibbonachiCalc();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(link);
        Assertions.assertEquals("XYZ Bank", driver.getTitle());

        AccountPage accountPage = loginPage.loginAs(accountOwner);
        Assertions.assertEquals(accountOwner, accountPage.getOwnerName());

        accountPage.addToDeposit(fibbonachiNum);
        Assertions.assertEquals("Deposit Successful", accountPage.getMessage());

        accountPage.withdrawlFromDeposit(fibbonachiNum);
        Assertions.assertEquals("Transaction successful", accountPage.getMessage());
        Assertions.assertEquals("0", accountPage.getBalance());

        TransactionsPage transactionsPage = accountPage.clickOnTransactionTabButton();
        Assertions.assertEquals(2, transactionsPage.getRowCount());
    }
}
