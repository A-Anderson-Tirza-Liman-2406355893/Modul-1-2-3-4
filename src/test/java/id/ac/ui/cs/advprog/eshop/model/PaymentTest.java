package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
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
    void testCreatePaymentVoucherHappyPath() {
        Map<String, String> paymentData = new HashMap<>();
        // 16 karakter, diawali ESHOP, berisi 8 angka (12345678)
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("1", "VOUCHER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherUnhappyPath_InvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123"); // Kurang dari 16 karakter
        Payment payment = new Payment("2", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherUnhappyPath_InvalidPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "PROMO1234ABC5678"); // Tidak diawali ESHOP
        Payment payment = new Payment("3", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherUnhappyPath_InsufficientNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12ABCDEFGHJ"); // Hanya 2 angka, harusnya 8
        Payment payment = new Payment("4", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCODHappyPath() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jalan Margonda Raya");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("5", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCODUnhappyPath_EmptyAddress() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("6", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCODUnhappyPath_NullDeliveryFee() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jalan Margonda Raya");
        // deliveryFee tidak dimasukkan (null)
        Payment payment = new Payment("7", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}