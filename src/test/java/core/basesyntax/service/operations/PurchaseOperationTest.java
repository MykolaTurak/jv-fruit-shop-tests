package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {

    @AfterEach
    public void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    public void purchase_operation_ok() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));

        OperationHandler purchaseOperation = new PurchaseOperation();
        purchaseOperation.run(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actual = reportGenerator.getReport();

        String expected = "banana,10";

        assertEquals(expected, actual);
    }

    @Test
    public void purchase_not_enough_product_should_throw_exception() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));

        OperationHandler purchaseOperation = new PurchaseOperation();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseOperation.run(new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, "banana", 40)));

        assertEquals("Too little of product: banana", exception.getMessage());
    }
}
