package lession.app.context;

import org.springframework.stereotype.Component;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by i311352 on 7/9/16.
 */
@Component
public class FileReadingService {

    public List<String> readFile(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
