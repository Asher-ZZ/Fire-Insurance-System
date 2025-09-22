package org.ace.accounting.web.system;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.ace.accounting.dto.ReservationDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReservationExcelExport {

    public static void exportReservationsToExcel(List<ReservationDTO> reservations, String filename) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reservations");

            // === Title ===
            Row titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("Car Rental Reservations");
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 8));

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setBorderBottom(BorderStyle.MEDIUM);
            titleRow.getCell(0).setCellStyle(titleStyle);

            // === Header ===
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            String[] headers = {
                "Customer Name", "Email", "Phone", "Car Type",
                "Start Date", "End Date", "Status", "Reason", "Total Cost"
            };
            Row headerRow = sheet.createRow(1);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // === Data ===
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int rowNum = 2;
            for (ReservationDTO r : reservations) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(r.getCustomerName() != null ? r.getCustomerName() : "");
                row.createCell(1).setCellValue(r.getEmail() != null ? r.getEmail() : "");
                row.createCell(2).setCellValue(r.getPhoneNumber() != null ? r.getPhoneNumber() : "");
                row.createCell(3).setCellValue(r.getCarType() != null ? r.getCarType() : "");
                row.createCell(4).setCellValue(r.getStartDate() != null ? sdf.format(r.getStartDate()) : "");
                row.createCell(5).setCellValue(r.getEndDate() != null ? sdf.format(r.getEndDate()) : "");
                row.createCell(6).setCellValue(r.getreserveStatus() != null ? r.getreserveStatus().toString() : "");
                row.createCell(7).setCellValue(r.getReason() != null ? r.getReason() : "");
                row.createCell(8).setCellValue(r.getTotalCost() != null ? r.getTotalCost().doubleValue() : 0.0);

                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            // Auto-size
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }

        facesContext.responseComplete();
    }
}
