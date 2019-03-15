package com.osikov.stas.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;


public class SearchPage extends BasePage {

    private Logger log = Logger.getLogger(String.valueOf(SearchPage.class));

    private SelenideElement searchInput = $(By.xpath("//div[@role = 'search']/descendant-or-self::input[@name = 'search']"));
    private ElementsCollection suggestListProduct = $$(By.xpath("//div[contains(@class, 'header-dropdown')]/descendant-or-self::ul[@class = 'suggest-list']/li[@data-name]//span"));

    public SearchPage enterProductName(String productName){
        searchInput.val(productName);
        return this;
    }

    public SearchPage waitFofProductsList(){
        try{
            suggestListProduct.shouldHave(CollectionCondition.sizeNotEqual(0), TIME_TO_WAIT);
        }
        catch (NoSuchElementException e){
            log.info("There is no such product " + e.getMessage());
        }
        return this;
    }

    public ProductPage chooseProductFromDropDown(String desirableProduct) {
        suggestListProduct.stream().filter(selenideElement -> selenideElement.text()
                .contains(desirableProduct))
                .forEach(SelenideElement::click);
        return page(ProductPage.class);
    }
}
