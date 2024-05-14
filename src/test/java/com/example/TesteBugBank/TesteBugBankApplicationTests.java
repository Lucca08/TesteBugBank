package com.example.TesteBugBank;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TesteBugBankApplicationTests {

    @Value("${BugBank.endpoint}")
    private String bugBankEndpoint;

    @Test
    public void contextLoads() {
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\lucca.garcia\\Downloads\\TesteBugBank\\TesteBugBank\\chromedriver.exe");
	WebDriver driver = new ChromeDriver();        
        try {

			driver.get(bugBankEndpoint);
            WebElement botaoRegistrar = driver.findElement(By.xpath("//button[contains(text(), 'Registrar')]"));
			
			botaoRegistrar.click();

			WebElement inputEmail = driver.findElement(By.xpath("//input[@placeholder='Informe seu e-mail']"));
            inputEmail.sendKeys("lucca@gmail.com");

	

		
		


			

		


        } catch (Exception e) {
            e.printStackTrace();
        } finally {	
			
		}
    }
}
