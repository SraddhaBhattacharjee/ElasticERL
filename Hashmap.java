package Assignment3_Final;

import java.util.Objects;

import Assignment3_Final.Hashmap.LinkedList.Node;

public class Hashmap {

	/**
	 * This class is an inner class to HashMap class with contains another inner class Node and two attributes head and size
	 * 
	 */
	class LinkedList {
		
		/**
		 * This inner class is used to store the student id , data and the link to the next node
		 * 
		 * 
		 */
		class Node {
			long hospID;
			String hospData;
			Node next;
			
			public Node(long id, String value, Node next) {
				this.hospID = id;
				this.stuData = value;
				this.next = next;
			}
		}
		
		Node head;
		int size;
		
		public LinkedList() {
			this.head = null;
			this.size = 0;
		}
		
		/**
		 * This method adds the student id and data to the start 
		 * 
		 * @param id,value
		 * @return void
		 */
		public void AddToStart(long id, String value) {
			if(this.head == null) {
				this.head = new Node(id, value, null);
			}
			else {
				this.head = new Node(id, value, this.head);
			}
			this.size++;
		}
		
		/**
		 * This method is used to display the contents 
		 * 
		 * @param nil
		 * @return void
		 */
		public void displayContents() {
			Node temp = this.head;
			while(temp != null) {
				System.out.print(temp.hospID + " " + temp.stuData);
				if(temp.next != null) { System.out.print(","); }
				temp = temp.next;
			}
			System.out.println();
		}
		
		/**
		 * This method returns the size
		 * 
		 * @param nil
		 * @return int
		 */
		public int size() {
			return this.size;
		}
		
		/**
		 * This method is checks whether the given id is present or not
		 * 
		 * @param id
		 * @return boolean
		 */
		public boolean contains(long id) {
			Node temp = this.head;
			while(temp != null) {
				if(temp.hospID == id) {
					return true;
				}
				temp = temp.next;
			}
			return false;
		}
		
		/**
		 * This method is used to find the node where the given id is present, returns null if it is not present
		 * 
		 * @param id
		 * @return Node
		 */
		public Node find(long id) {
			Node temp = this.head;
			while(temp != null) {
				if(temp.hospID == id) {
					return temp;
				}
				temp = temp.next;
			}
			return null;
		}
		
		/**
		 * This method is used to delete the id
		 * 
		 * @param id
		 * @return void
		 */
		public void delete(long id) {
			if(head == null) {
				return;
			}
			else if(head.hospID == id) {
				this.head = head.next;
			}
			else {
				Node temp = this.head;
				while(temp.next != null && temp.next.hospID != id) {
					temp = temp.next;
				}
				temp.next = temp.next.next;
			}
		}
		
	}
	
	private LinkedList[] list;
	private int capacity; 
	private int size;
	
	public Hashmap() {
		capacity = 1000;
		list = new LinkedList[capacity];
		size = 0;
	}
	
	/**
	 * This method is used for dynamic resizing of array when the number of elements increases
	 *  more than twice the size of array in order to decrease the collision
	 * 
	 * @param nil
	 * @return void
	 */
	private void dynamicResizingOfArr() {
		LinkedList[] tempList = list;
		capacity *= 2;
		list = new LinkedList[capacity];
		size = 0;
		for(int i=0; i<(int)capacity/2; i++) {
			if(tempList[i] != null) {
				Node tempNode = tempList[i].head;
				put(tempNode.hospID, tempNode.hospData);
				while(tempNode != null) {
					tempNode = tempNode.next;
				}
			}
		}
	}
	
	/**
	 * This method is used to compute the hash value using in-built hash function of Objects available in Java
	 * 
	 * @param id
	 * @return int
	 */	 
	private int computeHashValue(long id) {
		return Objects.hash(id);
	}
	
	/**
	 * This method is used to put the id and value on the calculated hashIndex
	 * 
	 * @param id,value
	 * @return void
	 */
	public void put(long id, String value) {
		int hashIndex = computeHashValue(id) % capacity;
		if(list[hashIndex] == null) {
			LinkedList listObj = new LinkedList(); 
			listObj.AddToStart(id, value);
			list[hashIndex] = listObj;
			size++;
		}
		else {
			LinkedList listObj = list[hashIndex];
			if(!listObj.contains(id)) {
				listObj.AddToStart(id, value);
				size++;
			}
			else {
				System.out.println("Id already exists");
			}
		}
		
		if(size > 2*capacity) {
			dynamicResizingOfArr();
		}
	}
	
	/**
	 * This method is used to fetch the student data from the calculated hash index
	 * 
	 * @param id
	 * @return String
	 */
	public String get(int id) {
		int hashIndex = computeHashValue(id) % capacity;
		if(list[hashIndex] == null) {
			return null;
		}
		else {
			LinkedList listObj = list[hashIndex];
			Node matchingData =  listObj.find(id);
			if(matchingData == null) {
				return null;
			}
			return matchingData.stuData;
		}
	}
	
	/**
	 * This method is used to remove the key value from the calculated hash index
	 * 
	 * @param id
	 * @return void
	 */
	public void remove(int id) {
		int hashIndex = computeHashValue(id) % capacity;
		if(list[hashIndex] == null || !list[hashIndex].contains(id)) {
			System.out.println(id + " does not exists");
		}
		else {
			list[hashIndex].delete(id);
		}
	}
	
	/**
	 * This method is used to check if the given id is present or not
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean containsId(long id) {
		int hashIndex = computeHashValue(id) % capacity;
		if(list[hashIndex] == null) {
			return false;
		}
		else {
			if(list[hashIndex].contains(id)) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	
}
