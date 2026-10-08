import java.io.File;                                  // 文件类：判断文件存不存在
import java.io.FileInputStream;                       // 读文件的工具（读xlsx文件用）
import java.io.FileOutputStream;                      // 写文件的工具（写xlsx文件用）
import java.util.ArrayList;                           // 动态数组：存商品/用户列表
import java.util.List;                                // 列表接口
import org.apache.poi.ss.usermodel.Row;               // Excel行对象：一行就是一条数据
import org.apache.poi.xssf.usermodel.XSSFSheet;       // Excel工作表：一个表就是一个数据表
import org.apache.poi.xssf.usermodel.XSSFWorkbook;    // Excel工作簿：整个xlsx文件

// 版本3：Excel 表格存储（用POI库）
// 商品存 products.xlsx 的"商品"表，用户存 users.xlsx 的"用户"表
// 保存 = 一行一条数据写进表；读取 = 一行一行读回来
// 好处：用 Excel 打开就能直接看到数据，比 txt/dat 直观
public class DataStore {

    // 打开或创建Excel文件：文件不存在就新建一个空工作簿
    private static XSSFWorkbook open() throws Exception {
        File f = new File("products.xlsx");                   // 创建文件对象指向products.xlsx
        if (f.exists()) {                                     // 文件存在就打开它
            FileInputStream fis = new FileInputStream(f);     // 第1步：打开文件
            return new XSSFWorkbook(fis);                     // 第2步：把文件读成工作簿对象
        } else {                                              // 文件不存在就新建
            return new XSSFWorkbook();                        // 新建一个空工作簿
        }
    }

    // 把商品列表存进Excel的"商品"表
    public static void saveProducts(List<Product> products) {
        try {
            XSSFWorkbook wb = open();                         // 第1步：打开或创建工作簿
            if (wb.getSheet("商品") == null) {                 // 第2步：没有"商品"表就新建一个
                wb.createSheet("商品");
            }
            XSSFSheet sheet = wb.getSheet("商品");             // 第3步：拿到"商品"表
            while (sheet.getLastRowNum() >= 0) {              // 第4步：先删掉表里的旧行（从最后一行往前删）
                sheet.removeRow(sheet.getRow(sheet.getLastRowNum()));
            }
            for (int i = 0; i < products.size(); i++) {       // 第5步：一行一个商品写进表
                Product p = products.get(i);                  // 拿出第i个商品
                Row row = sheet.createRow(i);                 // 创建第i行
                row.createCell(0).setCellValue(p.getId());    // 第0列：商品编号
                row.createCell(1).setCellValue(p.getName());  // 第1列：商品名称
                row.createCell(2).setCellValue(p.getProducter()); // 第2列：生产厂家
                row.createCell(3).setCellValue(p.getDate());  // 第3列：生产日期
                row.createCell(4).setCellValue(p.getModel()); // 第4列：型号
                row.createCell(5).setCellValue(p.getCost());  // 第5列：进价
                row.createCell(6).setCellValue(p.getPrice()); // 第6列：售价
                row.createCell(7).setCellValue(p.getCount()); // 第7列：库存
            }
            FileOutputStream out = new FileOutputStream("products.xlsx"); // 第6步：打开要写入的文件
            wb.write(out);                                    // 第7步：把工作簿写进文件
            wb.close();                                       // 第8步：关闭工作簿
            out.close();                                      // 第9步：关闭文件
        } catch (Exception e) {
            System.out.println("保存商品失败");
        }
    }

