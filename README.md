# JarsCrawler
爬取阿里maven中央仓库的所有jar包
## 使用方法
- 添加爬取地址
    * 在crawlers.text配置文件中添加爬取地址,地址是:http://maven.aliyun.com/nexus/content/groups/public/ 下的jar包目录
    * 例如:爬取org开头的jar包,http://maven.aliyun.com/nexus/content/groups/public/org
    * 爬取com开头的就是http://maven.aliyun.com/nexus/content/groups/public/com
- 启动服务即可

*注意只能固定http://maven.aliyun.com/nexus/content/groups/public/ 开头,即http://maven.aliyun.com/nexus/content/groups/public/xxx 这个目录规则*
*该项目仅供学习交流*
