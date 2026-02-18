package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonDataUtils {

    private static JsonNode rootNode;

    private static JsonNode getRootNode() {
        if (rootNode == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream is = JsonDataUtils.class.getResourceAsStream("/newsletter_data.json");
                rootNode = mapper.readTree(is);
            } catch (Exception e) {
                throw new RuntimeException("Failed to read test data from JSON", e);
            }
        }
        return rootNode;
    }

    public static Stream<Arguments> getInvalidEmails() {
        return getListFromNode("invalidEmails");
    }

    public static Stream<Arguments> getValidEmails() {
        return getListFromNode("validEmails");
    }

    public static String getSingleInvalidEmail() {
        return getListFromNode("invalidEmails").findFirst().get().get()[0].toString();
    }

    public static String getSingleValidEmail() {
        return getListFromNode("validEmails").findFirst().get().get()[0].toString();
    }

    private static Stream<Arguments> getListFromNode(String key) {
        try {
            JsonNode node = getRootNode().get(key);
            List<String> values = new ArrayList<>();
            if (node.isArray()) {
                for (JsonNode n : node) {
                    values.add(n.asText());
                }
            }
            return values.stream().map(Arguments::of);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read " + key + " from JSON", e);
        }
    }
}
