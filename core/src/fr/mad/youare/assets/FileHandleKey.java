package fr.mad.youare.assets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.badlogic.gdx.files.FileHandle;
import com.shephertz.app42.paas.sdk.java.crypto.CryptoService;

public class FileHandleKey extends FileHandle {

	private String key;
	
	
	
	public FileHandleKey(File file) {
		super(file);
	}



	public FileHandleKey(String fileName) {
		super(fileName);
	}



	@Override
	public InputStream read() {
		if (key == null)
			try {
				return new DigestInputStream(super.read(), MessageDigest.getInstance("MD5")){
					@Override
					public void close() throws IOException {
						super.close();
						key = new String(this.getMessageDigest().digest());
					}
				};
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return super.read();
			}
		return super.read();
	}

	public String key(){
		return key;
	}
}
