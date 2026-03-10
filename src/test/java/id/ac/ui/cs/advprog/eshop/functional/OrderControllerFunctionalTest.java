package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createOrderPage_isCorrect(ChromeDriver driver) throws Exception {
        // Mengakses halaman /order/create
        driver.get(baseUrl + "/order/create");

        // Memastikan Title halaman sesuai dengan yang kita buat di createOrder.html
        String pageTitle = driver.getTitle();
        assertEquals("Create Order", pageTitle);

        // Memastikan Header (h2) sesuai
        String headerMessage = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Create New Order", headerMessage);
    }

    @Test
    void orderHistoryPage_isCorrect(ChromeDriver driver) throws Exception {
        // Mengakses halaman /order/history
        driver.get(baseUrl + "/order/history");

        // Memastikan Title halaman sesuai dengan yang kita buat di orderHistoryForm.html
        String pageTitle = driver.getTitle();
        assertEquals("Order History", pageTitle);

        // Memastikan Header (h2) sesuai
        String headerMessage = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Check Order History", headerMessage);
    }
}