package saurav_practice_base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class testUtil {

	
	public static ArrayList<Object[]>  getTestDataFromText() throws IOException{
		
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		File file = new File("D:\\appium_Automation\\calculator_data.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		
		while ((st = br.readLine()) != null)
		{ 
//			arr[i][0] = st.split(" ")[0];
//			arr[i][1] = st.split(" ")[0];
//			arr[i][2] = st.split(" ")[2];
//			arr[i][4] = st.split(" ")[4];
			Object[] ob= {st};
			myData.add(ob);
		}
		
		
		return myData;
		}
	}


