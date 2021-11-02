package storage;




import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.image.BufferedImage;





public abstract class Storage{
	private User connectedUser;
	public static final String  StoragePath="C:\\Users\\38160\\Desktop\\Storage";

	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<String> extensions=new ArrayList<String>();


    public Storage() {
    	
    }
    public abstract void create(String path, int maxFolders);
    public abstract void delete();
    public abstract void transfer(File f);
    public abstract void preview(File f);
    
    
    public void create(String path, int maxSize, int maxFolders) {
    	
 
		create(path,maxFolders);
	}


	public void delete(String path) {

		File f = new File(path);
		if(f.delete()) {
			System.out.println("Folder je izbrisan");
		}else {
			System.out.println("Folder nije izbrisan");
		}
	}


	public void preview() {
		//TODO Implementirati za bilo koji tip fajlova
		File folder = new File("C:\\Users\\38160\\Desktop\\Storage");
		File [] prevFiles=folder.listFiles();
		System.out.println("Trazeni fajlovi:");
		for(File f:prevFiles) {
			if(f.getName().contains(".jpg")) {
				System.out.println(f.getName());
				
			}
		}


		
	}


	public void transfer() {
		File folder = new File("C:\\Users\\38160\\Desktop\\Storage");
		File [] prevFiles=folder.listFiles();
		for(File f:prevFiles) {
				File source = new File("C:\\Users\\38160\\Desktop\\Storage\\saved.jpg");
				if(source.exists()) {
					File dest = new File("C:\\\\Users\\\\38160\\\\Desktop\\idegas.jpg");
					try {
						Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else
					System.out.println("Ne postoji folder!");	
		}	
	}

       public void initialise(User user) {
    	
    	System.out.println("Korisnik" + user + "  pokusava da kreira skladiste...");
    	/*
    	if(!user.getPassword().contentEquals("pass")) {
    		System.out.println("Netacna lozinka!");
    		return;
    	}
    	*/
    	if(user.getPrivileges().get(Permissions.create)) {
    		
    		//Instantiate the File class   
            File storage = new File(StoragePath);  
            //Creating a folder using mkdir() method  
            boolean bool = storage.mkdir();  
            if(bool){  
               System.out.println("Skladiste je uspesno kreirano!");  
            }else{  
               System.out.println("Greska pri kreiranju skladista!");  
            }  
    	}else
    	System.out.println("Korisnik nema dozvolu da kreira skladiste!");
    	
    
    	
    	System.out.println("Uspesno logovanje!");
  
    }
	
    public void addUser(User user) {
    	getUsers().add(user);
    	
    }
    
    public void JSONSave(ArrayList<User> users) {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0;i < getUsers().size() ; i++) {
            JsonObject obj = new JsonObject();
            JsonObject objItem =  new JsonObject();
            objItem.addProperty("username", getUsers().get(i).getUsername());
            objItem.addProperty("password",  getUsers().get(i).getPassword());
            objItem.addProperty("record_permission",  getUsers().get(i).getPrivileges().get(Permissions.record));
            objItem.addProperty("download_permission",  getUsers().get(i).getPrivileges().get(Permissions.download));
            objItem.addProperty("delete_permission",  getUsers().get(i).getPrivileges().get(Permissions.delete));
            objItem.addProperty("preview_permission",  getUsers().get(i).getPrivileges().get(Permissions.preview));
            obj.add("User"+i, objItem);
            jsonArray.add(obj);
        }
        
        try (FileWriter file = new FileWriter(StoragePath+"\\Users.json")) {
            file.write(jsonArray.toString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + jsonArray);
        } catch(Exception e){
            System.out.println(e);

        }
        
    }
    
    public void disconnect(User user) {
		if(user.equals(getConnectedUser())) {
			setConnectedUser(null);
			System.out.println("Uspesno diskonektovanje!");
		}		
	}
    public void saveImage() {
    	try {
    		
        	BufferedImage bi = ImageIO.read(new URL("https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg"));
        	System.out.println("idemooo");
            File outputfile = new File(StoragePath+"\\saved.jpg");
            System.out.println("idemooo222222");
            ImageIO.write(bi, "png", outputfile);
            System.out.println("idemooo33333");
            
        } catch (IOException e) {
            // handle exception
        }
       
    }
    public void connect(User user) {
		if(getConnectedUser()==null && getUsers().contains(user)) {
			setConnectedUser(user);
			System.out.println("Korisnik "+ user.getUsername() + " je konektovan!");
		}		
	}
    public User getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

}
