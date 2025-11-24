package iborreg.orderchatbot.service;

import iborreg.orderchatbot.model.Order;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final Map<String, Order> orders = new HashMap<>();

    @PostConstruct
    public void init() {
        // Demo data, in a real app this would query a database
        orders.put("1001", new Order("1001", "Alice Smith", "Shipped",
                "Wireless Mouse", 1, 25.99));
        orders.put("1002", new Order("1002", "Bob Johnson", "Processing",
                "Mechanical Keyboard", 1, 89.99));
        orders.put("1003", new Order("1003", "Carlos Lopez", "Delivered",
                "USB-C Cable", 3, 29.97));
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}
