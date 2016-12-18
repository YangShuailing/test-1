package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class BinaryTree {
	/*
	 * http://m.blog.csdn.net/article/details?id=50673797
	 * 
	 */
	public static class Node{
        public Node leftChild;
        public Node rightChild;
        public String content;
        public Node(String ct){
            content = ct;
        }
    }

    @SuppressWarnings("serial")
	public static class NodeCopyTask extends RecursiveTask<Node>{
        Node mNode;
        public NodeCopyTask(Node node){
            mNode = node;
        }
        protected Node compute() {
            if(mNode==null)
                return null;

            NodeCopyTask taskLeft = new NodeCopyTask(mNode.leftChild);
            taskLeft.fork();
            NodeCopyTask taskRight = new NodeCopyTask(mNode.rightChild);
            taskRight.fork();

            Node node = new Node(mNode.content);
            node.leftChild = taskLeft.join();
            node.rightChild = taskRight.join();
            return node;
        }
    }

    public static void main(String[] args){
        //TODO: construct a real tree
        Node node = new Node("Hello,Fork-Join");
        node.leftChild = new Node("Left");
        node.rightChild = new Node("Right");
        

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        NodeCopyTask task = new NodeCopyTask(node);
        Future<Node> future = forkJoinPool.submit(task);
        try {
            Node nodeNew = future.get();
            System.out.println("nodeNew: " + nodeNew.content ); //add
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        System.out.println(node.content );//add
    }
    
}