package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();

        paymentData.put("orderId", order.getId());

        Payment payment = new Payment(paymentId, method, paymentData);
        paymentRepository.save(payment);

        if ("SUCCESS".equals(payment.getStatus())) {
            orderService.updateStatus(order.getId(), "SUCCESS");
        } else if ("REJECTED".equals(payment.getStatus())) {
            orderService.updateStatus(order.getId(), "FAILED");
        }

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        Payment savedPayment = paymentRepository.findById(payment.getId());

        if (savedPayment != null) {
            if (!status.equals("SUCCESS") && !status.equals("REJECTED")) {
                throw new IllegalArgumentException("Status must be SUCCESS or REJECTED");
            }

            Payment updatedPayment = new Payment(savedPayment.getId(), savedPayment.getMethod(), status, savedPayment.getPaymentData());
            paymentRepository.save(updatedPayment);

            String orderId = savedPayment.getPaymentData().get("orderId");

            if (orderId != null) {
                if (status.equals("SUCCESS")) {
                    orderService.updateStatus(orderId, "SUCCESS"); // Cocok dengan OrderStatus.SUCCESS
                } else if (status.equals("REJECTED")) {
                    orderService.updateStatus(orderId, "FAILED");  // Cocok dengan OrderStatus.FAILED
                }
            }

            return updatedPayment;
        }
        return null;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}