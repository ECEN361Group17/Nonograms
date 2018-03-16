package fileIO;

import java.io.File;

public class FileListReader {
	
	private String[] fileList;
	private String startingLocation;
	private File file;
	
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
	
	public String getRandomFile()  {
		int r = (int)(Math.random() * (this.fileList.length ));
		return (startingLocation+this.fileList[r]);
	}
}
