package storage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



public final class Storage {
	private User connectedUser;
	private static final String  StoragePath="C:\\Users\\38160\\Desktop\\Storage";
	private static Storage instance;
	private ArrayList<User> users = new ArrayList<User>();

	//singleton
    private Storage() {
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
    public void initialise(User user) {
    	
    	System.out.println("Korisnik "+ user.getUsername().toString() +" pokusava da kreira skladiste...");
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
    	
    	setConnectedUser(user);
    	addUser(user);
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
			Storage.getInstance().setConnectedUser(null);
			System.out.println("Uspesno diskonektovanje!");
		}		
	}
    public void connect(User user) {
		if(getConnectedUser()==null && getUsers().contains(user)) {
			Storage.getInstance().setConnectedUser(user);
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
