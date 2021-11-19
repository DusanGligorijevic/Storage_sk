package storage;




import java.io.ByteArrayOutputStream;
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
import java.util.List;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.image.BufferedImage;





public abstract class Storage{
	private User connectedUser;
	public static final String  StoragePath="C:\\Users\\38160\\Desktop\\Storage";

	private ArrayList<User> users = new ArrayList<User>();
	private Config cfg;


    public Storage() {
    	
    }
    /**
     * Ova metoda se koristi za kreiranje skladista.
     * 
     * @param path Lokacija na koju ce skladiste biti smesteno
     * @param numberOfFiles 
     * @return void
     */
    public abstract void createFiles(String path, String name, int numberOfFiles);
    

    
    public void createFile(String path, String name) {
		File f=new File(StoragePath);
		
    	
    	createFiles(path, name, 1);
    }
    
 
    /**
     * Ova metoda brise skladiste na datoj putanji.
     * 
     * @param path Putanja do skladista
     * @return void
     */
    public abstract void delete(String path);
    
    
    /**
     * Ova metoda premesta skladiste sa jedne
     * lokacije na drugu
     * 
     * @param location Trenutna lokacija fajla
     * @param destination Lokacija na koju ce fajl biti smesten
     * @param file Fajl koji se premesta
     * @return void
     */
    public abstract void transfer(String location,String destination,String file);
    
    
    
    /**
     * Ova metoda se koristi za prikazivanje svih
     * fajlova odredjenog tipa u nekom skladistu
     * 
     * @param f Skladiste
     * @param s tip ekstenzije  
     * @return void
     */
    public abstract void previewExt(String path, String extension);
    
    
    /**
     * Ova metoda se koristi za prikazivanje svih
     * fajlova u nekom skladistu
     * 
     * @param f Skladiste
     * @return void
     */
    public abstract void previewAll(String path);
    
    /**
     * Ova metoda se koristi za prikazivanje svih
     * fajlova koji jesu ili nisu direktorijum,
     * nekom skladistu
     * 
     * @param f Skladiste
     * @return directoriesOnly Da li se trazi skladiste ili ne
     */
    public abstract void previewDir(String path);
    
  
    
    /**
     * Ova metoda se koristi za skidanje
     * fajla ili direktorijuma, sa
     * nekog skladista
     * 
     * @param putanja do fajla ili direktorijuma
     * @return void
     */
    public abstract void download(String path);
    
    
    
    /**
     * Ova metoda se koristi za kreiranje
     * foldera na nekoj putanji.
     * 
     * @param putanja
     * @return void
     */
    public abstract void createFolders(String path, String name, String number);
    
 
    public void createFolder(String path, String name) {
    	
    	createFolders(path, name, "1");
    	
    }
   // public abstract <T> List<T> findAll(String collection, Class<T> type);

       public void initialise(User user) {
    	
    	System.out.println("Korisnik" + user + "  pokusava da kreira skladiste...");
    	
    	if(user.getPrivileges().get(Permissions.create)) {
    		
    		//Instantiate the File class   
            File storage = new File(StoragePath);  
            //Creating a folder using mkdir() method  
            boolean bool = storage.mkdir();  
            if(bool){  
               System.out.println("Skladiste je uspesno kreirano!");  
               createConfigFile(user);
            }else{  
               System.out.println("Greska pri kreiranju skladista!");  
            }  
    	}else
    	System.out.println("Korisnik nema dozvolu da kreira skladiste!");
    	
    	System.out.println("Uspesno logovanje!");
  
    }

       
	public void createConfigFile(User user) {
		Config config = new Config (this,user);
		config.setNumberOfFiles(100);
		config.setSize((byte)30000000);
		config.setStrage(this);
		JSONSaveConfig(config);
		this.cfg=config;
		
	}
    /**
     * Ova metoda se koristi za postavljanje
     * maksimalne velicine skladista u njegovoj konfiguraciji.
     * 
     * @param size Nova velicina skladista
     * @return void
     */
	public void setMaxSize(int size) {
		File f=new File(StoragePath);
		if(f.getTotalSpace()>(byte)size) {
			System.out.println("Velicina skladista vec prevazilazi zadatu!");
		}else {
			this.cfg.setSize((byte)size);
		}
	}
	
