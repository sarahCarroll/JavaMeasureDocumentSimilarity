// TODO: Auto-generated Javadoc
/**
 * The class documentParser.
 */
package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
/**
 * 
 * @author Sarah Carroll- G00330821
 * @version 1.0
 */

public class documentParser implements Runnable {
	

	private BlockingQueue <shingle> queue;
	private String file;
	private int shingleSize,k;
	private Deque<String>Buffer=new LinkedList<>();
	
	/**
	 * This constucts a document with a file,shinglesize,k.
	 * @param f the string file that is being Parsed
	 * @param ss Shingle size, the number of words per shingle
	 * @param q The Blocking queue
	 * @param k The number of shingles which will generate minHashes
	 */

	public documentParser(String f, int ss, BlockingQueue<shingle> q, int k) {
		
		//super();
		// TODO Auto-generated constructor stub
		this.file=f;
		this.queue = q;
		this.shingleSize=ss;
		this.k=k;
		
	}


	@Override
	public void run() {
		/**
		 * The run() converts the shingle to upper case, gets rid of spaces and puts the shingle onto queue.
		 * 
		 */
		//int count=0;
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line= null;
			//loop through all the lines in the file
			while((line = br.readLine())!=null){
				//convert to upperacase to avoid mismatch on minHash()
				String uLine= line.toUpperCase();
				//split the line into words seperated by spaces
				String [] words = uLine.split(" ");
				//add individual words to the buffer for shingle creation
				addWordsToBuffer(words);
				shingle s = getNextShingle();
				
				//count++;
				//add our shingle to the queue
				//System.out.println(count + ":" + s +"\n");
				if(s== null) {
					continue;		// ignore empty shingles caused by multiple newlines
				}
				queue.put(s);
			}
			
			// write End of File shingle - used by Consumer class
			
			queue.put(new shingle(0,0));
			
			flushBuffer();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private void flushBuffer() throws InterruptedException {
		/**
		 * flushes the buffer.
		 */
		// TODO Auto-generated method stub
		while(Buffer.size()>0) {
			shingle s =getNextShingle();
			if(s != null) {
				queue.put(s);	
			}else {
				queue.put(new Poison(0,0));
			}
		}
	}


	private shingle getNextShingle() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		while(counter < shingleSize) {
			if(Buffer.peek()!=null) 
				sb.append(Buffer.poll());
			counter++;
		}
		if(sb.length()>0) {
			
			//System.out.println(sb.toString());
			int docID=0;
			return (new shingle(docID,sb.toString().hashCode()));
		}
		else {
			return null;
		}
	}


	private void addWordsToBuffer(String[] words) {
		// TODO Auto-generated method stub
		//loops though array of words
		for(String s: words) {
			Buffer.add(s);
		}
	}



}
