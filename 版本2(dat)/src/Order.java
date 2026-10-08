import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    // toString 重写--把订单变成文字，打印订单时直接显示全部信息（含商品明细）
    @Override
    public String toString() {
        String result = "订单号：" + this.orderId + " | 时间：" + this.orderTime + " | 支付方式：" + this.payMethod + " | 总金额：" + this.totalAmount;
        List<Product> productList = new ArrayList<>(this.productList.keySet()); // 订单里所有商品装进列表
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i); // 拿第i个商品
            int quantity = this.productList.get(p); // 拿这个商品的数量
            result = result + "\n    商品：" + p.getName() + " x" + quantity + " = " + p.getPrice() * quantity + "元";
        }
        return result;
    }
}
