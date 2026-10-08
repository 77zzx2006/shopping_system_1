import java.util.List;
import java.util.Scanner;

public class Admin extends Abstract {

    public Admin(String userId, String username, String password, String email) {
        super(userId, username, password, email);
    }

    // 抽象方法实现--继承 AbstractUser 后必须实现重置密码手动输入新密码（最少8位）
    public void resetPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入新密码：");
        String newPassword = sc.nextLine();
        while (newPassword.length() < 8) {
            System.out.print("密码长度不能少于8个字符，请重新输入：");
            newPassword = sc.nextLine();
        }
        this.setPassword(newPassword);
        System.out.println("密码修改成功！");
    }

    // 修改自己的密码：先验证旧密码
    public void changePassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 修改密码 =====");
        System.out.print("请输入旧密码：");
        String oldPassword = sc.nextLine();
        String pwd = this.getPassword(); // 第1步：拿当前密码
        if (!pwd.equals(oldPassword)) { // 第2步：和输入的旧密码比较
            System.out.println("旧密码错误！");
        } else {
            System.out.print("请输入新密码（不少于8个字符）：");
            String newPassword = sc.nextLine();
            while (newPassword.length() < 8) {
                System.out.print("密码长度不能少于8个字符，请重新输入：");
                newPassword = sc.nextLine();
            }
            this.setPassword(newPassword);
            System.out.println("密码修改成功！");
        }
    }

    // 把指定顾客的密码重置为12345678，按顾客ID查找
    public void resetCustomerPassword(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 重置顾客密码 =====");
        System.out.print("请输入要重置密码的顾客ID：");
        String customerId = sc.nextLine();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String id = user.getUserId(); // 第1步：拿顾客ID
            if (id.equals(customerId)) { // 第2步：和输入的ID比较
                String newPassword = "12345678";
                user.setPassword(PasswordUtil.encrypt(newPassword)); // 版本3：存进文件的是密文
                System.out.println("顾客 " + user.getUsername() + " 的密码已重置为：" + newPassword);
                return;
            }
        }
        System.out.println("未找到该顾客！");
    }

    // 列出所有顾客的信息
    public void listAllCustomers(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 所有顾客信息 =====");
        if (users.isEmpty()) {
            System.out.println("暂无顾客");
        } else {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                System.out.println(user);
            }
            while (true) {
                System.out.print("输入 1 返回菜单：");
                String input = sc.nextLine();
                if (input.equals("1")) {
                    break;
                }
                System.out.println("输入无效，请重新输入！");
            }
        }
    }

    // 删除顾客
    public void deleteCustomer(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 删除顾客 =====");
        System.out.print("请输入要删除的顾客ID：");
        String customerId = sc.nextLine();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String id = user.getUserId(); // 第1步：拿顾客ID
            if (id.equals(customerId)) { // 第2步：和输入的ID比较
                System.out.println("警告：删除后将无法恢复，确认删除 " + user.getUsername() + " 吗？");
                System.out.print("确认删除请输 YES，取消请按回车：");
                String confirm = sc.nextLine();
                if (confirm.equals("YES")) {
                    users.remove(user);
                    System.out.println("删除成功！");
                } else {
                    System.out.println("已取消删除");
                }
                return;
            }
        }
        System.out.println("未找到该顾客！");
    }

    //查询顾客
    public void searchCustomer(List<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 查询顾客 =====");
        System.out.println("1. 按顾客ID查询");
        System.out.println("2. 按用户名查询");
        System.out.println("3. 查询所有顾客");
        System.out.print("请选择查询方式：");
        switch (sc.nextLine()) {
            case "1":
                System.out.print("请输入顾客ID：");
                String customerId = sc.nextLine();
                for (int i = 0; i < users.size(); i++) {
                    User user = users.get(i);
                    String id = user.getUserId(); //拿顾客ID
            if (id.equals(customerId)) { //和输入的ID比较
                        System.out.println(user);
                        while (true) {
                            System.out.print("输入 1 返回菜单：");
                            String input = sc.nextLine();
                            if (input.equals("1")) {
                                break;
                            }
                            System.out.println("输入无效，请重新输入！");
                        }
                        return;
                    }
                }
                System.out.println("未找到该顾客！");
                break;
            case "2":
                System.out.print("请输入用户名：");
                String username = sc.nextLine();
                for (int j = 0; j < users.size(); j++) {
                    User user = users.get(j);
                    String uname = user.getUsername(); // 第1步：拿管理员用户名
                    if (uname.equals(username)) { // 第2步：和输入的用户名比较
                        System.out.println(user);
                        while (true) {
                            System.out.print("输入 1 返回菜单：");
                            String input = sc.nextLine();
                            if (input.equals("1")) {
                                break;
                            }
                            System.out.println("输入无效，请重新输入！");
                        }
                        return;
                    }
                }
                System.out.println("未找到该顾客！");
                break;
            case "3":
                this.listAllCustomers(users);
                break;
            default:
                System.out.println("无效选择！");
        }
        while (true) {
            System.out.print("输入 1 返回菜单：");
            String input = sc.nextLine();
            if (input.equals("1")) {
                break;
            }
            System.out.println("输入无效，请重新输入！");
        }
    }

    // 列出所有商品的信息
    public void listAllProducts(List<Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 所有商品信息 =====");
        if (products.isEmpty()) {
            System.out.println("暂无商品");
        } else {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println(product);
            }
            while (true) {
                System.out.print("输入 1 返回菜单：");
                String input = sc.nextLine();
                if (input.equals("1")) {
                    break;
                }
                System.out.println("输入无效，请重新输入！");
            }
        }
    }

    //添加商品：输入商品全部信息，存入商品列表
    public void addProduct(List<Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 添加商品 =====");
        System.out.print("请输入商品编号：");
        String id = sc.nextLine();
        System.out.print("请输入商品名称：");
        String name = sc.nextLine();
        System.out.print("请输入生产厂家：");
        String producter = sc.nextLine();
        System.out.print("请输入生产日期：");
        String date = sc.nextLine();
        System.out.print("请输入型号：");
        String model = sc.nextLine();
        System.out.print("请输入进货价：");
        double cost = 0;
        while (true) { // 不是数字就一直重新输
            try {
                cost = Double.parseDouble(sc.nextLine()); // 转成小数
                break;
            } catch (NumberFormatException e) { // 不是数字
                System.out.print("进货价必须是数字，请重新输入：");
            }
        }
        System.out.print("请输入零售价格：");
        double price = 0;
        while (true) { // 不是数字就一直重新输
            try {
                price = Double.parseDouble(sc.nextLine()); // 转成小数
                break;
            } catch (NumberFormatException e) { // 不是数字
                System.out.print("零售价格必须是数字，请重新输入：");
            }
        }
        System.out.print("请输入数量：");
        int count = 0;
        while (true) { // 不是整数就一直重新输
            try {
                count = Integer.parseInt(sc.nextLine()); // 转成整数
                break;
            } catch (NumberFormatException e) { // 不是整数
                System.out.print("数量必须是整数，请重新输入：");
            }
        }
        Product newProduct = new Product(id, name, producter, date, model, cost, price, count); //造商品
        products.add(newProduct); //加进商品列表
        System.out.println("商品添加成功！");
    }

    // 修改商品
    public void updateProduct(List<Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 修改商品 =====");
        System.out.print("请输入要修改的商品编号：");
        String productId = sc.nextLine();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String pid = product.getId(); // 拿商品编号
            if (pid.equals(productId)) { // 和输入的编号比较
                System.out.println("当前商品信息：" + product);
                System.out.println("请选择要修改的字段：");
                System.out.println("1. 商品名称  2. 生产厂家  3. 生产日期  4. 型号");
                System.out.println("5. 进货价    6. 零售价格  7. 数量");
                System.out.print("请选择：");
                switch (sc.nextLine()) {
                    case "1":
                        System.out.print("请输入新名称：");
                        product.setName(sc.nextLine());
                        break;
                    case "2":
                        System.out.print("请输入新厂家：");
                        product.setProducter(sc.nextLine());
                        break;
                    case "3":
                        System.out.print("请输入新日期：");
                        product.setDate(sc.nextLine());
                        break;
                    case "4":
                        System.out.print("请输入新型号：");
                        product.setModel(sc.nextLine());
                        break;
                    case "5":
                        System.out.print("请输入新进价：");
                        while (true) { // 不是数字就一直重新输
                            try {
                                product.setCost(Double.parseDouble(sc.nextLine())); // 转成小数
                                break;
                            } catch (NumberFormatException e) { // 不是数字
                                System.out.print("进价必须是数字，请重新输入：");
                            }
                        }
                        break;
                    case "6":
                        System.out.print("请输入新零售价：");
                        while (true) { // 不是数字就一直重新输
                            try {
                                product.setPrice(Double.parseDouble(sc.nextLine())); // 转成小数
                                break;
                            } catch (NumberFormatException e) { // 不是数字
                                System.out.print("零售价必须是数字，请重新输入：");
                            }
                        }
                        break;
                    case "7":
                        System.out.print("请输入新数量：");
                        while (true) { // 不是整数就一直重新输
                            try {
                                product.setCount(Integer.parseInt(sc.nextLine())); // 转成整数
                                break;
                            } catch (NumberFormatException e) { // 不是整数
                                System.out.print("数量必须是整数，请重新输入：");
                            }
                        }
                        break;
                    default:
                        System.out.println("无效选择！");
                        return;
                }
                System.out.println("修改成功！");
                return;
            }
        }
        System.out.println("未找到该商品！");
    }

    // 删除商品
    public void deleteProduct(List<Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 删除商品 =====");
        System.out.print("请输入要删除的商品编号：");
        String productId = sc.nextLine();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String pid = product.getId(); //拿商品编号
            if (pid.equals(productId)) { //和输入的编号比较
                System.out.println("警告：删除后无法恢复！确认删除 " + product.getName() + " 吗？");
                System.out.print("确认删除请输 YES，取消请按回车：");
                String confirm = sc.nextLine();
                if (confirm.equals("YES")) {
                    products.remove(product);
                    System.out.println("删除成功！");
                } else {
                    System.out.println("已取消删除");
                }
                return;
            }
        }
        System.out.println("未找到该商品！");
    }

    // 查询商品
    public void searchProduct(List<Product> products) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== 查询商品 =====");
        System.out.print("请输入商品名称：");
        String name = sc.nextLine();
        boolean found = false;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String pname = product.getName();
            if (pname.contains(name)) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("未找到匹配的商品");
        }
        while (true) {
            System.out.print("输入 1 返回菜单：");
            String input = sc.nextLine();
            if (input.equals("1")) {
                break;
            }
            System.out.println("输入无效，请重新输入！");
        }
    }

}
