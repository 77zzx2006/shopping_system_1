import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Order implements Serializable {
    private String orderId = "ORD" + System.currentTimeMillis(); // 订单号
    private String orderTime;                // 下单时间
    private Map<Product, Integer> productList; // 订单里的 商品->数量
    private double totalAmount;              // 总金额
    private String payMethod;                // 支付方式

    public Order(Map<Product, Integer> productList, double totalAmount, String payMethod) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 造一个日期格式
        this.orderTime = sdf.format(new Date()); //格式化当前时间
        this.productList = productList;
        this.totalAmount = totalAmount;
        this.payMethod = payMethod;
    }

    public String getOrderId() {
        return this.orderId;
    }
}
