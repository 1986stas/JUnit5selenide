package com.osikov.stas.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.osikov.stas.model.Product;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductPage extends BasePage {

    private ElementsCollection totalProductCount = $$(By.xpath("//div[@name='goods_list_container']/descendant-or-self::*[@class='g-i-tile-i-box']"));
    private ElementsCollection productsTitles = $$(By.xpath("//div[@name='goods_list_container']/descendant-or-self::*[@class='g-i-tile-i-box']/descendant-or-self::*[contains(@class, 'g-i-tile-i-title clearfix')]/a"));

    private SelenideElement buyButton = $(By.xpath("//div[@class = 'toOrder']//button[@type = 'submit']"));

    private final static String COLORS_XPATH = "//div[@class='g-i-tile-i-box']/descendant-or-self::*[contains(text(), '$1')]/ancestor::div[@class='g-i-tile-i-box-desc']/preceding-sibling::div//*[contains(@class, 'g-i-tile-i-variants')]/a";

    public String getProductsPageTitle() {
        return $(By.xpath("//h1[contains(@itemprop, 'name')]")).text();
    }

    public ProductPage selectProduct(String parameterName) {
        for (int i = 0; i < productsTitles.size(); i++) {
            if(productsTitles.get(i).text().contains(parameterName)){
                productsTitles.get(i).click();
            }
        }
        return this;
    }

    private Map<SelenideElement, Product> getProductValiues(){
        return new HashMap<>();
    }

    public String ifProductExist(String expectedProductName){
        List<Product> productsList = getProductsWithOptions();
        Product product = productsList.stream()
                .filter(customer -> customer.getName().contains(expectedProductName))
                .findAny()
                .orElse(null);
        return product.getName();
    }

    public void setProductColor(String expectedColor){
        if(isColorSelected(expectedColor)){
            return;
        }
        else {
            $(By.xpath("//div[@class = 'detail-available' and @name = 'detail-price-block']/descendant-or-self::ul[@name = 'variants']/li//span[contains(@style, '$1')]"
                    .replace("$1", expectedColor))).waitUntil(Condition.enabled, TIME_TO_WAIT).scrollIntoView(true).click();
        }
    }

    public ProductPage clickOnBuyButton(){
        buyButton.waitUntil(Condition.enabled, TIME_TO_WAIT).click();
        return this;
    }

    public boolean isModalConfirmationWindowDisplayed(){
        return $(By.xpath("//div[@id = 'cart-popup']")).waitUntil(Condition.appear, TIME_TO_WAIT).isDisplayed();
    }

    public Integer getCartPurchaseCount(){
        return Integer.valueOf($(By.xpath("//ul[@class = 'header-user-buttons']/descendant-or-self::*[@class = 'hub-i-count']")).text());
    }

    private boolean isColorSelected(String color){
        return $(By.xpath("//div[@class = 'detail-available' and @name = 'detail-price-block']/descendant-or-self::ul[@name = 'variants']/li//span[contains(@style, '$1')]/ancestor::li"
                .replace("$1", color))).attr("class").contains("active");
    }

    private List<Product> getProductsWithOptions(){
        List<Product> products = new LinkedList<>();
        Product product = new Product();
        for (int i = 0; i < totalProductCount.size(); i++) {
            String tempProductName = getProductName().get(i);
            product.setName(tempProductName);
            product.setProductColors(getProductColors(tempProductName));
            products.add(product);
        }
        return products;
    }

    private List<String> getProductColors(String productName){
        ElementsCollection colorsCollection = $$(By.xpath(COLORS_XPATH.replace("$1", productName)));
        return colorsCollection.stream().map(attribute -> attribute.attr("style")).collect(Collectors.toCollection(LinkedList::new));
    }

    private List<String> getProductName(){
        return new LinkedList<>(productsTitles.texts());
    }
}
