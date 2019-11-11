##### 本篇我们主要分析ReentrantLock，在进入源码阅读前，我们需要知道几个核心的知识点，这样对我们阅读源码有一定的帮助。

* **AbstractQueuedSynchronizer-AQS的核心设计**  

```
// 头结点，可以简单理解为持有锁的节点
private transient volatile Node head;

// 阻塞的尾节点，每个进来等待的节点都加到尾部，形成一个链表
private transient volatile Node tail;

// 表示当前锁的状态，0代表没有被占用，大于 0 代表有线程持有当前锁
// 这个值可以大于 1，是因为锁可以重入，每次重入都加上 1
private volatile int state;

// 当前持有锁的线程信息，用作重入判断
//继承自AbstractOwnableSynchronizer
private transient Thread exclusiveOwnerThread;
```

* Node节点 阻塞队列的成员
```
static final class Node {
    // 标识节点当前在共享模式下
    static final Node SHARED = new Node();
    // 标识节点当前在独占模式下
    static final Node EXCLUSIVE = null;

    // ======== 下面的几个int常量是给waitStatus用的 ===========
    //代表改节点取消抢锁了
    static final int CANCELLED =  1;
    //代表改节点的后继节点需要被唤醒
    static final int SIGNAL    = -1;
    //本篇不分析
    static final int CONDITION = -2;
    //本篇不分析
    static final int PROPAGATE = -3;
    // =====================================================


   //节点的等待状态  默认 初始化是0
    volatile int waitStatus;
    // 前驱节点的引用
    volatile Node prev;
    // 后继节点的引用
    volatile Node next;
    // 当前线程对象
    volatile Thread thread;

}

```

##### 我们先以公平锁的列子来看lock的代码
```
//从构造器可以看到我们拿到是FairSync类
private static final ReentrantLock lock = new ReentrantLock(true);

public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}

//使用方式

//抢锁
lock.lock
try{

}finally{
  //释放锁【释放锁必须和抢锁在一个线程内】
	lock.unlock();
}
```

##### 先分析lock方法
```
//跟进ReentrantLock的lock方法

public void lock() {
    sync.lock();
}

//很明显要看FairSync的lock方法
static final class FairSync extends Sync {

    final void lock() {
        //改方法集成字AQS，copy过来直接分析
        acquire(1);
    }

    public final void acquire(int arg) {

        //step1 尝试抢锁，如果成功了，方法就返回了，失败了需要看step2
        //step2 addWaiter(Node.EXCLUSIVE)
        //step3 acquireQueued(addWaiter(Node.EXCLUSIVE), arg)
        // step2 step3 代码在AQS中，下面分析 
        if (!tryAcquire(arg) &&
             acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        //  第一次没抢到锁，入列后抢锁，且被中断唤醒后抢到锁，需要中断自己
        selfInterrupt();
    }

    //尝试抢锁
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        //判断锁是否已经被占用
        if (c == 0) {
            //未被占用的时候，看下是否人在排队，没有的话 直接CAS抢锁
            //为什么叫公平？因为如果有人排队的话，她乖乖直接去队列等通知
            if (!hasQueuedPredecessors() &&
                compareAndSetState(0, acquires)) {
                //设置占有锁的线程信息
                setExclusiveOwnerThread(current);
                //抢到锁了
                return true;
            }
        }
        //如果锁已经被占用了，看下是不是自己线程拿的，可重入 state + 1
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            //重入成功 
            return true;
        }
        
        //没拿到锁
        return false;
    }
}

```

