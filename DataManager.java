package application;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {
	private final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	public DataManager() {
		
	}
	
	/**
	 * This method return a list of observable list with enough information for a farm
	 * stored in the Data model. This method do take a year as an argument 
	 * @param fac
	 * @param id
	 * @param y
	 * @return an observable list of Data
	 */
	public ObservableList<Data> getFarmReport(CheeseFactory fac, String id, int y) {
		ObservableList<Data> fr = FXCollections.observableArrayList();
		
		if (!fac.data.containsKey(id)) {
			throw new InputMismatchException("There is no farm with the given ID");
		}
		Farm farm = fac.data.get(id);
		if (!farm.year.containsKey(y)) {
			throw new InputMismatchException("There is no data for the farm with the given year");
		}
		
		Set<Integer> month = farm.year.get(y).keySet();
		for (Integer i: month) {
			Collection<Double> w = farm.year.get(y).get(i).values();
			double sum = 0;
			for (double j : w) {
				sum += j;
			}
			// Take the just 2 decimals number for the percentage
			double percent = ((double)((int)(sum/getTotalMonth(fac, y, i)*10000)))/100;
			
			Data temp = new Data();
			temp.farm.set(id);
			temp.date.set(MONTH[i-1]+"-"+y);
			temp.totalMonth.set(sum);
			temp.percentTotalMonth.set(percent);
			// Take the just 2 decimals number for the percentage
			temp.average.set(((double)((int)(sum/30*100)))/100);
			fr.add(temp);
		}
		if (fr.isEmpty())
			throw new NullPointerException("No data for the given input");
		return fr;
	}
	
	/**
	 * This method return a list of observable list with enough information for a farm report
	 * stored in the Data model, this method does not take a year argument, it return
	 * data for the whole dataset
	 * @param fac
	 * @param id
	 * @return an observable list of Data
	 */
	public ObservableList<Data> getFarmReport(CheeseFactory fac, String id) {
		ObservableList<Data> avg = FXCollections.observableArrayList();
		
		if (!fac.data.containsKey(id)) {
			throw new InputMismatchException("There is no farm with the given ID");
		}
		Set<Integer> year_ = fac.data.get(id).year.keySet();
		for (Integer x : year_) {
			Set<Integer> month = fac.data.get(id).year.get(x).keySet();
			for (Integer i: month) {
				Collection<Double> w = fac.data.get(id).year.get(x).get(i).values();
				double sum = 0;
				for (double j : w) {
					sum += j;
				}
				// Take the just 2 decimals number for the percentage
				double percent = ((double)((int)(sum/getTotalMonth(fac, x, i)*10000)))/100;
			
				Data temp = new Data();
				temp.farm.set(id);
				temp.date.set(i+"-"+x);
				temp.totalMonth.set(sum);
				temp.percentTotalMonth.set(percent);
				// Take the just 2 decimals number for the percentage
				temp.average.set(((double)((int)(sum/30*100)))/100);
				avg.add(temp);
			}
		}
		return avg;
	}
	
	/**
	 * This class return a list of observable list with enough information for an annual report
	 * stored in the Data model
	 * @param fm
	 * @param year
	 * @return a list of Data model with enough information
	 */
	public ObservableList<Data> getAnnualReport(CheeseFactory fm, int year) {
		ObservableList<Data> ar = FXCollections.observableArrayList();
		Set<String> farm = fm.data.keySet();
		double de = getTotalYear(fm, year);
		for (String f : farm) {
			
			Data temp = new Data();
			temp.farm.set(f);
			double total = 0;
			
			try {
				Set<Integer> y = fm.data.get(f).year.get(year).keySet();
				
				for (Integer i: y) {
					Collection<Double> w = fm.data.get(f).year.get(year).get(i).values();
					for (Double j : w) {
						total += j;
					}
				}
			
				temp.date.set(""+year);
				temp.total_year.set(total);
				// Take the just 2 decimals number for the percentage
				temp.percentTotal_year.set(((double)((int)(total/de*10000)))/100);
				temp.average.set(((double)((int)(total/12*100)))/100);
				ar.add(temp);
			}
			catch (NullPointerException e) {
				continue;
			}
		}
		if (ar.isEmpty())
			throw new NullPointerException("No data for the given input");
		return ar;
	}
	
	/**
	 * This class return a list of observable list with enough information for a monthly report
	 * stored in the Data model
	 * @param fm
	 * @param year
	 * @param month
	 * @return monthly report in the Data model
	 */
	public ObservableList<Data> getMonthlyReport(CheeseFactory fm, int year, int month) {
		ObservableList<Data> mr = FXCollections.observableArrayList();
		Set<String> farm = fm.data.keySet();
		double de = getTotalMonth(fm, year, month);
		for (String f : farm) {
			try {
				Collection<Double> w = fm.data.get(f).year.get(year).get(month).values();
				
				Data temp = new Data();
				temp.date.set(year+"-"+month);
				temp.farm.set(f);
				
				double total = 0;
				for (Double j : w) {
					total += j;
				}
				
				temp.totalMonth.set(total);
				// Take the just 2 decimals number for the percentage
				temp.percentTotalMonth.set(((double)((int)(total/de*10000)))/100);
				temp.average.set(((double)((int)(total/30*100)))/100);
				mr.add(temp);
			}
			catch (NullPointerException e) {
				continue;
			}
		}
		if (mr.isEmpty())
			throw new NullPointerException("No data for the given input");
		return mr;
	}
	
	/**
	 * Return the data in Data model for the given date range
	 * @param fac
	 * @param from
	 * @param to
	 * @return dataset in the given date range in the Data model
	 */
	public ObservableList<Data> getDateRangeData(CheeseFactory fac, String from, String to) {
		ObservableList<Data> dr = FXCollections.observableArrayList();
		int[] fromDate = new int[3];
		int[] toDate = new int[3];
		try {
			String[] temp = from.split("-");
			for (int i = 0; i < fromDate.length; i++) {
				fromDate[i] = Integer.parseInt(temp[i]);
			}
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			throw new InputMismatchException("Invalid date (from), date must be numbers seperated by \"-\" character");
		}
		
		try {
			String[] temp = to.split("-");
			for (int i = 0; i < toDate.length; i++) {
				toDate[i] = Integer.parseInt(temp[i]);
			}
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			throw new InputMismatchException("Invalid date (to), date must be numbers seperated by \"-\" character");
		}
		Set<String> farm = fac.data.keySet();
		for (String i : farm) {
			Set<Integer> year_ = fac.data.get(i).year.keySet();
			for (Integer j : year_) {
				if (j < fromDate[0] || j > toDate[0])
					continue;
				
				Set<Integer> month = fac.data.get(i).year.get(j).keySet();
				
				for (Integer k : month) {
					// Skip the loop if the date is outside of the date range in terms of month and year
					if (j==fromDate[0] && k<fromDate[1])
						continue;
					if (j==toDate[0] && k>toDate[1])
						continue;
					
					Set<Integer> day = fac.data.get(i).year.get(j).keySet();
					
					for (Integer l : day) {
						
						Set<Integer> d = fac.data.get(i).year.get(j).get(l).keySet();
						
						for (Integer m : d) {
							// skip the loop if the date is outside of the loop in terms of full date
							if ((j==fromDate[0] && k==fromDate[1] && m<fromDate[2])
									|| (j==toDate[0] && k==toDate[1] && m>toDate[2]))
								continue;
							
							Data temp = new Data();
							temp.farm.set(i);
							temp.date.set(j+"-"+k+"-"+m);
							temp.weight.set(""+fac.data.get(i).year.get(j).get(l).get(m));
							dr.add(temp);
						}
					}
				}
			}
		}
		
		if (dr.isEmpty())
			throw new NullPointerException("No data for the given input");
		return dr;
	}
	
	/**
	 * Get the date range report in the Data model
	 * @param fac
	 * @param from
	 * @param to
	 * @return date range report in the Data model
	 */
	public ObservableList<Data> getDateRangeReport(CheeseFactory fac, String from, String to) {
		ObservableList<Data> dr = FXCollections.observableArrayList();
		int[] fromDate = new int[3];
		int[] toDate = new int[3];
		
		try {
			String[] temp = from.split("-");
			for (int i = 0; i < fromDate.length; i++) {
				fromDate[i] = Integer.parseInt(temp[i]);
			}
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			throw new InputMismatchException("Invalid date (from)");
		}
		
		try {
			String[] temp = to.split("-");
			for (int i = 0; i < toDate.length; i++) {
				toDate[i] = Integer.parseInt(temp[i]);
			}
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			throw new InputMismatchException("Invalid date (to)");
		}
		
		Set<String> farm = fac.data.keySet();
		
		for (String i : farm) {
			Data temp = new Data();
			temp.farm.set(i);
			temp.date.set(from+" to "+to);
			double total = 0;
			
			Set<Integer> year_ = fac.data.get(i).year.keySet();
			
			for (Integer j : year_) {
				// Skip the loop if the date is out of range in terms of year
				if (j < fromDate[0] || j > toDate[0])
					continue;
				
				Set<Integer> month = fac.data.get(i).year.get(j).keySet();
				
				for (Integer k : month) {
					// Skip the loop if the date is out of range
					if (j==fromDate[0] && k<fromDate[1])
						continue;
					if (j==toDate[0] && k>toDate[1])
						continue;
					
					Set<Integer> day = fac.data.get(i).year.get(j).keySet();
					
					for (Integer l : day) {
						Set<Integer> d = fac.data.get(i).year.get(j).get(l).keySet();
						
						for (Integer m : d) {
							// Skip the loop if the date is out of range
							if ((j==fromDate[0] && k==fromDate[1] && m<fromDate[2])
									|| (j==toDate[0] && k==toDate[1] && m>toDate[2]))
								continue;
							
							total+=fac.data.get(i).year.get(j).get(l).get(m);
						}
					}
				}
			}
			
			temp.weightDateRange.set(total);
			dr.add(temp);
		}
		
		double sum = 0;
		for (Data i : dr) {
			sum += i.getWeightDateRange();
		}
		
		for (Data i: dr) {
			i.weightDateRangePercent.set(((double)((int)(i.getWeightDateRange()/sum*10000)))/100);
		}
		
		if (dr.isEmpty())
			throw new NullPointerException("No data for the given input");
		return dr;
	}
	
	/**
	 * Helper method to get the total weight of the whole year across all farm
	 * @param fac
	 * @param year
	 * @return the total weight for the given year across all farm
	 */
	private double getTotalYear(CheeseFactory fac, int year) {
		double sum = 0;
		Set<String> farm = fac.data.keySet();
		for (String i: farm) {
			try {
				Set<Integer> m = fac.data.get(i).year.get(year).keySet();
				for (Integer j : m) {
					Collection<Double> d = fac.data.get(i).year.get(year).get(j).values();
					for (Double w : d) {
						sum += w;
					}
				}
			} catch(NullPointerException e) {
				continue;
			}
		}
		return sum;
	}
	
	/**
	 * Helper method to get the total of the whole month across all farm
	 * @param fac
	 * @param year
	 * @param month
	 * @return the total of the whole month across all farm
	 */
	private double getTotalMonth(CheeseFactory fac, int year, int month) {
		double sum = 0;
		Set<String> farm = fac.data.keySet();
		for (String i: farm) {
			try {
				Collection<Double> m = fac.data.get(i).year.get(year).get(month).values();
				for (Double j : m) {
					sum += j;
				}
			}
			catch (NullPointerException e) {
				continue;
			}
		}
		return sum;
	}

}
