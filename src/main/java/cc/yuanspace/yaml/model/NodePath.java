package cc.yuanspace.yaml.model;

/**
 * 节点路径
 */
public class NodePath {

    private String pathName;
    private boolean isArray;
    private int index;

    public NodePath(String pathName) {
        this(pathName, false);
    }

    public NodePath(String pathName, boolean isArray) {
        this.pathName = pathName;
        this.isArray = isArray;
        this.index = 0;
    }

    public void setIsArr() {
        this.isArray = true;
    }

    public String getPath() {
        String s = pathName;
        if (isArray) {
            s += "[" + index + "]";
        }
        return s;
    }

    public void next() {
        this.index++;
    }
}
