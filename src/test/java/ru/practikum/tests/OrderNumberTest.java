package ru.practikum.tests;

import org.junit.*;
import pages.MainPage;


public class OrderNumberTest {


    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    @Test
    public void searchOrderTest() {
        MainPage mainPage = new MainPage(driverFactory.getDriver());

        //Открыть главную страницу
        mainPage.openMainPage();
        //Клик на кнопку статус заказа
        mainPage.clickOnStatusButton();
        //Ввести номер заказа в поле номер заказа
        mainPage.enterOrderIn();
        //Нажать на кнопку Go!
        mainPage.clickOnGoButton();
        //Проверка ошибки
        mainPage.checkError();
    }

}