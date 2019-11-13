###
    我司目前使用disconf
    disconf-client  和disconf-web通信，第一次扫描拉远程配置的信息，以及zk的信息，并且监听disconf节点，应用.env的变化
    disconf-web   配置数据，写到mysql【多个节点部署】 并且往zk通知数据变化
    zookeeper 监听通知用的【zk集群 】
    mysql   多副本 也是高可用
    
    简单看过代码，没必要深究，现在外面用apollo比较多，尝试看看apollo的代码
    
    16年开始就没有维护了。