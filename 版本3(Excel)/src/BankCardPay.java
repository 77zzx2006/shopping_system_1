public class BankCardPay implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("使用银行卡支付 " + amount + " 元");
        System.out.println("银行卡支付成功！");
    }
}