```
//AQS核心方法分析

//mode 上层转过来是独占其实是个null 这里暂时忽略
private Node addWaiter(Node mode) {
    //构建当前节点的信息
	  Node node = new Node(Thread.currentThread(), mode);
    Node pred = tail;
    if (pred != null) {
        //如果前置节点不为空，把自己变为tail，构建链表 
        //CAS 多个节点并发入列的时候 可能失败 
        node.prev = pred;
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    //前置节点没有，或者cas没有入列成功，走到这里
    enq(node);
    return node;
}

//本质上是一个死循环，把当前node入列后变成tail 
//第一次进来的时候 会初始化head  且 tail = head 
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}

//这个方法是核心方法哦 好好看
// node 抢锁线程的节点，arg = 1
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        //判断是否被中断过
        boolean interrupted = false;
        for (;;) {
            //取当前节点的前置节点，如果它已经是head了，那可以尝试抢锁
            //尝试抢锁的代码上面分析过了，不在分析
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                //抢到锁了，那么自己就是head
                setHead(node);
                p.next = null; // help GC  前置节点去掉 不要了
                failed = false; 
                //返回中断标志
                return interrupted;
            }
            //当没抢到锁park住
            //因为park了，所以要检查下park是不是被中断唤醒的
            //如果是被中断唤醒的 interrupted = true; 
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}

private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    //pred前置节点
    //node当前节点

    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
        //需要被通知 
        return true;
    if (ws > 0) {
        //>0的只有1  是取消的意思
        //一直往前找，过滤已经取消的前置节点 取消的 队列中不需要了
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        //告诉前置节点，我需要被通知
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}

private final boolean parkAndCheckInterrupt() {
    //当前线程挂起，等待通知
    LockSupport.park(this);
    //判断当前线程是否被中断，且清除中断标志位
    return Thread.interrupted();
}

//到此，lock方法的核心流程 debug 结束了，当然，你可能看的有点懵逼，
//没关系 我们再来看unlock 看完之后结合起来理一下
```
##### 再分析unlock方法
```
//公平锁中没有实现release所以很简单 进到AQS的release方法
public void unlock() {
    sync.release(1);
}

//AQS的release方法
public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        //当heade不为空，且状态不是初始化的，说明有后面的节点需要被通知
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}

//进到Sync的tryRelease
//很简单 不一行一行分析了，state-1
//不是占有锁的线程是无法释放锁的，对应我们一开始锁的unlock一定要和lock在一个线程中
//当state=0 完全释放锁了，把占有线程的信息设置为null 给别的线程抢呗
protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}

#进入AQS的 unparkSuccessor方法
private void unparkSuccessor(Node node) {
    
    //当heade节点状态为负的时候，重置为0
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    //取后继节点，如果状态是取消的 需要遍历tail
    //从后网签遍历，一直遍历到head，取状态为负数的最靠前的那个节点
    Node s = node.next;
    if (s == null || s.waitStatus > 0) {
        s = null;
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    
    //唤醒需要被通知的后继节点
    if (s != null)
        LockSupport.unpark(s.thread);
}

```

- - - -
##### 总结思考？
* 0、AQS的state >0 表示被抢锁 =0表示没有现成占用锁
* 1、exclusiveOwnerThread保证了lock可重入，重入state ++
* 2、等待节点第一次入列的时候 会告诉前置节点需要通知我，然后再尝试抢一次锁，抢不到后，直接park住 等待通知
* 3、一开始head初始化假设为N
      阻塞队列的应该是  N <—> A <—> B <—> C【tail】
      A--park前需要告诉N记得通知我
      B--park前需要告诉A记得通知我
      以此类推，如果没有 没有告诉前置节点成功，那么当前节点会判断前置节点是不是head，如果是的话 尝试抢锁，否则继续告诉前置节点
* 4、为什么unlock通知后继节点的时候 当head状态为负数的时候，需要cas设置为0 且不一定保证成功—todo


分析完公平锁，我们来看下非公平锁

```
 static final class NonfairSync extends Sync {

	  //什么叫非公平？
    //管你有没有排队，进来就先抢一下  
    final void lock() {
        if (compareAndSetState(0, 1))
            setExclusiveOwnerThread(Thread.currentThread());
        else
            acquire(1);
    }

    protected final boolean tryAcquire(int acquires) {
         //sync中的实现
        return nonfairTryAcquire(acquires);
    }
}

final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        //非公平啊，只要有机会就抢
        //可能前一个刚释放，通知后继节点抢锁的过程中
        //有新节点进来了，直接抢,我要插队
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}




```

###### 对阻塞队列流程的梳理


![对阻塞队列流程的梳理]( /ido-base/doc/aqs阻塞队列.png)


1. 没抢到锁，安心入列
2. 入列后判断前驱节点是否为head，如果是直接尝试抢锁，抢到锁返回，没抢到锁进入第三步
3. 告诉前驱节点你释放锁的时候记得通知我【修改前驱的waitingStatus= -1】
4. park，让出cpu资源
5. 前驱节点unlock的时候 unpark后置节点线程







#jdk