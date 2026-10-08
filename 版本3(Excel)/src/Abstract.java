import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
// 把顾客和管理员共用的字段、方法放在这里，避免两个类重复写

abstract class Abstract implements Serializable {

    private String userId;       // 用户ID
    private String username;     // 用户名
    private String password;     // 密码
    private String phone;        // 手机号
    private String email;        // 邮箱
    private String registerTime; // 注册时间

    // 抽象方法--重置密码
    public abstract void resetPassword();

    public String getUserId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    // 构造方法--创建用户对象时自动执行
    public Abstract(String userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 第1步：造一个日期格式
        this.registerTime = sdf.format(new Date()); // 第2步：格式化当前时间
    }

    // toString 重写--把对象变成一行文字，打印这个对象时直接显示全部信息
    @Override
    public String toString() {
        return "用户ID：" + this.userId + " | 用户名：" + this.username + " | 邮箱：" + this.email + " | 手机：" + this.phone + " | 注册时间：" + this.registerTime;
    }
}
