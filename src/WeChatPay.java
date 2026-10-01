// 微信支付类：实现 PaymentStrategy 接口
public class WeChatPay implements PaymentStrategy {
    // 【接口实现】必须实现接口里的 pay 方法：这里是微信的付款逻辑（打印模拟支付）
    public void pay(double amount) {
        System.out.println("使用微信支付 " + amount + " 元");
        System.out.println("微信支付成功！");
    }
}
