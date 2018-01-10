package ie.gmit.sw;
/**
 * 
 * @author Sarah Carroll
 * @version 1.0
 */

public class shingle {
	private int docID;
	private int hashCode;
	/**
	 * 
	 * @param docID
	 * @param hashCode
	 */
	
	public shingle(int docID, int hashCode) {
		super();
		this.docID = docID;
		this.hashCode = hashCode;
		/**
		 * 
		 */
	}
	
	/**
	 * 
	 * Getter and setters
	 */
	
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
}
