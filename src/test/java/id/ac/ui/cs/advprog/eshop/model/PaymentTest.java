package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Payment payment;

    @BeforeEach
    void setUp() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        this.payment = new Payment("1", "VOUCHER", "SUCCESS", paymentData);
    }

    @Test
    void testGetId() {
        assertEquals("1", this.payment.getId());
    }

    @Test
    void testGetMethod() {
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testGetStatus() {
        assertEquals("SUCCESS", this.payment.getStatus());
    }

    @Test
    void testGetPaymentData() {
        assertEquals("ESHOP1234ABC5678", this.payment.getPaymentData().get("voucherCode"));
    }
}