package ru.practikum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.OrderPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderScooterTest {

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    //Параметры
    @Parameterized.Parameter(0)
    public String buttonType;

    @Parameterized.Parameter(1)
    public String customerName;

    @Parameterized.Parameter(2)
    public String customerLastName;

    @Parameterized.Parameter(3)
    public String address;

    @Parameterized.Parameter(4)
    public String subwayStation;

    @Parameterized.Parameter(5)
    public String phone;

    @Parameterized.Parameter(6)
    public String deliveryDate;

    @Parameterized.Parameter(7)
    public String rentalPeriod;

    @Parameterized.Parameter(8)
    public String scooterColor;

    @Parameterized.Parameter(9)
    public String comment;


    @Parameterized.Parameters(name = "Полный заказ через {0} кнопку")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {
                        "header",           // buttonType
                        "Святогор",             // customerName
                        "Котов",           // customerLastName
                        "ул. Верхние котлы, д.53", // address
                        "Сокольники",       // subwayStation
                        "+79991234567",     // phone
                        "15.12.2025",       // deliveryDate
                        "двое суток",       // rentalPeriod
                        "black",            // scooterColor
                        "Оставьте под красным козырьком"  // comment
                },
                {
                        "body",             // buttonType
                        "Алевтина",            // customerName
                        "Долгорукова",          // customerLastName
                        "ул. Кролики, д. 25", // address
                        "Красные Ворота",   // subwayStation
                        "+79997654321",     // phone
                        "20.12.2025",       // deliveryDate
                        "сутки",            // rentalPeriod
                        "grey",             // scooterColor
                        null                // comment (без комментария)
                }
        });
    }

    @Test
    public void testCreationScooterOrder() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        //Открываем главную страницу и нажимаем на кнопку
        mainPage.openMainPage();
        mainPage.clickOrderButton(buttonType);

        //Заполняем первую форму заказа
        orderPage.fillOrderForm(customerName, customerLastName, address, subwayStation, phone);
        orderPage.clickOnwardsButton();

        //Заполняем вторую форму заказа
        orderPage.enterDeliveryDate(deliveryDate);
        orderPage.selectRentalPeriod(rentalPeriod);
        orderPage.selectScooterColor(scooterColor);
        orderPage.enterComment(comment);

        //Завершаем заказ
        orderPage.clickOrderButton();
        orderPage.confirmOrder();

        //Проверяем успешное создание заказа
        assertTrue ( "Заказ не удалось подтвердить", orderPage.getOrderStatus().contains("Заказ оформлен"));
    }

}