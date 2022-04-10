package tests;

import tests.Xls_Reader;

public class DataFile {
	//LoginTest
		Xls_Reader d=new Xls_Reader("C:\\Selenium\\Nikul Patel\\Testing\\scotialoginTest.xlsx");
		public String wrongEmail = d.getCellData("Sheet1", "Wrong Email", 2);
		public String wrongPassword = d.getCellData("Sheet1", "Wrong Pword", 2);
		public String emailspecial = d.getCellData("Sheet1", "Email Special", 2);
		public String globalError = d.getCellData("Sheet1", "Gl Error", 2);
		public String uNameError = d.getCellData("Sheet1", "Uname Error", 2);
		public String pError = d.getCellData("Sheet1", "pError", 2);
		public String sPCharError = d.getCellData("Sheet1", "spError", 2);
		
		
		//footerTest
		
		//profileTest
		
		//addtocartTest
		
		//SearchTest
}
