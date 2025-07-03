package core.basesyntax.service;

import core.basesyntax.service.operations.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperationHandler(FruitTransaction fruitTransaction) {
        OperationHandler handler = operationHandlerMap.get(fruitTransaction.getOperation());
        if (handler == null) {
            throw new RuntimeException("Operation handler for " + fruitTransaction.getOperation()
                    + " not found");
        }
        return handler;
    }
}
