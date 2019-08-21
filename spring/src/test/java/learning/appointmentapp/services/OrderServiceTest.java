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
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineItemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.ProductRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    LineItemRepository lineItemRepo;

    @Test
    public void testCreateOrderSuccess() {
        Product product = seedProduct("Laptop", 2000L);

        Order order = new Order();

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(1L);

        List<LineItem> lineItems = new ArrayList<LineItem>();
        lineItems.add(lineItem);

        Order createdOrder = orderService.createOrder(order, lineItems);
        
        assertNotEquals(createdOrder, null);
    }

    @Test
    public void testCreateOrderInvalidProduct() {
        Product product = new Product();

        Order order = new Order();

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(1L);

        List<LineItem> lineItems = new ArrayList<LineItem>();
        lineItems.add(lineItem);

        Order createdOrder = orderService.createOrder(order, lineItems);
        
        assertEquals(createdOrder, null);
    }


    @Test
    public void testCreateOrderZeroQuantity() {
        Product product = seedProduct("Laptop", 2000L);

        Order order = new Order();

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(0L);

        List<LineItem> lineItems = new ArrayList<LineItem>();
        lineItems.add(lineItem);

        Order createdOrder = orderService.createOrder(order, lineItems);
        
        assertEquals(createdOrder, null);
    }

    Product seedProduct(String name, Long price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        productRepo.save(product);
        return product;
    }
}