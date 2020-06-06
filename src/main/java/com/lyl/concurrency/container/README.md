# 容器
Java 中的容器主要可以分为四个大类，分别是 List、Map、Set 和 Queue。
有些是线程安全的、有些不是。
例如，我们常用的 ArrayList、HashMap 就不是线程安全的。而HashTable是线程安全的。
那么为什么ArrayList、HashMap不是线程安全的呢？
以ArrayList为例(com.lyl.concurrency.container.unsafe.ArrayListTest)：
假设线程A执行add()方法，add()方法中会做什么？首先肯定会获取ArrayList对象里真正存放元素的数组长度，如果数组放满了，扩容，没有的话，把元素放到数组对应的位置上。这两个步骤不是原子的。
假设线程A和线程B刚好同时执行到add()方法查出当前数组存放了n-1个元素，而数组的长度为n，所以没有存满，于是线程A继续执行要把元素放在数组n-1位置上，
线程B同样判断数组没满，获得的存放索引也是n-1，最后会怎样？一定有一个元素会被覆盖而丢失更新。

HashMap为什么不是线程安全的呢？一个原因和上述分析ArrayList类似，多线程同时执行put的时候可能会因为覆盖而导致数据不一致。
还有一个原因是HashMap的get操作可能因为resize而引起死循环（cpu100%），不过在jdk1.8修复了这个问题。

那么我们如何让线程不安全的容器变为线程安全呢？
其实也很好回答这个问题，可以把方法变成同步方法(加上synchronized),见com.lyl.concurrency.container.synccontainer.SafeArrayList
你能想到的java大神们肯定也能想到，因此有了同步容器这玩意：

## 同步容器

Java 提供的同步容器有 Vector、Stack 和 Hashtable，这三个容器同样是基于 synchronized 实现的

ArrayList --> Vector\Stack
1、Vector
Vector和ArrayList一样实现了List接口，其对于数组的各种操作和ArrayList一样.

区别在于Vertor在可能出现线程不安全的所有方法都用synchronized进行了修饰,例如：
```
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}
```

2、Stack
Stack是Vertor的子类，Stack实现的是先进后出的栈。在出栈入栈等操作都进行了synchronized修饰。

HashMap --> HashTable
3、HashTable
HashTable实现了Map接口，它实现的功能HashMap基本一致（但注意HashTable不可存null,而HashMap的键和值都可以存null）。
区别在于HashTable使用了synchronized修饰了方法。
```
public synchronized V put(K key, V value) {
    // Make sure the value is not null
    if (value == null) {
        throw new NullPointerException();
    }

    // Makes sure the key is not already in the hashtable.
    Entry<?,?> tab[] = table;
    int hash = key.hashCode();
    int index = (hash & 0x7FFFFFFF) % tab.length;
    @SuppressWarnings("unchecked")
    Entry<K,V> entry = (Entry<K,V>)tab[index];
    for(; entry != null ; entry = entry.next) {
        if ((entry.hash == hash) && entry.key.equals(key)) {
            V old = entry.value;
            entry.value = value;
            return old;
        }
    }
    
    addEntry(hash, key, value, index);
    return null;
}
```

4、Collections提供的同步集合类
- `List list = Collections.synchronizedList(new ArrayList());`
- `Set set = Collections.synchronizedSet(new HashSet());`
- `Map map = Collections.synchronizedMap(new HashMap());`

上面我们提到的这些经过包装后线程安全容器，都是基于 synchronized 这个同步关键字实现的，所以也被称为同步容器。
而synchronized的同步粒度太大，所有方法都用 synchronized 来保证互斥，串行度太高了。导致在多线程处理的效率很低。
所以在JDK1.5的时候推出了并发包下的并发容器，来应对多线程下容器处理效率低的问题。



## 并发容器

