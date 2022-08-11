package altYapi.seleniumGauge.step;

import altYapi.seleniumGauge.driver.Driver;
import altYapi.seleniumGauge.methods.Methods;
import altYapi.seleniumGauge.methods.MethodsUtil;
import altYapi.seleniumGauge.utils.ReadProperties;
import com.thoughtworks.gauge.Step;
import org.bouncycastle.pqc.jcajce.provider.mceliece.McElieceKeysToParams;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.Color;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static altYapi.seleniumGauge.driver.Driver.driver;
import static org.junit.jupiter.api.Assertions.*;

public class StepImplementation {

    private static final Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory.getLogger(StepImplementation.class);
    Methods methods;
    MethodsUtil methodsUtil;

    public StepImplementation() {

        methods = new Methods();
        methodsUtil = new MethodsUtil();
    }

    @Step("<key> elementine tıkla")
    public void clickElement(String key) {

        methods.clickElement(methods.getBy(key));
    }

    @Step("<key> elementine çift tıkla")
    public void doubleClickElementWithAction(String key) {

        methods.doubleClickElementWithAction(methods.getBy(key), true);
    }

    @Step("<key> elementine tıkla <notClickByCoordinate> with staleElement")
    public void clickElementForStaleElement(String key, boolean notClickByCoordinate) {

        methods.clickElementForStaleElement(methods.getBy(key), notClickByCoordinate);
    }

    @Step("<key> elementine js ile tıkla")
    public void clickElementJs(String key) {

        methods.clickElementJs(methods.getBy(key));
    }

    @Step("<key> elementine koordinat <x> x ve <y> y ile tıkla")
    public void clickByWebElementCoordinate(String key, int x, int y) {

        methods.clickByWebElementCoordinate(methods.getBy(key), x, y);
    }

    @Step("<key> elementine js ile tıkla <notClickByCoordinate>")
    public void clickElementJs(String key, boolean notClickByCoordinate) {

        methods.clickElementJs(methods.getBy(key), notClickByCoordinate);
    }

    @Step("<key> elementine <text> değerini yaz")
    public void sendKeysElement(String key, String text) {

        text = text.endsWith("KeyValue") ? Driver.TestMap.get(text).toString() : text;
        methods.sendKeys(methods.getBy(key), text);
    }

    @Step("<key> elementine <fileUpload> dosya yolunu yaz")
    public void sendKeysFileUpload(String key, String fileUpload) {

        methods.sendKeys(methods.getBy(key),System.getProperty("user.dir") + fileUpload);
    }

    @Step("<key> elementine js ile <text> değerini yaz")
    public void sendKeysElementWithJsAndBackSpace(String key, String text) {

        methods.waitBySeconds(1);
        methods.jsExecuteScript("arguments[0].value = \"" + text + "\";"
                ,false, methods.findElement(methods.getBy(key)));
        methods.waitByMilliSeconds(500);
        methods.sendKeysWithKeys(methods.getBy(key),"BACK_SPACE");
        methods.waitByMilliSeconds(500);
        methods.sendKeys(methods.getBy(key),text.substring(text.length()-1));
    }

    @Step("<key> elementine <text> sayı değerini yaz")
    public void sendKeysElementWithNumpad(String key, String text) {

        methods.sendKeysWithNumpad(methods.getBy(key), text);
    }

    @Step("<url> adresine git")
    public void navigateTo(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        methods.navigateTo(url);
    }

