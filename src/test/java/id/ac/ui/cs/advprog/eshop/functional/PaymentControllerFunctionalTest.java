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
class PaymentFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void paymentDetailFormPage_isCorrect(ChromeDriver driver) throws Exception {
        // Mengakses halaman GET /payment/detail
        driver.get(baseUrl + "/payment/detail");

        String pageTitle = driver.getTitle();
        assertEquals("Find Payment", pageTitle);

        String headerMessage = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Find Payment by ID", headerMessage);
    }

    @Test
    void paymentAdminListPage_isCorrect(ChromeDriver driver) throws Exception {
        // Mengakses halaman GET /payment/admin/list
        driver.get(baseUrl + "/payment/admin/list");

        String pageTitle = driver.getTitle();
        assertEquals("All Payments", pageTitle);

        String headerMessage = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Payment Admin - All Payments", headerMessage);
    }

    @Test
    void orderPayPage_isCorrect(ChromeDriver driver) throws Exception {
        // Mengakses halaman GET /order/pay/[orderId] (menggunakan dummy ID)
        driver.get(baseUrl + "/order/pay/dummy-order-id");

        String pageTitle = driver.getTitle();
        assertEquals("Pay Order", pageTitle);

        String headerMessage = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Pay Your Order", headerMessage);
    }
}