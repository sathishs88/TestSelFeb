package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String[][] data=null;

		File file1 = new File("./testdata/Login.xlsx");
		FileInputStream fis = new FileInputStream(file1);
		XSSFWorkbook wbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = wbook.getSheetAt(0);

		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();

		data = new String[rowCount][colCount];

		System.out.println("Row count: "+rowCount);
		System.out.println("Column count: "+colCount);
		
		for (int i=1;i <=rowCount;i++){
			XSSFRow row = sheet.getRow(i);
			
			for(int j=0; j<colCount; j++){
				XSSFCell cell = row.getCell(j);
				data[i-1][j] = cell.getStringCellValue();
				System.out.println(data[i-1][j]);
			}
		}
		fis.close();
		wbook.close();
		//return data;
	}

}

