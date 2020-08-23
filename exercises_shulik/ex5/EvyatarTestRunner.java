import filesprocessing.DirectoryProcessor;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EvyatarTestRunner {
    static class MainTest {
        public MainTest(Path commandFile, Path sourceDir){


            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            ByteArrayOutputStream errContent = new ByteArrayOutputStream();

            System.setOut(new PrintStream(outContent));
            System.setErr(new PrintStream(errContent));

            DirectoryProcessor.main(new String[] { sourceDir.toString(), commandFile.toString() });

            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));

            String out = "", error = "";
            try {
                out = outContent.toString("UTF-8");
                error = errContent.toString("UTF-8");
            } catch (UnsupportedEncodingException ex){
                fail("Unsupported encoding, couldn't run test");
            }
            this.out = out;
            this.error = error;

        }

        private final String out;
        private final String error;

        String getOut() { return out; }
        String getError() { return error; }
    }
    static class TestRunner {
        private static final Path complexFiles = Paths.get("tests/tester_files/files_to_filter/complex");
        private static final Path simpleFiles = Paths.get("tests/tester_files/files_to_filter/simple");
        private static final String errorFolder = "tests/tester_files/tests_error_type_2";
        private static final String testsFolders = "tests/tester_files/tests";


        @ParameterizedTest
        @MethodSource("testStream")
        void runTest(FolderTest test){
            test.run();
        }


        static final String[] WINDOWS_ONLY_TESTS = new String[]{
                "zzz-test-by-school_filter054.flt",
                "zzz-test-by-school_filter057.flt",
                "zzz-test-by-school_filter060.flt",
                "test11_writble_hidden_excutable"
        };

        private static Predicate<File> folderTestPredicate(){
            boolean onWindows = System.getProperty("os.name").startsWith("Windows");
            if (onWindows) {
                return f -> true;
            }
            return (f -> Stream.of(WINDOWS_ONLY_TESTS).noneMatch(
                    testFolder -> f.toPath().endsWith(testFolder)
            ));

        }

        @Test
        void testIsOnWindows(){
            boolean onWindows = System.getProperty("os.name").startsWith("Windows");
            if (!onWindows) {
                fail(WINDOWS_ONLY_TESTS.length + " tests can only be run on windows, but you're on a different OS," +
                        " so they're being ignored.");
            }
        }

        @ValueSource
        static Stream<FolderTest> testStream() {
            File testsFolderDir = new File(testsFolders);
            File[] testSubfolders = testsFolderDir.listFiles();
            if (testSubfolders == null){
                fail("Couldn't find any test folders under \"" + testsFolderDir.toPath() + "\". Make sure the path is valid and accessible.");
            }
            return Arrays.stream(testSubfolders)
                    .filter(folderTestPredicate())
                    .flatMap(file -> {

                        FolderTest simple = new FolderTest(
                                file.toPath(), simpleFiles, file.toPath().resolve("commands.txt"),
                                file.toPath().resolve("simple_school_solution_output.txt"),
                                file.toPath().resolve("simple_school_solution_errors.txt")
                        );

                        FolderTest complex = new FolderTest(
                                file.toPath(), complexFiles, file.toPath().resolve("commands.txt"),
                                file.toPath().resolve("complex_school_solution_output.txt"),
                                file.toPath().resolve("complex_school_solution_errors.txt")
                        );
                         return Stream.of(simple, complex);
                    });
        }

        @ParameterizedTest
        @MethodSource("errorCommandFiles")
        void testErrorType2(File errorCommandFile){
                MainTest test = new MainTest(errorCommandFile.toPath(), simpleFiles);
                assertEquals("", test.getOut(), "There should be no STDOUT output upon Type I error.");
                String[] errorParts = test.getError().split("\\n|\\r\\n?");
                if (errorParts.length != 1){
                    fail("Expected exactly one line in STDERR in case of a type II error.");
                }
                assertTrue(errorParts[0].startsWith("ERROR: "), "STDERR should start with \"Error: \", got " + test.getError() +  "instead.");
        }

        @ValueSource
        static Stream<File> errorCommandFiles(){
            File errorFolderDir = new File(errorFolder);
            File[] errorCommandFiles = errorFolderDir.listFiles();
            if (errorCommandFiles == null){
                fail("Couldn't find/access command files under \"" + errorFolder + "\" - are you sure the path is valid and accessible?");
            }
            return Stream.of(errorCommandFiles);
        }
    }

}
