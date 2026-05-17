CREATE TABLE Account (
    fullName nvarchar(50) DEFAULT NULL,
    userName varchar(50) NOT NULL PRIMARY KEY,
    password varchar(60) DEFAULT NULL,
    role nvarchar(50) DEFAULT NULL,
    status int DEFAULT NULL,
    email varchar(50) DEFAULT NULL
);

CREATE TABLE MayTinh (
    maMay varchar(50) NOT NULL PRIMARY KEY,
    tenMay nvarchar(100) DEFAULT NULL,
    soLuong int NOT NULL DEFAULT 0,
    tenCpu nvarchar(50) NOT NULL DEFAULT '0',
    ram varchar(50) NOT NULL DEFAULT '0',
    cardManHinh nvarchar(50) DEFAULT NULL,
    gia float NOT NULL DEFAULT 0,
    mainBoard nvarchar(50) DEFAULT NULL,
    congSuatNguon int DEFAULT NULL,
    loaiMay nvarchar(50) DEFAULT NULL,
    rom varchar(50) DEFAULT NULL,
    kichThuocMan float DEFAULT NULL,
    dungLuongPin nvarchar(50) DEFAULT NULL,
    xuatXu nvarchar(50) DEFAULT NULL,
    trangThai int DEFAULT NULL
);

CREATE TABLE NhaCungCap (
    maNhaCungCap varchar(50) NOT NULL PRIMARY KEY,
    tenNhaCungCap nvarchar(50) DEFAULT NULL,
    Sdt varchar(50) DEFAULT NULL,
    diaChi nvarchar(150) DEFAULT NULL
);

CREATE TABLE PhieuNhap (
    maPhieu varchar(50) NOT NULL PRIMARY KEY,
    thoiGianTao datetime NULL,
    nguoiTao varchar(50) REFERENCES Account(userName),
    maNhaCungCap varchar(50) REFERENCES NhaCungCap(maNhaCungCap),
    tongTien float NOT NULL,
    trangThai varchar(20) NOT NULL DEFAULT 'Chờ xử lý'
);

CREATE TABLE PhieuXuat (
    maPhieu varchar(50) NOT NULL PRIMARY KEY,
    thoiGianTao datetime NOT NULL DEFAULT GETDATE(),
    nguoiTao varchar(50) REFERENCES Account(userName),
    tongTien float NOT NULL,
    trangThai varchar(20) NOT NULL DEFAULT 'Chờ xử lý'
);

CREATE TABLE ChiTietPhieuNhap (
    maPhieu varchar(50) NOT NULL REFERENCES PhieuNhap(maPhieu),
    maMay varchar(50) NOT NULL REFERENCES MayTinh(maMay),
    soLuong int DEFAULT NULL,
    donGia float DEFAULT NULL,
    PRIMARY KEY (maPhieu, maMay)
);

CREATE TABLE ChiTietPhieuXuat (
    maPhieu varchar(50) NOT NULL REFERENCES PhieuXuat(maPhieu),
    maMay varchar(50) NOT NULL REFERENCES MayTinh(maMay),
    soLuong int DEFAULT NULL,
    donGia float DEFAULT NULL,
    PRIMARY KEY (maPhieu, maMay)
);

-- Migration: thêm cột trạng thái cho phiếu nhập/xuất (chạy nếu DB đã tồn tại)
ALTER TABLE PhieuNhap ADD trangThai varchar(20) NOT NULL DEFAULT 'Chờ xử lý';
ALTER TABLE PhieuXuat ADD trangThai varchar(20) NOT NULL DEFAULT 'Chờ xử lý';

-- Các bản ghi cũ đã cập nhật tồn kho ngay lúc tạo → đánh dấu hoàn thành
UPDATE PhieuNhap SET trangThai = 'Đã hoàn thành';
UPDATE PhieuXuat SET trangThai = 'Đã hoàn thành';

-- Migration: thêm tracking lô hàng FIFO (chạy nếu DB đã tồn tại)
ALTER TABLE ChiTietPhieuNhap ADD soLuongConLai INT NOT NULL DEFAULT 0;

-- Tạo PhieuNhap ảo "(Đầu kỳ)" đại diện cho tồn kho cũ chưa có nguồn lô
INSERT INTO PhieuNhap (maPhieu, thoiGianTao, nguoiTao, maNhaCungCap, tongTien, trangThai)
VALUES ('DAU_KY', '2000-01-01 00:00:00', NULL, NULL, 0, 'Đã hoàn thành');

-- Tạo lô ảo cho từng sản phẩm đang có tồn kho (soLuongConLai = soLuong hiện tại)
INSERT INTO ChiTietPhieuNhap (maPhieu, maMay, soLuong, donGia, soLuongConLai)
SELECT 'DAU_KY', maMay, soLuong, 0, soLuong
FROM MayTinh WHERE soLuong > 0;
