package iborreg.orderchatbot.service;

import iborreg.orderchatbot.model.Order;
import iborreg.orderchatbot.nlp.IntentParser;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

@Service
public class ChatService {

    private final OrderService orderService;
    private final IntentParser intentParser = new IntentParser();

    public ChatService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String replyTo(String userMessage) {
        IntentParser.ParsedIntent intent = intentParser.parse(userMessage);

        if (userMessage != null) {
            String lower = userMessage.toLowerCase();
            if (lower.contains("track") && lower.contains("order") && !lower.matches(".*\\d+.*")) {
                return askForOrderNumberMessage();
            }
        }

        if (intent == null) {
            return mainMenuMessage();
        }

        String type = intent.getType();

        if ("GET_ORDER_STATUS".equals(type)) {
            return handleOrderStatus(intent.getOrderId());
        } else if ("FAQ".equals(type)) {
            return faqMessage();
        } else if ("CONTACT_INFO".equals(type)) {
            return contactInfoMessage();
        } else { // SMALL_TALK or unknown
            return smallTalkReply(userMessage);
        }
    }

    private String handleOrderStatus(String orderId) {
        Optional<Order> orderOpt = orderService.findById(orderId);
        if (!orderOpt.isPresent()) {
            return "I couldn't find an order with ID " + orderId +
                    ". Please check the number and try again.\n\n" +
                    "You can also type:\n" +
                    "- \"FAQ\" for common questions\n" +
                    "- \"Contact\" for support info";
        }

        Order order = orderOpt.get();
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

        return String.format(
                "Here are the details for order %s:%n" +
                        "- Customer: %s%n" +
                        "- Item: %s (x%d)%n" +
                        "- Status: %s%n" +
                        "- Total: %s%n%n" +
                        "You can type \"FAQ\", \"Contact\", or another order number.",
                order.getOrderId(),
                order.getCustomerName(),
                order.getItem(),
                order.getQuantity(),
                order.getStatus(),
                currency.format(order.getTotalAmount())
        );
    }
    private String didNotUnderstandMessage() {
        return "I didn't quite understand! \n\n" +
                "Here's what I can help with:\n" +
                "- FAQs: Type \"FAQ\" or click the FAQ button to see common questions.\n" +
                "- Order tracking: Type something like \"Track order 1001\" or \"Status of order 1002\".\n" +
                "- Contact info: Type \"Contact\" to see phone and email support.\n\n" +
                "For example, you can try:\n" +
                "- \"FAQ\"\n" +
                "- \"Track order\"\n" +
                "- \"Contact support\"";
    }

    private String faqMessage() {
        return "FAQ - Frequently Asked Questions\n" +
                "1) How do I track my order?\n" +
                "   → Type something like: \"Track order 1001\" or \"Status of order 1002\".\n\n" +
                "2) How long does shipping take?\n" +
                "   → Standard shipping usually takes 3–5 business days after your order is shipped.\n\n" +
                "3) Can I change or cancel my order?\n" +
                "   → Orders can be changed or canceled only while they are still in \"Processing\" status.\n\n" +
                "You can also type:\n" +
                "- \"Contact\" for support info\n" +
                "- \"Order 1001\" to see a sample order.";
    }

    private String contactInfoMessage() {
        return "Contact Information\n" +
                "- Phone: +1 (470)367-1234 (Mon - Fri, 9am - 5pm)\n" +
                "- Email: iborreg.student@gwinnetttech.com\n" +
                "You can also type \"FAQ\" for common questions or ask about a specific order.";
    }

    private String mainMenuMessage() {
        return "Welcome! I can help you with:\n" +
                "- FAQ: Type \"FAQ\" to see common questions.\n" +
                "- Order tracking: Type something like \"Track order 1001\".\n" +
                "- Contact info: Type \"Contact\" to see how to reach support.";
    }

    private String smallTalkReply(String userMessage) {
        String lower = userMessage.toLowerCase();
        if (lower.contains("hello") || lower.contains("hi")) {
            return "Hi!\n" + mainMenuMessage();
        } else if (lower.contains("help") || lower.contains("menu")) {
            return mainMenuMessage();
        } else {
            return didNotUnderstandMessage();
        }
    }


    private String askForOrderNumberMessage() {
        return "I can definitely help you track an order!\n\n" +
                "Please include your order number in your message.\n\n" +
                "For example, you can type:\n" +
                "- \"Track order 1001\"\n" +
                "- \"What is the status of order 1002?\"\n" +
                "- \"Order 1003 details\"\n\n" +
                "Try again with your order number and I'll look it up.";
    }
}
