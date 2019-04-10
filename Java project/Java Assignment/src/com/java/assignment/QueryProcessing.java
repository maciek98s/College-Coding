/*Author : Maciej Skrzypczynski
 *Last Update Date: 02/04/2019
 *
 *Desciption : A class manipulating and dealing with sql quaries 
 *using java sql package 
 *
 *Some of the code used in this classed is sources from the rs2xml.jar file 
 *that in the java project folder
 * 
 */

package com.java.assignment;
import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class QueryProcessing 
{

	private DBConnect databaseConnection;
	private ResultSet result;
	private Statement statement;
	private boolean connectionStatus;
	private boolean queryStatus;


	public QueryProcessing()
	{
		databaseConnection = new DBConnect();
		connectionStatus = databaseConnection.Connect();
	}
	
	public void executeQuery(String query)
	{
		try
		{
			statement = databaseConnection.getConnect().createStatement();
			setResult(statement.executeQuery(query));
			setQueryStatus(true);
		}
		catch(Exception e)
		{
			setQueryStatus(false);
			
		}
	}
	public ResultSet getResult() {
		return result;
	}

	public void setResult(ResultSet result) {
		this.result = result;
	}
	public boolean connectionStatus()
	{
		return connectionStatus;
	}
	public void closeConnection()
	{
		try
		{
			statement.close();
			databaseConnection.closeConnection();
			connectionStatus = false ;
		}
		catch(Exception e)
		{
			System.out.println("Could not close");
		}
		
	}
	public  TableModel queryToTableModel() {
		try 
		{
		    ResultSetMetaData metaData = result.getMetaData();
		    int numberOfColumns = metaData.getColumnCount();
		    Vector<String> columnNames = new Vector<String>();

		    // Get the column names
		    for (int column = 0; column < numberOfColumns; column++)
		    {
			columnNames.addElement(metaData.getColumnLabel(column + 1));
		    }

		    // Get all rows.
		    Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
		    
			while (result.next()) 
			{
				Vector<Object> newRow = new Vector<Object>();
				for (int i = 1; i <= numberOfColumns; i++)
				{
					newRow.addElement(result.getObject(i));
				}
		
				rows.addElement(newRow);
			}
		    

		    return new DefaultTableModel(rows, columnNames);
		} catch (Exception e)
		{
		    System.out.println("Could not enter the data into the table");

		    return null;
		}
	}

	public boolean isQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(boolean queryStatus) {
		this.queryStatus = queryStatus;
	}
}
	
	
