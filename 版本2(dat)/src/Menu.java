import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//负责主菜单循环、登录注册和各功能菜单的跳转
public class Menu {
    private Scanner scanner;
    private List<User> users;
    private List<Admin> admins;
    private List<Product> products;
    private Admin admin;
    String password;
    String username;
    String newpassword;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.users = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.products = new ArrayList<>();
    }
    public void start() {
        this.initData();
        while (true) {
            this.saveData();
            this.showMainMenu();
            switch (this.scanner.nextLine()) {
                case "1":
                    this.adminLogin();
                    break;
                case "2":
                    this.customerLogin();
                    break;
                case "3":
                    this.customerRegister();
                    break;
                case "4":
                    this.saveData();
                    System.out.println("感谢使用！再见！");
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    // 把商品和用户数据保存到文本文件，防止退出后丢失
    private void saveData() {
        DataStore.saveProducts(this.products);
        DataStore.saveUsers(this.users);
    }

    // 主菜单
    private void showMainMenu() {
        System.out.println("\n===== 购物管理系统 =====");
        System.out.println("1. 管理员登录");
        System.out.println("2. 顾客登录");
        System.out.println("3. 顾客注册");
        System.out.println("4. 退出");
        System.out.print("请选择：");
    }

    // 管理员菜单
    private void showAdminMenu() {
        System.out.println("\n===== 管理员菜单 =====");
        System.out.println("1. 修改自身密码");
        System.out.println("2. 重置顾客密码");
        System.out.println("3. 列出所有顾客");
        System.out.println("4. 删除顾客");
        System.out.println("5. 查询顾客");
        System.out.println("6. 列出所有商品");
        System.out.println("7. 添加商品");
        System.out.println("8. 修改商品");
        System.out.println("9. 删除商品");
        System.out.println("10. 查询商品");
        System.out.println("11. 退出登录");
        System.out.print("请选择：");
    }

    // 顾客菜单
    private void showCustomerMenu() {
        System.out.println("\n===== 顾客菜单 =====");
        System.out.println("1. 查看商品");
        System.out.println("2. 修改密码");
        System.out.println("3. 忘记密码（重置密码）");
        System.out.println("4. 查看购物车");
        System.out.println("5. 添加商品到购物车");
        System.out.println("6. 从购物车移除商品");
        System.out.println("7. 修改购物车商品数量");
        System.out.println("8. 结账");
        System.out.println("9. 查看购物历史");
        System.out.println("10. 退出登录");
        System.out.print("请选择：");
    }

    // 初始化数据：优先从文件加载，没有数据时才用预置数据
    private void initData() {
        this.products = DataStore.loadProducts();
        this.users = DataStore.loadUsers();
        if (this.products.isEmpty()) {
            Product product1 = new Product("P001", "华为手机", "华为公司", "2024-01-01", "Mate 60 Pro", 5000.0, 6999.0, 100); // 第1步：造预置商品
            this.products.add(product1); // 第2步：加进商品列表
            Product product2 = new Product("P002", "联想电脑", "联想公司", "2024-02-01", "ThinkPad X1", 8000.0, 9999.0, 50); // 第1步：造预置商品
            this.products.add(product2); // 第2步：加进商品列表
            Product product3 = new Product("P003", "苹果耳机", "苹果公司", "2024-03-01", "AirPods Pro", 1500.0, 1999.0, 200); // 第1步：造预置商品
            this.products.add(product3); // 第2步：加进商品列表
            Product product4 = new Product("P004", "小米手表", "小米公司", "2024-04-01", "Watch S1", 800.0, 999.0, 150); // 第1步：造预置商品
            this.products.add(product4); // 第2步：加进商品列表
        }
        Admin defaultAdmin = new Admin("A001", "admin", "ynuinfo#777", "admin@shop.com"); // 第1步：造一个默认管理员
        this.admins.add(defaultAdmin); // 加进管理员列表,默认管理员
        System.out.println("系统初始化完成！");
    }

    // 管理员登录：默认账号admin/ynuinfo#777，首次登录强制改密码
    private void adminLogin() {
        System.out.println("===== 管理员登录 =====");
        System.out.print("请输入用户名：");
        this.username = this.scanner.nextLine();
        System.out.print("请输入密码：");
        this.password = this.scanner.nextLine();
        if (this.username.equals("admin") && this.password.equals("ynuinfo#777")) {
            this.admin = this.admins.get(0); // 只有一个默认管理员，直接登录
            System.out.println("登录成功！欢迎管理员 " + this.username);
            System.out.println("请修改初始密码");
            this.newpassword = this.scanner.nextLine();
            this.admin.setPassword(this.newpassword);
            System.out.println("修改密码成功");
            this.adminMenu();
        } else {
            System.out.println("用户名或密码错误！");
        }
    }

    // 顾客登录入口
    private void customerLogin() {
        User user = User.login(this.users);
        if (user != null) {
            this.customerMenu(user);
        }
    }

    // 顾客注册入口
    private void customerRegister() {
        User.register(this.users);
    }

    // 管理员功能菜单循环
    private void adminMenu() {
        while (true) {
            this.showAdminMenu();
            switch (this.scanner.nextLine()) {
                case "1":
                    this.admin.changePassword();
                    break;
                case "2":
                    this.admin.resetCustomerPassword(this.users);
                    break;
                case "3":
                    this.admin.listAllCustomers(this.users);
                    break;
                case "4":
                    this.admin.deleteCustomer(this.users);
                    break;
                case "5":
                    this.admin.searchCustomer(this.users);
                    break;
                case "6":
                    this.admin.listAllProducts(this.products);
                    break;
                case "7":
                    this.admin.addProduct(this.products);
                    break;
                case "8":
                    this.admin.updateProduct(this.products);
                    break;
                case "9":
                    this.admin.deleteProduct(this.products);
                    break;
                case "10":
                    this.admin.searchProduct(this.products);
                    break;
                case "11":
                    System.out.println("已退出登录！");
                    this.admin = null;
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    // 顾客功能菜单循环
    private void customerMenu(User user) {
        while (true) {
            this.showCustomerMenu();
            switch (this.scanner.nextLine()) {
                case "1":
                    user.viewProducts(this.products);
                    break;
                case "2":
                    user.changePassword();
                    break;
                case "3":
                    User.forgotPassword(this.users);
                    break;
                case "4":
                    user.viewCart();
                    break;
                case "5":
                    user.addToCart(this.products);
                    break;
                case "6":
                    user.removeFromCart();
                    break;
                case "7":
                    user.updateCart();
                    break;
                case "8":
                    user.checkout();
                    break;
                case "9":
                    user.viewOrderHistory();
                    break;
                case "10":
                    System.out.println("已退出登录！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
            this.saveData(); // 顾客菜单每次操作完都保存到数据库，防止数据丢失
        }
    }
}
