package application;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Set;

public class CheeseFactory {
	String name;
	Hashtable<String, Farm> data;
	
	public CheeseFactory(String name) {
		this.name = name;
		data = new Hashtable<String, Farm>();
	}
	
	/**
	 * Insert single data with the given input
	 * @param date
	 * @param id
	 * @param weight
	 * @return true if insert successfully, false otherwise
	 */
	public boolean insertSingleData(String date, String id, String weight) {
		if (id == null) {
			throw new InputMismatchException("Missing Farm ID");
		}
		double temp = Double.parseDouble(weight);
		data.putIfAbsent(id, new Farm(id, name));
		try {
			if (!data.get(id).insertMilkForDate(date, temp)) {
				return false;
			}
		}
		catch (NumberFormatException e) {
			throw new InputMismatchException("Date must be numbers separated by \"-\" characters");
		}
		return true;
	}
	
	/**
	 * Edit the single data with the given id, date, and weight
	 * @param id
	 * @param date
	 * @param weight
	 * @return true if edit successfully, false otherwise
	 */
	public boolean editSingleData(String id, String date, String weight) {
		if (!data.containsKey(id)) {
			throw new NullPointerException("Error: No farm with the given ID");
		}
		double temp = 0;
		temp = Double.parseDouble(weight);
		if (!data.get(id).editMilkForDate(date, temp)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Remove a single data with the given id and date
	 * @param id
	 * @param date
	 * @return true if remove successfully, false otherwise
	 */
	public boolean removeSingleData(String id, String date) {
		if (!data.containsKey(id)) {
			throw new NullPointerException("Error: There is no farm with the given ID");
		}
		return data.get(id).removeMilkForDate(date);
	}
	
	/**
	 * insert data from a file to the factory
	 * @param input - the input file path
	 * @return true if read the whole file without any bad data, false otherwise
	 */
	public boolean insertData(String input) {
		FileManager read = new FileManager();
		if (!read.readFile(input)) {
			throw new InputMismatchException("File contains error(s), data has been partially added");
		}
		boolean check = true;
		for (String[] i : read.data) {
			if (i.length != 3) {
				continue;
			}
			if (!insertSingleData(i[0], i[1], i[2])) {
				check = false;
			}
		}
		return check;
	}
	
	public Set<String> getFarmID() {
		return data.keySet();
	}
}
