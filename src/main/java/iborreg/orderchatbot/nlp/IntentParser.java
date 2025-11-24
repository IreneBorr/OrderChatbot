package iborreg.orderchatbot.nlp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntentParser {

    // Look for "order" followed by digits
    private static final Pattern ORDER_ID_PATTERN =
            Pattern.compile("order\\s*#?\\s*(\\d+)", Pattern.CASE_INSENSITIVE);

    public static class ParsedIntent {
        private final String type; // e.g. "GET_ORDER_STATUS", "FAQ", "CONTACT_INFO"
        private final String orderId;

        public ParsedIntent(String type, String orderId) {
            this.type = type;
            this.orderId = orderId;
        }

        public String getType() {
            return type;
        }

        public String getOrderId() {
            return orderId;
        }
    }

    public ParsedIntent parse(String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) { // Java 8 safe
            return null;
        }

        String lower = userMessage.toLowerCase();

        // FAQ intent
        if (lower.contains("faq") ||
                lower.contains("questions") ||
                lower.contains("common questions") ||
                lower.contains("help menu")) {
            return new ParsedIntent("FAQ", null);
        }

        // Contact info intent
        if (lower.contains("contact") ||
                lower.contains("phone") ||
                lower.contains("email") ||
                lower.contains("support")) {
            return new ParsedIntent("CONTACT_INFO", null);
        }

        // Order tracking / status intent
        if (lower.contains("status") || lower.contains("track") ||
                lower.contains("where") || lower.contains("details") ||
                lower.contains("order")) {

            Matcher matcher = ORDER_ID_PATTERN.matcher(userMessage);
            if (matcher.find()) {
                String orderId = matcher.group(1);
                return new ParsedIntent("GET_ORDER_STATUS", orderId);
            }
        }

        // Fallback small talk
        return new ParsedIntent("SMALL_TALK", null);
    }
}