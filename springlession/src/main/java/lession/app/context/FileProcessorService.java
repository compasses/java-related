package lession.app.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by i311352 on 7/9/16.
 */
@Component
public class FileProcessorService {

    @Autowired
    private FileReadingService frs;

    public void processFile(String fileName) {
        List<String> lines = frs.readFile(fileName);
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
