package learning.appointmentapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.LineItem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.ProductRepository;


/**
 * OrderService
 */
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepo;

    @Autowired
    LineItemRepository lineItemRepo;

    @Autowired
    ProductRepository productRepo;

    public Order createOrder(Order order, List<LineItem> lineItems){

        List<LineItem> newLineItems = new ArrayList<LineItem>();

        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().getId() != null) {
                Product product = validateProduct(lineItem.getProduct().getId());
           
                if (product != null) {
                    if (validateQuantity(lineItem.getQuantity())) {
                        lineItem.setOrder(order);
                        lineItem.setPrice(product.getPrice());
                        newLineItems.add(lineItem);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
            
        }

        orderRepo.save(order);
        for (LineItem lineItem : newLineItems) {
            lineItemRepo.save(lineItem);
        }

        return order;
    }

    Product validateProduct(Long id) {
        Product product = productRepo.findById(id).orElse(null);
        return product;
    }

    Boolean validateQuantity(Long quantity) {
        if (quantity > 0 ) {
            return true;
        } else {
            return false;
        }
    }


    
}