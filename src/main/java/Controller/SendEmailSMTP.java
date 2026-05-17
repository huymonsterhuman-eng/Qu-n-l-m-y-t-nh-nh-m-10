package controller;

/**
 * Gửi email khôi phục mật khẩu qua SMTP.
 * sendOTP hiện là stub — in mã ra console để test; thay bằng JavaMail khi cần.
 */
public class SendEmailSMTP {

    // TODO: Cấu hình thông tin Gmail SMTP
    // private static final String SMTP_HOST = "smtp.gmail.com";
    // private static final String SMTP_PORT = "587";
    // private static final String SENDER_EMAIL = "your-email@gmail.com";
    // private static final String SENDER_PASSWORD = "your-app-password";

    /**
     * Gửi mã OTP đến email người dùng để xác nhận khôi phục mật khẩu.
     * Stub: in OTP ra console cho mục đích test.
     *
     * @param toEmail địa chỉ email nhận
     * @param otp     mã OTP 6 chữ số
     */
    public static void sendOTP(String toEmail, String otp) {
        // TODO: Thay bằng gửi email thật qua JavaMail/Jakarta Mail
        System.out.println("[SendEmailSMTP] OTP gửi tới " + toEmail + ": " + otp);
    }

    /**
     * Gửi email chứa mật khẩu mới đến địa chỉ email của người dùng.
     *
     * @param toEmail     địa chỉ email nhận
     * @param newPassword mật khẩu mới (chưa hash)
     */
    public static void sendRecoveryEmail(String toEmail, String newPassword) {
        // TODO: Implement email sending với JavaMail hoặc Jakarta Mail
        System.out.println("[SendEmailSMTP] Mật khẩu mới gửi tới " + toEmail + ": " + newPassword);
    }
}
