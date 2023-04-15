package Assignment3_Final;

import java.util.ArrayList;

public class AVLNode {
	private long key;
	private int height;
	private AVLNode leftChild, rightChild;
	private AVLNode rootNode;
	
	public AVLNode() {}
	
	public AVLNode(long key) {
		this.key = key;
		this.leftChild = null;
		this.rightChild = null;
		this.height = 1;
	}
	
	public AVLNode getRootNode() {
		return rootNode;
	}
	
	/**
	 * This method adds the key at root node
	 * 
	 * @param key
	 * @return void
	 */
	public void insertRootNode(long key) {
		rootNode = new AVLNode();
		rootNode.key = key;
		rootNode.leftChild = null;
		rootNode.rightChild = null;
		rootNode.height = 1;
	}
	
	/**
	 * This is a wrapper for the inOrderTraversal
	 * 
	 * @param nil
	 * @return ArrayList<Long>
	 */
	public ArrayList<Long> inOrderTraversal() {
		return inOrderTraversal(this.rootNode, new ArrayList<Long>());
	}
	
	/**
	 * This method is used for Inorder traversal of keys and hence returns list of sorted keys
	 * 
	 * @param node,arr
	 * @return ArrayList<Long>
	 */
	private ArrayList<Long> inOrderTraversal(AVLNode node, ArrayList<Long> arr) {
		if(node == null) {
			return arr;
		}
		inOrderTraversal(node.leftChild, arr);
		arr.add(node.key);
		inOrderTraversal(node.rightChild, arr);
		return arr;
	}
	
	/**
	 * This method is used to get the height of node
	 * 
	 * @param node
	 * @return int
	 */
	public static int getHeight(AVLNode node) {
		if(node == null) {
			return 0;
		}
		return node.height;
	}
	
	/**
	 * This method is used to get the balance of node
	 * 
	 * @param node
	 * @return int
	 */
	public static int getBalance(AVLNode node) {
		if(node == null) {
			return 0;
		}
		return getHeight(node.leftChild) - getHeight(node.rightChild);
	}
	
	/**
	 * This method is used to rotate the AVLTree to right side
	 * 
	 * @param disbalancedNode
	 * @return AVLNode
	 */
	private static AVLNode rotateRight(AVLNode disbalancedNode) {
		if(disbalancedNode.leftChild == null) {
			return disbalancedNode;
		}
		AVLNode newRoot = disbalancedNode.leftChild;
		disbalancedNode.leftChild = newRoot.rightChild;
		newRoot.rightChild = disbalancedNode;
		disbalancedNode.height = 1 + Math.max(getHeight(disbalancedNode.leftChild) ,getHeight(disbalancedNode.rightChild));
		newRoot.height = 1 + Math.max(AVLNode.getHeight(newRoot.leftChild), AVLNode.getHeight(newRoot.rightChild));
		return newRoot;
	}
	
	/**
	 * This method is used to rotate the AVLTree to left side
	 * 
	 * @param disbalancedNode
	 * @return AVLNode
	 */
	private static AVLNode rotateLeft(AVLNode disbalancedNode) {
		if(disbalancedNode.rightChild == null) {
			return disbalancedNode;
		}
		AVLNode newRoot = disbalancedNode.rightChild;
		disbalancedNode.rightChild = newRoot.leftChild;
		newRoot.leftChild = disbalancedNode;
		disbalancedNode.height = 1 + Math.max(getHeight(disbalancedNode.leftChild) ,getHeight(disbalancedNode.rightChild));
		newRoot.height = 1 + Math.max(AVLNode.getHeight(newRoot.leftChild), AVLNode.getHeight(newRoot.rightChild));
		return newRoot;
	}
	
	/**
	 * This method is used to insert the key at root node or calls a method to insert the key at child nodes
	 * 
	 * @param key
	 * @return AVLNode
	 */
	public AVLNode insertNode(long key) {
		if(this.rootNode == null) {
			insertRootNode(key);
			return this.rootNode;
		}
		else {
			return insertNode(this.rootNode, key);	
		}
	}
	
	/**
	 * This method is used to insert the key at root node or to the left or right child depending on the value of the key
	 * 
	 * @param node,keyVal
	 * @return AVLNode
	 */
	private AVLNode insertNode(AVLNode node, long keyVal) {
		if(node == null) {
			return new AVLNode(keyVal);
		}
		else if(keyVal < node.key) {
			node.leftChild = insertNode(node.leftChild, keyVal);
		}
		else {
			node.rightChild = insertNode(node.rightChild, keyVal);
		}
		
		node.height = 1 + Math.max(getHeight(node.leftChild) , getHeight(node.rightChild));
		
		int balance = getBalance(node);
		if(balance > 1 && keyVal < node.leftChild.key) { //Left Left Condition
			return rotateRight(node);
		}
		if(balance > 1 && keyVal > node.leftChild.key) { //Left Right Condition
			node.leftChild = rotateLeft(node.leftChild);
			return rotateRight(node);
		}
		if(balance < -1 && keyVal > node.rightChild.key) { //Right Right Condition
			return rotateLeft(node);
		}
		if(balance < -1 && keyVal < node.rightChild.key) { //Right Left Condition
			node.rightChild = rotateRight(node.rightChild);
			return rotateLeft(node);
		}
		return node;
	}
	
	/**
	 * This method is used to get the node with minimum value
	 * 
	 * @param node
	 * @return AVLNode
	 */
	private static AVLNode getMinValNode(AVLNode node) {
		if(node == null || node.leftChild == null) {
			return node;
		}
		return getMinValNode(node.leftChild);
	}
	
	/**
	 * This method is a wrapper to delete the node 
	 * 
	 * @param key
	 * @return AVLNode
	 */
	public AVLNode deleteNode(long key) {
		return deleteNode(this.rootNode, key);
	}
	
	/**
	 * This method is used to delete the node
	 * 
	 * @param node,keyVal
	 * @return AVLNode
	 */
	private AVLNode deleteNode(AVLNode node, long keyVal) {
		if(node == null) {
			return node;
		}
		else if(keyVal < node.key) {
			node.leftChild = deleteNode(node.leftChild, keyVal);
		}
		else if(keyVal > node.key) {
			node.rightChild = deleteNode(node.rightChild, keyVal);
		}
		else {
			if(node.leftChild == null) {
				AVLNode temp = node.rightChild;
				node = null;
				return temp;
			}
			else if(node.rightChild == null) {
				AVLNode temp = node.leftChild;
				node = null;
				return temp;
			}
			AVLNode temp = getMinValNode(node.rightChild);
			node.key = temp.key;
			node.rightChild = deleteNode(node.rightChild, temp.key);
		}
		

		int balance = getBalance(node);
		if(balance > 1 && keyVal < node.leftChild.key) { //Left Left Condition
			return rotateRight(node);
		}
		if(balance > 1 && keyVal > node.leftChild.key) { //Left Right Condition
			node.leftChild = rotateLeft(node.leftChild);
			return rotateRight(node);
		}
		if(balance < -1 && keyVal > node.leftChild.key) { //Right Right Condition
			return rotateLeft(node);
		}
		if(balance < -1 && keyVal < node.leftChild.key) { //Right Left Condition
			node.rightChild = rotateRight(node.rightChild);
			return rotateLeft(node);
		}
		return node;
	}
	
}
