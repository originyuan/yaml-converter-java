package cc.yuanspace.yaml.model;

import lombok.Data;


public class PropertiesResult {
    private String propStr;
    // 是否存在特殊符号，占位符、引用classpath
    private boolean existsSpecial;

    public PropertiesResult(String propStr) {
        this.propStr = propStr;
    }

    public void existsSpecial(boolean existsSpecial) {
        this.existsSpecial = existsSpecial;
    }

    public String getPropStr() {
        return propStr;
    }

    public boolean isExistsSpecial() {
        return existsSpecial;
    }

    @Override
    public String toString() {
        return "PropertiesResult{" +
                "propStr='" + propStr + '\'' +
                ", existsSpecial=" + existsSpecial +
                '}';
    }
}
