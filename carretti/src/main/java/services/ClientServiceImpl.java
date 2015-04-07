package services;

import java.util.Hashtable;

import carretti.Session;

public class ClientServiceImpl implements ClientService{

	
	Hashtable<String, String> usersData = new Hashtable<String,String>();

	public ClientServiceImpl() {
		//usersData.put("user1","f543c2ccfa550171f5816c9fdc310165c7e1cc10");
		//System.out.println("Key 'user1' has value = " + usersData.get("user1"));
	}
	
	@Override
	public String findUserByEmail(String email) {
		return usersData.get(email);
	}
	
	public void populateDummyData() {
		usersData.put("user1","f543c2ccfa550171f5816c9fdc310165c7e1cc10");

	}
	
	

}
