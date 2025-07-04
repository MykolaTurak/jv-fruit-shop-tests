package core.basesyntax.service.operations;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void run(FruitTransaction fruitTransaction) {
        Storage.STORAGE.put(fruitTransaction.getFruit(),
                Storage.STORAGE.get(fruitTransaction.getFruit()) + fruitTransaction.getQuantity());
    }
}
