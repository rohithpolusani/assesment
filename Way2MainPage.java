
import Assesment.ReadExcelFile;
import Assesment.Utility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Way2MainPage {
    public WebDriver driver;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;
    private ReadExcelFile readExcelFile;

    @BeforeTest
    public void setup() {
        //path of ExtentReports
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test_output/way2Automation.html");
        System.out.println("htmlReporter");
        htmlReporter.setAppendExisting(true);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        readExcelFile = new ReadExcelFile(System.getProperty("user.dir") + "/src/main/resources/way2Testdata.xlsx");
    }

    @Test
    public void crossbrowser() {
        test = extent.createTest("browser is started: ");
        //Intiated chrome Driver
        ChromeDriverManager manager = new ChromeDriverManager();
        manager.setup();
        driver = new ChromeDriver();
        test.log(Status.PASS, "Chrome is launched successfully");

    }

    @Test(dataProvider = "testdata")
    public void demoway2(String firstname, String lastname, String UserName, String Password, String customer, String Email, String cellphone) throws Exception {
        //Test is started

        test = extent.createTest("Way2Automation test is started");


        test.log(Status.PASS, "Chrome is launched successfully");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //navigate to iLab website
        driver.get("http://www.way2automation.com/angularjs-protractor/webtables/");
        //Logs to appear in the extent Report
        test.pass("way2 website is loaded", MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
        //Maximizing web window
        driver.manage().window().fullscreen();
        //click on Addusers link
        driver.findElement(By.xpath("/html/body/table/thead/tr[2]/td/button")).click();
        test.pass("Add Users is clicked", MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
        //clicking on southAfrica link
        driver.findElement(By.name("FirstName")).sendKeys(firstname);
        test.pass("First Name  Entered:" + firstname, MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
        driver.findElement(By.name("LastName")).sendKeys(lastname);
        test.pass("First Name  Entered:" + lastname, MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
        driver.findElement(By.name("UserName")).sendKeys(UserName);
        test.pass("First Name  Entered:" + UserName, MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
        driver.findElement(By.name("Password")).sendKeys(Password);
        test.pass("First Name  Entered:" + Password, MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
        String customertype = readExcelFile.getData(0, 2, 4);
        System.out.println(customertype);
        Thread.sleep(5000);
        if (customertype.equals("CompanyAAA")) {
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[1]/input")).click();
        } else {
            driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[5]/td[2]/label[2]/input")).click();
        }
        Select rolechoice = new Select(driver.findElement(By.xpath("/html/body/div[3]/div[2]/form/table/tbody/tr[6]/td[2]/select")));
        rolechoice.selectByVisibleText("Admin");
        driver.findElement(By.name("Email")).sendKeys(Email);
        driver.findElement(By.name("Mobilephone")).sendKeys(cellphone);
        driver.findElement(By.xpath("/html/body/div[3]/div[3]/button[2]")).click();
    }

    @DataProvider(name = "testdata")
    public Object[][] testdata() {

        //Data reading from excel file
        int rows = readExcelFile.getRowCount(0);
        int columns = 7;
        Object[][] userDetails = new Object[rows][columns];

        try {
            for (int i = 0; i < rows; i++) {
                int j = i + 2;

                for (int k = 0; k < columns; k++) {
                    userDetails[i][k] = readExcelFile.getData(0,/*row*/ j, /*column*/ k);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userDetails;
    }

    @AfterTest
    private void ReportingEnd()
    {
        extent.flush();
    }
}
