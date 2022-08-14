package cc.yuanspace.yaml.runner;

import cc.yuanspace.yaml.config.props.AppProperties;
import cc.yuanspace.yaml.config.props.DataSourceProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private DataSourceProps dataSourceProps;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(appProperties);
        System.out.println(dataSourceProps);
    }


}
