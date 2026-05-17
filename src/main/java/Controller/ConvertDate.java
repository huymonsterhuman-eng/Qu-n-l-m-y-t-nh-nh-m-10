package controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tiện ích chuyển đổi và định dạng ngày tháng.
 * Dùng khi lưu dữ liệu vào DB (cần java.sql.Date) hoặc hiển thị ra UI (cần String).
 */
public class ConvertDate {

    // Định dạng ngày giờ hiển thị trên giao diện
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    // Định dạng chỉ ngày (dùng cho bộ lọc theo ngày)
    private static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Chuyển java.util.Date (từ JDateChooser) sang java.sql.Date để lưu vào MySQL.
     *
     * @param utilDate ngày lấy từ UI
     * @return java.sql.Date để truyền vào PreparedStatement
     */
    public static java.sql.Date toSqlDate(Date utilDate) {
        if (utilDate == null) {
            return null;
        }
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * Chuyển java.sql.Date (từ ResultSet) sang java.util.Date để hiển thị trên UI.
     *
     * @param sqlDate ngày lấy từ database
     * @return java.util.Date để set vào JDateChooser hoặc format ra String
     */
    public static Date toUtilDate(java.sql.Date sqlDate) {
        if (sqlDate == null) {
            return null;
        }
        return new Date(sqlDate.getTime());
    }

    /**
     * Định dạng Timestamp từ database thành chuỗi "dd/MM/yyyy HH:mm" để hiển thị trong JTable.
     *
     * @param ts Timestamp lấy từ cột thời gian trong DB
     * @return chuỗi ngày giờ, ví dụ "21/04/2026 14:30"
     */
    public static String format(Timestamp ts) {
        if (ts == null) {
            return "";
        }
        return DATE_TIME_FORMAT.format(new Date(ts.getTime()));
    }

    /**
     * Phân tích chuỗi ngày "dd/MM/yyyy" thành java.util.Date.
     * Dùng khi người dùng nhập ngày thủ công vào JTextField.
     *
     * @param dateStr chuỗi ngày theo định dạng "dd/MM/yyyy"
     * @return Date tương ứng, hoặc null nếu chuỗi sai định dạng
     */
    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return DATE_ONLY_FORMAT.parse(dateStr.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Đặt giờ về 00:00:00.000 (đầu ngày) để lọc theo ngày bắt đầu.
     */
    public static Date changeFrom(Date date) {
        if (date == null) return null;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Đặt giờ về 23:59:59.999 (cuối ngày) để lọc theo ngày kết thúc.
     */
    public static Date changeTo(Date date) {
        if (date == null) return null;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 23);
        cal.set(java.util.Calendar.MINUTE, 59);
        cal.set(java.util.Calendar.SECOND, 59);
        cal.set(java.util.Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * Kiểm tra một ngày có nằm trong khoảng [from, to] không.
     * Dùng để lọc phiếu nhập/xuất theo khoảng thời gian.
     *
     * @param target ngày cần kiểm tra
     * @param from   ngày bắt đầu (có thể null = không giới hạn)
     * @param to     ngày kết thúc (có thể null = không giới hạn)
     * @return true nếu target nằm trong khoảng
     */
    public static boolean isInRange(Date target, Date from, Date to) {
        if (target == null) {
            return false;
        }
        if (from != null && target.before(from)) {
            return false;
        }
        if (to != null && target.after(to)) {
            return false;
        }
        return true;
    }
}
