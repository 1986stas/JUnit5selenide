package com.osikov.stas;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    private final static String BASE_URL = "https://rozetka.com.ua";

    protected BaseTest(){
        initWebDriver();
    }

    private static void initWebDriver(){
        open(BASE_URL);
    }

    @BeforeAll
    public static void setUp() throws IOException {
        Properties configProp = new Properties();
        configProp.load(new FileInputStream("./src/test/resources/selenideConfig.properties"));
        Configuration.browser = configProp.getProperty("browser");
        Configuration.startMaximized = true;
    }

    @AfterAll
    public static void tearDown(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

}
