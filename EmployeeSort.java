import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// import Apache Poi for reading / writing Excel files
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;
import java.io.*;
import java.util.stream.*; 
import java.io.FileNotFoundException;



import javax.validation.*;
import org.hibernate.validator.HibernateValidator;
import javax.validation.Configuration;
import javax.validation.constraintvalidation.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintValidator;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;



public class EmployeeSort 
{
    private static String tempId=null;
    private static String tempFN=null;
    private static String tempLN=null;
    private static String tempJT=null;
    private static String str1=null;
    private static boolean isEmp;
    
    
    private static  Validator validator;
    
    
    private static Logger logger=null;
	 
    
	public static void main(String[] args) throws IOException, NoHeaderException,FileNotFoundException,EmptyExcelException
	
	{
		
		Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        validator = factory.getValidator();
        factory.close();
		
		
		logger=logger.getRootLogger();
		BasicConfigurator.configure();
		logger.setLevel(Level.ALL);
		
		
		
		
		
		
		
		
		FileInputStream file= new FileInputStream( "C:\\Users\\Ye Wang\\eclipse-workspace\\Assignmen2edition\\Employee.xls" ); //read excel sheet
		
	    try 
	    
	 {
	    	
	    
		
		Workbook workbook= new HSSFWorkbook(file);// create a workbook to hold excel sheet
		
		for(int i = 0; i < workbook.getNumberOfSheets(); i++)
		{
		       isEmp=isSheetEmpty(workbook.getSheetAt(i));
		       if(isEmp==true) {
		    	   break;
		       }
		}
		
		if (isEmp==true)
		{
		
		Sheet sheet=workbook.getSheetAt(0);// read from very first sheet of the excel file
		
		Iterator <Row> rowIterator=sheet.iterator();
		// To check through all the rows in excel sheet
		
		 ArrayList <Employee> EmployeeList= new ArrayList();
		 
		 
		while (rowIterator.hasNext())
		{
			 Row row = rowIterator.next();
			 Iterator <Cell> cellIterator= row.cellIterator();
			 //go through each column of the row
			 while(cellIterator.hasNext())
			 {
				 Cell cell=cellIterator.next();
				 
				 if (cell.getCellTypeEnum() == CellType.NUMERIC)
				 {
					 int id1=0;
					 id1=(int)cell.getNumericCellValue();
					 
					 tempId=Integer.toString(id1);// conver int value ID to string
				 }
				 else 
				 {    
					 if(str1==null)
					 {
						 str1=cell.getStringCellValue()+"\t/";
					 }
					 else
					 {
						 str1+=cell.getStringCellValue()+"\t/";
					 }
				 }
				 
				 
			   }
			 
			 
			 String [] temp=str1.split("/");
			 tempFN=temp[0];
			 tempLN=temp[1];
			 tempJT=temp[2];
			 str1=null;
			 
			 Employee emp=new Employee(tempId,tempFN,tempLN,tempJT);
			 
			 EmployeeList.add(emp);
			 validator.validate(emp).stream().forEach(EmployeeSort::printError);
		}
		
		
		Iterator<Employee> Check=EmployeeList.iterator();
		       
		       if (Check.next().getID().matches("^[0-9]*+")==true)
		       {
		    	   throw  new NoHeaderException("File doesn't have headers");
		       }
		
		       else {
		    	 EmployeeList.remove(0);
		         Collections.sort(EmployeeList);// sort the object list
		         
		         
		         FileOutputStream fos=new FileOutputStream("sortedEmployee.txt");
		         PrintWriter pw= new PrintWriter(fos);
		         
		        	 Iterator<Employee> itr=EmployeeList.iterator();
		        	   while (itr.hasNext()) 
		        	   {
		        	        pw.println(itr.next().toString());
		                   
		        	   }
		        
		         
		        pw.close();
		       }
		}
		else
		{
			throw new EmptyExcelException("The File imported was blank");
		}
	    }
	    catch(FileNotFoundException e)
	    {
				e.printStackTrace();
	    }
		        
		        
		        
		        
		
	}
	
	
	

private static void printError (ConstraintViolation<Employee> violation)
{
logger.warn(violation.getMessage());
}
	
	



private static boolean isSheetEmpty(Sheet sheet)
{
    Iterator rows = sheet.rowIterator();
    while (rows.hasNext()) {
        HSSFRow row = (HSSFRow) rows.next();
        Iterator cells = row.cellIterator();
        while (cells.hasNext()) {
             HSSFCell cell = (HSSFCell) cells.next();
             if(!cell.getStringCellValue().isEmpty()){
                 return true;
                 
             }
        }
    }
    return false;
}

	
}



	








