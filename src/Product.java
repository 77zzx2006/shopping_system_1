import java.io.Serializable;

public class Product implements Serializable{
    private String id;         // 商品编号
    private String name;       // 商品名称
    private String producter;  // 生产厂家
    private String date;       // 生产日期
    private String model;      // 型号
    private double cost;       // 进货价
    private double price;      // 零售价
    private int count;         // 库存数量

    public Product(String id, String name, String producter, String date, String model, double cost, double price, int count) {
        this.id = id;
        this.name = name;
        this.producter = producter;
        this.date = date;
        this.model = model;
        this.cost = cost;
        this.price = price;
        this.count = count;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducter() {
        return this.producter;
    }

    public void setProducter(String producter) {
        this.producter = producter;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "商品编号：" + this.id + " | 名称：" + this.name + " | 厂家：" + this.producter + " | 日期：" + this.date + " | 型号：" + this.model + " | 进价：" + this.cost + " | 零售价：" + this.price + " | 数量：" + this.count;
    }

    // equals 重写 判断两个商品是否"同一个"
    @Override
    public boolean equals(Object other) {
        if (this == other){
            return true;
        }
        if (other == null || getClass() != other.getClass()){
            return false;
        }
        Product product = (Product) other;
        if (this.id != null) {
            return this.id.equals(product.id);
        } else {
            return product.id == null;
        }
    }

    //hashCode 重写 配合 equals：编号相同的商品算出相同的编号，防止出现相同名称
    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        } else {
            return 0;
        }
    }
}
