package com.example.TesteBugBank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

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

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

@SpringBootTest
class TesteBugBankApplicationTests {

    @Value("${BugBank.endpoint}")
    private String bugBankEndpoint;

    @Test
    @Description("Teste de fluxo completo de registro, login, transferência e verificação de saldo no BugBank")
    public void contextLoads() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lucca.garcia\\Downloads\\TesteBugBank\\TesteBugBank\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            driver.get(bugBankEndpoint);
            System.out.println("Acessando a página principal: " + bugBankEndpoint);

            registerUser(driver, wait, js, "lucca@gmail.com", "Lucca Garcia", "senha123", "senha123", true);
            closeModal(driver, wait);
            limparCampos(driver);

            registerUser(driver, wait, js, "luccabibianogarcia@gmail.com", "Nome do Novo Usuário", "senha123", "senha123", false);
            String[] pegarNumeroDaConta = pegarNumeroDaConta(wait);
            closeModal(driver, wait);

            loginUser(driver, wait, "lucca@gmail.com", "senha123");
            fazerTransferencia(driver, wait, js, pegarNumeroDaConta[0], pegarNumeroDaConta[1]);
            closeModal(driver, wait);
            voltarDaAreadaTransferencia(wait);
            verificarSaldo(wait, 500.0);
            sairDaConta(wait);

            loginUser(driver, wait, "luccabibianogarcia@gmail.com", "senha123");
            verificarSaldo(wait, 1500.0);
            sairDaConta(wait);
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
        }
    }

    @Step("Registrar usuário com email: {0}, nome: {1}, senha: {2}, confirmar senha: {3}, adicionar saldo: {4}")
    private void registerUser(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String email, String nome, String senha, String confirmarSenha, boolean addBalance) {
        WebElement botaoRegistrar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Registrar')]")));
        botaoRegistrar.click();

        WebElement inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='email'])[2]")));
        inputEmail.sendKeys(email);

        WebElement inputNome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='name']")));
        inputNome.sendKeys(nome);

        WebElement inputSenha = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='password'])[2]")));
        inputSenha.sendKeys(senha);

        WebElement inputConfirmarSenha = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='passwordConfirmation']")));
        inputConfirmarSenha.sendKeys(confirmarSenha);
       
        WebElement toggleAddBalanceLabel = driver.findElement(By.xpath("//label[@id='toggleAddBalance']"));
        boolean isToggleActive = toggleAddBalanceLabel.getAttribute("class").contains("active");

        if (addBalance && !isToggleActive) {
            js.executeScript("arguments[0].click();", toggleAddBalanceLabel);
        } else if (!addBalance && isToggleActive) {
            js.executeScript("arguments[0].click();", toggleAddBalanceLabel);
        }


        WebElement cadastrar = driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar')]"));
        cadastrar.click();
    }

    @Step("Fechar modal")
    private void closeModal(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnCloseModal")));
            closeButton.click();
            System.out.println("Modal fechado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao fechar modal: " + e.getMessage());
        }
    }

    @Step("Pegar número da conta")
    private String[] pegarNumeroDaConta(WebDriverWait wait) {
        WebElement modalTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalText")));
        String mensagemDeSucesso = modalTextElement.getText();
        String[] partes = mensagemDeSucesso.split(" ");
        String infoConta = partes[2]; // "873-0"
        String[] partesConta = infoConta.split("-");
        return partesConta; // Retorna ["873", "0"]
    }

    @Step("Logar usuário com email: {0}")
    private void loginUser(WebDriver driver, WebDriverWait wait, String email, String senha) {
        WebElement inputEmailLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        inputEmailLogin.sendKeys(email);

        WebElement inputSenhaLogin =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        inputSenhaLogin.sendKeys(senha);

        WebElement acessarButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.style__ContainerButton-sc-1wsixal-0")));
        acessarButton.click();
    }

    @Step("Limpar campos")
    private void limparCampos(WebDriver driver) {
        WebElement inputEmail = driver.findElement(By.xpath("(//input[@name='email'])[2]"));
        inputEmail.clear();

        WebElement inputNome = driver.findElement(By.cssSelector("input[name='name']"));
        inputNome.clear();

        WebElement inputSenha = driver.findElement(By.xpath("(//input[@name='password'])[2]"));
        inputSenha.clear();

        WebElement inputConfirmarSenha = driver.findElement(By.cssSelector("input[name='passwordConfirmation']"));
        inputConfirmarSenha.clear();
    }

    @Step("Fazer transferência para conta: {0}-{1}")
    private void fazerTransferencia(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, String numeroConta, String digitoConta) {
        WebElement transferencia = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-TRANSFERÊNCIA")));
        transferencia.click();

        WebElement inputNumeroDaConta = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("accountNumber")));
        inputNumeroDaConta.sendKeys(numeroConta);

        WebElement inputDigito = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("digit")));
        inputDigito.sendKeys(digitoConta);

        WebElement inputValorTransferencia = driver.findElement(By.name("transferValue"));
        inputValorTransferencia.sendKeys("500");

        WebElement inputDescricao = driver.findElement(By.name("description"));
        inputDescricao.sendKeys("Transferência de 500 reais");

        WebElement transferirButton = driver.findElement(By.xpath("//button[contains(text(), 'Transferir agora')]"));
        transferirButton.click();
    }

    @Step("Sair da conta")
    private void sairDaConta(WebDriverWait wait) {
        WebElement SairButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnExit")));
        SairButton.click();
    }

    @Step("Voltar da área de transferência")
    private void voltarDaAreadaTransferencia(WebDriverWait wait) {
        WebElement voltarButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnBack")));
        voltarButton.click();
    }


    @Step("Verificar saldo: {0}")
    private void verificarSaldo(WebDriverWait wait, double saldoEsperado) {
        WebElement saldo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("textBalance")));
        String saldoTexto = saldo.getText();
        String saldoValorTexto = saldoTexto.split("R\\$")[1].trim().replace(".", "").replace(",", ".");
        double saldoValor = Double.parseDouble(saldoValorTexto);
        assertEquals(saldoEsperado, saldoValor, 0.01, "O saldo após a transferência deve ser R$ " + saldoEsperado);
    }

    
}
