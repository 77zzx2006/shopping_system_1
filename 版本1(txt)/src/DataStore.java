import java.io.File;                                  // 文件类：判断文件存不存在
import java.io.FileWriter;                            // 写文本文件 版本1
import java.util.ArrayList;                           // 动态数组：存商品/用户列表
import java.util.List;                                // 列表接口
import java.util.Scanner;                             // 按行读文本文件 版本1

// 版本1：txt 文本存储
// 商品存 products.txt，用户存 users.txt，一行一条，属性用 | 隔开
// 保存 = FileWriter 逐行写；读取 = Scanner 按 | 拆行
public class DataStore {

    public static void saveProducts(List<Product> products) {
        try {
            FileWriter fw = new FileWriter("products.txt");       // 创建文本文件
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                fw.write(p.getId() + "|" + p.getName() + "|" + p.getProducter() + "|"
                        + p.getDate() + "|" + p.getModel() + "|"
                        + p.getCost() + "|" + p.getPrice() + "|" + p.getCount() + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("保存商品失败");
        }
    }

    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();               // 空列表，装读回来的商品
        try {
            File f = new File("products.txt");                    // 创建文件对象指向products.txt
            if (f.exists()) {
                Scanner sc = new Scanner(f);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();                  // 读出一行
                    String[] s = line.split("\\|");               // 按 | 拆成8段
                    Product p = new Product(s[0], s[1], s[2], s[3], s[4],
                            Double.parseDouble(s[5]), Double.parseDouble(s[6]), Integer.parseInt(s[7]));  //转换成数字
                    products.add(p);
                }
                sc.close();
            }
        } catch (Exception e) {
            System.out.println("读取商品失败");
        }
        return products;
    }

    public static void saveUsers(List<User> users) {
        try {
            FileWriter fw = new FileWriter("users.txt");          // 写文本工具
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                fw.write(u.getUserId() + "|" + u.getUsername() + "|" + u.getPassword() + "|"
                        + u.getEmail() + "|" + u.getPhone() + "|" + u.getRegisterTime() + "|"
                        + u.getLevel() + "|" + u.getTotalSpent() + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("保存用户失败");
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            File f = new File("users.txt");                       // 指向users.txt
            if (f.exists()) {
                Scanner sc = new Scanner(f);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] s = line.split("\\|");
                    User u = new User(s[0], s[1], s[2], s[3]);
                    u.setPhone(s[4]);
                    u.setRegisterTime(s[5]);
                    u.setLevel(s[6]);
                    u.setTotalSpent(Double.parseDouble(s[7]));
                    users.add(u);
                }
                sc.close();
            }
        } catch (Exception e) {
            System.out.println("读取用户失败");
        }
        return users;
    }
}
