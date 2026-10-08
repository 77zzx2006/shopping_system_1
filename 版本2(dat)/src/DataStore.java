import java.io.File;                                  // 文件类：判断文件存不存在
import java.io.FileInputStream;                       // 读文件的工具（读dat文件用）
import java.io.FileOutputStream;                      // 写文件的工具（写dat文件用）
import java.io.ObjectInputStream;                     // 读对象的工具：把文件里的对象读回来
import java.io.ObjectOutputStream;                    // 写对象的工具：把对象整体写进文件
import java.util.ArrayList;                           // 动态数组：存商品/用户列表
import java.util.List;                                // 列表接口

// 版本2：dat 对象序列化存储
// 商品存 products.dat，用户存 users.dat，整个列表一次性写进文件
// 保存 = ObjectOutputStream 把对象直接写进文件；读取 = ObjectInputStream 一次读回整个列表
// 好处：不用像版本1那样一行行拆，整个对象原样存，读回来还是对象
// 注意：要能被序列化的类必须实现 Serializable 接口（Product、User、Cart、Order 都已经加了）
public class DataStore {

    // 把商品列表整个写进 products.dat
    public static void saveProducts(List<Product> products) {
        try {
            FileOutputStream fos = new FileOutputStream("products.dat"); // 第1步：打开文件（不存在会自动创建）
            ObjectOutputStream oos = new ObjectOutputStream(fos);        // 第2步：给文件套上"写对象的外套"
            oos.writeObject(products);                                   // 第3步：把整个商品列表一次性写进文件
            oos.close();                                                 // 第4步：写完关闭（自动把数据落盘）
        } catch (Exception e) {
            System.out.println("保存商品失败");
        }
    }

    // 从 products.dat 读回整个商品列表
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();               // 空列表，装读回来的商品
        try {
            File f = new File("products.dat");                    // 创建文件对象指向products.dat
            if (f.exists()) {                                     // 文件存在才读（第一次运行没有文件）
                FileInputStream fis = new FileInputStream(f);     // 第1步：打开文件
                ObjectInputStream ois = new ObjectInputStream(fis); // 第2步：给文件套上"读对象的外套"
                products = (List<Product>) ois.readObject();      // 第3步：一次性读回整个列表，强转成 List<Product>
                ois.close();                                      // 第4步：读完关闭
            }
        } catch (Exception e) {
            System.out.println("读取商品失败");
        }
        return products;
    }

    // 把用户列表整个写进 users.dat
    public static void saveUsers(List<User> users) {
        try {
            FileOutputStream fos = new FileOutputStream("users.dat"); // 第1步：打开文件（不存在会自动创建）
            ObjectOutputStream oos = new ObjectOutputStream(fos);     // 第2步：给文件套上"写对象的外套"
            oos.writeObject(users);                                   // 第3步：把整个用户列表一次性写进文件
            oos.close();                                              // 第4步：写完关闭
        } catch (Exception e) {
            System.out.println("保存用户失败");
        }
    }

    // 从 users.dat 读回整个用户列表
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            File f = new File("users.dat");                   // 创建文件对象指向users.dat
            if (f.exists()) {                                 // 文件存在才读
                FileInputStream fis = new FileInputStream(f); // 第1步：打开文件
                ObjectInputStream ois = new ObjectInputStream(fis); // 第2步：给文件套上"读对象的外套"
                users = (List<User>) ois.readObject();        // 第3步：一次性读回整个列表，强转成 List<User>
                ois.close();                                  // 第4步：读完关闭
            }
        } catch (Exception e) {
            System.out.println("读取用户失败");
        }
        return users;
    }
}
