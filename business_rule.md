# Business Rules — Hệ thống Quản lý Kho Cửa hàng Máy tính

> Tài liệu này tổng hợp toàn bộ ràng buộc nghiệp vụ của dự án.  
> Mục đích: dùng làm tham chiếu khi thiết kế hệ thống quản lý kho tương tự.

---

## 1. Sản phẩm (MayTinh)

### 1.1 Phân loại
- Có 2 loại máy: **Laptop** và **PC/Case**, phân biệt qua trường `loaiMay`.
- **Laptop** có thêm: `kichThuocMan` (float, > 0), `dungLuongPin` (string, > 0).
- **PC** có thêm: `mainBoard` (string), `congSuatNguon` (int, > 0).
- Nếu `loaiMay` NULL trong DB: suy luận từ prefix `maMay` — bắt đầu bằng "LP" → Laptop, còn lại → PC.

### 1.2 Validate khi thêm / cập nhật sản phẩm
| Trường | Ràng buộc |
|---|---|
| `maMay` | Bắt buộc, duy nhất (PK), không rỗng |
| `tenMay` | Bắt buộc, không rỗng |
| `donGia` / `gia` | Phải là số, **> 0** (không âm, không bằng 0) |
| `soLuong` | Phải là số nguyên, **>= 0** |
| `tenCpu`, `ram`, `rom` | Bắt buộc, không rỗng |
| `cardManHinh`, `xuatXu` | Bắt buộc, không rỗng |
| `kichThuocMan` (Laptop) | Phải là số, **> 0** |
| `dungLuongPin` (Laptop) | Phải là số, **> 0** |
| `congSuatNguon` (PC) | Phải là số nguyên, **> 0** |

Tất cả giá trị text phải được `.trim()` trước khi validate.

### 1.3 Trạng thái sản phẩm
- `trangThai = 1`: **Hoạt động** — hiển thị trong mọi form, được phép thêm vào phiếu nhập/xuất.
- `trangThai = 0`: **Ngừng hoạt động** — ẩn khỏi danh sách chọn hàng; xóa mềm, không xóa khỏi DB.
- Chỉ sản phẩm `trangThai = 1` mới được `selectAllExist()` trả về (dùng khi tạo phiếu).

---

## 2. Tài khoản (Account)

### 2.1 Validate khi tạo / cập nhật
| Trường | Ràng buộc |
|---|---|
| `userName` | Bắt buộc, duy nhất (PK), không rỗng, **không thay đổi được sau khi tạo** |
| `fullName` | Bắt buộc, không rỗng |
| `password` | Bắt buộc khi tạo, mã hóa bằng **BCrypt** trước khi lưu |
| `email` | Bắt buộc, đúng định dạng regex: `^[\w-_\.+]*[\w-_\.]\@([\w]+\.)+[\w]+[\w]$` |
| `role` | Bắt buộc, phải là một trong 3 giá trị hợp lệ (xem §2.2) |
| `status` | `1` = Hoạt động, `0` = Bị khóa |

### 2.2 Các Role hợp lệ
| Role | Quyền |
|---|---|
| `Quản lý kho` | Toàn quyền: tạo, sửa, xóa, hoàn thành, hủy phiếu; xuất/nhập Excel |
| `Nhân viên nhập` | Chỉ tạo phiếu nhập; **không** sửa/xóa/xuất-nhập Excel phiếu nhập |
| `Nhân viên xuất` | Chỉ tạo phiếu xuất; **không** sửa/xóa/xuất-nhập Excel phiếu xuất |

> Nhân viên nhập/xuất vẫn được phép nhấn **Hoàn thành** và **Hủy** phiếu.

### 2.3 Đặt lại mật khẩu
- Admin có thể reset password cho tài khoản khác: sinh mật khẩu ngẫu nhiên → BCrypt → lưu DB.

---

## 3. Nhà cung cấp (NhaCungCap)

