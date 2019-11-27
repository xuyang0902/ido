###### 用kibana的devtools基本操作

        GET  获取
        PUT  改变
        POST 创建
        DELETE 删除
        HEAD 获取头部信息
        
        
        ###------等同
        curl -H "Content-Type:application/json"  -XPUT "http://192.168.1.121:9200/yuren_index/user/1" -H 'Content-Type: application/json' -d'
        {
          "name":"zhangsan",
          "age":"20"
        }'
        
         
        ##curl -H "Content-Type:application/json"  -XGET "http://192.168.1.121:9200/yuren_index/user/_search?pretty"
        
        
        #创建索引 必须全小写 不能_ - + 开头 不包含特殊字符
        PUT /yuren_index
        
        #新增数据 es会自动生成id
        POST /yuren_index/user/
        {
          "name":"zhangsan",
          "age":"19"
        }
        
        #修改数据 需要指定id
        PUT /yuren_index/user/1
        {
          "name":"zhangsan",
          "age":"20"
        }
     
        #获取数据
        GET /yuren_index/user/1
        
        #获取所有的数据
        GET /yuren_index/user/_search?pretty
       



########################
        ##匹配查询
        GET /yuren_index/user/_search
        {
          "query": {
            "match": {
              "name": "zhangsan"
            }
          }  
        }
        
        
        #mget  _source 只返回什么字段
        GET /_mget
        {
          "docs":[
            {
              "_index":"yuren_index",
              "_type":"user",
              "_source":"name",
              "_id":1
            }
            ]
        } 
         
            
###### bulk api实现批量操作



    discovery.zen

###### 9200 和 9300有什么区别？ 早期版本
    9300 tpc协议 集群间通信  java客户端--transport在9300上通信
    9200 http协议 restful接口访问端口

    
###### 6版本以上 没有9300端口