package Assignment3;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class Driver {
	
	private static String RandomHospData() {
		String[] data = new String[] {
				"12345678",
				"87654321",
				"12121212",
				"88888888",
				"66666666",
				"98765432",
				"55555555",
				"99999999",
				"78787878",
				"45455445"
		};
		Random rnd = new Random();
		int randomIndex = rnd.nextInt(49)%10;
		return data[randomIndex];
	}
	
	
	public static void main(String[] args)throws Exception {
		try {
			
			// Using benchmark file
			//String filePath = "C:\\Users\\msais\\eclipse-workspace\\assigment3\\src\\Assignment3\\customfiles\\custom_test_file5_array_1002.txt";
			//String filePath = "C:\\Users\\msais\\eclipse-workspace\\assigment3\\src\\Assignment3\\customfiles\\custom_test_file4_AVL_100001.txt";
			String filePath = "C:\\Users\\msais\\eclipse-workspace\\assigment3\\src\\Assignment3\\customfiles\\custom_test_file3_1261_array.txt";
			//String filePath = "C:\\Users\\msais\\eclipse-workspace\\assigment3\\src\\Assignment3\\customfiles\\custom_test_file2_88_Array.txt";
			//String filePath = "C:\\Users\\msais\\eclipse-workspace\\assigment3\\src\\Assignment3\\customfiles\\custom_test_file1_61_Array.txt";
			Scanner sc = new Scanner(new FileInputStream(filePath));
			
			Path file = Paths.get(filePath);
			
			// Determining input size ElasticERL
			int noOfEntries = (int) Files.lines(file).count();
			
			System.out.println("The file has " + noOfEntries + " entries.");
			
			boolean goodToGo = true;
			
			ElasticERL ERL1 = null;
			
			try {
				ERL1 = new ElasticERL(noOfEntries);
			}
			catch (Exception e) {
				System.out.println("Error creating ElasticERL object: " + e.getMessage());
				goodToGo = false;
			}
			
			
			if (!goodToGo) {
				System.out.println("Exiting program");
				return;
			}
			while(sc.hasNextLong()) {
				ERL1.add(sc.nextLong(), RandomHospData());
			}
			
			System.out.println("The Elastic ERL has " + ERL1.getSize() + " elements");
			
			System.out.println("\n==========================================================================\n");
			Scanner scan = new Scanner(System.in);
			
			
			System.out.println("Enter a EIN number to add to the Elastic ERL");
			String userInputEIN = scan.next();
			System.out.println("Generating new key for the input EIN value");
			int newKey = ERL1.generate();
			System.out.println("Generated key for your input is " + newKey);
			ERL1.add(newKey,userInputEIN);
			System.out.println("Value added for the new key is => " + ERL1.getValues(newKey));
			
			System.out.println("Successfully added the new key: "+ newKey + " ==> " +ERL1.getValues(newKey)); 
			
			System.out.println("\n==========================================================================\n");
			
			System.out.println("Printing previous and next values of the new generated key " + newKey);
			
			System.out.println("Prev Key of "+ newKey + " ==> " +ERL1.prevKey(newKey));
			System.out.println("Next Key of "+ newKey + " ==> " +ERL1.nextKey(newKey));
			
			System.out.println("\n==========================================================================\n");
			
			
//			long[] allKeys = ERL1.allKeys();
//			System.out.println("get first 10 keys from " + allKeys.length + " keys ==> ");
//			for(int i=0; i<10; i++) {
//				System.out.println(String.format("%08d", allKeys[i]));
//			}
			
			System.out.println("Now removing the new generated key + " + newKey);
			ERL1.remove(newKey);
			System.out.println("Successfully removed "+ newKey + " ==> " +ERL1.getValues(newKey)); 
			
			System.out.println("\n==========================================================================\n");
			
			System.out.println("Demonstrating Range functionality");
			System.out.println("Enter the lower range key");
			int key1 = scan.nextInt();
			System.out.println("Enter the upper range key");
			int key2 = scan.nextInt();
			long[] rangeKeys = ERL1.rangeKey(key1, key2);	
			System.out.println("Entered range is from "+key1+" to "+key2);
			if(rangeKeys!=null) {
				int endIndex;
				//if(rangeKeys.length > 10) { endIndex = 10; }
				//else { endIndex = rangeKeys.length; }
				endIndex = rangeKeys.length;
				System.out.println("Showing from keys from "+key1+" to "+key2);

				for(int i=0; i<endIndex; i++) {
					System.out.println(String.format("%08d", rangeKeys[i]));
				}
			}
			else {
				System.out.println("No keys are present in this range");
			}
			
			scan.close();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