- `maNhaCungCap` (PK): không rỗng, duy nhất.
- `PhieuNhap.maNhaCungCap` là FK → `NhaCungCap.maNhaCungCap`: **không được xóa** nhà cung cấp nếu còn phiếu nhập tham chiếu.
- Mỗi phiếu nhập bắt buộc phải chọn nhà cung cấp.

---

## 4. Phiếu nhập (PhieuNhap)

### 4.1 Vòng đời trạng thái

```
[Tạo mới] ──► CHO_XU_LY ──► HOAN_THANH
                   │
                   └──────► DA_HUY
```

- Chỉ được chuyển trạng thái từ `CHO_XU_LY`.
- `HOAN_THANH` và `DA_HUY` là trạng thái **cuối** — không thể đảo ngược.

### 4.2 Tạo phiếu nhập
- Mã phiếu tự động sinh: prefix `PN` + số tăng dần (PN1, PN2, ...), không trùng.
- `nguoiTao` = tài khoản đang đăng nhập.
- `thoiGianTao` = thời điểm hiện tại.
- Bắt buộc phải chọn **nhà cung cấp**.
- Phiếu phải có **ít nhất 1 sản phẩm** trong chi tiết.
- Số lượng từng sản phẩm trong chi tiết phải **> 0**.
- Khi tạo: `trangThai = 'Chờ xử lý'`, **không** cập nhật tồn kho.

### 4.3 Hoàn thành phiếu nhập (`CHO_XU_LY` → `HOAN_THANH`)
Thực hiện trong **1 transaction**:
1. Cập nhật `trangThai` → `'Đã hoàn thành'`
2. `MayTinh.soLuong += soLuong` cho từng sản phẩm trong chi tiết
3. `ChiTietPhieuNhap.soLuongConLai = soLuong` (kích hoạt lô FIFO)
4. Commit — rollback toàn bộ nếu có lỗi

### 4.4 Hủy phiếu nhập (`CHO_XU_LY` → `DA_HUY`)
- Cập nhật `trangThai` → `'Đã hủy'`.
- **Không** cập nhật tồn kho.

### 4.5 Guard conditions
| Thao tác | Điều kiện bắt buộc |
|---|---|
| Sửa phiếu | `trangThai = 'Chờ xử lý'` **và** role không phải `Nhân viên nhập` |
| Xóa phiếu | `trangThai = 'Chờ xử lý'` **và** role không phải `Nhân viên nhập` |
| Hoàn thành | `trangThai = 'Chờ xử lý'` (mọi role) |
| Hủy | `trangThai = 'Chờ xử lý'` (mọi role) |

---

## 5. Phiếu xuất (PhieuXuat)

### 5.1 Vòng đời trạng thái
Giống phiếu nhập:
```
[Tạo mới] ──► CHO_XU_LY ──► HOAN_THANH
                   │
                   └──────► DA_HUY
```

### 5.2 Tạo phiếu xuất
- Mã phiếu tự động sinh: prefix `PX` + số tăng dần, không trùng.
- Phiếu phải có **ít nhất 1 sản phẩm** trong chi tiết.
- Số lượng từng sản phẩm phải **> 0**.
- Khi thêm sản phẩm vào phiếu: `soLuongCanXuat <= soLuongKhaDung` (xem §6.2).
  - Nếu sản phẩm đã có trong phiếu: `(soLuongCũ + soLuongThêm) <= soLuongKhaDung`.
- Khi tạo: `trangThai = 'Chờ xử lý'`, **không** cập nhật tồn kho; số lượng bị "giữ chỗ" trong công thức khả dụng.

### 5.3 Hoàn thành phiếu xuất (`CHO_XU_LY` → `HOAN_THANH`)
**Validate trước transaction:** với mỗi sản phẩm trong chi tiết:
- `MayTinh.soLuong >= soLuong` (tồn kho thực tế đủ) — nếu không: báo lỗi, dừng.