    // 从Excel的"商品"表读回商品列表
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();           // 空列表，装读回来的商品
        try {
            File f = new File("products.xlsx");               // 创建文件对象指向products.xlsx
            if (f.exists()) {                                 // 文件存在才读
                FileInputStream fis = new FileInputStream(f); // 第1步：打开文件
                XSSFWorkbook wb = new XSSFWorkbook(fis);      // 第2步：把文件读成工作簿对象
                XSSFSheet sheet = wb.getSheet("商品");         // 第3步：拿到"商品"表
                if (sheet != null) {                          // 表存在才读
                    for (int i = 0; i <= sheet.getLastRowNum(); i++) { // 第4步：一行一个商品读回来
                        Row row = sheet.getRow(i);            // 拿出第i行
                        if (row == null) {                    // 空行跳过
                            continue;
                        }
                        Product p = new Product(              // 第5步：按列读出属性，造商品对象
                                row.getCell(0).getStringCellValue(),  // 第0列：商品编号
                                row.getCell(1).getStringCellValue(),  // 第1列：商品名称
                                row.getCell(2).getStringCellValue(),  // 第2列：生产厂家
                                row.getCell(3).getStringCellValue(),  // 第3列：生产日期
                                row.getCell(4).getStringCellValue(),  // 第4列：型号
                                row.getCell(5).getNumericCellValue(), // 第5列：进价（数字）
                                row.getCell(6).getNumericCellValue(), // 第6列：售价（数字）
                                (int) row.getCell(7).getNumericCellValue()); // 第7列：库存（数字）
                        products.add(p);                      // 加进商品列表
                    }
                }
                wb.close();                                   // 第6步：关闭工作簿
            }
        } catch (Exception e) {
            System.out.println("读取商品失败");
        }
        return products;
    }

    // 把用户列表存进Excel的"用户"表
    public static void saveUsers(List<User> users) {
        try {
            XSSFWorkbook wb = open();                         // 第1步：打开或创建工作簿
            if (wb.getSheet("用户") == null) {                 // 第2步：没有"用户"表就新建一个
                wb.createSheet("用户");
            }
            XSSFSheet sheet = wb.getSheet("用户");             // 第3步：拿到"用户"表
            while (sheet.getLastRowNum() >= 0) {              // 第4步：先删掉表里的旧行
                sheet.removeRow(sheet.getRow(sheet.getLastRowNum()));
            }
            for (int i = 0; i < users.size(); i++) {          // 第5步：一行一个用户写进表
                User u = users.get(i);                        // 拿出第i个用户
                Row row = sheet.createRow(i);                 // 创建第i行
                row.createCell(0).setCellValue(u.getUserId());     // 第0列：用户ID
                row.createCell(1).setCellValue(u.getUsername());   // 第1列：用户名
                row.createCell(2).setCellValue(u.getPassword());   // 第2列：密码（存的是密文）
                row.createCell(3).setCellValue(u.getEmail());      // 第3列：邮箱
                row.createCell(4).setCellValue(u.getPhone());      // 第4列：手机号
                row.createCell(5).setCellValue(u.getRegisterTime()); // 第5列：注册时间
                row.createCell(6).setCellValue(u.getLevel());      // 第6列：顾客等级
                row.createCell(7).setCellValue(u.getTotalSpent()); // 第7列：累计消费
            }
            FileOutputStream out = new FileOutputStream("users.xlsx"); // 第6步：打开要写入的文件
            wb.write(out);                                    // 第7步：把工作簿写进文件
            wb.close();                                       // 第8步：关闭工作簿
            out.close();                                      // 第9步：关闭文件
        } catch (Exception e) {
            System.out.println("保存用户失败");
        }
    }

    // 从Excel的"用户"表读回用户列表
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            File f = new File("users.xlsx");                  // 创建文件对象指向users.xlsx
            if (f.exists()) {                                 // 文件存在才读
                FileInputStream fis = new FileInputStream(f); // 第1步：打开文件
                XSSFWorkbook wb = new XSSFWorkbook(fis);      // 第2步：把文件读成工作簿对象
                XSSFSheet sheet = wb.getSheet("用户");         // 第3步：拿到"用户"表
                if (sheet != null) {                          // 表存在才读
                    for (int i = 0; i <= sheet.getLastRowNum(); i++) { // 第4步：一行一个用户读回来
                        Row row = sheet.getRow(i);            // 拿出第i行
                        if (row == null) {                    // 空行跳过
                            continue;
                        }
                        User u = new User(                    // 第5步：按列读出属性，造用户对象
                                row.getCell(0).getStringCellValue(),  // 第0列：用户ID
                                row.getCell(1).getStringCellValue(),  // 第1列：用户名
                                row.getCell(2).getStringCellValue(),  // 第2列：密码（密文）
                                row.getCell(3).getStringCellValue()); // 第3列：邮箱
                        u.setPhone(row.getCell(4).getStringCellValue());     // 第4列：手机号
                        u.setRegisterTime(row.getCell(5).getStringCellValue()); // 第5列：注册时间
                        u.setLevel(row.getCell(6).getStringCellValue());     // 第6列：顾客等级
                        u.setTotalSpent(row.getCell(7).getNumericCellValue()); // 第7列：累计消费（数字）
                        users.add(u);                         // 加进用户列表
                    }
                }
                wb.close();                                   // 第6步：关闭工作簿
            }
        } catch (Exception e) {
            System.out.println("读取用户失败");
        }
        return users;
    }
}
