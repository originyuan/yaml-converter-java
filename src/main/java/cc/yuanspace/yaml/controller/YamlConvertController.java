package cc.yuanspace.yaml.controller;

import cc.yuanspace.yaml.conveter.YamlConverter;
import cc.yuanspace.yaml.model.PropertiesResult;
import cc.yuanspace.yaml.vo.ConvertResult;
import cc.yuanspace.yaml.vo.YamlContentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/convertYaml")
@RestController
public class YamlConvertController {

    @PostMapping("/convertYaml")
    public Map<String, Object> convertYaml(@RequestParam("yamlContent") String yamlContent) {

        log.info("前端传递的数据为：\r\n {}", yamlContent);

        List<PropertiesResult> propertiesResults = YamlConverter.yaml2Prop(yamlContent);
        ConvertResult result = buildRes(propertiesResults);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "success");
        map.put("data", result);
        return map;
    }

    private ConvertResult buildRes(List<PropertiesResult> propertiesResults) {
        StringBuilder props = new StringBuilder();
        StringBuilder specialProps = new StringBuilder();
        for (PropertiesResult propertiesResult : propertiesResults) {
            props.append(propertiesResult.getPropStr()).append("\r\n");
            if (propertiesResult.isExistsSpecial()) {
                specialProps.append(propertiesResult.getPropStr()).append("\r\n");
            }
        }
        ConvertResult res = new ConvertResult();
        res.setProps(props.toString());
        res.setSpecialProps(specialProps.toString());
        return res;
    }


    @PostMapping("/convertYamlFromFile")
    public Map convertYamlFromFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<PropertiesResult> propertiesResults = YamlConverter.yaml2Prop(file.getInputStream());
        ConvertResult result = buildRes(propertiesResults);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "success");
        map.put("data", result);
        return map;
    }
}
