package econo.webper.server.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonExtractorTest {

    @Test
    public void getValueByKeyTest() {
        String jsonData = "{ \"key\" : \"value\" }";

        Object value = JsonExtractor.getValueByKey(jsonData, "key");
        assertThat(value).isEqualTo(value);
    }

}