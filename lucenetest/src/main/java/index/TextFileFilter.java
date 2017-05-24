package index;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by i311352 on 5/23/2017.
 */
public class TextFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".txt");
    }
}
