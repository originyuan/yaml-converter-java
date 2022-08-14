### 转换 YAML 为 Properties 的小工具

主要是解决 yaml 的数组转换问题，网上的转换方法都不能很好的将 yaml 数组转换为 properties，不是缺就是格式混乱。

用了一个简单的方法处理 yaml 数组，先将 yaml 转换为 json，然后遍历 json 树，构造 properties 的 key。

只要能正确转换为 json 就能转换为 properties。

这里是使用 jackson 解析 yaml 为 json 数据


直接运行 `yaml-converter-java.jar` 访问 [http://localhost:8080/](http://localhost:8080/) （一个简陋的操作页面）



