package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListReader {
	
	private String[] fileList;
	private String startingLocation;
	private File file;
	private Scanner sc;
	
	public FileListReader(String startingLocation)  {
		this.startingLocation = startingLocation;
		generateFileList();
	}
	
	private void generateFileList()  {
		this.file = new File(this.startingLocation);
		this.fileList = this.file.list();
	}
	
	public String[] getFileList()  {
		return this.fileList;
	}
	
	public String[] getEasyFileList()  {
		ArrayList<String> list = new ArrayList<String>();
		
		for(String s : this.fileList)  {
			
			int difficulty = 0;
			
			try {
				sc = new Scanner(new File(this.startingLocation+s));
					
				String line = sc.nextLine();
				String data[] = line.split(";");
				
				difficulty = Integer.parseInt(data[0]);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(difficulty == 0)  {
				list.add(s);
			}
			sc.close();
		}
		
		return list.toArray(new String[list.size()]);
	}
	
	public String[] getHardFileList()  {
		ArrayList<String> list = new ArrayList<String>();
		
		for(String s : this.fileList)  {
			int difficulty = 0;
			
			try {
				sc = new Scanner(new File(this.startingLocation+s));
					
				String line = sc.nextLine();
				String data[] = line.split(";");
				
				difficulty = Integer.parseInt(data[0]);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(difficulty == 1)  {
				list.add(s);
			}
			sc.close();
		}
		
		return list.toArray(new String[list.size()]);
	}
	
	public String getRandomFile()  {
		int r = (int)(Math.random() * (this.fileList.length ));
		return (startingLocation+this.fileList[r]);
	}
	
	public String getRandomFile(String[] list)  {
		int r = (int)(Math.random() * (list.length ));
		return (startingLocation+list[r]);
	}
}
