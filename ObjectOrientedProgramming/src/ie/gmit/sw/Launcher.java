package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author Sarah Carroll - G00330821
 *
 */
public class Launcher {

	int k; //minHashes
	private int [] minHashes;
	/**
	 * 
	 * @param fileName1 -first file
	 * @param fileName2 -second file to compare with first
	 * @param hashes -  also know as k the max number of shingles involved in matching
	 * @throws InterruptedException
	 */
	

	public Launcher(String fileName1, String fileName2, int hashes) throws InterruptedException {
		// TODO Auto-generated constructor stub
		
		k = hashes;
	
		Random random =new Random();
		
		minHashes = new int [k];
		//populating minHashes table with random integers
		for (int i=0;i<minHashes.length;i++) {
			minHashes[i] = random.nextInt();
			//System.out.println("minHashes["+i+"] = "+ minHashes[i]);
		}

		//Queues of Shingles for each file
		
		BlockingQueue <shingle> q1 = new LinkedBlockingQueue<>();
		BlockingQueue <shingle> q2 = new LinkedBlockingQueue<>();
		
		//Minihash maps
		
		Map <Integer,List<Integer>> m1 = new HashMap<>();
		Map <Integer,List<Integer>> m2 = new HashMap<>();
		
		// Document Parser Threads
		
		Thread t1 = new Thread(new documentParser(fileName1,4,q1,k),"T1");
		Thread t2 = new Thread(new documentParser(fileName2,4,q2,k),"T2");
		
		t1.start();
		t2.start();

		t1.join();
		t2.join();

		// Consumer threads- creates mini-hashes for Jaccard indexing
		
		Thread t3 = new Thread(new Consumer(q1,m1,k,minHashes),"T3");
		Thread t4 = new Thread(new Consumer(q2,m2,k,minHashes),"T4");
		
		t3.start();
		t4.start();
		
		t3.join();
		t4.join();
		
		//List<Integer> l1 = m1.get(0);
		//System.out.println("Map 1 ="+m1);
		//System.out.println("Map 1 size ="+l1.size());
		//List<Integer> l2 = m2.get(0);
		//System.out.println("Map 2 ="+m2);
		//System.out.println("Map 2 size ="+l2.size());
		
		
		// code here to perform Jaccard calculation on 2 HashMaps
		
		//float result = 0.0f;
		float result = Jaccard(m1.get(0),m2.get(0));
		
		System.out.println("------------------------------------------------------");
		System.out.println("Similarity: "+result+"%");
		System.out.println("------------------------------------------------------");
		
	}
	
	float Jaccard(List<Integer> x,List<Integer> y) {
		
		float result = 0.0f;
		
		List<Integer> intersection = new ArrayList<Integer>(x);
		
		intersection.retainAll(y);
		
		System.out.println("Common Mini-Hashes: "+intersection.size());
		
		result = ((float)intersection.size()/k)*100;
		
		return result;
	}
}
