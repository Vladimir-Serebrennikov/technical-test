package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.UserPage;
import page.LoginPage;
import page.TransactionsPage;

import java.util.ResourceBundle;

import static utilities.Utilities.fibbonachiCalc;

public class BankingAppTests extends BaseTest {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    String url = resourceBundle.getString("url");

    @Test
    @DisplayName("Проверка информации о транзакциях")
    public void openTransactionsTest() throws InterruptedException {
        String user = "Harry Potter";
        int fibonacciValue = fibbonachiCalc();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(url);
        Assertions.assertEquals("XYZ Bank", driver.getTitle());

        UserPage userPage = loginPage.login(user);
        Assertions.assertEquals(user, userPage.getOwnerName());

        userPage.addToDeposit(fibonacciValue);
        Assertions.assertEquals("Deposit Successful", userPage.getMessage());

        userPage.withdrawlFromDeposit(fibonacciValue);
        Assertions.assertEquals("Transaction successful", userPage.getMessage());
        Assertions.assertEquals("0", userPage.getBalance());

        TransactionsPage transactionsPage = userPage.clickOnTransactionTabButton();
        Assertions.assertEquals(2, transactionsPage.getRowCount());
    }
}
