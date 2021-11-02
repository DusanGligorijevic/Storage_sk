package storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import storage.Permissions;

public class User {
	
	private String username, password;
	private HashMap<Permissions, Boolean> privileges;
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.privileges = new HashMap<Permissions, Boolean>();
	}
	
	public void createUser() {
		/*
		if(!Storage.getInstance().getConnectedUser().getPrivileges().get(Permissions.create)) {
			System.out.println("Nije ulogovan admin. Korisnik "+ Storage.getInstance().getConnectedUser()+
				" nema dozvolu da kreira druge korisnike!");
			return;
		}
		*/
		Scanner sc = new Scanner(System.in);
		System.out.println("Unesite username novog korisnika: ");
		String username = sc.next();
		System.out.println("Unesite password novog korisnika: ");
		String pass = sc.next();
		User user = new User(username, pass);
		System.out.println("Da li novi korisnik ima dozvolu za snimanje fajlova?");
		while(true) {
			if(sc.next().contentEquals("y")) {
				user.getPrivileges().put(Permissions.record,true);
				break;
			}
			if(sc.next().contentEquals("n")) {
				user.getPrivileges().put(Permissions.record,false);
				break;
			}
		}
		System.out.println("Da li novi korisnik ima dozvolu za skidanje fajlova?");
		while(true) {
			if(sc.next().contentEquals("y")) {
				user.getPrivileges().put(Permissions.download,true);
				break;
			}
			if(sc.next().contentEquals("n")) {
				user.getPrivileges().put(Permissions.download,false);
				break;
			}
		}
		System.out.println("Da li novi korisnik ima dozvolu za pregled fajlova?");
		while(true) {
			if(sc.next().contentEquals("y")) {
				user.getPrivileges().put(Permissions.preview,true);
				break;
			}
			if(sc.next().contentEquals("n")) {
				user.getPrivileges().put(Permissions.preview,false);
				break;
			}
		}
		System.out.println("Da li novi korisnik ima dozvolu za brisanje fajlova?");
		while(true) {
			if(sc.next().contentEquals("y")) {
				user.getPrivileges().put(Permissions.delete,true);
				break;
			}
			if(sc.next().contentEquals("n")) {
				user.getPrivileges().put(Permissions.delete,false);
				break;
			}
		}
		//Storage.getInstance().addUser(user);
		System.out.println("Uspesno ste dodali korisnika!");
		
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public HashMap<Permissions, Boolean> getPrivileges() {
		return privileges;
	}


	public void setPrivileges(HashMap<Permissions, Boolean> privileges) {
		this.privileges = privileges;
	}

	
}
