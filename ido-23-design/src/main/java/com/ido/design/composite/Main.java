package com.ido.design.composite;
/*
混合模式
 */
public class Main {
    public static void main(String[] args) {
        BranchNode root = new BranchNode("root");
        BranchNode chapter1 = new BranchNode("chapter1");
        BranchNode chapter2 = new BranchNode("chapter2");
        LeafNode c11 = new LeafNode("c11");
        LeafNode c12 = new LeafNode("c12");
        BranchNode b21 = new BranchNode("section21");
        LeafNode c211 = new LeafNode("c211");
        LeafNode c212 = new LeafNode("c212");
        root.add(chapter1);
        root.add(chapter2);
        chapter1.add(c11);
        chapter1.add(c12);
        chapter2.add(b21);
        b21.add(c211);
        b21.add(c212);
        tree(root,0);
    }
    static void tree(Node node,int depth){
        for (int i = 0; i < depth; i++) {
            System.out.print("--");
        }
        node.p();
        if (node instanceof BranchNode){
            for (Node n : ((BranchNode) node).nodes) {
                tree(n,depth+1);
            }
        }
    }
}
