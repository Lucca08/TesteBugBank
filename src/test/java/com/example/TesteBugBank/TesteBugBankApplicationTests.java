package com.example.TesteBugBank;

import java.util.NoSuchElementException;

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
    
            if (isRegistrationPage(driver)) {
                WebElement inputName = driver.findElement(By.cssSelector("input[name='name']"));
                inputName.sendKeys("Lucca Garcia");

                WebElement inputPassword = driver.findElement(By.xpath("(//input[@name='password'])[2]"));
                inputPassword.sendKeys("senha123");

    
                WebElement inputEmail = driver.findElement(By.xpath("(//input[@name='email'])[2]"));
                inputEmail.sendKeys("lucca@gmail.com");

    
                WebElement inputPasswordRegistration = driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
                inputPasswordRegistration.sendKeys("senha123");
    
            } else {
                System.out.println("Não estamos na página de registro.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
         
        }
    }
    
    // Função para verificar se estamos na página de registro
    private boolean isRegistrationPage(WebDriver driver) {
        try {
            driver.findElement(By.cssSelector("input[name='name']"));
            driver.findElement(By.cssSelector("input[name='password']"));   
            driver.findElement(By.cssSelector("input[name='email']"));
            driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}    