    @Step("Şu anki url <url> ile aynı mı")
    public void doesUrlEqual(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "equal"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> içeriyor mu")
    public void doesUrlContain(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "contain"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> ile başlıyor mu")
    public void doesUrlStartWith(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "startWith"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> ile sonlanıyor mu")
    public void doesUrlEndWith(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "endWith"),"Beklenen url, sayfa url ine eşit değil");
    }

    @Step("Şu anki url <url> ile farklı mı")
    public void checkUrlDifferent(String url) {

        url = url.endsWith("KeyValue") ? Driver.TestMap.get(url).toString() : url;
        url = methodsUtil.setValueWithMap(url);
        assertTrue(methods.doesUrl(url, 80, "notEqual"),url + " sayfasından başka bir sayfaya geçiş sağlanamadı.");
    }

    @Step("<key> elementinin değerini temizle")
    public void clearElement(String key) {

        methods.clearElement(methods.getBy(key));
    }

    @Step("<key> elementinin değerini back space kullanarak temizle <value>")
    public void clearElementWithBackSpace(String key, String value) {

        methods.clearElementWithBackSpace(methods.getBy(key), value);
    }

    @Step("<seconds> saniye bekle")
    public void waitBySeconds(long seconds) {

        methods.waitBySeconds(seconds);
    }

    @Step("<milliseconds> milisaniye bekle")
    public void waitByMilliSeconds(long milliseconds) {

        methods.waitByMilliSeconds(milliseconds);
    }

    @Step("<key> elementinin görünür olması kontrol edilir")
    public void checkElementVisible(String key) {

        checkElementVisible(key,30);
    }

    @Step("<key> elementinin var olduğu kontrol edilir")
    public void checkElementExist(String key) {

        assertTrue(methods.doesElementExist(methods.getBy(key),80),"");
    }

    @Step("<key> elementinin var olmadığı kontrol edilir")
    public void checkElementNotExist(String key) {

        assertTrue(methods.doesElementNotExist(methods.getBy(key),80),"");
    }

    @Step("<key> elementinin görünür olmadığı kontrol edilir")
    public void checkElementInVisible(String key) {

        checkElementInVisible(key,30);
    }

    @Step("<key> elementinin konumunu aldığı kontrol edilir")
    public void checkElementLocated(String key) {

        checkElementLocated(key,30);
    }

    @Step("<key> elementinin konumunu aldığı kontrol edilir <timeout>")
    public void checkElementLocated(String key, long timeout) {

        methods.checkElementLocated(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin görünür olması kontrol edilir <timeout>")
    public void checkElementVisible(String key, long timeout) {

        methods.checkElementVisible(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin görünür olmadığı kontrol edilir <timeout>")
    public void checkElementInVisible(String key, long timeout) {

        methods.checkElementInVisible(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin tıklanabilir olması kontrol edilir")
    public void checkElementClickable(String key) {

        checkElementClickable(key,30);
    }

    @Step("<key> elementinin tıklanabilir olması kontrol edilir <timeout>")
    public void checkElementClickable(String key, long timeout) {

        methods.checkElementClickable(methods.getBy(key), timeout);
    }

    @Step("<key> elementinin text değerini <mapKey> keyinde tut <trim>")
    public void getElementTextAndSave(String key, String mapKey, boolean trim){

        String text = methods.getText(methods.getBy(key));
        text = trim ? text.trim() : text;
        logger.info(text);
        Driver.TestMap.put(mapKey, text);
    }

    @Step("<key> elementinin <attribute> attribute değerini <mapKey> keyinde tut <trim>")
    public void getElementAttributeAndSave(String key, String attribute, String mapKey, boolean trim){

        String value = methods.getAttribute(methods.getBy(key), attribute);
        value = trim ? value.trim() : value;
        logger.info(value);
        Driver.TestMap.put(mapKey, value);
    }

    @Step({"<key> elementinin text değeri <expectedText> değerine eşit mi",
            "get text <key> element and control <expectedText>"})
    public void controlElementText(String key, String expectedText) {

        expectedText = expectedText.endsWith("KeyValue") ? Driver.TestMap.get(expectedText).toString() : expectedText;
        //methods.waitByMilliSeconds(250);
        String actualText = methods.getText(methods.getBy(key))
                .replace("\r", "").replace("\n", "").trim();
        System.out.println("Beklenen text: " + expectedText);
        System.out.println("Alınan text: " + actualText);
        assertEquals(expectedText, actualText,"Text değerleri eşit değil");
        System.out.println("Text değerleri eşit");
    }

    @Step({"<key> elementinin text değeri <expectedText> değerini içeriyor mu",
            "get text <key> element and control contains <expectedText>"})
    public void controlElementTextContain(String key, String expectedText) {

        expectedText = expectedText.endsWith("KeyValue") ? Driver.TestMap.get(expectedText).toString() : expectedText;
        //methods.waitByMilliSeconds(250);
        String actualText = methods.getText(methods.getBy(key))
                .replace("\r", "").replace("\n", "").trim();
        System.out.println("Beklenen text: " + expectedText);
        System.out.println("Alınan text: " + actualText);
        assertTrue(actualText.contains(expectedText),"Text değerleri eşit değil");
        System.out.println("Text değerleri eşit");
    }

    @Step("<key> elementinin <attribute> niteliği <expectedValue> değerine eşit mi")
    public void checkElementAttribute(String key, String attribute, String expectedValue) {

        expectedValue = expectedValue.endsWith("KeyValue") ? Driver.TestMap.get(expectedValue).toString() : expectedValue;
        String attributeValue = methods.getAttribute(methods.getBy(key), attribute);
        assertNotNull(attributeValue,"Elementin değeri bulunamadi");
        System.out.println("expectedValue: " + expectedValue);
        System.out.println("actualValue: " + attributeValue);
        assertEquals(expectedValue, attributeValue,"Elementin değeri eslesmedi");
    }

    @Step("<key> elementinin <attribute> niteliği <expectedValue> değerini içeriyor mu")
    public void checkElementAttributeContains(String key, String attribute, String expectedValue) {

        expectedValue = expectedValue.endsWith("KeyValue") ? Driver.TestMap.get(expectedValue).toString() : expectedValue;
        String attributeValue = methods.getAttribute(methods.getBy(key), attribute);
        assertNotNull(attributeValue,"Elementin değeri bulunamadi");
        System.out.println("expectedValue: " + expectedValue);
        System.out.println("actualValue: " + attributeValue);
        assertTrue(attributeValue.contains(expectedValue)
                ,expectedValue + " elementin değeriyle " + attributeValue + " eslesmedi");
    }

    @Step("<key> mouseover element")
    public void mouseOver(String key) {

        methods.mouseOverJs(methods.getBy(key),"1");
    }

    @Step("<key> mouseout element")
    public void mouseOut(String key) {

        methods.mouseOutJs(methods.getBy(key),"1");
    }

    @Step("<key> hover element")
    public void hoverElementAction(String key) {

        methods.hoverElementAction(methods.getBy(key), true);
    }

    @Step("<key> hover element <isScrollElement>")
    public void hoverElementAction(String key, boolean isScrollElement) {

        methods.hoverElementAction(methods.getBy(key), isScrollElement);
    }

    @Step("<key> hover and click element")
    public void moveAndClickElement(String key) {

        methods.moveAndClickElement(methods.getBy(key), true);
    }

    @Step("<key> scroll element")
    public void scrollElementJs(String key) {

        methods.scrollElementJs(methods.getBy(key),"3");
    }

    @Step("<key> scroll element center")
    public void scrollElementCenterJs(String key) {

        methods.scrollElementCenterJs(methods.getBy(key),"3");
    }

    @Step("<key> focus element")
    public void focusElement(String key) {

        methods.focusElementJs(methods.getBy(key));
    }

    @Step("<key> select element by index <index>")
    public void selectElementByIndex(String key, int index) {

        methods.selectByIndex(methods.getBy(key), index);
    }

    @Step("<key> select element by value <value>")
    public void selectElementByValue(String key, String value) {

        methods.selectByValue(methods.getBy(key), value);
    }

    @Step("<key> select element by text <text>")
    public void selectElementByText(String key, String text) {

        methods.selectByVisibleText(methods.getBy(key), text);
    }

    @Step("<key> select element by index <index> js")
    public void selectElementByIndexJs(String key, int index) {

        methods.selectByIndexJs(methods.getBy(key), index,"3");
    }

    @Step("<key> select element by value <value> js")
    public void selectElementByValueJs(String key, String value) {

        methods.selectByValueJs(methods.getBy(key), value,"3");
    }

    @Step("<key> select element by text <text> js")
    public void selectElementByTextJs(String key, String text) {

        methods.selectByTextJs(methods.getBy(key), text,"3");
    }

    @Step("Open new tab <url>")
    public void openNewTabJs(String url){

        methods.openNewTabJs(url);
    }

    @Step("Switch tab <switchTabNumber>")
    public void switchTab(int switchTabNumber){

        methods.switchTab(switchTabNumber);
    }

    @Step("<key> switch frame element")
    public void switchFrameWithKey(String key) {
        methods.switchFrameWithKey(methods.getBy(key));
    }

    @Step("Switch default content")
    public void switchDefaultContent() {
        methods.switchDefaultContent();
    }

    @Step("Switch parent frame")
    public void switchParentFrame() {
        methods.switchParentFrame();
    }

    @Step("Navigate to refresh")
    public void navigateToRefresh(){

        methods.navigateToRefresh();
    }

    @Step("Navigate to back")
    public void navigateToBack(){

        methods.navigateToBack();
    }

    @Step("Navigate to forward")
    public void navigateToForward(){

        methods.navigateToForward();
    }

    @Step("Get Cookies")
    public void getCookies(){

        logger.info("cookies: " + methods.getCookies().toString());
    }

    @Step("Delete All Cookies")
    public void deleteAllCookies(){

        methods.deleteAllCookies();
    }

    @Step("Get User Agent")
    public void getUserAgent(){

        logger.info("userAgent: " + methods.jsExecuteScript("return navigator.userAgent;", false).toString());
    }

    @Step("Get page source")
    public void getPageSource(){

        logger.info("pageSource: " + methods.getPageSource());
    }

    @Step("Get performance logs <logContainText>")
    public void getPerformanceLogs(String logContainText){

        LogEntries les = Driver.driver.manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry le : les) {
            //if(le.getMessage().contains("\"method\":\"Network.responseReceived\"")) {
            if(le.getMessage().contains(logContainText)){
                System.out.println(le.getMessage());
            }
        }
    }

    @Step("Get Navigator Webdriver")
    public void getNavigatorWebdriver(){

        Object object = methods.jsExecuteScript("return navigator.webdriver;", false);
        logger.info("NavigatorWebdriver: " + (object == null ? "null" : object.toString()));
    }

    @Step("Get Navigator Webdriver Json")
    public void getNavigatorWebdriverJson(){

        Object object = methods.jsExecuteScript("return Object.defineProperty(navigator, 'webdriver', { get: () => undefined });", false);
        logger.info("NavigatorWebdriverJson: " + (object == null ? "null" : object.toString()));
    }


    @Step("Sekmeyi kapat")
    public void closeTab(){
        methods.close();

    }

    @Step("Tablar kontrol edilir")
    public void tabControl(){
        for (int i = 0; i < 20; i++){
            methods.waitByMilliSeconds(400);
            if (methods.listTabs().size() > 1){
                break;
            }
        }
    }

    @Step("<key> elementi için <deltaY> deltaY <offsetX> offsetX ve <offsetY> offsetY değerleriyle mouse tekerleğini kaydır")
    public void mouseWheel(String key, int deltaY, int offsetX, int offsetY){

        methods.getJsMethods().wheelElement(methods.findElement(methods.getBy(key)), deltaY, offsetX, offsetY);
    }

    @Step("<key> elementi için <deltaY> deltaY <offsetX> offsetX ve <offsetY> offsetY değerleriyle mouse tekerleğini kaydır 2")
    public void mouseWheelSimple(String key, int deltaY, int offsetX, int offsetY){

        methods.getJsMethods().wheelElementSimple(methods.findElement(methods.getBy(key)), deltaY, offsetX, offsetY);
    }

    @Step("waitPageLoadCompleteJs")
    public void waitPageLoadCompleteJs(){

        methods.waitPageLoadCompleteJs();
    }

    @Step("<key> elementinin değerini <value> değeriyle değiştirerek <newKey> elementini oluştur")
    public void keyValueChangerMethod(String key, String value, String newKey){

        methods.keyValueChangerMethodWithNewElement(key,newKey,methods
                .getKeyValueChangerStringBuilder(value,"|!","KeyValue"),"|!");
    }

    @Step("<value> değeri <replaceValues> degerlerini temizle ve <mapKey> değerinde tut <trim>")
    public void replaceText(String value, String replaceValues, String mapKey, boolean trim){

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        String[] splitValues = replaceValues.split("\\?!");
        for (String splitValue: splitValues){
            if (!splitValue.equals("")){
                value = value.replace(splitValue,"");
            }
        }
        value = trim ? value.trim() : value;
        Driver.TestMap.put(mapKey, value);
    }

    @Step("<value> degerini <mapKey> keyinde tut")
    public void saveData(String value, String mapKey) {

        value = value.endsWith("KeyValue") ? Driver.TestMap.get(value).toString() : value;
        Driver.TestMap.put(mapKey, value);
    }
    @Step("<key> elementine git")
    public void scroll(String key){
        methods.scrollWithAction(methods.getBy(key));
    }
    @Step("<key> elementinden random url ile gidiş")
    public void randomURL(String key){
        methods.randomChooseURL(methods.getBy(key));
    }

    @Step("<key> elementinde enter komutunu bas")
    public void enterKey(String key){
        driver.findElement(methods.getBy(key)).sendKeys(Keys.ENTER);
    }
    @Step("<key> elementine göre random geçiş")
    public void random(String key) {
        if (methods.checkElementClickable(methods.getBy(key))) {
            methods.randomChoose(methods.getBy(key));
        }
        else {
            methods.randomChoose(methods.getBy(key));
        }
    }
    @Step("<key> Elementi görünürse tıkla görünmezse <timeout> kadar bekle")
    public void islem(String key,long timeout){

      if (methods.isElementVisible(methods.getBy(key),timeout)){

              methods.clickElement(methods.getBy(key));

      }

            methods.isElementInVisible(methods.getBy(key),timeout);
    }

    @Step("<key> elementini <repeat> kadar tekrar et")
    public void tekrarla(String key, int repeat){
        for (int i=0; i<repeat;i++){
            if (methods.checkElementClickable(methods.getBy(key))) {
                methods.randomChoose(methods.getBy(key));
                methods.waitBySeconds(3);
            }
            else {
                methods.randomChoose(methods.getBy(key));
                methods.waitBySeconds(3);
            }
        }
    }

    @Step("<key> elementini yapıştır")
    public void ctrlV(String key){
        driver.findElement(methods.getBy(key)).click();
        driver.findElement(methods.getBy(key)).sendKeys(Keys.CONTROL+"V");
    }


}