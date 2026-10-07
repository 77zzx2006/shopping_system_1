import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

//负责注册、登录、购物车和结账
public class User extends Abstract {
    private String level = "铜牌顾客";          // 顾客等级（铜牌/银牌/金牌）
    private double totalSpent = 0.0;           // 累计消费金额
    private final List<Order> orderHistory = new ArrayList<>(); // 订单历史
    private final Cart cart;                          // 该顾客的购物车

    // 构造方法--创建顾客对象时自动执行：先初始化父类公共字段，再创建自己的购物车
    public User(String userId, String username, String password, String email) {
        super(userId, username, password, email);
        this.cart = new Cart();
    }

    // 抽象方法实现--继承 AbstractUser 后必须实现的"重置密码"
    public void resetPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        String newPassword = sb.toString();
        System.out.println("新密码已生成：" + newPassword);
        this.setPassword(PasswordUtil.encrypt(newPassword)); // 版本3：打印给顾客看明文，存进文件的是密文
        System.out.println("（模拟发送到邮箱：" + this.getEmail() + "）");
    }

    //顾客注册
    public static void register(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 顾客注册 =====");
        System.out.print("请输入用户名（不少于5个字符）：");
        String username = sc.nextLine();
        while (username.length() < 5) { // 长度不够就一直重新输入
            System.out.print("用户名长度不能少于5个字符，请重新输入：");
            username = sc.nextLine();
        }
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i); // 检查用户名是否已被占用
            String listName = user.getUsername();//拿列表里的用户名
            if (listName.equals(username)) { //和输入的用户名比较
                System.out.println("用户名已存在，注册失败！");
                return;
            }
        }
        System.out.print("请输入密码（不少于8个字符）：");
        String password = sc.nextLine();
        while (password.length() < 8) {
            System.out.print("密码长度不能少于8个字符，请重新输入：");
            password = sc.nextLine();
        }
        System.out.print("请输入邮箱：");
        String email = sc.nextLine();
        while (true) {
            if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|cn|net|org|edu|gov)$")) { break; }
            System.out.print("邮箱格式不对，请重新输入：");
            email = sc.nextLine();
        }
        System.out.print("请输入手机号：");
        String phone = sc.nextLine();
        while (true) {
            if (phone.matches("^1[3-9]\\d{9}$")) { break; }
            System.out.print("手机号必须是11位数字，请重新输入：");
            phone = sc.nextLine();
        }
        String userId = "U" + System.currentTimeMillis(); // 用时间戳生成唯一ID
        String secret = PasswordUtil.encrypt(password); // 版本3：存之前先加密成密文
        User newUser = new User(userId, username, secret, email);
        newUser.setPhone(phone);
        users.add(newUser);
        System.out.println("注册成功！您的用户ID是：" + userId);
    }

    //顾客登录
    public static User login(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 顾客登录 =====");
        System.out.print("请输入用户名：");
        String inputName = sc.nextLine();
        System.out.print("请输入密码：");
        String inputPassword = sc.nextLine();
        User findUser = null;
        for (int j = 0; j < users.size(); j++) {
            User user = users.get(j); // 遍历找同名的用户
            String listName = user.getUsername(); // 拿列表里的用户名
            if (listName.equals(inputName)) { //和输入的用户名比较
                findUser = user;
                break;
            }
        }
        if (findUser == null) {
            System.out.println("用户名不存在！");
            return null;
        }
        for (int i = 0; i < 3; i++) { // 最多试3次密码
            String findPwd = findUser.getPassword(); //拿找到用户的密码（存的是密文）
            if (findPwd.equals(PasswordUtil.encrypt(inputPassword))) { //版本3：输入的密码也加密后再比较
                System.out.println("登录成功！欢迎 " + findUser.getUsername());
                return findUser;
            }
            if (i < 2) {
                System.out.print("密码错误！还剩 " + (2 - i) + " 次机会，请重新输入：");
                inputPassword = sc.nextLine();
            }
        }
        System.out.println("密码错误3次，账户已被锁定！");
        return null;
    }

    // 修改密码
    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 修改密码 =====");
        System.out.print("请输入旧密码：");
        String oldPassword = sc.nextLine();
        String myPwd = this.getPassword(); //拿自己当前的密码
        if (!myPwd.equals(oldPassword)) { //和输入的旧密码比较
            System.out.println("旧密码错误！");
        } else {
            System.out.print("请输入新密码（不少于8个字符）：");
            String newPassword = sc.nextLine();
            while (newPassword.length() < 8) {
                System.out.print("密码长度不能少于8个字符，请重新输入：");
                newPassword = sc.nextLine();
            }
            this.setPassword(PasswordUtil.encrypt(newPassword)); // 版本3：存的是密文
            System.out.println("密码修改成功！");
        }
    }

    //忘记密码
    public static void forgotPassword(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 忘记密码 =====");
        System.out.print("请输入用户名：");
        String inputName = sc.nextLine();
        System.out.print("请输入注册邮箱：");
        String inputEmail = sc.nextLine();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String listName = user.getUsername(); //拿列表里的用户名
            String listEmail = user.getEmail(); // 拿列表里的邮箱
            if (listName.equals(inputName) && listEmail.equals(inputEmail)) { //用户名和邮箱都匹配
                user.resetPassword();
                System.out.println("新密码已发送到您的邮箱！");
                return;
            }
        }
        System.out.println("用户名和邮箱不匹配！");
    }

    //查看自己的购物历史
    public void viewOrderHistory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 购物历史 =====");
        if (this.orderHistory.isEmpty()) {
            System.out.println("暂无购物记录");
        } else {
            for (int i = 0; i < this.orderHistory.size(); i++) {
                Order order = this.orderHistory.get(i);
                System.out.println(order);
            }
            System.out.print("输入 1 返回菜单：");
            sc.nextLine();
        }
    }

    //查看所有商品
    public void viewProducts(List<Product> products) {
        System.out.println("===== 商品列表 =====");
        if (products.isEmpty()) {
            System.out.println("暂无商品");
        } else {
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.println("编号：" + p.getId() + " | 名称：" + p.getName() + " | 零售价：" + p.getPrice() + " | 库存：" + p.getCount());
            }
        }
        System.out.print("输入 1 返回菜单：");
        Scanner sc = new Scanner(System.in); // 造一个扫描器
        sc.nextLine(); //读一次回车
    }

    // 添加商品到购物车
    public void addToCart(List<Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 添加商品到购物车 =====");
        System.out.print("请输入商品编号：");
        String productId = sc.nextLine();
        Product product = null;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i); // 按编号找商品
            String pid = p.getId(); //获取商品编号
            if (pid.equals(productId)) { //和输入的编号比较
                product = p;
                break;
            }
        }
        if (product == null) {
            System.out.println("商品不存在！");
        } else {
            System.out.print("请输入数量：");
            int quantity;
            try { // 输入的不是整数就取消，防止程序崩溃
                quantity = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("数量必须是整数，本次操作已取消");
                return;
            }
            if (product.getCount() < quantity) {
                System.out.println("库存不足！当前库存：" + product.getCount());
            } else {
                this.cart.addItem(product, quantity);
            }
        }
    }

    //从购物车移除商品
    public void removeFromCart() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 从购物车移除商品 =====");
        Map<Product, Integer> items = this.cart.getItems(); //拿购物车
        if (items.isEmpty()) { // 购物车空不空
            System.out.println("购物车是空的");
        } else {
            this.displayCart();
            System.out.print("请输入要移除的商品编号：");
            String productId = sc.nextLine();
            Product product = this.cart.findProduct(productId); // 按编号在购物车里找商品
            if (product == null) {
                System.out.println("购物车中没有该商品！");
            } else {
                System.out.println("警告：确认移除 " + product.getName() + " 吗？");
                System.out.print("确认移除请输 YES，取消请按回车：");
                String confirm = sc.nextLine();
                if (confirm.equals("YES")) {
                    this.cart.removeItem(product);
                } else {
                    System.out.println("已取消移除");
                }
            }
        }
    }

    //修改购物车里某商品的数量
    public void updateCart() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 修改购物车商品数量 =====");
        Map<Product, Integer> items = this.cart.getItems(); //拿购物车
        if (items.isEmpty()) { //购物车空不空
            System.out.println("购物车是空的");
        } else {
            this.displayCart();
            System.out.print("请输入要修改的商品编号：");
            String productId = sc.nextLine();
            Product product = this.cart.findProduct(productId); // 按编号在购物车里找商品
            if (product == null) {
                System.out.println("购物车中没有该商品！");
            } else {
                System.out.print("请输入新的数量：");
                int newQuantity;
                try {
                    newQuantity = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("数量必须是整数，本次操作已取消");
                    return;
                }
                this.cart.updateQuantity(product, newQuantity);
            }
        }
    }

    //结账选支付方式 → 扣库存 → 生成订单 → 更新等级
    public void checkout() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 结账 =====");
        Map<Product, Integer> items = this.cart.getItems(); // 获取购物车
        if (items.isEmpty()) { // 购物车空不空
            System.out.println("购物车是空的，无法结账");
        } else {
            this.displayCart();
            double total = this.cart.getTotalPrice();
            System.out.println("总计：" + total);
            System.out.println("请选择支付方式：");
            System.out.println("1. 支付宝");
            System.out.println("2. 微信");
            System.out.println("3. 银行卡");
            System.out.print("请选择：");
            String choice = sc.nextLine();
            //根据选择创建对应支付对象，再统一调用 pay()，新增支付方式只需加一个实现类
            PaymentStrategy payStrategy;
            String payMethodName;
            switch (choice) {
                case "1":
                    payStrategy = new Alipay();
                    payMethodName = "支付宝";
                    break;
                case "2":
                    payStrategy = new WeChatPay();
                    payMethodName = "微信";
                    break;
                case "3":
                    payStrategy = new BankCardPay();
                    payMethodName = "银行卡";
                    break;
                default:
                    System.out.println("无效选择！");
                    return;
            }
            payStrategy.pay(total); // 多态：同一个 pay() 方法，不同对象执行不同支付逻辑
            this.cart.reduceStock(); // 扣减库存
            // 生成订单并记录
            Order order = new Order(items, total, payMethodName); // 用提前获取的购物车
            this.orderHistory.add(order);
            this.totalSpent += total;
            this.updateLevel();
            items.clear(); // 清空购物车
            System.out.println("结账成功！订单已生成");
            System.out.println("订单号：" + order.getOrderId());
        }
    }

    // 列出商品和数量，算出总价
    public void viewCart() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 购物车 =====");
        Map<Product, Integer> items = this.cart.getItems(); // 获取购物车
        if (items.isEmpty()) { // 检查购物车空不空
            System.out.println("购物车是空的");
        } else {
            this.displayCart(); // 打印购物车条目
            System.out.println("总计：" + this.cart.getTotalPrice());
            System.out.print("输入 1 返回菜单：");
            sc.nextLine();
        }
    }

    //打印购物车明细，只在本类内部调用
    private void displayCart() {
        System.out.println("===== 购物车 =====");
        this.cart.showItems(); // 购物车自己打印条目
        System.out.println("================");
    }

    //根据累计消费自动更新会员等级：1万以上金牌，5千以上银牌，否则铜牌
    private void updateLevel() {
        if (this.totalSpent >= 10000.0) {
            this.level = "金牌顾客";
        } else if (this.totalSpent >= 5000.0) {
            this.level = "银牌顾客";
        } else {
            this.level = "铜牌顾客";
        }
    }
    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getTotalSpent() {
        return this.totalSpent;
    }


    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    // toString 重写--把顾客变成一行文字，打印时显示全部信息
    @Override
    public String toString() {
        return "用户ID：" + this.getUserId() + " | 用户名：" + this.getUsername() + " | 邮箱：" + this.getEmail() + " | 手机：" + this.getPhone() + " | 注册时间：" + this.getRegisterTime() + " | 等级：" + this.level + " | 累计消费：" + this.totalSpent + " | 订单数：" + this.orderHistory.size();
    }
}
