package services;

import java.util.Hashtable;


public class ClientServiceImpl implements ClientService{

	
	Hashtable<String, String> usersData = new Hashtable<String,String>();
	
	public ClientServiceImpl() {
		populateDummyData();
	}
	
	@Override
	public String findUserByEmail(String email) {
		return usersData.get(email);
	}
	
	public void populateDummyData() {
		usersData.put("user1","f543c2ccfa550171f5816c9fdc310165c7e1cc10");

	}
	
	

}
