package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String TEST_PATH = "src/test/resources/test-database.csv";
    private static final String INCORRECT_PATH = "/incorrect/path";
    private static FileWriter fileWriter;

    @BeforeAll
    static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of(TEST_PATH));
    }

    @Test
    public void write_into_file_ok() throws IOException {
        String content = "expected message";
        fileWriter.write(content, TEST_PATH);

        List<String> actual = Files.readAllLines(Path.of(TEST_PATH));
        List<String> expected = List.of("expected message");

        assertEquals(expected, actual);
    }

    @Test
    public void incorrect_path_should_throw_exception() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write("some text", INCORRECT_PATH));

        assertEquals("Can't open the file: " + INCORRECT_PATH, exception.getMessage());
    }
}
