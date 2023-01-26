import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {
    private SelenideElement inputSearch=$(By.xpath("//input[@type='text']")); //Окно для ввода поискового запроса
    private SelenideElement showAdvertisementButton = $("span.ButtonWithLoader__content"); //Кнопка "Показать объявления"
    private ElementsCollection listOfAdvertisement=$$(By.xpath("//div[@class='ListingItem']")); //Список объявлений
    private SelenideElement firstResultOfSearch = $(By.xpath("//div[@class='SearchLineSuggestItem']"));//Предлагаемые
    // результаты, найденные по поисковому запросу
    private SelenideElement useCarButton = $x("//span[@class='Button__text' and .='С пробегом']");
    private SelenideElement carDrive = $x("//span[@class='Button__text' and .='Привод']");
    private SelenideElement inputMaxCarPrice= $x("//input[@name='price_to']");
    private static int carQuantity;
    @Step("Ввод поиского запроса ")
    MainPage inputCarVendorAndModel(String CarVendorAndModel){
        inputSearch.sendKeys(CarVendorAndModel); //Ввести посиковый запрос
        firstResultOfSearch.click(); //Выбрать предложенный результат
        return page(MainPage.class);
    }
    @Step("Посчитать количесво полученных объявлений")
    MainPage getCarQuantity(){
        carQuantity = listOfAdvertisement.size(); //Размер коллекции со списком объявлений и является кол-вом найденных объявлений
        return page(MainPage.class);
    }
    @Step("Нажать на кнопку \"Показать результаты\"")
    MainPage pressButtonShowResults(){
        /*В текст кнопки "Показать объявления" подгружается количество найденных объявлений, но проблема в том,
        * что это занимает определнное время, которое нужно подождать. С помощью Selenium это решить не удастся,
        * тк кнопка загрузилась, нужно именно ждать загрузки текста в ней
        * */
        long referencePoint = System.currentTimeMillis();
        while (true){
            //Запускаем бесконечный цикл и ждем, когда текст кнопки станет будет содержать цифры и буквы
            if(showAdvertisementButton.getText().matches("\\D+[1-9]+\\D+")){
                break; //выход из цикла
            }
            if(System.currentTimeMillis()-referencePoint>5000){
                break;//Если в течение 5 сек не загрузилось, то выход из цикла. В конце тест упадет
            }
        }
        showAdvertisementButton.click();
        return page(MainPage.class);
    }
    @Step("Сравнить количество полученных объявлений с исходными данными")
    void checkCarQuantity(int trueQuantity){
       assertEquals(trueQuantity,carQuantity);
    }

    MainPage selectUseCar(String carVendor){
        useCarButton.click();
        $x("//div[.='Renault']").click();
        return page(MainPage.class);
    }
    MainPage selectCarDrive(String carDriveType){
        carDrive.click();
        $x("//div[@role='menuitem' and .='"+carDriveType+"']").click();
        return page(MainPage.class);
    }

    MainPage setMaxPrice(String maxCarPrice){
        inputMaxCarPrice.sendKeys(maxCarPrice);
        return  page(MainPage.class);
    }

}
