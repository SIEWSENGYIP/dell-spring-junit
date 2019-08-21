package learning.appointmentapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Payment;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.ProductRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepo;

    @Test
    public void testCreatePaymentSuccess() {
        Order order = seedOrder();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaid(true);
        payment.setRefunded(false);

        Payment createdPayment = paymentService.createPayment(payment);

        assertNotEquals(createdPayment, null);
        assertEquals(createdPayment.getOrder().getId(), order.getId());
        assertEquals(createdPayment.getPaid(), true);
        assertEquals(createdPayment.getRefunded(), false);
    }

    @Test
    public void testCreatePaymentFail() {
        Order order = new Order();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaid(true);
        payment.setRefunded(false);

        Payment createdPayment = paymentService.createPayment(payment);

        assertEquals(createdPayment, null);
    }

    @Test
    public void testRefundPaymentSuccess() {
        Payment payment = seedPayment(false);

        Payment refundedPayment = paymentService.refundPayment(payment.getOrder());

        assertNotEquals(refundedPayment, null);
        assertEquals(refundedPayment.getRefunded(), true);
    }

    @Test
    public void testRefundPaymentOrderNotPaid() {
        Order order = seedOrder();

        Payment refundedPayment = paymentService.refundPayment(order);

        assertEquals(refundedPayment, null);
    }

    @Test
    public void testRefundPaymentOrderAlreadyRefunded() {
        Payment payment = seedPayment(true);

        Payment refundedPayment = paymentService.refundPayment(payment.getOrder());

        assertEquals(refundedPayment, null);
    }


    Product seedProduct(String name, Long price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        productRepo.save(product);
        return product;
    }

    Order seedOrder() {
        Product product = seedProduct("Laptop", 2000L);

        Order order = new Order();

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(1L);

        List<LineItem> lineItems = new ArrayList<LineItem>();
        lineItems.add(lineItem);

        Order createdOrder = orderService.createOrder(order, lineItems);

        return createdOrder;
    
    }

    Payment seedPayment(Boolean refunded) {
        Order order = seedOrder();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaid(true);

        if (refunded) {
            payment.setRefunded(true);
        } else {
            payment.setRefunded(false);
        }
        
        Payment createdPayment = paymentService.createPayment(payment);

        return createdPayment;
    }
}