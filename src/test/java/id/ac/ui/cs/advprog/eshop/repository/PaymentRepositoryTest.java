package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment1;
    Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        // Setup data untuk testing
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        payment1 = new Payment("1", "VOUCHER", paymentData1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jalan Margonda Raya");
        paymentData2.put("deliveryFee", "10000");
        payment2 = new Payment("2", "CASH_ON_DELIVERY", paymentData2);
    }

    // Happy Path: Berhasil menyimpan payment
    @Test
    void testSaveCreate() {
        Payment result = paymentRepository.save(payment1);
        assertNotNull(result);
        assertEquals(payment1.getId(), result.getId());
    }

    // Happy Path: Berhasil mencari berdasarkan ID yang ada
    @Test
    void testFindByIdIfFound() {
        paymentRepository.save(payment1);
        Payment result = paymentRepository.findById(payment1.getId());
        assertNotNull(result);
        assertEquals(payment1.getId(), result.getId());
    }

    // Unhappy Path: Mencari ID yang tidak ada di repository
    @Test
    void testFindByIdIfNotFound() {
        Payment result = paymentRepository.findById("ID_NGASAL");
        assertNull(result); // Harus mengembalikan null karena data tidak ada
    }

    // Happy Path: Mengambil semua data
    @Test
    void testFindAll() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> payments = paymentRepository.findAll();
        assertNotNull(payments);
        assertEquals(2, payments.size());
    }
}