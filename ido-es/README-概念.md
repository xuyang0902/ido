
###### ElasticSearch
        分布式搜索引擎
        全文检索 结构化检索 数据分析
        对海量数据进行实时分析
        es基于lucene 提供restful接口 java api接口
        开箱即用
    
######  核心概念
       Near Realtime （NRT）
       Node 
       Index  数据库
       Type 表
       Document 行
            filed   mapping
            
###### 倒排索引
       分词 词有唯一的id
       <单词id>  <单词>  <单词出现的文档,文档中的位置，出现的次数>,<单词出现的文档,文档中的位置，出现的次数>
       
       单词 不区分大小写，同义词转换
        
###### 分词器
        character filter 分词器 过滤调html标签 特殊符合
        tokenizer 分词
        token filter 标准化 同义词 大小写转换
        
        内置分词器
            standard（默认的） 大小写转换，去除停用词 标点符号 支持 中文 单字分割
            simple 
            whitespace 去除空格 不支持中文
            language 不支持中文
            
        IK分词器 
            git clone https://github.com/medcl/elasticsearch-analysis-ik.git
            
            mvn clean install -DSkipTest
            
            
            把target/release下的zip包 copy到es的安装目录的plugin目录下
            创建文件夹ik
            把zip包内容解压到ik
             scp /usr/local/opt/elasticsearch-analysis-ik-master/target/releases/elasticsearch-analysis-ik-7.4.0.zip /usr/local/opt/elasticsearch654/plugins/

            cd /usr/local/opt/elsaticsearch654/plugins/
            mkdir ik
            unzip -d ik elasticsearch-analysis-ik-7.4.0.zip

            
            重启es 表示配置好ik了
            

            
          
