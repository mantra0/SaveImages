package in.eprison;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Image {

    static WebDriver driver;
    static String url = "https://eprisons.nic.in/Delhi/secure/police/DisplayNew.aspx?PIDNo=";
    static int id = 1036928;
    static String prisonerID;

    public static void main(String[] args) {
        try{
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
                    + "\\src\\main\\java\\in\\eprison\\drivers\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get("https://eprisons.nic.in/Delhi/secure/login.aspx");
            login("inspjaitpur", "Password@22");
            String imageURL = url+id;
            driver.get(imageURL);
            Thread.sleep(1000L);
            prisonerID = driver.findElement(By.cssSelector("#lbl_pid")).getText();
            while(prisonerID != null){
                saveImage();
                id++;
                imageURL = url+id;
                driver.get(imageURL);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    static void login(String userName, String password){
        driver.findElement(By.cssSelector("#txtUserid")).sendKeys(userName);
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys(password);
        driver.findElement(By.cssSelector("#btnLogin")).click();
    }

    static void saveImage(){
        prisonerID = driver.findElement(By.cssSelector("#lbl_pid")).getText();
        WebElement image = driver.findElement(By.cssSelector("#Image1"));
        String imageSRC = image.getAttribute("src");
        String[] strArray = imageSRC.split(",");
        byte[] data = Base64.getDecoder().decode(strArray[1]);
        String path = "C:\\Users\\Farmguide\\Pictures\\P_image\\"+ prisonerID + ".png";
        File file = new File(path);
        try(OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))){
            outputStream.write(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
