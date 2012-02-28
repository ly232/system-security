
package XML;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;


public class MyResultSet implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//the data table
	/**
	 * transfer the result from table to 
	 */
	ArrayList<ArrayList<String>> table; 
	Hashtable<String, Integer> columes;
	
	public String getStringValue(int row, String ColumeName){
		try {
			Object[] rows =  table.toArray();
			ArrayList<String> record= (ArrayList<String>) rows[row];
			Object[] attributes =  record.toArray();
			String result = (String)attributes[columes.get(ColumeName) - 1];
			//Object[] attributes = ((ArrayList<String>)rows[row]).toArray();
			return result;//attributes[columes.get(ColumeName)];
		} catch (Exception e) {
			System.err.println("getStringValue error");
			return null;
		}
	}
	
	
    public ArrayList<ArrayList<String>> getTable() {
		return table;
	}



	public void setTable(ArrayList<ArrayList<String>> table) {
		this.table = table;
	}



	public Hashtable<String, Integer> getColumes() {
		return columes;
	}



	public void setColumes(Hashtable<String, Integer> columes) {
		this.columes = columes;
	}

   

	public MyResultSet(ResultSet resultSet)
    	throws SQLException {  
		
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
	            table = new ArrayList<ArrayList<String>>();  
	        else {    
	            resultSet.last();  
	            table = new ArrayList<ArrayList<String>>(resultSet.getRow());  
	            resultSet.beforeFirst();  
	        }  
	    
	        for(ArrayList<String> row; resultSet.next(); table.add(row)) {  
	            row = new ArrayList<String>(columnCount);  
	      
	            for(int c = 1; c <= columnCount; ++ c)  
	                row.add(resultSet.getString(c).intern());  
	        }
    }


}
