package cc.yuanspace.yaml.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class WebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> exceptionHandle(Exception e) {
        log.error("exception", e);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", "500");
        map.put("msg", e.getMessage());
        return map;
    }
}
