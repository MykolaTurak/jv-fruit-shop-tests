package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    @AfterEach
    public void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    public void balance_operation_ok() {
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.run(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));

        Integer actualQuantity = Storage.STORAGE.get("banana");
        Integer expectedQuantity = 20;

        assertEquals(expectedQuantity, actualQuantity);
    }
}
