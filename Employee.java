


import javax.validation.constraints.Pattern;



public class Employee implements Comparable<Employee> 


{
	
	
	
	
	@Pattern(regexp="^[0-9]*+", message="{ID Number can only be numbers}")
	private String ID;
	
@Pattern(regexp="^[A-Za-z-'\\s\\]*+", message="{First name can only be alphabets, spaces, hyphens and single quotes}")
	private String FirstName;
@Pattern(regexp="^[A-Za-z-'\\s\\]*+", message="{Last name can only be alphabets, spaces, hyphens and single quotes}")
	private String LastName;
	
@Pattern(regexp="^[A-Za-z0-9\\s\\]*+", message="{Job Title can only be alphanumeric and contain spaces}")
	private String JobTitle;


	private String str;
	
	public Employee(String id, String fname, String lname, String jtitle)
	{
		this.ID=id;
		this.FirstName=fname;
		this.LastName=lname;
		this.JobTitle=jtitle;
	}
	



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		this.ID = iD;
	}



	public String getFirstName() {
		return FirstName;
	}



	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}



	public String getLastName() {
		return LastName;
	}



	public void setLastName(String lastName) {
		this.LastName = lastName;
	}



	public String getJobTitle() {
		return JobTitle;
	}



	public void setJobTitle(String jobTitle) {
		this.JobTitle = jobTitle;
	}
	
	public String toString()
	{
		
		str=this.ID+"  "+this.FirstName+"  "+this.LastName+"  "+this.JobTitle;
		return str;
	
	}




	@Override
	public int compareTo(Employee e) 
	{
		
				return this.getFirstName().compareTo(e.getFirstName());
	}

}
