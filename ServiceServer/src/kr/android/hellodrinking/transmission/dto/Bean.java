package kr.android.hellodrinking.transmission.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class Bean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8539514135837580213L;
	
	protected String id = "";
	protected String imageFilePath = "";
	protected byte[] buffer = {};

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null)
			this.id = id;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		if (imageFilePath != null) {
			this.imageFilePath = imageFilePath;
			convertToBytes();
		}
	}

	protected void convertToBytes() {
		try {
			File file = new File(imageFilePath);
			if (!file.exists() || !file.isFile())
				return;

			byte[] buf = new byte[(int) file.length()];
			FileInputStream reader = new FileInputStream(file);
			reader.read(buf);
			this.buffer = buf;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBuffer(byte[] buffer) {
		if (buffer != null)
			this.buffer = buffer;
	}

	public byte[] getBuffer() {
		return buffer;
	}
}
