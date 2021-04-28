package Tasks.commons;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MasterParameter {

	List<File> getFilesInDirectory();

    void setIgnoredWords() throws IOException;

}
