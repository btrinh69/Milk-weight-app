package application;
import java.util.Hashtable;
import java.util.InputMismatchException;

public class Farm {
	String id;
	String owner;
	Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Double>>> year;
	
	public Farm(String id, String owner) {
		this.id = id;
		this.owner = owner;
		year = new Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Double>>>();
	}
	
	/**
	 * Insert milk for the given date in the farm
	 * @param date
	 * @param weight
	 * @return
	 * @throws NumberFormatException
	 * @throws IndexOutOfBoundsException
	 */
	public boolean insertMilkForDate(String date, double weight) 
			throws NumberFormatException, IndexOutOfBoundsException {
		String[] m_d_y = date.trim().split("-");
		int y = Integer.parseInt(m_d_y[0]);
		if (y < 1 || y > 2020)
			throw new InputMismatchException("Invalid year, year must be > 0 and < the current year: 2020");
		int m = Integer.parseInt(m_d_y[1]);
		if ((m < 1) || (m >12))
			throw new InputMismatchException("Invalid month, month must be > 0 and < 12");
		int d = Integer.parseInt(m_d_y[2]);
		if ((d<1) || (d>31))
			throw new InputMismatchException("Invalid day, day must be > 0 and < 31");
		if (weight < 0)
			throw new InputMismatchException("Error on the date " + y + "-" + m + "-" + d + ": Weight cannot be less than 0");
		year.putIfAbsent(y, new Hashtable<Integer, Hashtable<Integer, Double>>());
		year.get(y).putIfAbsent(m, new Hashtable<Integer, Double>());
		if (year.get(y).get(m).putIfAbsent(d, weight) != null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change the milk weight for the given date
	 * @param date
	 * @param weight
	 * @return
	 * @throws NullPointerException
	 * @throws NumberFormatException
	 * @throws IndexOutOfBoundsException
	 */
	public boolean editMilkForDate(String date, double weight) 
			throws NullPointerException, NumberFormatException, IndexOutOfBoundsException {
		String[] m_d_y = date.trim().split("-");
		int y = Integer.parseInt(m_d_y[0]);
		int m = Integer.parseInt(m_d_y[1]);
		int d = Integer.parseInt(m_d_y[2]);
		if (!year.containsKey(y)) {
			throw new NullPointerException("There is no data for the given year");
		}
		if (!year.get(y).containsKey(m)) {
			throw new NullPointerException("There is no data for the given month");
		}
		if (weight < 0)
			throw new InputMismatchException("Error: Weight cannot be less than 0");
		if (!year.get(y).get(m).containsKey(d)) {
			return false;
		}
		year.get(y).get(m).replace(d, weight);
		return true;
	}
	
	/**
	 * Remove the data for the given date
	 * @param date
	 * @return
	 * @throws NumberFormatException
	 * @throws IndexOutOfBoundsException
	 */
	public boolean removeMilkForDate(String date)
			throws NumberFormatException, IndexOutOfBoundsException {
		String[] m_d_y = date.trim().split("-");
		int y = Integer.parseInt(m_d_y[0]);
		int m = Integer.parseInt(m_d_y[1]);
		int d = Integer.parseInt(m_d_y[2]);
		if (!year.containsKey(y)) {
			return false;
		}
		if (!year.get(y).containsKey(m)) {
			return false;
		}
		if (!year.get(y).get(m).containsKey(d)) {
			return false;
		}
		year.get(y).get(m).remove(d);
		return true;
	}
	
	public void clear() {
		year = new Hashtable<Integer, Hashtable<Integer, Hashtable<Integer, Double>>>();
	}
}
