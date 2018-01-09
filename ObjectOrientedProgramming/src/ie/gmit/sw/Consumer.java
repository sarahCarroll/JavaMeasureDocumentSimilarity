package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
/**
 * 
 * @author Sarah Carroll - G00330821
 *
 */

public class Consumer implements Runnable{

	private BlockingQueue<shingle> queue;
	private int k;
	private int [] minHashes;
	
	Map <Integer,List<Integer>> map;
	//private ExecutorService pool;
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int docCount = 1;		//fixed
		int max = Integer.MAX_VALUE;
		int value = 0;
		/**
		 * The run() method
		 */
		
		while(docCount>0) {
			
			try {
				
				shingle s = queue.take();
				
				//System.out.println("Shingle ="+s);
				
				//Has EOF been encountered in queue?
				if(s.getHashCode()==0) {
					docCount--;
					continue;
				}

				List<Integer> list = map.get(s.getDocID());
				
				//System.out.println("List(F)="+list);
				
				if(list == null) {
					//System.out.println("List == null");
						
					list = new ArrayList<Integer>(k);
		
					//System.out.println("List(X) size ="+list.size());
					
					for(int j = 0; j <k;j++) {
						list.add(j, max);
						//System.out.println("List(i) size ="+list.size());
						//System.out.println("List[+"+j+"]="+list.get(j));
					}
					
					map.put(s.getDocID(), list);	
					///System.out.println("Map ="+map);
					//System.out.println("List(b) size ="+list.size());
				}

				
				for(int i = 0;i<minHashes.length;i++) {
					
					value =s.getHashCode() ^ minHashes[i];
									
					//populate with minimum hash calculated this far
					if(list.get(i) > value) {
						list.set(i, value);
					}
				}
					
			}
			
			 catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// this is a blocking method
			
		}
	}
	
	public Consumer(BlockingQueue<shingle> q, Map <Integer,List<Integer>> map,int k,int[] hashes) {
		super();
		this.queue = q;
		this.k = k;
		this.map = map;
		this.minHashes = hashes;
	}


}
