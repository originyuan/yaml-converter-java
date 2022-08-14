package cc.yuanspace.yaml.conveter;

import cc.yuanspace.yaml.model.NodePath;
import cc.yuanspace.yaml.model.PropertiesResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * yaml 转换工具
 */
public class YamlConverter {
    private static final ObjectMapper yamlOm;
    private static final ObjectMapper jsonOm;

    static {
        yamlOm = new ObjectMapper(new YAMLFactory());
        jsonOm = new ObjectMapper();
        // 格式化输出json
        jsonOm.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static List<PropertiesResult> yaml2Prop(InputStream inputStream) {
        List<PropertiesResult> result = new ArrayList<>();
        try {
            JsonNode jsonNode = yamlOm.readTree(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            parseJson2Prop(jsonNode, new ArrayList<>(), result);
            dealSpecialSymbols(result);
        } catch (Exception e) {
            throw new RuntimeException("转换异常，请检查格式");
        }
        return result;
    }

    /**
     * yaml 转换 properties 工具
     * 使用json作为中转
     */
    public static List<PropertiesResult> yaml2Prop(String yamlContent) {
        List<PropertiesResult> result = new ArrayList<>();
        try {
            JsonNode jsonNode = yamlOm.readTree(yamlContent);
            parseJson2Prop(jsonNode, new ArrayList<>(), result);
            dealSpecialSymbols(result);
        } catch (Exception e) {
            throw new RuntimeException("转换异常，请检查格式");
        }
        return result;
    }

    /**
     * 递归处理json节点
     * @param jsonNode 节点
     * @param path 经过的树路径
     * @param result properties 结果集
     */
    private static void parseJson2Prop(JsonNode jsonNode, List<NodePath> path, List<PropertiesResult> result) {
        if (jsonNode.isValueNode()) {
            // 值节点构造properties属性并返回
            buildProp(jsonNode, path, result);
            return;
        }
        if (jsonNode.isArray()) {
            Iterator<JsonNode> iterator = jsonNode.iterator();
            NodePath curPath = path.get(path.size() - 1);
            curPath.setIsArr();
            while (iterator.hasNext()) {
                JsonNode next = iterator.next();
                parseJson2Prop(next, path, result);
                curPath.next();
            }
        } else {
            // 遍历节点
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> node = fields.next();
                String curPath = node.getKey();
                JsonNode subNode = node.getValue();
                path.add(new NodePath(curPath));
                parseJson2Prop(subNode, path, result);
                path.remove(path.size()-1);
            }
        }

    }

    private static void buildProp(JsonNode jsonNode, List<NodePath> path, List<PropertiesResult> result) {
        String nodeVal = jsonNode.asText();
        if (nodeVal == null || "null".equals(nodeVal)) {
            nodeVal = "";
        }
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("值【" + nodeVal + "】的路径为空");
        }
        StringBuilder sb = new StringBuilder();
        path.forEach(p -> sb.append(p.getPath()).append("."));
        sb.delete(sb.length()-1, sb.length());
        sb.append("=").append(nodeVal);
        result.add(new PropertiesResult(sb.toString()));
    }



    /**
     * 标记可能出现的特殊符号
     * 包括占位符引用 ${xxx}  文件引用 classpath:xxx.yml
     * 只做简单判断标记，不处理
     * todo 可以考虑替换${xxx}
     * @param result
     */
    public static void dealSpecialSymbols(List<PropertiesResult> result) {

        result.forEach(e -> {
            String propStr = e.getPropStr();
            if (propStr.contains("${") || propStr.contains("classpath:")) {
                e.existsSpecial(true);
            }
        });
    }


    public static String yaml2Json(InputStream inputStream){
        try {
            JsonNode jsonRoot = yamlOm.readTree(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            return jsonOm.writeValueAsString(jsonRoot);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String yaml2Json(String yamlContent) {
        try {
            JsonNode jsonNode = yamlOm.readTree(yamlContent);
            return jsonOm.writeValueAsString(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
