import java.io.File;

import javax.swing.filechooser.FileFilter;

public class filter extends FileFilter{
	private String fileType="TXT";
	private char dot='.';
	@Override
	public boolean accept(File file) {
		if(file.isDirectory()){
			return true;
		}
		if(extension(file).equalsIgnoreCase(fileType)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "txt only";
	} 
	public String extension(File f){
		int indexFile=f.getName().lastIndexOf(dot);
		if(indexFile>0&&indexFile<f.getName().length()-1){
			return f.getName().substring(indexFile-1);
		}else{
			return "";
		}
	}
	
}
