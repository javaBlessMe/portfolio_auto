package Base;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.io.ByteArrayInputStream;

public class TestListener implements TestWatcher {
    private ByteArrayInputStream byteArrayInputStream;
    public void setFailScreenshot(byte[] bytes){
        byteArrayInputStream = new ByteArrayInputStream(bytes);
    }
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) { // В случае, если тест упал, в отчет добавится скриншот
        Allure.addAttachment("Скриншот перед закрытием", byteArrayInputStream);
    }
}
