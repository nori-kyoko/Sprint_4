package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.EnvConfig;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;


    //Локаторы элементов
    private final By img = By.cssSelector("img[alt='Not found']");
    private final By buttonGo = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");
    private final By inputOrder = By.cssSelector(".Input_Input__1iN_Z.Header_Input__xIoUq");
    private final By statusOrderButton = By.cssSelector(".Header_Link__1TAG7");
    private final By faqBlock = By.className("Home_FAQ__3uVm4");
    private final By questionButton = By.cssSelector(".accordion__button");
    private final By answerBlock = By.cssSelector(".accordion__panel");
    private final By headerOrderButton = By.cssSelector(".Button_Button__ra12g");
    private final By bodyOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");



    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    //Проверка ошибки
    public void checkError() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(img));
        Assert.assertTrue(driver.findElement(img).isDisplayed());
    }
    //Клик по кнопке Go!
    public void clickOnGoButton() {
        WebElement button = driver.findElement(buttonGo);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }
    //Ввести номер заказа в поле номера заказа
    public void enterOrderIn() {
        WebElement input = driver.findElement(inputOrder);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        input.sendKeys("12345");
    }
    //Клик на кнопку статус заказа
    public void clickOnStatusButton() {
        WebElement statusButton = driver.findElement(statusOrderButton);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", statusButton);

        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICITY_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(statusButton));

        statusButton.click();
    }
    //Метод открывающий главную страницу
    public void openMainPage() {
        driver.get(EnvConfig.BASE_URL);
    }

    // Методы для FAQ теста
    public void clickQuestion(int index) {
        // Ждем загрузки раздела FAQ
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICITY_TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(faqBlock));

        List<WebElement> questions = driver.findElements(questionButton);

        if (index < 0 || index >= questions.size()) {
            throw new IndexOutOfBoundsException("Question index " + index + " out of bounds");
        }

        WebElement question = questions.get(index);
        // Скролл и клик через JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);

        // Ждем раскрытия ответа
        wait.until(d -> {
            WebElement answer = driver.findElements(answerBlock).get(index);
            return answer.isDisplayed();
        });
    }

    public String getAnswer(int index) {
        List<WebElement> answers = driver.findElements(answerBlock);

        if (index < 0 || index >= answers.size()) {
            throw new IndexOutOfBoundsException("Answer index " + index + " out of bounds");
        }

        return answers.get(index).getText();
    }

    //Клик по кнопке заказа
    public void clickOrderButton(String buttonType) {
        if ("header".equals(buttonType)) {
            driver.findElement(headerOrderButton).click();
        } else {
            WebElement button = driver.findElement(bodyOrderButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
        }
    }
}