# Quản Lý Máy Tính — Nhóm 10

Ứng dụng desktop quản lý kho cửa hàng máy tính, xây dựng bằng Java Swing + MySQL theo kiến trúc MVC.

---

## Tính năng

- Đăng nhập / đăng xuất (mật khẩu mã hóa BCrypt)
- Quản lý sản phẩm (máy tính, linh kiện)
- Quản lý nhà cung cấp
- Quản lý phiếu nhập / xuất kho
- Theo dõi tồn kho real-time
- Quản lý tài khoản người dùng
- Thống kê doanh thu, xuất báo cáo Excel / PDF

---

## Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java 17+ |
| Build tool | Maven |
| GUI | Java Swing + FlatLaf v3.4 |
| Cơ sở dữ liệu | MySQL 8+ |
| JDBC Driver | MySQL Connector/J v9.2.0 |
| Biểu đồ | JFreeChart v1.5.4 |
| Xuất Excel | Apache POI v5.2.3 |
| Xuất PDF | iText 5 v5.5.13.3 |
| Bảo mật | jBCrypt v0.4 |

---

## Yêu cầu môi trường

- **Java** 17 trở lên
- **MySQL** 8.0 trở lên
- **Maven** 3.8 trở lên

---

## Hướng dẫn cài đặt & chạy

### 1. Clone repository

```bash
git clone https://github.com/huymonsterhuman-eng/Qu-n-l-m-y-t-nh-nh-m-10.git
cd Qu-n-l-m-y-t-nh-nh-m-10
```

### 2. Tạo cơ sở dữ liệu

Mở MySQL client (Workbench hoặc CLI) và chạy toàn bộ lệnh `CREATE TABLE` trong file [`database.md`](database.md).

```sql
-- Ví dụ kết nối MySQL CLI:
mysql -u root -p
source database.md
```

### 3. Cấu hình kết nối Database

Mở file `src/main/java/database/JDBCUtil.java` và sửa 3 thông số sau cho khớp với MySQL của bạn:

```java
private static final String URL = "jdbc:mysql://localhost:3306/TEN_DATABASE";
private static final String USER = "root";
private static final String PASSWORD = "mat_khau_cua_ban";
```

### 4. Build dự án

```bash
mvn clean compile
```

### 5. Chạy ứng dụng

```bash
mvn exec:java -Dexec.mainClass="view.Login"
```

---

## Cấu trúc thư mục

```
src/main/java/
├── database/       # JDBCUtil — singleton kết nối MySQL
├── model/          # Entity: MayTinh, Account, NhaCungCap, ...
├── DAO/            # Data Access Objects (CRUD)
├── controller/     # BCrypt, Search, WritePDF, SendEmailSMTP, ...
└── view/           # Giao diện Swing (.java + .form)
```

---

## Tài khoản mặc định

> Sau khi tạo database, thêm tài khoản admin theo hướng dẫn trong `database.md`.

---

## Nhóm thực hiện

Nhóm 10 — Môn Lập trình ứng dụng
