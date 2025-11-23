package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.EnvConfig;

import java.time.Duration;
import java.util.List;

public class OrderPage {

    //Локаторы
    private final By inputName = By.xpath("//input[@placeholder='* Имя']");
    private final By inputLastName = By.xpath("//input[@placeholder='* Фамилия']");
    private final By inputAddress = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By inputPhone = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By onwardsButton = By.xpath("//button[text()='Далее']");

    private final By deliveryDate = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriod = By.xpath("//div[contains(text(), 'Срок аренды')]");
    private final By rentalOptions = By.xpath("//div[contains(@class, 'Dropdown-option')]");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("(//button[text()='Заказать'])[2]");
    private final By confirmButton = By.xpath("//button[text()='Да']");
    private final By successModal = By.cssSelector(".Order_ModalHeader__3FDaJ");
    private final By metroOption = By.cssSelector(".select-search__option");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICITY_TIMEOUT));
    }
    //Заполняем первую форму
    public void fillOrderForm(String name, String lastName, String address, String subwayStation, String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(inputName));

        driver.findElement(inputName).sendKeys(name);
        driver.findElement(inputLastName).sendKeys(lastName);
        driver.findElement(inputAddress).sendKeys(address);
        selectSubwayStation(subwayStation);
        driver.findElement(inputPhone).sendKeys(phone);
    }

    //Выбор метро
    public void selectSubwayStation(String stationName) {
        WebElement metroInput = driver.findElement(metroField);

        metroInput.click();
        metroInput.sendKeys(stationName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(metroOption));

        WebElement firstStation = driver.findElement(metroOption);
        firstStation.click();

    }
    //Кликаем на кнопку Дальше
    public void clickOnwardsButton() {
        WebElement button = driver.findElement(onwardsButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        wait.until(ExpectedConditions.elementToBeClickable(deliveryDate));
    }
    //Выбираем дату
    public void enterDeliveryDate(String date) {
        WebElement dateInput = driver.findElement(deliveryDate);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateInput);

        dateInput.clear();
        dateInput.sendKeys(date);

        dateInput.sendKeys(Keys.ENTER);
    }

    //Выбираем срок аренды
    public void selectRentalPeriod(String period) {

        WebElement dropdown = driver.findElement(rentalPeriod);
        dropdown.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(rentalOptions));

        List<WebElement> options = driver.findElements(rentalOptions);
        for (WebElement option : options) {
            if (option.getText().equals(period)) {
                option.click();
                break;
            }
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(rentalOptions));
    }

    //Выбираем чекбокс
    public void selectScooterColor(String scooterColor) {
        By label = By.xpath("//label[@for='" + scooterColor + "']");
        WebElement checkboxLabel = wait.until(ExpectedConditions.elementToBeClickable(label));
        checkboxLabel.click();
    }


    //Оставляем комментарий
    public void enterComment(String comment) {
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }
    }

    //Кликаем по кнопке заказать
    public void clickOrderButton() {
        WebElement button = driver.findElement(orderButton);
        button.click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton));
    }

    //Кликаем по кнопке Да
    public void confirmOrder() {
        WebElement button = driver.findElement(confirmButton);
        button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(successModal));
    }

    //Получаем подтверждение заказа
    public String getOrderStatus() {
        WebElement CommentElement = driver.findElement(successModal);
        return CommentElement.getText();
    }
}

