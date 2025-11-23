package ru.practikum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import ru.practikum.tests.DriverFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FAQTest {

    // УБИРАЕМ extends BaseTest и используем DriverFactory
    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    // УБИРАЕМ final и конструктор
    @Parameterized.Parameter(0)
    public int index;

    @Parameterized.Parameter(1)
    public String expectedText;

    @Parameterized.Parameters(name = "FAQ question №{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    @Test
    public void testFaqAnswers() {
        MainPage mainPage = new MainPage(driverFactory.getDriver());
        mainPage.openMainPage(); // ← исправлено на openMainPage()
        mainPage.clickQuestion(index);
        String actualText = mainPage.getAnswer(index);
        assertFalse("Должен присутствовать ответ ", actualText.isEmpty());
        assertTrue("Фактический ответ на вопрос №" + (index + 1) + " не соответствует ожидаемому.\n" +
                        "Ожидаемый ответ: " + expectedText + "\n" +
                        "Фактический ответ: " + actualText,
                actualText.equals(expectedText));
    }
}