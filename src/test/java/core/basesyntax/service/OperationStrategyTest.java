package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        strategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    public void get_operation_handler_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);

        Class<? extends OperationHandler> actual = strategy.getOperationHandler(transaction)
                .getClass();
        Class<? extends OperationHandler> expected = BalanceOperation.class;

        assertEquals(expected, actual);
    }

    @Test
    public void get_operation_handler_invalid_should_throw_exception() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 100);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> strategy.getOperationHandler(transaction));

        assertEquals("Operation handler for SUPPLY not found", exception.getMessage());
    }
}
