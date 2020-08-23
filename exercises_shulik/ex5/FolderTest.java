import filesprocessing.DirectoryProcessor;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FolderTest {
    private final String sourceDir;
    private final String commandFile;
    private final String expectedOutput;
    private final String expectedError;
    private final Path testFolder;

    public FolderTest(Path testFolder, Path sourceDir, Path commandFile, Path expectedOutFile,
                      Path expectedErrFile) {
        this.testFolder = testFolder;
        this.sourceDir = sourceDir.toString();
        this.commandFile = commandFile.toString();
        String expectedError = "FAILED TO INIT TEST";
        String expectedOutput = "FAILED TO INIT TEST";
        try {
            expectedOutput = normalizeNewlines(new String(Files.readAllBytes(expectedOutFile), Charset.defaultCharset()));
            expectedError = normalizeNewlines(new String(Files.readAllBytes(expectedErrFile), Charset.defaultCharset()));
        } catch (IOException ex){
            System.err.println("Error with test infrastructure: " + ex);
        }
        this.expectedError = expectedError;
        this.expectedOutput = expectedOutput;
    }

    private static String normalizeNewlines(String data){
        return data.replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
    }

    @Override
    public String toString() {
        return testFolder.getFileName().toString();
    }

    public String toStringLong(){
        return testFolder.getFileName() + "\n[Command file] " + commandFile + "\n[Source file] " + sourceDir;
    }

    void run(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        DirectoryProcessor.main(new String[] { sourceDir, commandFile });

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));

        try {
            String outRes = normalizeNewlines(outContent.toString("UTF-8"));
            String outErr = normalizeNewlines(errContent.toString("UTF-8"));

            assertEquals(expectedError, outErr, "STDERR output mismatch\n" + this.toStringLong());
            assertEquals(expectedOutput, outRes, "STDOUT output mismatch\n" + this.toStringLong());


        } catch (UnsupportedEncodingException e){
            System.err.println("Error in test infrastructure" + e);
        }
    }
}