### List类型
1.CopyOnWriteArrayList
CopyOnWriteArrayList相当于实现了线程安全的ArrayList。
CopyOnWrite，顾名思义就是写的时候会将共享变量新复制一份出来，完成操作后将副本数组引用赋值给容器。
底层是通过ReentrantLock来保证同步。
而读操作是完全无锁的。
但它通过牺牲容器的一致性来换取容器的高并发效率（在copy期间读到的是旧数据）。所以不能在需要强一致性的场景下使用。

一个是应用场景，CopyOnWriteArrayList 仅适用于写操作非常少的场景，而且能够容忍读写的短暂不一致。例如上面的例子中，写入的新元素并不能立刻被遍历到。另一个需要注意的是，CopyOnWriteArrayList 迭代器是只读的，不支持增删改。因为迭代器遍历的仅仅是一个快照，而对快照进行增删改是没有意义的。

### Set类型
1.CopyOnWriteArraySet
CopyOnWriteArraySet和CopyOnWriteArrayList原理一样，它是实现了CopyOnWrite机制的Set集合。
2.ConcurrentSkipListSet
ConcurrentSkipListSet和ConcurrentSkipListMap原理一样，它是实现了高并发线程安全的TreeSet。
### Map类型
1.ConcurrentHashMap
ConcurrentHashMap相当于实现了线程安全的HashMap。其中的key是无序的，并且key和value都不能为null。在JDK8之前，ConcurrentHashMap采用了分段锁机制来提高并发效率，只有在操作同一分段的键值对时才需要加锁。到了JDK8之后，摒弃了锁分段机制，改为利用CAS算法。
2.ConcurrentSkipListMap
ConcurrentSkipListMap相当于实现了线程安全的TreeMap。其中的key是有序的，并且key和value都不能为null。它采用的跳跃表的机制来替代红黑树。为什么不继续使用红黑树呢？因为红黑树在插入或删除节点的时候需要旋转调整，导致需要控制的粒度较大。而跳跃表使用的是链表，利用无锁CAS机制实现高并发线程安全。
### Queue类型
阻塞型
1.ArrayBlockingQueue
ArrayBlockingQueue是采用数组实现的有界阻塞线程安全队列。如果向已满的队列继续塞入元素，将导致当前的线程阻塞。如果向空队列获取元素，那么将导致当前线程阻塞。采用ReentrantLock来保证在并发情况下的线程安全。

2.LinkedBlockingQueue
LinkedBlockingQueue是一个基于单向链表的、范围任意的（其实是有界的）、FIFO 阻塞队列。访问与移除操作是在队头进行，添加操作是在队尾进行，并分别使用不同的锁进行保护，只有在可能涉及多个节点的操作才同时对两个锁进行加锁。

3.PriorityBlockingQueue
PriorityBlockingQueue是一个支持优先级的无界阻塞队列。默认情况下元素采用自然顺序升序排列。也可以自定义类实现compareTo()方法来指定元素排序规则，

4.DelayQueue
DelayQueue是一个内部使用优先级队列实现的无界阻塞队列。同时元素节点数据需要等待多久之后才可被访问。取数据队列为空时等待，有数据但延迟时间未到时超时等待。

5.SynchronousQueue
SynchronousQueue没有容量，是一个不存储元素的阻塞队列，会直接将元素交给消费者，必须等队列中的添加元素被消费后才能继续添加新的元素。相当于一条容量为1的传送带。

6.LinkedTransferQueue
LinkedTransferQueue是一个有链表组成的无界传输阻塞队列。它集合了ConcurrentLinkedQueue、SynchronousQueue、LinkedBlockingQueue等优点。具体机制较为复杂。

7.LinkedBlockingDeque
LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列指的是可以从队列的两端插入和移出元素。

非阻塞型
1.ConcurrentLinkedQueue
ConcurrentLinkedQueue是线程安全的无界非阻塞队列,其底层数据结构是使用单向链表实现,入队和出队操作是使用我们经常提到的CAS来保证线程安全。
2、ConcurrentLinkedDeque