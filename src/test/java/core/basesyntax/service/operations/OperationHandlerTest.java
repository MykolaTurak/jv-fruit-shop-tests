package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {

    @AfterEach
    public void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    public void no_such_fruit_not_ok() {
        Storage.STORAGE.clear();

        OperationHandler returnOperation = new ReturnOperation();

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> returnOperation.run(
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)));

        assertEquals("Can't find fruit: apple", exception.getMessage());
    }
}
