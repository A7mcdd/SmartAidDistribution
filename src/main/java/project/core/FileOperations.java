package project.core;
import java.io.IOException;

public interface FileOperations {
	void saveToTextFile (String filename) throws IOException;
	void loadFromTextFile (String filename) throws IOException;
	void saveToBinaryFile (String filename) throws IOException;
	void loadFromBinaryFile (String filename) throws IOException;

}
