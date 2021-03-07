package db;

public class RowData {

	public String day_num = "";
	public String date;
	public String weight;
	public String glucose;
	public String ketones;
	
	public RowData(String day_num, String date, String weight, String glucose, String ketones) {
		this.day_num = day_num;
		this.date = date;
		this.weight = weight;
		this.glucose = glucose;
		this.ketones = ketones;
	}
	

}
