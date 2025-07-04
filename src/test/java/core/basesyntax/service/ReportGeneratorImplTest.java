package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.infrastructure.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {

    @AfterEach
    void cleanUpStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    public void get_report_ok() {
        Map<String, Integer> testData = new HashMap<>();
        testData.put("banana", 7);
        testData.put("apple", 110);
        Storage.STORAGE.putAll(testData);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();
        String expected = "banana,7\napple,110";

        assertEquals(expected, actual);
    }
}
