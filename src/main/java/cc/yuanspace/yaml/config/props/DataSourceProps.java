package cc.yuanspace.yaml.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "my.datasource")
public class DataSourceProps {

    private List<SubDataSourceProp> list;

    @Data
    public static class SubDataSourceProp{
        private String name;
        private String url;
        private Long timeout;
    }
}
