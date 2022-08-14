package cc.yuanspace.yaml.factory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectMapperFactory {

    /**
     * 获取 json 的 ObjectMapper
     * @return
     */
    public static ObjectMapper getJsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 格式化输出json
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 不输出null值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
