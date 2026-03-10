package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService; // Tambahkan ini untuk integrasi Payment

    @GetMapping("/create")
    public String createOrderPage() {
        return "createOrder";
    }

    @GetMapping("/history")
    public String historyOrderPage() {
        return "orderHistoryForm";
    }

    @PostMapping("/history")
    public String historyOrderPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        model.addAttribute("author", author);
        return "orderHistoryList";
    }

    // =========================================
    // ENDPOINT BARU UNTUK ORDER PAY (Checklist 3)
    // =========================================
    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "orderPay";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId, @RequestParam Map<String, String> paymentData, Model model) {
        Order order = orderService.findById(orderId);
        String method = paymentData.get("method"); // "VOUCHER" atau "CASH_ON_DELIVERY"

        // Buat payment
        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("payment", payment);

        return "paymentSuccess"; // Halaman yang mengembalikan ID Payment
    }
}