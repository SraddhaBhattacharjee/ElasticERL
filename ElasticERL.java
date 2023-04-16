package Assignment3;

import java.util.Arrays;
import java.util.Random;

/**
 *  ElasticERL ADT that internally implements AVL Tree for datasets with size in range of 100,000 to 500,000 & 
 *  Arrays for datasets with size in the range of 100 to 99,999 -
 *  along with that we have stored the student id and student data as a key value pair in Hashmap
 * 
 * 
 */
public class ElasticERL {

	private int thresholdValue;
	private AVLNode avlTree;
	private long[] hospList;
	private Hashmap hashMap;
	
	public int getSize() {
		return hashMap.getSize();
	}

	private int size = 0;
	
	public ElasticERL(int size) throws Exception {
		hashMap = new Hashmap();
		SetEINThreshold(size);
		if(size >= 100000) { 
			System.out.println("As size is " + size + " , we are storing data in AVL Tree");
			avlTree = new AVLNode();
		}

		else { 
			System.out.println("As size is " + size + " , we are storing data in Array & using Merge Sort");
			hospList = new long[size];
		}
		
	}
	
	/**
	 * This method sets the ERL threshhold value
	 * 
	 * @param size
	 * @return void
	 */
	public void SetEINThreshold(int size) throws Exception {
		try {
			if (size < 100) {
				throw new Exception("Size should be greater than or equal to 100.");
			}
			this.thresholdValue = size;
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	
	/**
	 * This method generates an 8 digit random unique key
	 * 
	 * @param null
	 * @return int
	 */
	public int generate() { //O(1)
		Random rnd = new Random();
	    int key = rnd.nextInt(99999999);
	    while(hashMap.containsId(key)) {
	    	key = rnd.nextInt(99999999);
	    }
		return key;
	}
	
	/**
	 * This method fetches all the keys in a sorted order using ether avl tree, merge sort or quick sort depending on the size of input
	 * 
	 * @param nil
	 * @return array of long
	 */
	public long[] allKeys() { // O(n)
		if(thresholdValue >= 100000) { // AVL
			return avlTree.inOrderTraversal().stream().mapToLong(i -> i).toArray();
		}
		else { //
			return MergeSort.getSortedArr(hospList);
		}
		
	}
	
	/**
	 * This method adds the key and value pair to the hashmap if the key doesn't exist.
	 *  Then, according to the size of input, key is either inserted into the avl tree or array
	 * 
	 * @param nil
	 * @return int
	 */
	public void add(long key, String val) {
		if(!hashMap.containsId(key)) {
			hashMap.put(key, val); // O(1)
			if(thresholdValue >= 100000) { // AVL
				avlTree.insertNode(key); // O(log n)
			}
			else {
				//System.out.println(size);
				hospList[size] = key; // O(1)
			}
			//System.out.println(size);
			size++;
		}
		else {
			//System.out.println(key + " already exists");
		}
		
	}
	
	/**
	 * This method removes the element from the array
	 * 
	 * @param id
	 * @return void
	 */
	private void removeEleFromArr(int id) {
		int indexToBeDeleted = -1;
		for(int i=0; i<size; i++) {
			if(hospList[i] == id) {
				indexToBeDeleted = i;
				break;
			}
		}
		if(indexToBeDeleted == -1) {
			System.out.println(id + " does not exist");
		}
		else if(indexToBeDeleted == size-1) { //last ele
			hospList[size-1] = 0;
			size--;
		}
		else {
			for(int i=indexToBeDeleted; i<size-1; i++) {
				hospList[i] = hospList[i+1];
			}
			hospList[size-1] = 0;
			size--;
		}
	}
	
	/**
	 * This method removes the key from hashmap and then delete it from avl tree or array depending on the size of the input
	 * 
	 * @param key
	 * @return void
	 */
	public void remove(int key) {
		hashMap.remove(key); // O(1)
		if(thresholdValue >= 100000) { // AVL
			avlTree.deleteNode(key); // O(log n)
		}
	
		else {
			removeEleFromArr(key);
		}
		
	}
	
	/**
	 * This method fetches the value of a particular key from the hashmap
	 * 
	 * @param key
	 * @return String
	 */
	public String getValues(int key) {
		return hashMap.get(key); // O(1)
	}
	
	/**
	 * This method returns the key next to that of the mentioned key
	 * 
	 * @param key
	 * @return long
	 */
	public long nextKey(int key) { // O(n)
		long[] sortedKeysList = allKeys();
		for(int i=0; i<sortedKeysList.length; i++) {
			if(sortedKeysList[i]== key) {
				if(i+1 < size) {
					return sortedKeysList[i+1];
				}
			}
		}
		return -1;
	}
	
	/**
	 * This method returns the key previous to that of the mentioned key
	 * 
	 * @param key
	 * @return long
	 */
	public long prevKey(int key) { // O(n)
		long[] sortedKeysList = allKeys();
		for(int i=0; i<sortedKeysList.length; i++) {
			if(sortedKeysList[i]== key) {
				if(i-1 >= 0){
					return sortedKeysList[i-1];
				}
			}
		}
		return -1;
	}
	
	/**
	 * This method returns the array of the keys which falls in the range of key1 and key2
	 * 
	 * @param key1, key2
	 * @return array of long
	 */
	public long[] rangeKey(int key1, int key2) { // O(n)
		long[] sortedKeysList = allKeys();
		int startIndex = 0, endIndex = 0;
		for(int i=0; i<sortedKeysList.length; i++) {
			if(i>0) {
				if(sortedKeysList[i-1] < key1 && sortedKeysList[i] >= key1) {
					startIndex = i;
				}
				if(sortedKeysList[i-1] <= key2 && sortedKeysList[i] > key2) {
					endIndex = i-1;
				}
			}
			else if(sortedKeysList[0] == key1) {
				startIndex = 0;
			}
		}
		if(startIndex<=endIndex) {
			return Arrays.copyOfRange(sortedKeysList, startIndex, endIndex+1);
		}
		else {
			return Arrays.copyOfRange(sortedKeysList, startIndex, sortedKeysList.length);
		}
	}

}
