package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is the data model for the ObservableList, which is used to display data
 * in the GUI
 *
 */
public class Data {
	SimpleStringProperty date;
	SimpleStringProperty farm;
	SimpleDoubleProperty total_year;
	SimpleDoubleProperty percentTotal_year;
	SimpleDoubleProperty totalMonth;
	SimpleDoubleProperty percentTotalMonth;
	SimpleStringProperty weight;
	SimpleDoubleProperty weightDateRange;
	SimpleDoubleProperty weightDateRangePercent;
	SimpleDoubleProperty average;
	
	public Data() {
		date = new SimpleStringProperty();
		farm = new SimpleStringProperty();
		total_year = new SimpleDoubleProperty();
		percentTotal_year = new SimpleDoubleProperty();
		totalMonth = new SimpleDoubleProperty();
		percentTotalMonth = new SimpleDoubleProperty();
		weight = new SimpleStringProperty();
		weightDateRange = new SimpleDoubleProperty();
		weightDateRangePercent = new SimpleDoubleProperty();
		average = new SimpleDoubleProperty();
	}
	
	public String getWeight() {
		return weight.get();
	}
	
	public Double getWeightDateRange() {
		return weightDateRange.get();
	}
	
	public Double getWeightDateRangePercent() {
		return weightDateRangePercent.get();
	}
	
	public Double getAverage() {
		return average.get();
	}
	
	public String getDate() {
		return date.get();
	}
	
	public Double getTotalMonth() {
		return totalMonth.get();
	}
	
	public Double getPercentTotalMonth() {
		return percentTotalMonth.get();
	}
	
	public String getFarm() {
		return farm.get();
	}
	
	public Double getTotal_year() {
		return total_year.get();
	}
	
	public Double getPercentTotal_year() {
		return percentTotal_year.get();
	}
}
