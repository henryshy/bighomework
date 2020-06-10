import java.io.Serializable;

//收支文件
public class BalFile implements Serializable {
	public static final long serialVersionUID=1L;
	private int id;
	private int date;
	private String type;
	private String item;
	private double bal;
	public int getId() {
		return id;
	}

	public int getDate() {
		return date;
	}

	public double getBal() {
		return bal;
	}

	public String getType() {
		return type;
	}

	public String getItem() {
		return item;
	}

	public BalFile(int id, int date, String type, String item, double bal) {
		this.id = id;
		this.date = date;
		this.type = type;
		this.item = item;
		this.bal = bal;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setBal(double bal) {
		this.bal = bal;
	}
}
