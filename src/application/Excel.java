package application;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.concurrent.Task;

public class Excel {
	
	
	public static Task<Void> loadFromArmxml() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				updateProgress(0, 100);
				
				XSSFWorkbook workbook = new XSSFWorkbook();			
				
				//headerfont
		        Font headerFont = workbook.createFont();
		        headerFont.setFontHeightInPoints((short) 20);
		        headerFont.setColor(IndexedColors.RED.getIndex());
		        
		        //styles
		        CellStyle headerCellStyle = workbook.createCellStyle();
		        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());  
		        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		        headerCellStyle.setFont(headerFont);
		        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
				
		        for (int i = 1; i <= 45; i++) {
		        	Sheet sheet = workbook.createSheet("week " + i);
		        	Row headerRow = sheet.createRow(0);
		        	Cell headerCell = headerRow.createCell(0);
		        	headerCell.setCellValue("KAPSTER      donderdag 25/6/2020");
		        	headerCell.setCellStyle(headerCellStyle);
		        }
				
				return null;
			}
		};
	}
	
	public void createHeader() {

        
        
	}

}
