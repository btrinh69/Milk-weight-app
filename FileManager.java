package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javafx.collections.ObservableList;

public class FileManager {
	String output = "output.csv";
	List<String[]> data;
	
	public FileManager() {
		data = new ArrayList<String[]>();
	}
	
	/**
	 * Read a csv file from the given path, storing data in the class data field
	 * @param input - the file path
	 * @return true if read successfully, false otherwise
	 */
	public boolean readFile(String input) {
		File csvFile = new File(input);
		if (!csvFile.isFile()) {
			throw new InputMismatchException("Error: Wrong type, this is not a csv file/File does not exist");
		}
		String row = "";
		BufferedReader csvReader = null;
		try {
			csvReader = new BufferedReader(new FileReader(input));
			row = csvReader.readLine();
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				for (int i = 0; i < data.length; i++)
					data[i] = data[i].trim();
				this.data.add(data);
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		finally {
			if (csvReader != null) {
				try {
					csvReader.close();
				} catch (IOException e) {}
			}
		}
		return true;
	}
	
	/**
	 * Take an ObservableList of data and write it to a file
	 * @param data - the observable list of data
	 * @return true if write successfully, false otherwise
	 * @throws IOException
	 */
	public boolean writeToFile(ObservableList<Data> data) throws IOException {
		FileWriter w = new FileWriter(new File(output));
		if (data.get(0).weightDateRange.get() != 0) {
			String temp = "date,farm,total,sharet\n";
			for (Data i : data) {
				temp = temp+i.getDate()+","+i.getFarm()+","+i.getWeightDateRange()+","+i.getWeightDateRangePercent()+"\n";
			}
			w.append(temp);
		}
		else if (data.get(0).total_year.get() == 0) {
			String temp = "date,farm,monthly total,share\n";
			for (Data i : data) {
				temp = temp+i.getDate()+","+i.getFarm()+","+i.getTotalMonth()+","+i.getPercentTotalMonth()+"\n";
			}
			w.append(temp);
		}
		else {
			String temp = "date,farm,yearly total,share\n";
			for (Data i : data) {
				temp = temp+i.getDate()+","+i.getFarm()+","+i.getTotal_year()+","+i.getPercentTotal_year()+"\n";
			}
			w.append(temp);
		}
		w.close();
		return true;
	}
	
	public String getFileContent() {
		String content = "";
		for (String[] i : data) {
			for (String j : i) {
				content += j + ", ";
			}
			content = content.substring(0, content.length() - 2);
			content += "\n";
		}
		return content;
	}
}
