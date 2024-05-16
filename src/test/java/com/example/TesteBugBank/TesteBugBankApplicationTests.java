package com.example.TesteBugBank;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

            WebElement inputEmail = driver.findElement(By.xpath("(//input[@name='email'])[2]"));
            inputEmail.sendKeys("lucca@gmail.com");

            WebElement InputNome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
            InputNome.sendKeys("Lucca Garcia");

            WebElement InputSenha = driver.findElement(By.xpath("(//input[@name='password'])[2]"));
            InputSenha.sendKeys("senha123");

            WebElement InputConfirmarSenha = driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
            InputConfirmarSenha.sendKeys("senha123");

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

            WebElement InputNome2 = driver.findElement(By.cssSelector("input[name='name']"));
            InputNome2.clear();
            InputNome2.sendKeys("Nome do Novo Usuário");

            WebElement InputSenha2 = driver.findElement(By.xpath("(//input[@name='password'])[2]"));
            InputSenha2.clear();
            InputSenha2.sendKeys("senha123");

            WebElement InputConfirmarSenha2 = driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
            InputConfirmarSenha2.clear();
            InputConfirmarSenha2.sendKeys("senha123");

            WebElement cadastrar2 = driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar')]"));
            cadastrar2.click();

            WebElement modalTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalText")));
            String mensagemDeSucesso = modalTextElement.getText();

            String[] partes = mensagemDeSucesso.split(" ");
            String infoConta = partes[2]; // "873-0"
            String[] partesConta = infoConta.split("-");
            String numeroConta = partesConta[0];//873
            String digitoConta = partesConta[1];//0

            
            WebElement closeButton2 = driver.findElement(By.cssSelector("div.styles__ContainerCloseButton-sc-8zteav-2.ffzYTz a"));
            js.executeScript("arguments[0].click();", closeButton2);

            WebElement inputEmailLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
            inputEmailLogin.sendKeys("lucca@gmail.com");

            WebElement inputSenhaLogin =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            inputSenhaLogin.sendKeys("senha123");

            WebElement acessarButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.style__ContainerButton-sc-1wsixal-0")));
            acessarButton2.click();

            WebElement transferencia = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-TRANSFERÊNCIA")));
            transferencia.click();

            WebElement InputNumeroDaConta = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("accountNumber")));
            InputNumeroDaConta.sendKeys(numeroConta);

            WebElement InputDigito = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("digit")));
            InputDigito.sendKeys(digitoConta);

            WebElement InputValorTransferencia = driver.findElement(By.name("transferValue"));
            InputValorTransferencia.sendKeys("500");

            WebElement InputDescricao = driver.findElement(By.name("description"));
            InputDescricao.sendKeys("Transferência de 500 reais");

            WebElement transferirButton = driver.findElement(By.xpath("//button[contains(text(), 'Transferir agora')]"));
            transferirButton.click();

            WebElement closeButton3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCloseModal")));
            closeButton3.click();

            WebElement SairButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnExit")));
            SairButton.click();

            WebElement inputEmailLogin2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
            inputEmailLogin2.sendKeys("luccabibianogarcia@gmail.com");

            WebElement inputSenhaLogin2 =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            inputSenhaLogin2.sendKeys("senha123");

            WebElement acessarButton3 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.style__ContainerButton-sc-1wsixal-0")));
            acessarButton3.click();

            WebElement saldo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("textBalance")));
            String saldoTexto = saldo.getText();

            String saldoValorTexto = saldoTexto.split("R\\$")[1].trim().replace(".", "").replace(",", ".");
            double saldoValor = Double.parseDouble(saldoValorTexto);

            assertEquals(1500.00, saldoValor, 0.01, "O saldo após a transferência deve ser R$ 1.500,00");



        } catch (NoSuchElementException e) {
            System.out.println("Elemento não encontrado: " + e.getMessage());
        } finally {
        
        }
    }
}
