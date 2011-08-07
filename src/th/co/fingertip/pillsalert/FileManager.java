package th.co.fingertip.pillsalert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileManager {

	private static String STATE = Environment.getExternalStorageState();
	public static final String ROOT = Environment.getExternalStorageDirectory().toString();
	public static final String MAIN_DIRECTORY =  ROOT + File.separator + "PillsAlert";
	
	public FileManager() {
		
	}
	
	public static boolean isAvailableState() {
		boolean result = false;
		if (Environment.MEDIA_MOUNTED.equals(STATE)) {
			result = true;
		}
		return result;
	}
	
	public static boolean isWritableState() {
		boolean result = false;
		if (isAvailableState() && Environment.MEDIA_MOUNTED_READ_ONLY.equals(STATE) == false) {
			result = true;
		}
		return result;
	}

	public static String buildFullPath(String sub_folder, String file_name) {
		StringBuilder s = new StringBuilder(MAIN_DIRECTORY);
		if (sub_folder.equals("")) {
			s.append(File.separatorChar).append(file_name);
		} else {
			s.append(File.separatorChar).append(sub_folder)
			.append(File.separatorChar).append(file_name);
		}
		return s.toString();
	}
	
	public static boolean writeToFile(Bitmap bit_map, String name) {
		boolean result = false;
		FileOutputStream file_stream = null;
		String directory = MAIN_DIRECTORY;
		
		if (isAvailableState() && isWritableState()) {
			//make folder
			File dir = new File(directory);
			File file = new File(name);
			if (!dir.exists()) { // if folder doesn't exist, make one here
				dir.mkdir();
			}
			if (file.exists()) { // delete existing file
				file.delete();
			}
			if (dir.canWrite()) {
				try {
					file_stream = new FileOutputStream(file);
					bit_map.compress(Bitmap.CompressFormat.PNG, 100, file_stream);
					file_stream.flush();
					file_stream.close();
					result = true;
				} catch (Exception e) {
					
					result = false;
				}
			} else {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}
	
	public static FileInputStream readFromFile(String name) {
		FileInputStream file_stream = null;
		String directory = MAIN_DIRECTORY;
		File f = new File(directory, name);
		try {
			file_stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			
		}
		
		return file_stream;
		
	}
	
	public static FileInputStream readFromFile(String name, String directory){
		FileInputStream file_stream = null;
		File f = new File(directory, name);
		try {
			file_stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			
		}
		
		return file_stream;
	}
	
}
