package cs622.hw2;

import java.io.*;

public class Merge{

    public static void MergeFiles() throws FileNotFoundException, IOException{
    	
    	File dir = new File(new File("./pubmeddata/").getAbsolutePath()); 
    	String[] fileNames = dir.list();//array of file names
    	
    	byte[] buffer = new byte[2<<20]; 
    	OutputStream out = new FileOutputStream("outFile.txt");
    	
    	System.out.println("Merging Files...");
    	
    	//looping through files using individual file name
    	for (String file: fileNames ) {
    		System.out.println(file);
    		InputStream in = new FileInputStream(new File("./pubmeddata/" + file).getAbsolutePath());
    		int b = 0;
    		while (( b = in.read(buffer)) >= 0) {
    			out.write(buffer, 0, b);	
    		}
    		in.close();
    	}
    	out.close();
    	
    	System.out.println("Merge completed.");
    	
    	
	}

    	
}
    
    
    
    
    
	

