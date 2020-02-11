//Yuting Pan MET CS622
//Program to merge xml files into a single txt file and then perform search function
//to provide information like time, journal of publication, article title, and author name


package cs622.hw2;

import java.io.*;
import java.util.Scanner;

public class Main {
	
	static String searchKeyword;//for search keyword
	static String currentPubmedArticle;//first scanner for printing info
	static String currentLine;//second scanner for searching keywords
	static int count;//used to control iterations in 20s
    static boolean more = true;//to determine whether more results are needed
    static int totalCount=0;//total result count
    
    
	public static void main(String[] args) {
		
		//checking whether a merged file already exist
		File tmpDir = new File("./outFile.txt");
		boolean exists = tmpDir.exists();
		
		if(!exists) {
			try {
				Merge.MergeFiles();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		SearchRecord record = new SearchRecord();
		
		//create scanner for user input
		@SuppressWarnings("resource")
		Scanner input = new Scanner (System.in);
		
		while(true) {
			
			//reset more for other search
			more = true;
			
			System.out.println("Please enter '!' for the search history, "
					+ "'-' to erase the search history, or enter the search keyword:");
			searchKeyword = input.nextLine();
			
			if(searchKeyword.equals("!")) {
				
				//display search history with '!' input
				record.displaySearchHistory();
			}else if(searchKeyword.equals("-")) {
				
				//erase search history with '-' input
				record.eraseSearchHistory();
			}else {
		
				//call search method
				System.out.println("searching...");
				searchKeyword();
				record.saveSearch(searchKeyword);
			}
		
		}

	}
	
	public static void searchKeyword() {
	
	try {
		
		//instantiating objects
        FileReader fileIn = new FileReader(new File("./outFile.txt").getAbsolutePath());
        Scanner scanner1 = new Scanner(fileIn);
        Scanner scanner2 = new Scanner(fileIn);
        currentPubmedArticle = scanner1.nextLine();
        count = 0;
        
        //Initializing scanner1 to first article
        while(scanner1.hasNext() && !(currentPubmedArticle.contains("<PubmedArticle>"))) {  
        	currentPubmedArticle = scanner1.nextLine();

        }
        
        //while loop for more results
        while(more) {
        	//count is reset for loops
        	count = 0;
            
            //search loops scanning one line an a time
	        while(count < 20 && scanner2.hasNextLine()) {
	        	
	        	currentLine = scanner2.nextLine();
	        	
	        	//keyword hit
	        	if((currentLine.contains(searchKeyword))) {
 		            
	        		//keyword hit in desired fields
	        		if(currentLine.contains("<ArticleTitle>")||currentLine.contains("</DescriptorName>")||currentLine.contains("</QualifierName>")
	        				||currentLine.contains("</Keyword>")) {
	        			
	        			count++;
	        			totalCount++;
	        			System.out.println("Result " + totalCount);
	        			
	        			//keep looping till end of article
	        			while(!(currentLine.contains("<PubmedArticle>"))) {
	        				currentLine = scanner2.nextLine();
	        			}
	        			
	        			//printing
	        			while(!(currentPubmedArticle.contains("</PubmedArticle>"))) {
	        				
	        				currentPubmedArticle = scanner1.nextLine();
	        				
	        				//pubdate
	        				while(currentPubmedArticle.contains("<PubDate>")) {
	        					while(!(currentPubmedArticle.contains("</PubDate>"))) {
	        						
	        						currentPubmedArticle = scanner1.nextLine().trim();
	        						if(!(currentPubmedArticle.contains("</PubDate>"))) {
	        							if(currentPubmedArticle.contains("<Year>") || currentPubmedArticle.contains("<Month>")) {
	        								System.out.println(currentPubmedArticle);
	        							}
	        						}
	        					}
	        				}
	        				
	        				//JournalTitle
	        				if(currentPubmedArticle.contains("<Title>")) {
	        						System.out.println("Journal:   " + currentPubmedArticle.trim());
	        				}
	        				
	        				//ArticleTitle
	        				if(currentPubmedArticle.contains("<ArticleTitle>")) {
	    						System.out.println(currentPubmedArticle.trim());
	    				    }  
	        				
	        				//Author names
	        				if(currentPubmedArticle.contains("<LastName>")) {
	        					System.out.print("Author: " + currentPubmedArticle + " ");
	        				}
	        				
	        				if(currentPubmedArticle.contains("<ForeName>")) {
	        					System.out.println(currentPubmedArticle);
	        				}
	        			}
	        			currentPubmedArticle= scanner1.nextLine();
	        		}
	        	}
	        }
	        
	        //input for more results
			@SuppressWarnings("resource")
			Scanner input = new Scanner (System.in);
			System.out.println("Please enter 'N' if no more results are needed, enter anything else to continue");
			String in = input.nextLine();

			if(in.equals("N")) {
				more = false;
				scanner1.reset();
				scanner2.reset();
				count = 0;
			}
      	
	    }
      	
        //close scanners
        scanner1.close();
        scanner2.close();
        System.out.println("Search finished. " + totalCount + " results found.");
        totalCount = 0;
        
	}catch (IOException e){
        	System.out.println(e);
    	}
	}
}
	
