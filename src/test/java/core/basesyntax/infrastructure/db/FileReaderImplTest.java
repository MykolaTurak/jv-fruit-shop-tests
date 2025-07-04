package core.basesyntax.infrastructure.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String PATH = "src/main/resources/database.csv";
    private static final String INCORRECT_PATH = "/incorrect/path";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-file.csv";
    private static FileReader reader;

    @BeforeAll
    static void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    public void read_info_from_file_ok() throws IOException {
        String content = "banana,152\napple,90";
        Files.write(Path.of(PATH), content.getBytes());

        List<String> actual = reader.read(PATH);
        List<String> expected = List.of("banana,152", "apple,90");

        assertEquals(expected, actual);
    }

    @Test
    public void file_not_found_should_throw_exception() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reader.read(INCORRECT_PATH));
        assertEquals("Can't read from file", exception.getMessage());
    }

    @Test
    public void read_empty_file_ok() throws IOException {
        Path emptyFilePath = Path.of(EMPTY_FILE_PATH);
        Files.writeString(emptyFilePath, "");

        List<String> result = reader.read(emptyFilePath.toString());

        assertEquals(List.of(), result);

        Files.deleteIfExists(emptyFilePath);
    }

}
