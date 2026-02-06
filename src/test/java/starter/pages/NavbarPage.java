package starter.pages;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

public class NavbarPage extends PageObject {

    private By categoryOption = By.xpath("/html/body/div/div/div[1]/a[2]");

    public void clickAddCategoryButton() {
        $(categoryOption).waitUntilClickable();
        $(categoryOption).click();
    }
}
