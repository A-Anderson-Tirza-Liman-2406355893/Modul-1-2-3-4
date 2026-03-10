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