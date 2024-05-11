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

import static utilities.Utilities.formatDateTime;

public class TransactionsPage extends BasePage {

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
        exportTableToCsv();
        return driver.findElements(rowsInTable).size();
    }

    public void exportTableToCsv() {
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

            addCsvAttachment();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "table", type = "text/csv")
    public byte[] addCsvAttachment() throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources/table.csv"));
    }
}
