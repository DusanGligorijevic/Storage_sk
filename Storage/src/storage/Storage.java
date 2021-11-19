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
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.image.BufferedImage;





public abstract class Storage{
	private User connectedUser;
	public  String  StoragePath;

	private ArrayList<User> users = new ArrayList<User>();
	private Config cfg;


    public Storage() {
    	
    }
    /**
     * Ova metoda se koristi za kreiranje skladista.
     * 
     * @param path Lokacija na koju ce skladiste biti smesteno
     * 
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
     * Ova metoda premesta fajl sa jedne
     * lokacije u skladistu na drugu
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
     * @param path Skladiste
     * @param extension tip ekstenzije  
     * @return void
     */
    public abstract void previewExt(String path, String extension);
    
    
    /**
     * Ova metoda se koristi za prikazivanje svih
     * fajlova u nekom skladistu
     * 
     * @param path Skladiste
     * @return void
     */
    public abstract void previewAll(String path);
    
    /**
     * Ova metoda se koristi za prikazivanje svih
     * fajlova koji jesu ili nisu direktorijum u
     * nekom skladistu
     * 
     * @param path Skladiste
     * @return directoriesOnly Da li se trazi skladiste ili ne
     */
    public abstract void previewDir(String path);
    /**
     * Ova metoda se koristi za prikazivanje svih
     * fajlova u nekom skladistu u sortiranom formatu.
     * 
     * @param path Skladiste 
     * @return void
     */
    public abstract void previewSorted(String path); 
    
    /**
     * Ova metoda se koristi za prikazivanje 
     * fajla odredjenog naziva u skladistu,ukoliko ne postoji
     * korisnik biva obavesten.
     * 
     * @param path Skladiste
     * @param name naziv fajla  
     * @return void
     */
    public abstract void prevName(String path,String name);
    
    /**
     * Ova metoda se koristi za skidanje
     * fajla ili direktorijuma, sa
     * nekog skladista
     * 
     * @param path putanja do fajla ili direktorijuma
     * @return void
     */
    public abstract void download(String path);
    
    
    
    /**
     * Ova metoda se koristi za kreiranje vise 
     * foldera na nekoj putanji.
     * 
     * @param path Putanja skladista
     * @param name Naziv foldera
     * @param number broj foldera
     * 
     * @return void
     */
    public abstract void createFolders(String path, String name, String number);
    
    /**
     * Ova metoda se koristi za kreiranje jednog 
     * foldera na nekoj putanji.
     * 
     * @param path Putanja skladista
     * @param name Naziv foldera
     * 
     * @return void
     */
    public void createFolder(String path, String name) {
    	
    	createFolders(path, name, "1");
    	
    }
    /**
     * Ova metoda se koristi za kreiranje 
     * samog skladista.
     * 
     * @param storagePath Putanja skladista
     * @param user Korisnik kreator,superuser
     * 
     * @return void
     */
      public abstract void initialise(User user, String storagePath);

