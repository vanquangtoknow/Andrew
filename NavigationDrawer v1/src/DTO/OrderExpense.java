package DTO;

public class OrderExpense {
	public OrderExpense()
	{
		this.Id = 0;
		this.Name = "TmpName";
		this.Date = "TmpDate";
		this.Price = 0;
	}
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
    private String Date;
	public String getDate() {
		return Date;
	}

	public void setDate(String value) {
		Date = value;
	}
	private float Price;
	public float getMoney() {
		return Price;
	}

	public void setMoney(float value) {
		Price = value;
	}
}
