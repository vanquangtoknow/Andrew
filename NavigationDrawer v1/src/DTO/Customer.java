package DTO;

public class Customer {
	int _id;

	private int Id;
    public int getId()
    {
    	return Id;
    }
    public void setId(int nId)
    {
    	Id = nId;
    }
    private String Name;
    public String getName()
    {
    	return Name;
    }
    public void setName(String nName)
    {
    	Name = nName;
    }


    private String Addr;
    public String getAddr()
    {
    	return Addr;
    }
    public void setAddr(String nAddr)
    {
    	Addr = nAddr;
    }

    private String City;
    public String getCity()
    {
    	return City;
    }
    public void setCity(String nCity)
    {
    	City = nCity;
    }

    private String State;
    public String getState()
    {
    	return State;
    }
    public void setState(String nState)
    {
    	State = nState;
    }

    private String Zip;
    public String getZip()
    {
    	return Zip;
    }
    public void setZip(String nZip)
    {
    	Zip = nZip;
    }

    private String Phone;
    public String getPhone()
    {
    	return Phone;
    }
    public void setPhone(String nPhone)
    {
    	Phone = nPhone;
    }

    private String Dob;
    public String getDob()
    {
    	return Dob;
    }
    public void setString(String nDob)
    {
    	Dob = nDob;
    }


    private int Point;
    public int getPoint()
    {
    	return Point;
    }
    public void setPoint(int nPoint)
    {
    	Point = nPoint;
    }
    public Customer()
    {
        Addr = "";
        City = "";
        State = "";
        Zip = "";
        Dob = "";
    }
}