Nếu đủ, thực hiện trong **1 transaction**:
1. Cập nhật `trangThai` → `'Đã hoàn thành'`
2. **FIFO**: `deductFIFO(con, maMay, soLuong)` — trừ `soLuongConLai` từ lô cũ nhất trước
3. `MayTinh.soLuong -= soLuong` cho từng sản phẩm
4. Commit — rollback toàn bộ nếu có lỗi

### 5.4 Hủy phiếu xuất (`CHO_XU_LY` → `DA_HUY`)
- Cập nhật `trangThai` → `'Đã hủy'`.
- **Không** cập nhật tồn kho; giải phóng số lượng đang "giữ chỗ".

### 5.5 Guard conditions
| Thao tác | Điều kiện bắt buộc |
|---|---|
| Sửa phiếu | `trangThai = 'Chờ xử lý'` **và** role không phải `Nhân viên xuất` |
| Xóa phiếu | `trangThai = 'Chờ xử lý'` **và** role không phải `Nhân viên xuất` |
| Hoàn thành | `trangThai = 'Chờ xử lý'` + đủ tồn kho thực tế (mọi role) |
| Hủy | `trangThai = 'Chờ xử lý'` (mọi role) |

---

## 6. Tồn kho (Inventory)

### 6.1 Cột `MayTinh.soLuong`
- Là tồn kho **thực tế** — chỉ thay đổi khi phiếu được **Hoàn thành**.
- Tăng khi phiếu nhập hoàn thành, giảm khi phiếu xuất hoàn thành.
- Không được âm (được bảo vệ bởi validate tầng View trước khi hoàn thành phiếu xuất).

### 6.2 Tồn kho khả dụng (`soLuongKhaDung`)
```
soLuongKhaDung = soLuong
               - SUM(soLuong của các chi tiết phiếu xuất "Chờ xử lý")
```
- Dùng để validate khi **thêm sản phẩm vào phiếu xuất** (không cho đặt vượt quá).
- SQL phải dùng `CASE WHEN px.maPhieu IS NOT NULL THEN ct.soLuong ELSE 0 END` để chỉ tính đúng phiếu "Chờ xử lý" (tránh lỗi LEFT JOIN sum sai).

### 6.3 Theo dõi lô hàng — FIFO
- Mỗi dòng `ChiTietPhieuNhap` = 1 **lô hàng**.
- Thêm cột `soLuongConLai INT DEFAULT 0`:
  - `= 0` khi phiếu nhập còn "Chờ xử lý"
  - `= soLuong` khi phiếu nhập "Hoàn thành" (kích hoạt lô)
  - Giảm dần khi phiếu xuất hoàn thành (deductFIFO)
- **Invariant quan trọng:** `SUM(soLuongConLai tất cả lô) == MayTinh.soLuong` sau mỗi transaction.

### 6.4 Thuật toán FIFO (deductFIFO)
```
Lấy danh sách lô ORDER BY PhieuNhap.thoiGianTao ASC WHERE soLuongConLai > 0
remaining = soLuongCanTru
For each lô:
    tru = min(lô.soLuongConLai, remaining)
    UPDATE soLuongConLai -= tru
    remaining -= tru
    if remaining == 0: break
```
- Thực hiện trong cùng `Connection` (transaction) với `updateSoLuong`.

### 6.5 Lô ảo "Đầu kỳ"
- Khi triển khai FIFO lần đầu, tồn kho cũ chưa có nguồn lô → tạo phiếu nhập ảo `maPhieu = 'DAU_KY'`, `thoiGianTao = '2000-01-01'`.
- Mỗi sản phẩm có `soLuong > 0` được tạo 1 dòng `ChiTietPhieuNhap` với `soLuongConLai = soLuong`.
- Lô này có ngày cũ nhất → được FIFO xuất trước tiên.
- Hiển thị trong UI với tên `"(Đầu kỳ)"` và ngày `"-"`.

---

## 7. Transaction & Data Integrity

