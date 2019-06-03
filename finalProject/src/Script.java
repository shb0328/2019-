import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Script {
	String[][] str1P;
	String[][][] str2P;

	public static void main(String[] arg) {
		Script sc = new Script();
	}

	public Script() {
		super();
		str1P = new String[10][];
		str2P = new String[10][2][];
		for (int i = 0; i < str1P.length; ++i) {
			str1P[i] = new String[2];
			str2P[i][0]= new String[2];
			str2P[i][1]= new String[2];
		}

		try {
			FileInputStream fis = new FileInputStream(
					"C://eclipse_workspace//networkP//realfinalproj//src//RPGscript.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
//				for(int columnIndex = 0; columnIndex <= cells; columnIndex++) {
//					XSSFCell cell = row.getCell(columnIndex);
					XSSFCell cell = row.getCell(0);
					String value = "";
					value = cell.getStringCellValue() + "";
//					System.out.println("value : " + value);

					str1P[rowIndex/2][rowIndex % 2] = value;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fis = new FileInputStream(
					"C://eclipse_workspace//networkP//realfinalproj//src//RPGscript.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(1);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
//				for(int columnIndex = 0; columnIndex <= cells; columnIndex++) {
//					XSSFCell cell = row.getCell(columnIndex);
					XSSFCell cell = row.getCell(0);
					String value = "";
					value = cell.getStringCellValue() + "";
//					System.out.println("value : " + value);

					if((rowIndex%4)%2 == 0)
						str2P[rowIndex/4][(rowIndex/2)%2][0] = value;
					else
						str2P[rowIndex/4][(rowIndex/2)%2][1] = value;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}//end of constructor
	

}
