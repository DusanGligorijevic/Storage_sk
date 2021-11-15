package storage;

public class Config {
	private Storage strage;
	private byte size;
	private int numberOfFiles;
	private User user;
	public Config(Storage strage, User user) {
		super();
		this.strage = strage;
		this.user = user;
	}
	public Storage getStrage() {
		return strage;
	}
	public void setStrage(Storage strage) {
		this.strage = strage;
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
