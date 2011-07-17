package th.co.fingertip.pillsalert.factory;

import th.co.fingertip.pillsalert.FileManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageFactory {
	/**
     * get bitmap from image path
     * 
     * @param image_path string expressing image path
     * @return new bitmap according to supplied path
     */
	public static Bitmap get_bitmap(String file_name){
		return BitmapFactory.decodeStream(
			FileManager.readFromFile(file_name)
		);
	}
	public static boolean save_bitmap(Bitmap bitmap, String file_name){
		return FileManager.writeToFile(bitmap, FileManager.buildFullPath("", file_name));
	}
}
