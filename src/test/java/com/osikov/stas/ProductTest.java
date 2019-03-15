package com.osikov.stas;

import com.osikov.stas.dataProvider.ProductDataProvider;
import com.osikov.stas.page.ProductPage;
import com.osikov.stas.page.SearchPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ProductTest extends BaseTest {

    private SearchPage searchPage = new SearchPage();
    private static final String EXPECTED_COLOR = "#666";

    @ParameterizedTest
    @ArgumentsSource(ProductDataProvider.class)
    @DisplayName("Product should be found and added to cart successfully")
    public void testSearchFunctionality(String parameterName) {
        searchPage.enterProductName("iphone").waitFofProductsList();
        ProductPage productPage = searchPage.chooseProductFromDropDown("iphone se");
        assertThat("Title is incorrect", productPage.getProductsPageTitle(), is(equalTo("Apple iPhone SE")));
        productPage.selectProduct(parameterName).setProductColor(EXPECTED_COLOR);
        productPage.clickOnBuyButton();
        assertThat("Modal confirmation window is not displayed", productPage.isModalConfirmationWindowDisplayed());
        assertThat("Cart does not contain an order", productPage.getCartPurchaseCount() == 1);
    }
}
