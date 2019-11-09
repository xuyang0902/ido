package com.ido.jdk.collections.tree;


/**
 * 二叉搜索树
 * <p>
 * <p>
 * 递归和循环都能做，如何选择
 * 优点                缺点
 * 递归： 代码简洁，可读性好        堆栈的开销，深度太大，栈溢出
 * 循环： 速度快 结构简单           有些问题不一定能解决
 *
 * @author xu.qiang
 * @date 19/5/13
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

    private BinaryNode<AnyType> root;

    //节点结构
    private static class BinaryNode<AnyType> {

        BinaryNode(AnyType theElement) {
            this(theElement, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }

    public BinarySearchTree() {
        root = null;
    }

    public void insert(AnyType x) {

        if (x == null) {
            throw new NullPointerException();
        }

        if (root == null) {
            root = new BinaryNode<AnyType>(x, null, null);
            return;
        }

        //取叶子节点  insert
        BinaryNode<AnyType> tmp = this.root;
        BinaryNode<AnyType> parent;
        int compare;
        do {
            parent = tmp;
            compare = x.compareTo(tmp.element);
            if (compare < 0) {
                tmp = tmp.left;
            } else if (compare > 0) {
                tmp = tmp.right;
            } else {
                //覆盖
            }
        } while (tmp != null);

        BinaryNode<AnyType> current = new BinaryNode<AnyType>(x, null, null);
        if (compare > 0) {
            parent.right = current;
        } else if (compare < 0) {
            parent.left = current;
        }

    }

    public void remove(AnyType x) {

        //先找到需要移除的node

        //如果是叶子节点 直接删除

        //被删除节点只有左孩子  那么直接左孩子节点替换自己即可

        //被删除节点 只有右孩子，那么直接右孩子节点替换自己即可

        //被删除节点 有两个孩子  取后继节点 替换自己  （后继节点 不可能有左孩子，可能存在右孩子，右孩子挂到 后继节点的父节点的左孩子下）

        //不在这里实现了  哈哈哈
    }

    public AnyType findMin() {
        if (isEmpty()) {
            return null;
        }

        //寻找最左边的叶子节点
        BinaryNode<AnyType> tmp = this.root;
        BinaryNode<AnyType> parent;
        do {
            parent = tmp;
            tmp = tmp.left;
        } while (tmp != null);

        return parent.element;
    }

    public AnyType findMax() {
        if (isEmpty()) {
            return null;
        }

        //寻找最右边的叶子节点
        BinaryNode<AnyType> tmp = this.root;
        BinaryNode<AnyType> parent;
        do {
            parent = tmp;
            tmp = tmp.right;
        } while (tmp != null);

        return parent.element;
    }

    public boolean contains(AnyType x) {

        if (isEmpty()) {
            return false;
        }
        //遍历树查找，其实和插入的逻辑很类似
        BinaryNode<AnyType> tmp = this.root;
        int compare;
        do {
            compare = x.compareTo(tmp.element);
            if (compare < 0) {
                tmp = tmp.left;
            } else if (compare > 0) {
                tmp = tmp.right;
            } else {
                return true;
            }
        } while (tmp != null);

        return false;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }


    private void printTree(BinaryNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }


    // Test program
    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        binarySearchTree.insert(1);
        binarySearchTree.insert(3);
        binarySearchTree.insert(5);
        binarySearchTree.insert(7);
        binarySearchTree.insert(9);
        binarySearchTree.insert(8);
        binarySearchTree.insert(4);
        binarySearchTree.insert(6);
        binarySearchTree.insert(2);
        binarySearchTree.insert(0);
        binarySearchTree.insert(100);
        binarySearchTree.insert(200);
        binarySearchTree.insert(150);


        System.out.println(binarySearchTree.findMin());
        System.out.println(binarySearchTree.findMax());
        System.out.println(binarySearchTree.contains(0));

        binarySearchTree.printTree();


    }
}
