import java.sql.Connection;                          // 数据库连接类
import java.sql.DriverManager;                        // 驱动管理：负责连接数据库
import java.sql.ResultSet;                            // 查询结果集：装查出来的数据
import java.sql.Statement;                            // 执行SQL语句的工具
import java.util.ArrayList;                           // 动态数组：存商品/用户列表
import java.util.List;                                // 列表接口

// 版本4：SQLite 数据库存储
// 商品存 product 表，用户存 user 表，都放在 shop.db 文件里
// 保存 = INSERT 插入；读取 = SELECT 查询；用 DBeaver 打开 shop.db 就能看到数据
public class DataStore {

    // 连接数据库（文件不存在会自动创建）
    private static Connection getConn() throws Exception {
        return DriverManager.getConnection("jdbc:sqlite:shop.db");
    }

    // 建表：程序启动时调用，第一次运行自动建好两张表
    public static void createTables() {
        try {
            Connection conn = getConn();              // 第1步：连上数据库
            Statement st = conn.createStatement();    // 第2步：造一个执行SQL的工具
            st.executeUpdate("CREATE TABLE IF NOT EXISTS product (" // 第3步：建商品表（不存在才建）
                    + "id TEXT, name TEXT, producter TEXT, date TEXT, model TEXT,"
                    + "cost REAL, price REAL, count INTEGER)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS user ("    // 建用户表（不存在才建）
                    + "userId TEXT, username TEXT, password TEXT, email TEXT,"
                    + "phone TEXT, registerTime TEXT, level TEXT, totalSpent REAL)");
            conn.close();                             // 用完关闭连接
        } catch (Exception e) {
            System.out.println("创建数据表失败");
        }
    }

    // 把商品列表全部存进数据库
    public static void saveProducts(List<Product> products) {
        try {
            Connection conn = getConn();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM product");  // 先清空旧数据，再重新插入，保证和内存一致
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                st.executeUpdate("INSERT INTO product VALUES ('" + p.getId() + "','" + p.getName() + "','"
                        + p.getProducter() + "','" + p.getDate() + "','" + p.getModel() + "',"
                        + p.getCost() + "," + p.getPrice() + "," + p.getCount() + ")");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("保存商品失败");
        }
    }

    // 从数据库读回商品列表
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();   // 空列表，装读回来的商品
        try {
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM product"); // 查出所有商品
            while (rs.next()) {                       // 每行就是一个商品
                Product p = new Product(rs.getString("id"), rs.getString("name"),
                        rs.getString("producter"), rs.getString("date"), rs.getString("model"),
                        rs.getDouble("cost"), rs.getDouble("price"), rs.getInt("count"));
                products.add(p);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("读取商品失败");
        }
        return products;
    }

    // 把用户列表全部存进数据库
    public static void saveUsers(List<User> users) {
        try {
            Connection conn = getConn();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM user");     // 先清空旧数据
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                st.executeUpdate("INSERT INTO user VALUES ('" + u.getUserId() + "','" + u.getUsername() + "','"
                        + u.getPassword() + "','" + u.getEmail() + "','" + u.getPhone() + "','"
                        + u.getRegisterTime() + "','" + u.getLevel() + "'," + u.getTotalSpent() + ")");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("保存用户失败");
        }
    }

    // 从数据库读回用户列表
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = getConn();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user"); // 查出所有用户
            while (rs.next()) {                       // 每行就是一个用户
                User u = new User(rs.getString("userId"), rs.getString("username"),
                        rs.getString("password"), rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRegisterTime(rs.getString("registerTime"));
                u.setLevel(rs.getString("level"));
                u.setTotalSpent(rs.getDouble("totalSpent"));
                users.add(u);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("读取用户失败");
        }
        return users;
    }
}
