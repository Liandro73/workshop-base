package TestSample;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TesteCalculadora {

    private AppiumDriver<MobileElement> driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();

    //udid = 0044581080
    //udid = emulator-5554

    public void iniciar() throws MalformedURLException {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Moto G5s");
        capabilities.setCapability(MobileCapabilityType.UDID, "0044581080");
        capabilities.setCapability("appPackage", "com.google.android.calculator");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

        driver = new AppiumDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void tirarPrintTela(String nomeEtapa) throws IOException {
        File evidencia = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String nomeEtapaFormatado = nomeEtapa
                .toLowerCase()
                .replace("ã", "a")
                .replace("ç", "c")
                .replace(" ", "_");
        FileUtils.moveFile(evidencia, new File("target/screenshots/evidencia_" + nomeEtapaFormatado + ".jpg"));
    }

    public void tirarPrintEImprimirNoLog(String passo) throws IOException {
        System.out.println(passo);
        tirarPrintTela(passo);
    }

    @Test
    public void TesteCalculadora_Somar() throws IOException {
        iniciar();

        driver.findElementById("com.google.android.calculator:id/digit_7").click();
        tirarPrintEImprimirNoLog("Clicou no botão 7");

        driver.findElementByAccessibilityId("mais").click();
        tirarPrintEImprimirNoLog("Clicou no botão mais");

        driver.findElementById("com.google.android.calculator:id/digit_8").click();
        tirarPrintEImprimirNoLog("Clicou no botão 8");

        driver.findElementByAccessibilityId("igual").click();
        tirarPrintEImprimirNoLog("Clicou no botão igual");

        if (("15").equals(driver.findElementById("com.google.android.calculator:id/result_final").getText())) {
            tirarPrintEImprimirNoLog("Verificou o valor do resultado final com SUCESSO :)");
        } else {
            tirarPrintEImprimirNoLog("Verificou que o valor era diferente do esperado :(");
        }

        Assert.assertEquals("15", driver.findElementById("com.google.android.calculator:id/result_final").getText());

    }

    @Test
    public void TesteCalculadora_Subtrair() throws MalformedURLException {
        iniciar();

        driver.findElementById("com.google.android.calculator:id/digit_9").click();
        driver.findElementByAccessibilityId("menos").click();
        driver.findElementById("com.google.android.calculator:id/digit_3").click();
        driver.findElementByAccessibilityId("igual").click();
        Assert.assertEquals("6", driver.findElementById("com.google.android.calculator:id/result_final").getText());

    }

    @Test
    public void TesteCalculadora_Multiplicar() throws MalformedURLException {
        iniciar();

        driver.findElementById("com.google.android.calculator:id/digit_5").click();
        driver.findElementByAccessibilityId("multiplicar").click();
        driver.findElementById("com.google.android.calculator:id/digit_5").click();
        driver.findElementByAccessibilityId("igual").click();
        Assert.assertEquals("25", driver.findElementById("com.google.android.calculator:id/result_final").getText());

    }

}
