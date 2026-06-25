package com.ticketfast.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Punto 4: Automatizacion Frontend E2E (30%)
 *
 * Esta prueba automatiza el flujo completo del frontend:
 * 1. Navegar a http://localhost:4200/reservas
 * 2. Diligenciar formulario: email, zona "VIP", cantidad 1
 * 3. Hacer clic en boton de confirmacion
 * 4. Verificar que el resumen muestre "150.000" usando esperas dinamicas
 */
public class FrontendE2ETest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testReservaFrontendE2E() {
        // 1. Navegar a la URL de reservas
        driver.get("http://localhost:4200/reservas");

        // 2. Diligenciar el formulario

        // Ingresar email
        WebElement emailInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-testid='input-email-cliente']")
            )
        );
        emailInput.clear();
        emailInput.sendKeys("test-e2e@correo.com");

        // Seleccionar zona VIP
        WebElement zonaSelect = driver.findElement(
            By.cssSelector("[data-testid='select-zona-evento']")
        );
        Select select = new Select(zonaSelect);
        select.selectByValue("VIP");

        // Ingresar cantidad
        WebElement cantidadInput = driver.findElement(
            By.cssSelector("[data-testid='input-cantidad-asientos']")
        );
        cantidadInput.clear();
        cantidadInput.sendKeys("1");

        // 3. Hacer clic en el boton de confirmacion
        WebElement btnConfirmar = driver.findElement(
            By.cssSelector("[data-testid='btn-confirmar-reserva']")
        );
        btnConfirmar.click();

        // 4. Verificar que el resumen muestre el valor correcto usando espera dinamica
        WebElement resumenTotal = wait.until(
            ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("[data-testid='seccion-resumen-total']"),
                "150.000"
            )
        );

        String textoResumen = driver.findElement(
            By.cssSelector("[data-testid='seccion-resumen-total']")
        ).getText();

        assertTrue(textoResumen.contains("150.000"),
                  "El resumen debe contener el valor formateado 150.000");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
