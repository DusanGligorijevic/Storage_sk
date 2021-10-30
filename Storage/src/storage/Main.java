package storage;

import java.util.Scanner;


public class Main {
	private static String username, password;

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Kreiranje SuperAdmina..");
		System.out.println("Unesite username: ");
		username = sc.next();
		System.out.println("Unesite sifru: ");
		password = sc.next();
		User user = new User(username,password);
		user.getPrivileges().put(Permissions.delete, true);
		user.getPrivileges().put(Permissions.record, true);
		user.getPrivileges().put(Permissions.download, true);
		user.getPrivileges().put(Permissions.preview, true);
		user.getPrivileges().put(Permissions.create, true);
		System.out.println("Uspesno ste kreirali admina. Admin ima sve privilegije!");
		Storage.getInstance().initialise(user);
		//user.createUser();
		//for(User u : Storage.getInstance().getUsers()) {
		//	System.out.println(u.getUsername().toString());
		//}
		//Storage.getInstance().disconnect(user);
		//Storage.getInstance().connect(user);
		Storage.getInstance().JSONSave(Storage.getInstance().getUsers());
		
		Storage.getInstance().create(Storage.getInstance().StoragePath, (byte) 100, 10);
	
		
		Storage.getInstance().delete(Storage.getInstance().StoragePath+"\\file0");
		Storage.getInstance().saveImage();
	}

}
