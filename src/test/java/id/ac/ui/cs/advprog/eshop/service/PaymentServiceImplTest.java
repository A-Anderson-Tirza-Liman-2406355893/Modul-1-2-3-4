package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product; // Pastikan import Product
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService; // <--- MOCK BARU UNTUK INTEGRASI

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();

        List<Product> products = new ArrayList<>();
        Product dummyProduct = new Product();
        dummyProduct.setProductId("P1");
        dummyProduct.setProductName("Dummy");
        dummyProduct.setProductQuantity(1);
        products.add(dummyProduct);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Riyapandzan");

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        paymentData1.put("orderId", order.getId()); // Relasi disisipkan di sini
        Payment payment1 = new Payment("1", "VOUCHER", paymentData1);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jalan Margonda Raya");
        paymentData2.put("deliveryFee", "10000");
        paymentData2.put("orderId", order.getId()); // Relasi disisipkan di sini
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
        Payment payment = payments.get(0); // VOUCHER

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        when(orderService.updateStatus(order.getId(), "SUCCESS")).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());

        verify(orderService, times(1)).updateStatus(order.getId(), "SUCCESS");
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(1); // CASH_ON_DELIVERY

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        when(orderService.updateStatus(order.getId(), "FAILED")).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());

        verify(orderService, times(1)).updateStatus(order.getId(), "FAILED");
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

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = payments.get(0);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        when(orderService.updateStatus(order.getId(), "SUCCESS")).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        verify(orderService, times(1)).updateStatus(order.getId(), "SUCCESS");
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Payment payment = payments.get(1);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        when(orderService.updateStatus(order.getId(), "FAILED")).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        verify(orderService, times(1)).updateStatus(order.getId(), "FAILED");
        assertEquals("REJECTED", result.getStatus());
    }
}