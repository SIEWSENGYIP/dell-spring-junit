package learning.appointmentapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Payment;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.PaymentRepository;


/**
 * OrderService
 */
@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    LineItemRepository lineItemRepo;

    public Payment createPayment(Payment payment){
        
        if(payment.getOrder().getId() != null) {
            Order order = validateOrder(payment.getOrder().getId());
            if (order != null) {
                payment.setAmount(calculateAmount(order));
                paymentRepo.save(payment);
            } else {
                return null;
            }
        } else {
            return null;
        }

        return payment;
    }

    public Payment refundPayment(Order order){

        Payment payment = paymentRepo.findByOrder(order);

        if (payment != null) {
            if(payment.getPaid()) {
                if(payment.getRefunded()) {
                    return null;
                } else {
                    payment.setRefunded(true);
                    paymentRepo.save(payment);
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

        return payment;

    }
    
    Order validateOrder(Long id) {
        Order order = orderRepo.findById(id).orElse(null);

        return order;
    }

    Long calculateAmount(Order order) {
        List<LineItem> lineItems = lineItemRepo.findByOrder(order);
        Long amount = 0L;

        for (LineItem lineItem : lineItems) {
            amount += lineItem.getPrice() * lineItem.getQuantity();
        }

        return amount;
    }
}