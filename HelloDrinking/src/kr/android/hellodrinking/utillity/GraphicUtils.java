package kr.android.hellodrinking.utillity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class GraphicUtils {
	public static final String TEMP_IMAGE_FILE_NAME = "temp.jpg";
	public static Drawable DEFAULT_NO_SEARCH_IMAGE;
	private static final int POIOVERLAY_IMAGE_WIDTH = 150;
	private static final int POIOVERLAY_IMAGE_HEIGHT = 150;

	private GraphicUtils() {
	}
	
	public static void setDefaultNoSearchImage(Resources res, int drawableId){
		DEFAULT_NO_SEARCH_IMAGE = res.getDrawable(drawableId);
	}

	public static File createTempImageFile(Context context, Drawable drawable) {
		File cacheDir = context.getCacheDir();
		File file = null;
		try {
			if (!cacheDir.isDirectory() || !cacheDir.exists())
				cacheDir.mkdir();
		} catch (Exception e) {
			Toast.makeText(context, "Create Folder Error about Cache Directory", Toast.LENGTH_SHORT).show();
		}
		try {
			file = new File(context.getCacheDir(), TEMP_IMAGE_FILE_NAME);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);

			Rect rect = drawable.getBounds();

			Bitmap bitmap = Bitmap.createBitmap(rect.right, rect.bottom, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, rect.right, rect.bottom);
			drawable.draw(canvas);

			bitmap.compress(CompressFormat.JPEG, 80, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			Toast.makeText(context, "Save Error about Image File", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return file;
	}

	public static File createImageFile(Context context, byte[] buffer, File file) {
		File cacheDir = context.getCacheDir();
		try {
			if (!cacheDir.isDirectory() || !cacheDir.exists())
				cacheDir.mkdir();
		} catch (Exception e) {
			Toast.makeText(context, "Create Folder Error about Cache Directory", Toast.LENGTH_SHORT).show();
		}

		String path = file.getName();
		int index = path.lastIndexOf('\\');
		String name = path.substring(index + 1);

		File imagefile = new File(cacheDir, name);
		FileOutputStream fileWriter = null;

		try {
			fileWriter = new FileOutputStream(imagefile);
			fileWriter.write(buffer);
			fileWriter.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		return imagefile;
	}

	public static Bitmap createBitmapFromImageFile(File imagefile) {
		Bitmap bitmap = null;
		try {
			FileInputStream fis = new FileInputStream(imagefile);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (IOException e) {
			e.printStackTrace();
			if (imagefile.exists())
				imagefile.delete();
		}
		return bitmap;
	}
	
	public static Drawable getOverlayDrawableFromBitmap(Bitmap bitmap) {
		Drawable image;
		if (bitmap != null) {
			bitmap = Bitmap.createScaledBitmap(bitmap, POIOVERLAY_IMAGE_WIDTH, POIOVERLAY_IMAGE_HEIGHT, true);
			image = new BitmapDrawable(bitmap);
		} else
			image = GraphicUtils.DEFAULT_NO_SEARCH_IMAGE;
		image.setAlpha(180);
		return image;
	}
}
