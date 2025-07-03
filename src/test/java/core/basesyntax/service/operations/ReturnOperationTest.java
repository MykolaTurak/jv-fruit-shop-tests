package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    @AfterEach
    public void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    public void return_operation_ok() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));

        OperationHandler returnOperation = new ReturnOperation();
        returnOperation.run(new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 20));

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();

        String expected = "banana,40";

        assertEquals(expected, actual);
    }
}
