package storage;

import java.util.ArrayList;

public class Config {
	private Storage storage;
	private byte size;
	private int numberOfFiles;
	private User user;
	private ArrayList<String> extensions=new ArrayList<String>();
	

	public ArrayList<String> getExtensions() {
		return extensions;
	}

	public void setExtensions(ArrayList<String> extensions) {
		this.extensions = extensions;
	}

	public Config(Storage storage, User user) {
		super();
		this.storage = storage;
		this.user = user;
	}
	
	public Storage getStrage() {
		return storage;
	}
	public void setStrage(Storage strage) {
		this.storage = strage;
	}
	public byte getSize() {
		return size;
	}
	public void setSize(byte size) {
		this.size = size;
	}
	public int getNumberOfFiles() {
		return numberOfFiles;
	}
	public void setNumberOfFiles(int numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