	public Config getCfg() {
		return cfg;
	}
	public void setCfg(Config cfg) {
		this.cfg = cfg;
	}
	/*public void maxSizeLimit(Config c,int size) {
		File f=new File(StoragePath);
		if(f.getTotalSpace()>(byte)size) {
			System.out.println("Velicina skladista vec prevazilazi zadatu!");
		}else {
			c.setSize((byte)size);
		}
	}*/
    /**
     * Ova metoda se koristi za zabranu dodavanja novih
     * fajlova nekog tipa.Postavlja se u konfiguraciji 
     * skladista.
     * @param s ekstenzija
     * @return void
     */
	public void extensionLimit(String s) {
		//TO-DO napraviti listu i u konfig fajlu
		this.cfg.getExtensions().add(s);
		System.out.println("Fajlovi sa "+s+"ekstenzijom vise ne mogu biti dodavani!");
		
	}
    /**
     * Ovam metodom se postavlja maksimalni broj fajlova koji se 
     * mogu dodati u neko skladiste.
     * 
     * @param num Novi maksimalni broj fajlova
     * @return void
     */
	public void fileAmountLimit(int num) {
		File f=new File(StoragePath);
		File [] prevFiles=f.listFiles();
		int cnt=0;
		for(File file:prevFiles) {
			cnt++;
		}
		if(cnt>num) {
			System.out.println("Broj fajlova vec prevazilazi zadati!");

		}else {
			this.cfg.setNumberOfFiles(num);
		}
		
	}
    /**
     * Metoda koja se koristi za dodavanje korisnika u skladiste.
     * 
     * @param user Kosrisnik koji se dodaje
     * @return void
     */
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
    }
        
        public void JSONSaveConfig(Config config) {
            JsonArray jsonArray = new JsonArray();
           
                JsonObject obj = new JsonObject();
                JsonObject objItem =  new JsonObject();
                objItem.addProperty("size ", config.getSize());
                objItem.addProperty(" numberOfFiles ",  config.getNumberOfFiles());
                objItem.addProperty(" Admin ",  config.getUser().toString());
                objItem.addProperty(" Storage ",  config.getStrage().toString());
                obj.add(" User ", objItem);
                jsonArray.add(obj);
        
        try (FileWriter file = new FileWriter(StoragePath+"\\Config.json")) {
            file.write(jsonArray.toString());
            System.out.println("Successfully Copied JSON Object Config to File...");
            System.out.println("\nJSON Object config: " + jsonArray);
        } catch(Exception e){
            System.out.println(e);

        }
        
    }
        /**
         * Metoda kojom se korisnik diskonektuje
         * se skladista.
         * 
         * @param user Korisnik
         * @return void
         */
    public void disconnect(User user) {
		if(user.equals(getConnectedUser())) {
			setConnectedUser(null);
			System.out.println("Uspesno diskonektovanje!");
		}		
	}
    
    
    public void saveImage() {
    	try {
        	BufferedImage bi = ImageIO.read(new URL("https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg"));
    	    ByteArrayOutputStream tmp = new ByteArrayOutputStream();
    	    ImageIO.write(bi, "png", tmp);
    	    tmp.close();
    	   	int b=tmp.size();
    	   	File f = new File(StoragePath);
    	   	if(this.getCfg().getSize()>((byte)b+(byte)(f.getTotalSpace()-f.getUsableSpace()))) {
    	   		System.out.println("idemooo");
                File outputfile = new File(StoragePath+"\\saved.jpg");
                System.out.println("idemooo222222");
                ImageIO.write(bi, "png", outputfile);
                System.out.println("idemooo33333");
    	   	}else {
    	   		System.out.print("U skladistu nema dovoljno mesta!");
    	   	}
        	
            
        } catch (IOException e) {
            // handle exception
        }
       
    }
    /**
     * Ova metoda se koristi za povezivanje 
     * korisnika sa skladistem.
     * 
     * @param user Korisnik koji se povezuje
     * @return void
     */
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
