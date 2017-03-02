package chromeOptions;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DisablePopupNotifications {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
		Map<String, Object> pref  = new HashMap<String, Object>();
		pref.put("profile.default_content_setting_values.notifications", 2);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", pref);
		
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://www.google.co.in/");
		driver.close();
		
	}

}
