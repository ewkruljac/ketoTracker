package ketoTracker;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		new view.KetoStatsGui();
		
		
		db.DbDriver dbDriver = new db.DbDriver();
		
		/*
		dbDriver.insert("2021-01-01", 200.1f, 94.3f, 4.2f, "stats");
		dbDriver.update("stats", 21, "weight", 180f);
		dbDriver.destroy("stats", 25);*/
		
		List<db.RowData> results = dbDriver.retrieveAll();
		for (db.RowData param : results) {
			System.out.print(param.day_num + ", ");
			System.out.print(param.weight + ", ");
			System.out.print(param.date + ", ");
			System.out.print(param.glucose + ", ");
			System.out.print(param.ketones);
			System.out.println(" ");
		}
		
	}

}
