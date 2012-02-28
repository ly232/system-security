
package XML;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


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
	HashMap<String, Integer> columes;

	
	
    public ArrayList<ArrayList<String>> getTable() {
		return table;
	}



	public void setTable(ArrayList<ArrayList<String>> table) {
		this.table = table;
	}



	public HashMap<String, Integer> getColumes() {
		return columes;
	}



	public void setColumes(HashMap<String, Integer> columes) {
		this.columes = columes;
	}



	public MyResultSet(ResultSet resultSet)
    	throws SQLException {  
	        int columnCount = resultSet.getMetaData().getColumnCount();  
	         ResultSetMetaData srmdData = resultSet.getMetaData();
	         columes = new HashMap<String, Integer>();
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
