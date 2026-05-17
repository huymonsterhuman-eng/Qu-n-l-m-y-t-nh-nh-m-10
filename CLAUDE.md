# Tổng quan dự án — Quản lý kho cửa hàng máy tính

## I. Chức năng chính

1. Đăng nhập / đăng xuất
2. Quản lý sản phẩm
3. Quản lý nhà cung cấp
4. Quản lý phiếu nhập / xuất
5. Theo dõi tồn kho
6. Quản lý người dùng
7. Thống kê

---

## II. Công nghệ sử dụng

### 1. Ngôn ngữ và Nền tảng
- **Java 25** — ngôn ngữ chính
- **Maven** — quản lý dependencies

### 2. Giao diện người dùng (GUI)
- **Java Swing** — framework desktop (file `.form` + `.java` trong gói `view`)
- **FlatLaf v3.4** — Look & Feel hiện đại cho Swing
- **Absolute Layout** — sắp xếp component theo tọa độ tuyệt đối (NetBeans)
- **JCalendar v1.4** — date picker component
- **JFreeChart v1.5.4** — biểu đồ thống kê (cột, đường) trong ThongKeForm

### 3. Cơ sở dữ liệu
- **MySQL** — hệ quản trị CSDL chính
- **MySQL Connector/J v9.2.0** — JDBC driver
- **JDBCUtil.java** — singleton quản lý mở/đóng connection

### 4. Xử lý dữ liệu và Bảo mật
- **Apache POI v5.2.3** — đọc/ghi file Excel (XLS, XLSX)
- **iText 5 v5.5.13.3** — xuất file PDF
- **jBCrypt v0.4** — hash mật khẩu người dùng

### 5. Kiến trúc phần mềm
- **MVC** — tách biệt Model / View / Controller
- **DAO Pattern** — tách logic truy cập dữ liệu vào gói `DAO`

### 6. Tính năng phụ
- **WritePDF.java** — xuất JTable ra PDF dùng iText 5
- **SendEmailSMTP.java** — khôi phục mật khẩu qua email (placeholder, chưa triển khai)

---

## III. Cấu trúc thư mục

```
Quanlimaytinhnew/
├── pom.xml                         # Maven config (dependencies)
├── CLAUDE.md                       # Tổng quan dự án
├── rule.md                         # Quy định coding và workflow
├── Progress.md                     # Tiến trình triển khai
├── database.md                     # Schema SQL
├── business_rule.md                # Ràng buộc nghiệp vụ (tham chiếu thiết kế)
├── log.md                          # Log thay đổi theo session (⚠️ xem bên dưới)
└── src/
    └── main/
        ├── java/
        │   ├── database/
        │   │   └── JDBCUtil.java   # Kết nối MySQL (singleton)
        │   ├── model/              # Các entity: MayTinh, Account, NhaCungCap, ...
        │   ├── DAO/                # Data Access Objects (CRUD cho từng entity)
        │   ├── controller/         # ✅ Hoàn thành — BCrypt, ConvertDate, Search*, WritePDF, SendEmailSMTP
        │   └── view/               # Giao diện Swing (.java + .form)
        └── resources/
            └── icon/               # Icon và assets cho giao diện
```

---

---

## III.b. Quy tắc truy cập log.md

> **QUAN TRỌNG:** `log.md` chỉ được đọc khi người dùng cho phép.
> Trước khi đọc file này, Claude **phải hỏi**: *"Bạn có muốn tôi xem log.md không?"*
> Không được tự ý đọc `log.md` mà không có sự đồng ý rõ ràng của người dùng.

---

## IV. Trạng thái triển khai

| Phase | Nội dung | Trạng thái |
|-------|----------|-----------|
| Phase 1 | Controller Layer | ✅ Hoàn thành |
| Phase 2 | View Logic Rewrite (tất cả form) | ✅ Hoàn thành |

Xem chi tiết tiến độ và ghi chú sửa đổi trong **Progress.md**.

---

## V. Hướng dẫn khởi động

**Yêu cầu môi trường:** Java 17+, MySQL 8+, Maven 3.8+

1. **Tạo database**: chạy toàn bộ lệnh `CREATE TABLE` trong `database.md`
2. **Cấu hình kết nối DB**: sửa `src/main/java/database/JDBCUtil.java`
   - `URL` — chuỗi kết nối MySQL (host, port, tên DB)
   - `USER` — tên đăng nhập MySQL
   - `PASSWORD` — mật khẩu MySQL
3. **Build**: `mvn clean compile`
4. **Chạy**: `mvn exec:java -Dexec.mainClass="view.Login"`
