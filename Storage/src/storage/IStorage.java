package storage;

public interface IStorage {
	public void create(String path, byte maxSize, int maxFolders);
	public void delete(String path);
	public void preview();
	public void transfer();
}
