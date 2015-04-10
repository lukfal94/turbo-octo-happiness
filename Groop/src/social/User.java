
package social;

import java.util.List;

public class User{
   	private String email;
   	private String firstname;
   	private int id;
   	private String lastname;
   	private String organization;
   	private String password;
   	private String phone;
   	private String username;

 	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
 	public String getFirstname(){
		return this.firstname;
	}
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
 	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
 	public String getLastname(){
		return this.lastname;
	}
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
 	public String getOrganization(){
		return this.organization;
	}
	public void setOrganization(String organization){
		this.organization = organization;
	}
 	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
 	public String getPhone(){
		return this.phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
 	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public String getFullname() {
		return this.firstname + " " + this.lastname;
	}
}
