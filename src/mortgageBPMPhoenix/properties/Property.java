package mortgageBPMPhoenix.properties;

// A property will be created only when creating a new MtgRequest
public class Property {
	
	private int id;
	private double area;
	private int currentPrice;
	
	public Property(int id, double area, int currentPrice) {
		this.id = id;
		this.area = area;
		this.currentPrice = currentPrice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	@Override
	public String toString() {
		return "Property [id=" + id + ", area=" + area + ", currentPrice=" + currentPrice + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
