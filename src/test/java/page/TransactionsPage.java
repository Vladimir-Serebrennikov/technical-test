package page;

import io.qameta.allure.Attachment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static helpers.Helpers.formatDateTime;

public class TransactionsPage extends BasePage {

    private final By headersInTable = By.xpath("//table/thead/tr/td");
    private final By rowsInTable = By.xpath("//table/tbody/tr");
    private final By backButton = By.xpath("//button[text()='Back']");
    private final By transactionsButton = By.xpath("//button[@ng-click='transactions()']");

    public TransactionsPage(WebDriver driver) {
        super(driver);
    }

    public int getRowCount() {
        int count = driver.findElements(rowsInTable).size();
        if (count == 0) {
            driver.findElement(backButton).click();
            driver.findElement(transactionsButton).click();
        }
        parseTableAndWriteToCSV();
        return driver.findElements(rowsInTable).size();
    }

    public void parseTableAndWriteToCSV() {
        try {
            FileWriter csvWriter = new FileWriter("src/test/resources/table.csv");
            List<WebElement> rows = driver.findElements(rowsInTable);
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    String text = cell.getText();
                    text = formatDateTime(text);
                    csvWriter.append(text);
                    csvWriter.append(";");
                }
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();

            attachCsvFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "table", type = "text/csv")
    public byte[] attachCsvFile() throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources/table.csv"));
    }
}