      /**
       * Metoda koja se koristi za kreiranje podataka o 
       * konfiguraciji u skladistu.
       * @param user Korisnik 
       * 
       * @return void
       */ 
	public void createConfigFile(User user) {
		Config config = new Config (this,user);
		Scanner sc = new Scanner(System.in);
		System.out.println("Unesite broj dozvoljenih fajlova u skladistu.");
		config.setNumberOfFiles(Integer.parseInt(sc.nextLine()));
		System.out.println("Unesite maksimalnu velicinu skaldista.");
		config.setSize((byte)Integer.parseInt(sc.nextLine()));
		while(true) {
			System.out.println("Unesite tip fajla koji ne moze biti dodat. Kada zavrsite prtisnite 0.");
			config.getExtensions().add(sc.nextLine());
			if(sc.nextLine().contentEquals("0"))
				break;
			
		}
		config.setStorage(this);
		JSONSaveConfig(config);
		this.cfg=config;
		System.out.println("Zavrsena konfiguracija skladista.");
	}
    /**
     * Ova metoda proverava uslove iz konfiguracije.
     * 
     * @param path Putanja skladista
     * @param name Naziv ekstenzija
     * 
     * @return void
     */
	public boolean check(String path, String name){
		File f = new File(path);
		if (f.exists()) {
			if(!checkMaxSize(f.getTotalSpace()) || !FilesLimit() || !extensionLimit(name)) {
				return false;
			}
			System.out.println("Provera zavrsena uspesno.");
			return true;
		}else{
			System.out.println("Greska1");
			return false;
		}
	}
    /**
     * Ova metoda se koristi za proveru
     * maksimalne velicine skladista u njegovoj konfiguraciji.
     * 
     * @param size Nova velicina skladista
     * @return void
     */
	public boolean checkMaxSize(long size) {
		File f=new File(StoragePath);
		System.out.println(f.getTotalSpace() +" "+ size);
		if(6402476318721l> size) {
			return true;
		}else {
			System.out.println("Nema dovoljno prostora u skladistu.");
			return false;
		}
	}
	
	public Config getCfg() {
		return cfg;
	}
	public void setCfg(Config cfg) {
		this.cfg = cfg;
	}
    /**
     * Ova metoda se koristi za proveru
     * maksimalne kolicine fajlova skladista
     *  u njegovoj konfiguraciji.
     * 
     * @return void
     */
	public boolean FilesLimit() {
		File f=new File(StoragePath);
		if(f.exists()) {
			System.out.println(f.listFiles().length +" "+getCfg().getNumberOfFiles());
		if(f.listFiles().length < getCfg().getNumberOfFiles()) {
			return true;
		}else {
			System.out.println("Skladiste sadrzi maksimalni broj fajlova.");
			return false;
		}
	}else
	{
		System.out.println("Greska2");
		return false;
	}
	}
    /**
     * Ova metoda se koristi za zabranu dodavanja novih
     * fajlova nekog tipa.Postavlja se u konfiguraciji 
     * skladista.
     * @param s ekstenzija
     * @return void
     */
	public boolean extensionLimit(String path) {
			if(path.contains(".")) {
				String[] parts = path.split("\\.");
				System.out.println(getCfg().getExtensions());
				for(String s: getCfg().getExtensions()) {
					if(s.toString().contentEquals(parts[1])) {
						System.out.println("Zabranjen fajl!");
						return false;
					}
				}
					return true;
				}	
			else {
				System.out.println("Pogresan format putanje do fajla");
				return false;
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
    	JSONSave(getUsers());
    }
    /**
     * Metoda koja se koristi za kreiranje podataka o 
     * korisnicima u skladistu.
     * 
     * @param users Korisnici koji se dodaju
     * @return void
     */
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
            System.out.println("Successfully Copied JSON Object Config to File...");
            System.out.println("\nJSON Object config: " + jsonArray);
        } catch(Exception e){
            System.out.println(e);

        }
    }
    /**
     * Metoda za kreiranje konfiguracionog fajla
     * u skladistu.
     * 
     * @param config Konfiguracija za koju se pravi fajl
     * @return void
     */
        public void JSONSaveConfig(Config config) {
            JsonArray jsonArray = new JsonArray();
           
                JsonObject obj = new JsonObject();
                JsonObject objItem =  new JsonObject();
                objItem.addProperty("size ", config.getSize());
                objItem.addProperty(" numberOfFiles ",  config.getNumberOfFiles());
                objItem.addProperty(" Admin ",  config.getUser().toString());
                objItem.addProperty(" Storage ",  config.getStorage().toString());
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
    public void connect(String username, String password) {
    	for(User u: getUsers()) {
    		
		if(getConnectedUser()==null && u.getUsername().contentEquals(username) && u.getPassword().contains(password)) {
			setConnectedUser(u);
			System.out.println("Korisnik "+ u.getUsername() + " je konektovan!");
		}
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
