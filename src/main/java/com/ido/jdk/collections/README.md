# JDK常用的集合类

---
* ArrayList: 变长的数组【非线程安全的】 查询效率高 插入删除效率低
* LinkedList: 双向链表【非线程安全的】  插入 删除 效率高  查询效率低
* Vector:  可以理解为线程安全的ArrayList
* CopyOnWriteArrayList:读取无锁，修改的时候加锁，每次copy一个新的数组 add
    
    
        并发环境下想使用linkedList
        Collections./synchronizedCollection/(new LinkedList());
        
---
* HashMap: 数组+链表【非线程安全的】
	1. jdk1.8 引入红黑树 （先横向 table扩展，再node链表扩展，两者都到一定程度了，node变成红黑树，查找的性能增加）
* HashTable:线程安全的hashmap  synchronized方法
* ConcurrentHashMap:
 	1. jdk1.7:
 	 final Segment<K,V>[] segments; //核心数据结构
	 可以简单理解key定位到segment的位置，put前 会lock住
	 这样的话，每个segment是可以支持并发写和读的，但是读取效率依旧比较低
 	2. jdk1.8抛弃了原有的 Segment 分段锁，而采用了 CAS[table] + synchronized[操作数据] 来保证并发安全性。
* TreeMap:基于红黑树的实现【非线程安全】
* ConcurrentSkipListMap:基于跳跃表实现的Map【线程安全】
* TreeSet:基于TeeMap的实现【非线程安全】
* ConcurrentSkipListSet：基于ConcurrentSkipListMap的实现【线程安全】



---


* Queue:FIFO  LIFO  队列
* Deque：双向队列


---





#数据结构