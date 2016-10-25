package valuesmap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by I311352 on 8/24/2016.
 */
public class EShopAPILogParser {
    private static String filename = "C:\\Users\\i311352\\Downloads\\eshop-eshop-2702105216-ch8it-00_api-2016-08-23.log";

    public static void main(String args[]) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pair = 0;


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        long lasttimestamp;
        long endtimesStamp;
        try {
            for (int i = 0; i < lines.size(); ++i) {
                String line = lines.get(i);

                JsonNode jsonNode = objectMapper.readTree(line);
                JsonNode node = jsonNode.get("message");
                if (node.asText().contains("miscCheck")) {
                    System.out.println(jsonNode.get("datetime").asText());

                    Date date = format.parse(jsonNode.get("datetime").asText());
                    lasttimestamp = date.getTime();
                    //System.out.println("got date " + date.toString());
                    JsonNode jsonNode1 = objectMapper.readTree(lines.get(i+1));

                    String lines1 = jsonNode1.get("datetime").asText();
                    if (jsonNode1.get("message").asText().contains("HTTP/1.1 200 OK")) {
                        Date date1 = format.parse(lines1);
                        //System.out.println("got end date " + date1.toString());

                        endtimesStamp = date1.getTime();
                        System.out.println((endtimesStamp - lasttimestamp));
                        System.out.println(lines1);
                        i++;
                    } else {
                        //System.out.println("failed. " + jsonNode1.get("message").asText());
                    }

                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        lines.stream().forEach(line -> {
//            long lasttimestamp;
//            long endtimesStamp;
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//
//            try {
//                JsonNode jsonNode = objectMapper.readTree(line);
//                JsonNode node = jsonNode.get("message");
//                if (node.asText().contains("getATS")) {
//                    System.out.println(node.asText());
//                    Date date = format.parse(jsonNode.get("datetime").asText());
//                    lasttimestamp = date.getTime();
//                    System.out.println(lasttimestamp);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });
        System.out.println(lines.size());
        //JsonNode jsonNode =
    }
}
