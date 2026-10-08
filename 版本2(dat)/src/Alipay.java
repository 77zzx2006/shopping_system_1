public class Alipay implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("使用支付宝支付 " + amount + " 元");
        System.out.println("支付宝支付成功！");
    }
}
