package iborreg.orderchatbot.model;

public class Order {
    private String orderId;
    private String customerName;
    private String status;
    private String item;
    private int quantity;
    private double totalAmount;

    public Order() {
    }

    public Order(String orderId, String customerName, String status,
                 String item, int quantity, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = status;
        this.item = item;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}