package ketoTracker;


public class Main {

	public static void main(String[] args) {
		db.DbDriver dbDriver = new db.DbDriver();
		
		
		dbDriver.insert("2021-01-01", 200.1f, 94.3f, 4.2f, "stats");
		dbDriver.update("stats", "3", "weight", "199");
		dbDriver.destroy("stats", "9");
		
		String[][] results = dbDriver.retrieveAll();
		for (int row=0; row<results.length; row++) {
			for (int col=0; col<results[row].length; col++) {
				System.out.print(results[row][col] + ", ");
			}
			System.out.println(" ");
		}
		
	}

}
