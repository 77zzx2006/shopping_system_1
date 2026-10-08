import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// 版本3：密码加密工具——把明文密码变成64位十六进制密文，存进Excel的是密文
// 原理：SHA-256 是不可逆算法，只能由明文算出密文，没法由密文反推明文
public class PasswordUtil {

    // 加密方法：输入明文密码，返回64位十六进制密文
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 创建SHA-256加密器
            byte[] bytes = md.digest(password.getBytes());           //把密码字符串变成字节，算出摘要
            StringBuilder sb = new StringBuilder();                   // 把每个字节转成2位十六进制拼起来
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xff & bytes[i]);   // 一个字节转成十六进制字符串
                if (hex.length() == 1) {                             // 只有1位就前面补0（凑成2位）
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();                                    // 返回64位密文
        } catch (NoSuchAlgorithmException e) {
            return password;//出错返回原密码
        }
    }
}
