package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();

        // Setup dummy Order (Asumsi Order memiliki constructor ID, List Product, time, author, status)
        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", new ArrayList<>(), 1708560000L, "Safira Riyapandzan");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("1", "VOUCHER", paymentData1);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jalan Margonda Raya");
        paymentData2.put("deliveryFee", "10000");
        Payment payment2 = new Payment("2", "CASH_ON_DELIVERY", paymentData2);
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment dummyPayment = new Payment("dummy-id", "VOUCHER", paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(dummyPayment);

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(result);
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);

        // Mock getPayment
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus()); // Karena Order terkait harus jadi SUCCESS
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(1);

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus()); // Karena Order terkait harus jadi FAILED
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = payments.get(0);

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "INVALID_STATUS");
        });
    }

    @Test
    void testGetPaymentIfFound() {
        Payment payment = payments.get(0);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        when(paymentRepository.findById("INVALID_ID")).thenReturn(null);
        Payment result = paymentService.getPayment("INVALID_ID");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(payments);
        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }
}