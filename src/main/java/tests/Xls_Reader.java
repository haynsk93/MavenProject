package tests;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;


import java.io.*;
import java.util.Calendar;


public class Xls_Reader {
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	
	public Xls_Reader(String path) {
		
		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
		
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);
		int col_Num=-1;
		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				col_Num=i;
		}
		if(col_Num==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		Cell c = row.getCell(col_Num);
		
		if(c==null)
			return "";
		//System.out.println(cell.getCellType());
		if(c.getCellType()==CellType.STRING)
			  return c.getStringCellValue();
		else if(c.getCellType()==CellType.NUMERIC || c.getCellType()==CellType.FORMULA ){
			  
			  String cellText  = String.valueOf(c.getNumericCellValue());
			  
				  if(DateUtil.isCellDateFormatted(c)){
		           // format in form of M/D/YY
				  double d = c.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(DateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cal.get(Calendar.MONTH)+1 + "/" + 
		                      cellText;
		           
		           //System.out.println(cellText);

		         }

			  
			  
			  return cellText;
		  }else if(c.getCellType()==CellType.BLANK)
		      return ""; 
		  else 
			  return String.valueOf(c.getBooleanCellValue());
		
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return "";
		
	
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		Cell c = row.getCell(colNum);
		if(c==null)
			return "";
		
	  if(c.getCellType()==CellType.STRING)
		  return c.getStringCellValue();
	  else if(c.getCellType()==CellType.NUMERIC || c.getCellType()==CellType.FORMULA ){
		  
		  String cellText  = String.valueOf(c.getNumericCellValue());
		  
		  return cellText;
	  }else if(c.getCellType()==CellType.BLANK)
	      return "";
	  else 
		  return String.valueOf(c.getBooleanCellValue());
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		
		sheet = workbook.getSheetAt(index);
		

		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName))
				colNum=i;
		}
		if(colNum==-1)
			return false;

		sheet.autoSizeColumn(colNum); 
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);

	    // cell style
	    CellStyle cs = workbook.createCellStyle();
	    cs.setWrapText(true);
	    cell.setCellStyle(cs);
	    cell.setCellValue(data);

	    fileOut = new FileOutputStream(path);

		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		
		
		FileOutputStream fileOut;
		try {
			 workbook.createSheet(sheetname);	
			 fileOut = new FileOutputStream(path);
			 workbook.write(fileOut);
		     fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean removeSheet(String sheetName){		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;
		
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
  // find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
				if(index==-1)
					return false;
				else
					return true;
		}
		else
			return true;
	}
	
	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
		 return -1;
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		
		if(row==null)
			return -1;
		
		return row.getLastCellNum();
		
		
		
	}
}