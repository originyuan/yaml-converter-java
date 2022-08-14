package cc.yuanspace.yaml.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "my.app")
public class AppProperties {
    private List<User> ulist;
    private Map<String, User> umap;

    private List<String> keys;

    @Data
    public static class User{
        private String uname;
        private String usex;
        private List<String> keys;
    }
}
