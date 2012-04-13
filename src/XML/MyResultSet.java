
package XML;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Security.SharedKey;


public class MyResultSet implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the data table
	/**
	 * transfer the result from table to 
	 */
	ArrayList<ArrayList<byte[]>> table; 
	Hashtable<String, Integer> columes;
	
	
	public String getStringValue(int row, String ColumeName){
		try {
			Object[] rows =  table.toArray();
			if (rows.length == 0) {
				return null;
			}
			ArrayList<String> record= (ArrayList<String>) rows[row];
			Object[] attributes =  record.toArray();
			String result = (String)attributes[columes.get(ColumeName) - 1];
			//Object[] attributes = ((ArrayList<String>)rows[row]).toArray();
			return result;//attributes[columes.get(ColumeName)];
		} catch (Exception e) {
			System.err.println("getStringValue error");
			System.err.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	
	
	public byte[] getCipherValue(int row, String ColumeName){
		try {
			Object[] rows =  table.toArray();
			if (rows.length == 0) {
				return null;
			}
			ArrayList<byte[]> record= (ArrayList<byte[]>) rows[row];
			Object[] attributes =  record.toArray();
			byte[] result = (byte[])attributes[columes.get(ColumeName) - 1];
			//Object[] attributes = ((ArrayList<String>)rows[row]).toArray();
			return result;//attributes[columes.get(ColumeName)];
		} catch (Exception e) {
			System.err.println("getStringValue error");
			System.err.println(e.getLocalizedMessage());
			return null;
		}
	}
	
    public ArrayList<ArrayList<byte[]>> getTable() {
		return table;
	}



	public void setTable(ArrayList<ArrayList<byte[]>> table) {
		this.table = table;
	}



	public Hashtable<String, Integer> getColumes() {
		return columes;
	}



	public void setColumes(Hashtable<String, Integer> columes) {
		this.columes = columes;
	}

   

	public MyResultSet(ResultSet resultSet, String sessionkey)
    	throws SQLException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {  
		
		//ResultSetUtils rus = ResultSetUtills.cr
		
		//	ResultSetUtil rsu = new ResultSetUtil().;
		//	data = rsu.resultSetto
		
	        int columnCount = resultSet.getMetaData().getColumnCount();  
	         ResultSetMetaData srmdData = resultSet.getMetaData();
	         columes = new Hashtable<String, Integer>();
	        for (int i = 1; i <= columnCount; i++) {
				columes.put(srmdData.getColumnName(i), i);
			}
	        
	        if(resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY)   
	            table = new ArrayList<ArrayList<byte[]>>();  
	        else {    
	            resultSet.last();  
	            table = new ArrayList<ArrayList<byte[]>>(resultSet.getRow());  
	            resultSet.beforeFirst();  
	        }  
	    
	        for(ArrayList<byte[]> row; resultSet.next(); table.add(row)) {  
	            row = new ArrayList<byte[]>(columnCount);  
	      
	            //Lin-modified on 4/12/12 to encrypt data with session key.
	            //TODO: here expects string...ciphertext is byte[]...
	            for(int c = 1; c <= columnCount; ++ c){  
	            	String data = resultSet.getString(c).intern();
	            	//System.out.println("db decrypted value: " + data);
	            	SharedKey sk = SharedKey.getInstance();
	            	byte[] ciphertext = sk.sessionKeyEncrypt(sessionkey, data);
	            	//System.out.println("sent cipher: " + ciphertext);
	                row.add(ciphertext);  
	            }
	        }
    }

}
