package valuesmap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by i311352 on 7/18/2016.
 */
public class TreeMapper {
    public static void IterateObjectNode(ObjectNode objectNode) {
        Iterator<JsonNode> elements = objectNode.elements();
        Iterator<Map.Entry<String, JsonNode>> maps = objectNode.fields();
        while (maps.hasNext()) {
            Map.Entry<String, JsonNode> node = maps.next();
            System.out.println("{ \" " + node.getKey() + " \"");
            JsonNode jsonNode = node.getValue();
            IterateJsonNode(jsonNode);
            System.out.println("}");
        }
    }

    public static void IteratorArrayNode(ArrayNode arrayNode) {
        Iterator<JsonNode> elements = arrayNode.elements();
        while (elements.hasNext()) {
            JsonNode jsonNode = elements.next();
            IterateJsonNode(jsonNode);
        }
    }

    public static void IterateJsonNode(JsonNode jsonNode) {
        Iterator<String> fieldNames = jsonNode.fieldNames();
        while (fieldNames.hasNext()) {
            String name = fieldNames.next();
            JsonNode iterNode = jsonNode.get(name);
            System.out.println(" \"" + name + " \"");
            if (iterNode.isObject()) {
                System.out.print("{");
                IterateObjectNode((ObjectNode) iterNode);
                System.out.print("}");
            } else if (iterNode.isArray()) {
                System.out.print("[");
                IteratorArrayNode((ArrayNode) iterNode);
                System.out.print("]");
            } else {
                System.out.print(" " + iterNode.asText() + " ");
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(new File("resources.json"));
        IterateJsonNode(jsonNode);
//        System.out.println(jsonNode.getNodeType());
//        System.out.println(jsonNode.isContainerNode());
//
//        Iterator<String> fileNames = jsonNode.fieldNames();
//        while (fileNames.hasNext()) {
//            String fileName = fileNames.next();
//            System.out.println(fileName + " ");
//        }
//
//        System.out.println();
//        System.out.println(jsonNode.get("paths").asText());
//        JsonNode paths = jsonNode.get("paths");
//        System.out.println(paths.getNodeType());
//
//        fileNames = paths.fieldNames();
//        while (fileNames.hasNext()) {
//            String fieldName = fileNames.next();
//            if (paths.get(fieldName).isObject()) {
//                System.out.println("get object node " + fieldName);
//                ObjectNode objPaths = (ObjectNode) paths.get(fieldName);
//                IterateObjectNode(objPaths);
//            } else if (paths.get(fieldName).isArray()) {
//                System.out.println("get array node " + fieldName);
//                ArrayNode arrPaths = (ArrayNode) paths.get(fieldName);
//            }
//
//        }
    }
}