### 7.1 Scope transaction
| Sự kiện | Các thao tác trong transaction |
|---|---|
| Hoàn thành phiếu nhập | updateTrangThai + updateSoLuong×n + activateLoHang |
| Hoàn thành phiếu xuất | updateTrangThai + deductFIFO×n + updateSoLuong×n |

### 7.2 Rollback
- Bất kỳ lỗi SQL nào xảy ra trong transaction → rollback toàn bộ.
- Sau rollback: `soLuong`, `soLuongConLai`, `trangThai` đều không thay đổi.
- Báo lỗi cho người dùng qua dialog.

### 7.3 Overload DAO cho transaction
- Các method DAO có 2 phiên bản:
  - `method(String id, ...)` — tự tạo connection (dùng cho thao tác độc lập như Hủy phiếu).
  - `method(Connection con, String id, ...)` — dùng connection chung (dùng trong transaction).

---

## 8. Bảng quyền theo Role

| Chức năng | Quản lý kho | Nhân viên nhập | Nhân viên xuất |
|---|:---:|:---:|:---:|
| Xem phiếu nhập | ✅ | ✅ | ✅ |
| Tạo phiếu nhập | ✅ | ✅ | ❌ |
| Sửa phiếu nhập | ✅ | ❌ | ❌ |
| Xóa phiếu nhập | ✅ | ❌ | ❌ |
| Hoàn thành phiếu nhập | ✅ | ✅ | ❌ |
| Hủy phiếu nhập | ✅ | ✅ | ❌ |
| Xuất/nhập Excel phiếu nhập | ✅ | ❌ | ❌ |
| Xem phiếu xuất | ✅ | ✅ | ✅ |
| Tạo phiếu xuất | ✅ | ❌ | ✅ |
| Sửa phiếu xuất | ✅ | ❌ | ❌ |
| Xóa phiếu xuất | ✅ | ❌ | ❌ |
| Hoàn thành phiếu xuất | ✅ | ❌ | ✅ |
| Hủy phiếu xuất | ✅ | ❌ | ✅ |
| Xuất/nhập Excel phiếu xuất | ✅ | ❌ | ❌ |
| Quản lý sản phẩm | ✅ | ✅ | ✅ |
| Quản lý tài khoản | ✅ | ❌ | ❌ |
| Quản lý nhà cung cấp | ✅ | ✅ | ✅ |
| Xem tồn kho | ✅ | ✅ | ✅ |
| Xem thống kê | ✅ | ✅ | ✅ |

---

## 9. Quy tắc định dạng & hiển thị

| Loại | Định dạng |
|---|---|
| Tiền tệ | `###,###,###đ` (VND, ví dụ: `1,500,000đ`) |
| Ngày giờ | `dd/MM/yyyy HH:mm` |
| Mã phiếu nhập | `PN` + số tăng dần (PN1, PN2, ...) |
| Mã phiếu xuất | `PX` + số tăng dần (PX1, PX2, ...) |
| Password | BCrypt hash, độ dài lưu `varchar(60)` |

---

## 10. Luồng nghiệp vụ tổng hợp

### Luồng nhập hàng
```
1. Nhân viên nhập tạo phiếu nhập (CHO_XU_LY)
   - Chọn nhà cung cấp
   - Thêm sản phẩm + số lượng + đơn giá
2. Quản lý/Nhân viên xét duyệt:
   - Hoàn thành → soLuong tăng, lô FIFO được kích hoạt
   - Hủy → không ảnh hưởng tồn kho
```

### Luồng xuất hàng
```
1. Nhân viên xuất tạo phiếu xuất (CHO_XU_LY)
   - Thêm sản phẩm + số lượng (validate soLuongKhaDung)
   - Tồn kho khả dụng bị giảm ngay (giữ chỗ)
2. Quản lý/Nhân viên xét duyệt:
   - Hoàn thành → validate soLuong thực tế → FIFO deduct → soLuong giảm
   - Hủy → giải phóng giữ chỗ, không ảnh hưởng soLuong thực tế
```
