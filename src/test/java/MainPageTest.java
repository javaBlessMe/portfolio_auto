
import Base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Story("Поиск объявлений о покупке автомобилей")
public class MainPageTest extends BaseTest {
    @Feature("Подбор объявлений через поисковый запрос")
    @ParameterizedTest
    @CsvFileSource(resources = "/cars_list.csv", numLinesToSkip = 1)
    void checkInputSearchTest(String request,int quantity){
                 new MainPage().
                 inputCarVendorAndModel(request).
                 pressButtonShowResults().
                 getCarQuantity().
                 checkCarQuantity(quantity);
    }
    @Test
    void checkMainMenuTest(){
        new MainPage().
                selectUseCar("Renault").
                selectCarDrive("Полный").
                setMaxPrice("500000").
                pressButtonShowResults().
                getCarQuantity().
                checkCarQuantity(5);
    }
}
