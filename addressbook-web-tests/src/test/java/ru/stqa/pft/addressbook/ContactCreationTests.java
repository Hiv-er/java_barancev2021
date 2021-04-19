package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;

public class ContactCreationTests {
    private WebDriver wd;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        login("admin", "secret");
    }

    private void login(String username, String password) {
        wd.get("http://localhost/addressbook/");
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys(username);
        wd.findElement(By.name("pass")).click();
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys(password);
        wd.findElement(By.xpath("//input[@value='Login']")).click();
    }

    @Test
    public void testContactCreation() throws Exception {
        goToContactPage();
        fillContactForm(new ContactData("test1", "test2", "test3", "a@a.ru", "1234567"));
        submitContactCreation();
        returnToHomePage();
    }

    private void logout() {
        wd.findElement(By.linkText("Logout")).click();
    }

    private void returnToHomePage() {
        wd.findElement(By.linkText("home page")).click();
    }

    private void submitContactCreation() {
        wd.findElement(By.name("submit")).click();
    }

    private void fillContactForm(ContactData contactData) {
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstName());
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastName());
        wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
        wd.findElement(By.name("home")).sendKeys(contactData.getHomePhone());
        wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    }


    private void goToContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        logout();
        wd.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
