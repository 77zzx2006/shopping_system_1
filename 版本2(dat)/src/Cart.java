import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable {
    private Map<Product, Integer> items; // 商品 -> 数量

    public Cart() {
        this.items = new HashMap<>();
    }
//添加购物车
    public void addItem(Product product, int quantity) {
        if (product != null && quantity > 0) {
            if (this.items.containsKey(product)) {
                int oldQuantity = this.items.get(product);
                this.items.put(product, oldQuantity + quantity);
            } else {
                this.items.put(product, quantity);
            }
            System.out.println("已添加 " + product.getName() + " x" + quantity);
        } else {
            System.out.println("商品不能为空，数量必须大于0");
        }
    }
//移除购物车
    public boolean removeItem(Product product) {
        if (product != null && this.items.containsKey(product)) {
            this.items.remove(product);
            System.out.println("已从购物车移除：" + product.getName());
            return true;
        } else {
            System.out.println("购物车中没有该商品");
            return false;
        }
    }
//修改购物车数量
    public void updateQuantity(Product product, int quantity) {
        if (product != null && this.items.containsKey(product)) {
            if (quantity <= 0) {
                this.removeItem(product);
                System.out.println("数量小于等于0，已从购物车移除");
            } else {
                this.items.put(product, quantity);
                System.out.println("已修改 " + product.getName() + " 数量为 " + quantity);
            }
        } else {
            System.out.println("购物车中没有该商品");
        }
    }
    //计算总价
    public double getTotalPrice() {
        double total = 0.0;
        List<Product> productList = new ArrayList<>(items.keySet()); // 购物车里所有商品装进列表
        for (int i = 0; i < productList.size(); i++) { //
            Product product = productList.get(i); // 拿第i个商品
            int quantity = items.get(product); // 拿这个商品的数量
            total += product.getPrice() * quantity;
        }
        return total;
    }

    // 按商品编号在购物车里找商品，找不到返回 null
    public Product findProduct(String productId) {
        Product result = null;
        List<Product> productList = new ArrayList<>(items.keySet()); // 购物车里所有商品装进列表
        for (int i = 0; i < productList.size(); i++) { //
            Product entryProduct = productList.get(i); // 拿第i个商品
            String pid = entryProduct.getId(); // 拿编号
            if (pid.equals(productId)) { // 编号对上
                result = entryProduct;
                break;
            }
        }
        return result;
    }

    // 减少库存
    public void reduceStock() {
        List<Product> productList = new ArrayList<>(items.keySet()); // 购物车里所有商品装进列表
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i); // 拿第i个商品
            int quantity = items.get(product); // 拿数量
            product.setCount(product.getCount() - quantity);
        }
    }

    // 打印购物车所有条目
    public void showItems() {
        List<Product> productList = new ArrayList<>(items.keySet()); // 购物车里所有商品装进列表
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i); // 拿第i个商品
            int quantity = items.get(product); // 拿数量
            System.out.println(product.getName() + " x" + quantity + " = " + product.getPrice() * quantity);
        }
    }
//获取购物车
    public Map<Product, Integer> getItems() {
        return this.items;
    }
}
