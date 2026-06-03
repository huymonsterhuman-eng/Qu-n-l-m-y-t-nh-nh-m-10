# Quản Lý Kho Cửa Hàng Máy Tính — Nhóm 10

Ứng dụng desktop quản lý kho cửa hàng máy tính **Nguyễn Công PC**, xây dựng bằng **Java Swing + MySQL** theo kiến trúc **MVC + DAO**.

---

## Tính năng chính

- Đăng nhập / đăng xuất với phân quyền theo vai trò (Admin, Quản lý kho, Nhân viên nhập, Nhân viên xuất)
- Quản lý sản phẩm: Laptop và PC với thông số kỹ thuật riêng từng loại
- Quản lý nhà cung cấp
- Lập và xử lý phiếu nhập / xuất kho (hoàn thành, hủy, sửa, xóa)
- Theo dõi tồn kho thực tế và khả dụng theo thời gian thực
- Xuất kho theo nguyên tắc **FIFO** (nhập trước xuất trước)
- Thống kê, biểu đồ và xuất báo cáo ra **Excel / PDF**
- Mã hóa mật khẩu bằng **BCrypt**

---

## Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java 17+ |
| Build tool | Maven |
| GUI | Java Swing + FlatLaf v3.4 |
| Cơ sở dữ liệu | MySQL (XAMPP) |
| JDBC Driver | MySQL Connector/J v9.2.0 |
| Biểu đồ | JFreeChart v1.5.4 |
| Xuất Excel | Apache POI v5.2.3 |
| Xuất PDF | iText 5 v5.5.13.3 |
| Bảo mật | jBCrypt v0.4 |

---

## Yêu cầu môi trường

- **Java** 17 trở lên — [Tải tại đây](https://www.oracle.com/java/technologies/downloads/)
- **XAMPP** (bao gồm MySQL) — [Tải tại đây](https://www.apachefriends.org/)
- **Maven** 3.8 trở lên — [Tải tại đây](https://maven.apache.org/download.cgi)

---

## Hướng dẫn cài đặt và chạy

### Bước 1 — Clone repository

```bash
git clone https://github.com/huymonsterhuman-eng/Qu-n-l-m-y-t-nh-nh-m-10.git
cd Qu-n-l-m-y-t-nh-nh-m-10
```

### Bước 2 — Khởi động XAMPP và MySQL

1. Mở **XAMPP Control Panel**
2. Nhấn **Start** ở dòng **MySQL**
3. Đảm bảo MySQL đang chạy trên cổng **3306**

### Bước 3 — Tạo cơ sở dữ liệu

1. Mở trình duyệt, truy cập **http://localhost/phpmyadmin**
2. Nhấn **New** (bên trái) để tạo database mới
3. Đặt tên database là `quanlimaytinh`, nhấn **Create**
4. Chọn database `quanlimaytinh` vừa tạo
5. Chọn tab **Import** → nhấn **Choose File**
6. Chọn file `database.sql` trong thư mục dự án
7. Nhấn **Import** và chờ hoàn tất



### Bước 4 — Kiểm tra cấu hình kết nối

Mở file `src/main/java/database/JDBCUtil.java` và kiểm tra 3 dòng sau:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/quanlimaytinh";
private static final String USERNAME = "root";
private static final String PASSWORD = "";
```

> Đây là cấu hình mặc định của XAMPP. Nếu bạn đặt mật khẩu riêng cho MySQL thì sửa `PASSWORD` tương ứng.

### Bước 5 — Build dự án

```bash
mvn clean compile
```

### Bước 6 — Chạy ứng dụng

```bash
mvn exec:java -Dexec.mainClass="View.Login"
```

---

## Tài khoản mặc định

Sau khi import `database.sql`, tài khoản **Admin** được seed sẵn vào hệ thống:

| Tên đăng nhập | Mật khẩu | Vai trò |
|---|---|---|
| `admin` | `23112004` | Admin |

> **Lưu ý:** Tài khoản Admin không thể tạo/sửa/xóa qua giao diện — được bảo vệ trực tiếp trong DB. Các vai trò còn lại (Quản lý kho, Nhân viên nhập, Nhân viên xuất) được tạo qua phân hệ **Quản lý tài khoản** sau khi đăng nhập Admin.

---

## Cấu trúc thư mục

```
src/main/java/
├── database/       # JDBCUtil — singleton kết nối MySQL, quản lý transaction
├── model/          # Entity: MayTinh, Laptop, PC, Account, NhaCungCap, PhieuNhap, Phieuxuat, ...
├── DAO/            # Data Access Objects — toàn bộ truy vấn SQL theo từng entity
├── Controller/     # BCrypt, ConvertDate, Search*, WritePDF, SendEmailSMTP
└── View/           # Giao diện Swing (.java + .form)
```

---

## Phân quyền theo vai trò

| Chức năng | Admin | Quản lý kho | NV Nhập | NV Xuất |
|---|:---:|:---:|:---:|:---:|
| Quản lý tài khoản | ✅ | ❌ | ❌ | ❌ |
| Quản lý sản phẩm | ✅ | ✅ | ❌ | ❌ |
| Quản lý nhà cung cấp | ✅ | ✅ | ❌ | ❌ |
| Tạo / hoàn thành / hủy phiếu nhập | ✅ | ✅ | ✅ | ❌ |
| Sửa / xóa phiếu nhập | ✅ | ✅ | ❌ | ❌ |
| Tạo / hoàn thành / hủy phiếu xuất | ✅ | ✅ | ❌ | ✅ |
| Sửa / xóa phiếu xuất | ✅ | ✅ | ❌ | ❌ |
| Xem tồn kho | ✅ | ✅ | ✅ | ✅ |
| Thống kê & báo cáo | ✅ | ✅ | ❌ | ❌ |

---

## Nhóm thực hiện

**Nhóm 10** — Môn Lập trình ứng dụng
