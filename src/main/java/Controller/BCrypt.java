package controller;



/**
 * Tiện ích mã hóa và kiểm tra mật khẩu dùng thuật toán BCrypt.
 * Dùng khi đăng ký, đổi mật khẩu, và xác thực đăng nhập.
 */
public class BCrypt {

    /**
     * Mã hóa mật khẩu thô thành chuỗi hash BCrypt.
     * Gọi khi tạo tài khoản mới hoặc đổi mật khẩu.
     *
     * @param plainPassword mật khẩu người dùng nhập
     * @return chuỗi hash BCrypt đã mã hóa
     */
    public static String hashPassword(String plainPassword) {
        // generateSalt() tạo salt ngẫu nhiên, đảm bảo mỗi lần hash cho kết quả khác nhau
        return org.mindrot.jbcrypt.BCrypt.hashpw(plainPassword, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    /**
     * Kiểm tra mật khẩu người dùng nhập có khớp với hash trong database không.
     * Gọi khi đăng nhập hoặc xác minh mật khẩu cũ.
     *
     * @param plainPassword  mật khẩu thô người dùng nhập
     * @param hashedPassword hash BCrypt lưu trong database
     * @return true nếu khớp, false nếu sai
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
