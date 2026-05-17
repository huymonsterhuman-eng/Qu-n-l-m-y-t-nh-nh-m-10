package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Xuất dữ liệu từ JTable ra file PDF dùng thư viện iText 5.
 * Dùng trong CTPhieuNhap, CTPhieuXuat, ThongKeForm khi người dùng nhấn nút xuất PDF.
 */
public class WritePDF {

    // Font hỗ trợ tiếng Việt (lấy font hệ thống Arial trên Windows)
    private static BaseFont baseFont;

    static {
        try {
            // Dùng font Arial có sẵn trên Windows để hiển thị tiếng Việt đúng
            baseFont = BaseFont.createFont("C:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
            // Fallback về font mặc định nếu không tìm thấy Arial
            baseFont = null;
        }
    }

    /**
     * Xuất toàn bộ dữ liệu trong JTable ra file PDF với tiêu đề.
     *
     * @param table    JTable cần xuất (đọc cả header và data)
     * @param title    tiêu đề hiển thị đầu trang PDF, ví dụ "Phiếu Nhập Hàng"
     * @param filePath đường dẫn file PDF đầu ra, ví dụ "C:/phieu_nhap.pdf"
     * @throws DocumentException nếu có lỗi khi tạo cấu trúc PDF
     * @throws IOException       nếu không ghi được file
     */
    public static void exportTable(JTable table, String title, String filePath)
            throws DocumentException, IOException {

        Document document = new Document(PageSize.A4.rotate()); // Xoay ngang cho bảng rộng
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Tạo font tiêu đề và font nội dung
        Font titleFont = (baseFont != null)
                ? new Font(baseFont, 16, Font.BOLD)
                : new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font headerFont = (baseFont != null)
                ? new Font(baseFont, 10, Font.BOLD)
                : new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font cellFont = (baseFont != null)
                ? new Font(baseFont, 9)
                : new Font(Font.FontFamily.HELVETICA, 9);

        // Thêm tiêu đề vào đầu trang
        Paragraph titleParagraph = new Paragraph(title, titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(15f);
        document.add(titleParagraph);

        // Tạo bảng PDF với số cột bằng số cột của JTable
        TableModel model = table.getModel();
        int columnCount = model.getColumnCount();
        PdfPTable pdfTable = new PdfPTable(columnCount);
        pdfTable.setWidthPercentage(100); // Bảng chiếm toàn bộ chiều rộng trang

        // Thêm hàng tiêu đề cột (header)
        for (int i = 0; i < columnCount; i++) {
            PdfPCell headerCell = new PdfPCell(new Phrase(model.getColumnName(i), headerFont));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5f);
            pdfTable.addCell(headerCell);
        }

        // Thêm các hàng dữ liệu
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < columnCount; col++) {
                Object value = model.getValueAt(row, col);
                String cellText = (value != null) ? value.toString() : "";
                PdfPCell cell = new PdfPCell(new Phrase(cellText, cellFont));
                cell.setPadding(4f);
                pdfTable.addCell(cell);
            }
        }

        document.add(pdfTable);
        document.close();
    }

    /**
     * Hiển thị hộp thoại chọn nơi lưu file và xuất PDF.
     * Tiện lợi hơn khi gọi từ View vì không cần tự tạo JFileChooser.
     *
     * @param parent JFrame/JDialog cha để hộp thoại hiển thị đúng vị trí
     * @param table  JTable cần xuất
     * @param title  tiêu đề của PDF
     */
    public static void exportWithChooser(java.awt.Component parent, JTable table, String title) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File(title.replaceAll("[^a-zA-Z0-9_]", "_") + ".pdf"));
        // Chỉ cho chọn file .pdf
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF files", "pdf"));

        int option = chooser.showSaveDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            // Tự động thêm đuôi .pdf nếu người dùng quên nhập
            if (!path.toLowerCase().endsWith(".pdf")) {
                path += ".pdf";
            }
            try {
                exportTable(table, title, path);
                JOptionPane.showMessageDialog(parent, "Xuất PDF thành công!\nFile: " + path,
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Lỗi xuất PDF: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
