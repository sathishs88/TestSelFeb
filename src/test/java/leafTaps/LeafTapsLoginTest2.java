package leafTaps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LeafTapsLoginTest2 {

	public static RemoteWebDriver driver=null;
	public static ExtentReports report;
	public static ExtentTest test;
	public static String[][] data;
	
	@Test
	public static void test1() throws InterruptedException, IOException {

		//set system property
		/*System.setProperty("webdriver.edge.driver", "./drivers/MicrosoftWebDriver.exe");
		driver = new EdgeDriver();*/
		
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		
		//start reports
		report = new ExtentReports("./report/result.html", true);
		test = report.startTest("Login to LeafTaps");

		//login steps
		data = getdata("./testdata/Login.xlsx");
		
		System.out.println(data[0][0]);
		System.out.println(data[0][1]);
		invokeApp(data[0][0], data[0][1]);

		//end reports
		report.endTest(test);
		report.flush();
	}

	public static void invokeApp(String username, String password) throws IOException, InterruptedException{

		driver.manage().window().maximize();
		driver.get("http://leaftaps.com/control/main");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElementById("username").sendKeys(username);
		reportStep("pass", "Username Entered");

		driver.findElementById("password").sendKeys(password);
		reportStep("pass", "Password Entered");

		driver.findElementByClassName("decorativeSubmit").click();
		Thread.sleep(3000);
		reportStep("pass", "Login button clicked");

		driver.close();
	}

	public static long takeSnap() throws IOException{
		long snapNumber = (long) Math.floor(Math.random()*90000000L) + 10000000L;
		//System.out.println(snapNumber);
		File src = driver.getScreenshotAs(OutputType.FILE);
		File dest = new File("./snaps/"+snapNumber+".jpg");
		FileUtils.copyFile(src, dest);
		return snapNumber;
	}

	public static void reportStep(String status, String desc) throws IOException{
		String imagePath = "./../snaps/"+takeSnap()+".jpg";
		if(status.toLowerCase().equals("pass"))
			test.log(LogStatus.PASS, desc+test.addScreenCapture(imagePath));
		else if(status.toLowerCase().equals("fail"))
			test.log(LogStatus.FAIL, desc+test.addScreenCapture(imagePath));
		else if(status.toLowerCase().equals("info"))
			test.log(LogStatus.INFO, desc+test.addScreenCapture(imagePath));
	}

	public static String[][] getdata(String path) throws IOException {
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
		return data;
	}
}
