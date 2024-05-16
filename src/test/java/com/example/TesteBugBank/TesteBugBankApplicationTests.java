package com.example.TesteBugBank;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TesteBugBankApplicationTests {

    @Value("${BugBank.endpoint}")
    private String bugBankEndpoint;

    @Test
    public void contextLoads() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lucca.garcia\\Downloads\\TesteBugBank\\TesteBugBank\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  
        JavascriptExecutor js = (JavascriptExecutor) driver;
    
        try {
            driver.get(bugBankEndpoint);
    
            WebElement botaoRegistrar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Registrar')]")));
            botaoRegistrar.click();

            WebElement inputName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
            inputName.sendKeys("Lucca Garcia");

            WebElement inputPassword = driver.findElement(By.xpath("(//input[@name='password'])[2]"));
            inputPassword.sendKeys("senha123");

            WebElement inputEmail = driver.findElement(By.xpath("(//input[@name='email'])[2]"));
            inputEmail.sendKeys("lucca@gmail.com");

            WebElement inputPasswordRegistration = driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
            inputPasswordRegistration.sendKeys("senha123");

            WebElement toggleAddBalanceLabel = driver.findElement(By.xpath("//label[@id='toggleAddBalance']"));
            js.executeScript("arguments[0].click();", toggleAddBalanceLabel);

            WebElement cadastrar = driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar')]"));
            cadastrar.click();

            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCloseModal")));
            closeButton.click();

            botaoRegistrar.click();
        
            WebElement inputEmail2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='email'])[2]")));
            inputEmail2.clear();
            inputEmail2.sendKeys("luccabibianogarcia@gmail.com");

            WebElement inputName2 = driver.findElement(By.cssSelector("input[name='name']"));
            inputName2.clear();
            inputName2.sendKeys("Nome do Novo Usuário");

            WebElement inputPassword2 = driver.findElement(By.xpath("(//input[@name='password'])[2]"));
            inputPassword2.clear();
            inputPassword2.sendKeys("senha123");

            WebElement inputPasswordRegistration2 = driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
            inputPasswordRegistration2.clear();
            inputPasswordRegistration2.sendKeys("senha123");

            WebElement toggleAddBalanceLabel2 = driver.findElement(By.xpath("//label[@id='toggleAddBalance']"));
            js.executeScript("arguments[0].click();", toggleAddBalanceLabel2);

            WebElement cadastrar2 = driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar')]"));
            cadastrar2.click();

            WebElement modalTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalText")));
            String successMessage = modalTextElement.getText();

            WebElement closeButton2 = driver.findElement(By.cssSelector("div.styles__ContainerCloseButton-sc-8zteav-2.ffzYTz a"));
            js.executeScript("arguments[0].click();", closeButton2);

            WebElement inputEmailLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
            inputEmailLogin.sendKeys("lucca@gmail.com");

            WebElement inputPasswordLogin =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            inputPasswordLogin.sendKeys("senha123");

            WebElement acessarButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.style__ContainerButton-sc-1wsixal-0")));
            acessarButton2.click();

            WebElement transferencia = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-TRANSFERÊNCIA")));
            transferencia.click();
        } catch (NoSuchElementException e) {
            System.out.println("Elemento não encontrado: " + e.getMessage());
        } finally {
        
        }
    }
}
