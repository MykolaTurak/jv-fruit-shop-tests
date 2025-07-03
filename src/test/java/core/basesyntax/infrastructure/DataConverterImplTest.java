package core.basesyntax.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static DataConverter converter;

    @BeforeAll
    static void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    public void convert_to_transaction_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

        List<String> input = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<FruitTransaction> actual = converter.convertToTransaction(input);

        assertEquals(expected, actual);
    }

    @Test
    public void not_integer_value_should_throw_exception() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,wrong");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(list));

        assertEquals("Invalid number format: 'wrong'. Expected an integer value.",
                exception.getMessage());
    }

    @Test
    public void negative_value_should_throw_exception() {
        List<String> list = new ArrayList<>();
        list.add("p,banana,-10");

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(list));

        assertEquals("Error! Number can't be less than zero", exception.getMessage());
    }
}
