/**
 * 
 */
package application;

import java.io.IOException;
import java.util.InputMismatchException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Bon
 *
 */
public class Main extends Application {

	private static final int WINDOW_WIDTH = 600;
	private static final int WINDOW_HEIGHT = 400;
	private static final String APP_TITLE = "Milk Weight App";
	private CheeseFactory fac;
	private FileManager fm;
	private DataManager dm;
	
	public void init() {
		fac = new CheeseFactory("My fac");
		fm = new FileManager();
		dm = new DataManager();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		init();
		
		// MENU SCENE
		
		// Layout
		BorderPane root = new BorderPane();
		BorderPane add_layout = new BorderPane();
		BorderPane adjust_layout = new BorderPane();
		BorderPane report_layout = new BorderPane();
		BorderPane farm_layout = new BorderPane();
		BorderPane annual_layout = new BorderPane();
		BorderPane monthly_layout = new BorderPane();
		BorderPane dr_layout = new BorderPane();
		
		// Basic button to switch scene to other functions
		Button add = new Button("Add data");
		Button adjust = new Button("Adjust data");
		Button report = new Button("Report");
		
		VBox btn = new VBox();
		btn.getChildren().addAll(add, adjust, report);
		btn.setSpacing(10);
		
		root.setTop(new Label("Main Menu"));
		root.setCenter(btn);
		
		// Layout style
		root.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		add_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		adjust_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		report_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		farm_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		annual_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		monthly_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		dr_layout.setStyle("-fx-padding: 10;" +
        		"-fx-border-width: 0;" +
        		"-fx-border-insets: 5;");
		
		// All scenes
		Scene mainMenu = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene add_scene = new Scene(add_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene adjust_scene = new Scene(adjust_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene report_scene = new Scene(report_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene farm = new Scene(farm_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene annual = new Scene(annual_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene monthly = new Scene(monthly_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		Scene dr = new Scene(dr_layout, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Menu navigations
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(add_scene);
			}
		});
		
		adjust.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(adjust_scene);
			}
		});
		
		report.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(report_scene);
			}
		});
		
		
		// ADD SCENE
		GridPane data_add = new GridPane();
		
		// Elements to add data by taking input csv files
		TextField path = new TextField();
		Label upload_label = new Label("Path: ");
		upload_label.setLabelFor(path);
		
		Button upload = new Button("_Upload");
		upload.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				data_add.getChildren().clear();
				String input = path.getText();
				try {
					fac.insertData(input);
					data_add.addRow(1, new Label("Successfully added data from " + input));
				}
				catch (InputMismatchException er) {
					data_add.addRow(1, new Label(er.getMessage()));
				}
				catch(Exception er) {
					data_add.addRow(2, new Label("File contains error(s), unable to completely add data"));
				}
			}
		});
		
		// Group all elements for taking input files
		HBox up = new HBox();
		up.getChildren().addAll(upload_label, path, upload);
		up.setSpacing(5);
		Label up_label = new Label("Upload a file for month report");
		up_label.setLabelFor(up);
		
		
		// Elements for adding new data manually
		VBox manual = new VBox();
		manual.setSpacing(5);
		Label manual_label = new Label("Add manually");
		TextField date_add = new TextField();
		date_add.setPromptText("yy-mm-dd");
		Label date_label_add = new Label("Date");
		date_label_add.setLabelFor(date_add);
		
		TextField farmID_add = new TextField();
		Label farmID_label_add = new Label("Farm ID");
		farmID_label_add.setLabelFor(farmID_add);
		
		TextField weight_add = new TextField();
		Label weight_label_add = new Label("Weight");
		weight_label_add.setLabelFor(weight_add);
		Label weight_unit_add = new Label("Kg");
		weight_unit_add.setLabelFor(weight_add);
		
		// Final group of all data manual adding
		HBox manual_data = new HBox();
		manual_data.setSpacing(2);
		manual_data.getChildren().addAll(date_label_add, date_add,
				farmID_label_add, farmID_add, weight_label_add, weight_add, weight_unit_add);
		
		manual.getChildren().addAll(manual_label, manual_data);
		
		// Group all elements to take user input (file path or data input) to add data
		VBox add_data = new VBox();
		add_data.getChildren().addAll(up_label, up, manual, data_add);
		add_data.setSpacing(10);
		
		
		// Functional buttons
		Button add_add = new Button("Add");
		add_add.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				data_add.getChildren().clear();
				String date = date_add.getText();
				String id = farmID_add.getText();
				String w = weight_add.getText();
				try {
					if (fac.insertSingleData(date, id, w)) {
						data_add.addRow(0, new Label("Successfully added data for "+date+", farm ID: "+id+", weight: "+w));
					}
					else {
						data_add.addRow(0, new Label("Data for "+date+", farm ID: "+id+" already exist"));
					}
				}
				catch (IndexOutOfBoundsException er) {
					data_add.addRow(0, new Label("Missing value month, day, or year for the date"));
				}
				catch (InputMismatchException er) {
					data_add.addRow(0, new Label(er.getMessage()));
				}
				catch (NumberFormatException er) {
					data_add.addRow(0, new Label("Weight must be a number"));
				}
				catch (Exception er) {
					data_add.addRow(0, new Label("Error occured, unable to add data with the given input"));
				}
			}
		});
		
		Button back_add = new Button("_Back");
		back_add.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(mainMenu);
			}
		});
		
		// Group all buttons
		HBox bot_add = new HBox();
		bot_add.getChildren().addAll(back_add, add_add);
		bot_add.setSpacing(100);
		
		add_layout.setTop(new Label("ADDING DATA"));
		add_layout.setCenter(add_data);
		add_layout.setBottom(bot_add);
		
		
		// ADJUST SCENE
		
		// Center
		// Elements to take user input for date range to retrieve data
		TextField date_adjust = new TextField();
		date_adjust.setPromptText("yy-mm-dd");
		Label date_label_adjust = new Label("Date (From)");
		date_label_adjust.setLabelFor(date_adjust);
		
		TextField date_to_adjust = new TextField();
		date_to_adjust.setPromptText("yy-mm-dd (optional)");
		Label date_to_label_adjust = new Label("Date (To)");
		date_to_label_adjust.setLabelFor(date_to_adjust);
		
		GridPane adjust_pane_control = new GridPane();
		adjust_pane_control.addRow(0, date_label_adjust, date_to_label_adjust);
		adjust_pane_control.addRow(1, date_adjust, date_to_adjust);
		adjust_pane_control.setVgap(5);
		adjust_pane_control.setHgap(10);
		
		// Instruction for users
		Label adjust_instruction = new Label("Adjust the data directly or remove an entry by selecting an element in the row and click remove");
		Label noti_adjust = new Label();
		
		// Data visualization
		TableView<Data> data_adjust = new TableView<Data>();
		data_adjust.setEditable(true);
		
		TableColumn<Data, String> date_col_adjust = new TableColumn<Data, String>("Date");
		date_col_adjust.setMinWidth(150);
		date_col_adjust.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
		
		TableColumn<Data, String> farm_col_adjust = new TableColumn<Data, String>("Farm ID");
		farm_col_adjust.setMinWidth(150);
		farm_col_adjust.setCellValueFactory(new PropertyValueFactory<Data, String>("farm"));
		
		// Allow user to modify the weights
		TableColumn<Data, String> w_col_adjust = new TableColumn<Data, String>("Weight");
		w_col_adjust.setMinWidth(150);
		w_col_adjust.setCellValueFactory(new PropertyValueFactory<Data, String>("weight"));
		w_col_adjust.setCellFactory(TextFieldTableCell.forTableColumn());
		w_col_adjust.setOnEditCommit(
	            new EventHandler<CellEditEvent<Data, String>>() {
	                @Override
	                public void handle(CellEditEvent<Data, String> t) {
	                	Data temp = ((Data) t.getTableView().getItems().get(t.getTablePosition().getRow()));
	                	try {
	                		// If the the method return true, data has been changed, change the data in the table as well
	                		if (fac.editSingleData(temp.getFarm(), temp.getDate(), t.getNewValue())) {
	                			// Simply change the data in the observable object
	                			((Data) t.getTableView().getItems().get(t.getTablePosition().getRow())).weight.set(t.getNewValue());
	                			}
	                		else {
	                			noti_adjust.setText("Invalid change");
	                		}
	                	}
	                	catch (NumberFormatException er) {
	                		noti_adjust.setText("Weight must be a number > than 0");
	                	}
	                	catch (NullPointerException | InputMismatchException er) {
	                		noti_adjust.setText(er.getMessage() +" Error");
	                	}
	                	catch (Exception er) {
	                		noti_adjust.setText("Invalid change");
	                	}
	                }
	            }
	        );
		
		data_adjust.getColumns().addAll(date_col_adjust, farm_col_adjust, w_col_adjust);
		
		// Group all elements in the center
		VBox adjust_center = new VBox();
		adjust_center.getChildren().addAll(adjust_pane_control, adjust_instruction, noti_adjust, data_adjust);
		adjust_center.setSpacing(10);
		
		
		
		// Bottom Buttons
		// Allow user to remove data by clicking Remove button
		Button adjust_remove = new Button("_Remove");
		adjust_remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Data selected = data_adjust.getSelectionModel().getSelectedItem();
				try {
					fac.removeSingleData(selected.getFarm(), selected.getDate());
					noti_adjust.setText("");
					String fromDate = date_adjust.getText();
					String toDate = date_to_adjust.getText();
					try {
						data_adjust.setItems(dm.getDateRangeData(fac, fromDate, toDate));
					}
					catch (InputMismatchException er) {
						noti_adjust.setText(er.getMessage());
					}
					catch (NullPointerException er) {
						noti_adjust.setText(er.getMessage());
					}
				}
				catch (Exception er) {
					noti_adjust.setText("Error occured, unable to remove");
				}
			}
		});
		
		// Allow user to view data in the chosen date range by clicking View button
		Button view_adjust = new Button("_View");
		view_adjust.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				noti_adjust.setText("");
				String fromDate = date_adjust.getText();
				String toDate = date_to_adjust.getText();
				try {
					data_adjust.setItems(dm.getDateRangeData(fac, fromDate, toDate));
				}
				catch (InputMismatchException er) {
					noti_adjust.setText(er.getMessage());
				}
				catch (NullPointerException er) {
					noti_adjust.setText(er.getMessage());
				}
				catch (Exception er) {
					noti_adjust.setText("Error occured, unable to display data for the given input");
				}
			}
		});
		
		Button back_adjust = new Button("_Back");
		back_adjust.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				noti_adjust.setText("");
				primaryStage.setScene(mainMenu);
			}
		});
		
		// Group all buttons inthe bottom
		HBox adjust_bot = new HBox();
		adjust_bot.getChildren().addAll(back_adjust, view_adjust, adjust_remove);
		adjust_bot.setSpacing(200);
		
		adjust_layout.setTop(new Label("ADJUSTING DATA"));
		adjust_layout.setCenter(adjust_center);
		adjust_layout.setBottom(adjust_bot);
		
		
		// REPORT SCENE
		// Navigate to different types of report through buttons
		Button farm_report = new Button("Farm report");
		farm_report.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(farm);
			}
		});
		Button annual_report = new Button("Annual report");
		annual_report.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(annual);
			}
		});
		Button monthly_report = new Button("Monthly report");
		monthly_report.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(monthly);
			}
		});
		Button dr_report = new Button("Date range report");
		dr_report.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(dr);
			}
		});
		
		VBox report_type = new VBox();
		report_type.getChildren().addAll(farm_report, annual_report, monthly_report, dr_report);
		report_type.setSpacing(10);
		
		Button back_report = new Button("_Back");
		back_report.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				primaryStage.setScene(mainMenu);
			}
		});
		
		report_layout.setTop(new Label("REPORT"));
		report_layout.setCenter(report_type);
		report_layout.setBottom(back_report);
		
		
			// FARM REPORT SCENE
			// Let user pick the farm ID either by typing or choosing in the ComboBox
				// uncompleted: has not been able to display the list of farm ID in the ComboBox
			ComboBox<String> farmID_farm = new ComboBox<String>();
			farmID_farm.setEditable(true);
			ObservableList<String> idElement_farm = FXCollections.observableArrayList(fac.getFarmID());
			farmID_farm.getItems().addAll(idElement_farm);
			
			Label farmID_farm_label = new Label("Farm ID");
			farmID_farm_label.setLabelFor(farmID_farm);
			
			VBox farmID_farm_box = new VBox();
			farmID_farm_box.getChildren().addAll(farmID_farm_label, farmID_farm);
			farmID_farm_box.setSpacing(5);
			
			// Let user pick a year as well
			TextField year_farm = new TextField();
			Label year_farm_label = new Label("Year");
			year_farm_label.setLabelFor(year_farm);
			
			// Instruction
			Label year_farm_instruction = new Label("\"All\" for all dataset");
			year_farm_instruction.setLabelFor(year_farm);
			
			// Group all year related elements
			VBox year_farm_box = new VBox();
			year_farm_box.getChildren().addAll(year_farm_label, year_farm, year_farm_instruction);
			year_farm_box.setSpacing(5);
			
			// Group all filter for data
			HBox filter_farm = new HBox();
			filter_farm.getChildren().addAll(farmID_farm_box, year_farm_box);
			filter_farm.setSpacing(50);
			
			Label noti_farm = new Label();
			
			// Table for data visualization
			TableView<Data> data_farm = new TableView<Data>();
			
			TableColumn<Data, String> month_farm = new TableColumn<Data, String>("Month");
			month_farm.setMinWidth(130);
			TableColumn<Data, Double> total_month_farm = new TableColumn<Data, Double>("Monthly Total");
			total_month_farm.setMinWidth(130);
			TableColumn<Data, Double> avg_month_farm = new TableColumn<Data, Double>("Monthly Average");
			avg_month_farm.setMinWidth(130);
			TableColumn<Data, Double> percent_month_farm = new TableColumn<Data, Double>("Share");
			percent_month_farm.setMinWidth(130);
			
			data_farm.getColumns().addAll(month_farm, total_month_farm, avg_month_farm, percent_month_farm);

			month_farm.setCellValueFactory(new PropertyValueFactory<Data, String>("date"));
			total_month_farm.setCellValueFactory(new PropertyValueFactory<Data, Double>("totalMonth"));
			avg_month_farm.setCellValueFactory(new PropertyValueFactory<Data, Double>("average"));
			percent_month_farm.setCellValueFactory(new PropertyValueFactory<Data, Double>("percentTotalMonth"));
			
			// Group the notification and the data visualization in the center with the filter
			VBox mid_farm = new VBox();
			mid_farm.getChildren().addAll(noti_farm, data_farm);
			
			VBox center_farm = new VBox();
			center_farm.getChildren().addAll(filter_farm, mid_farm);
			center_farm.setSpacing(10);
			
			// Functional buttons
			Button back_farm = new Button("_Back");
			back_farm.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					primaryStage.setScene(report_scene);
				}
			});
			
			// Display the data after clicking the View Button
			Button view_farm = new Button("_View report");
			view_farm.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_farm.setText("");
					String id = farmID_farm.getValue();
					String year = year_farm.getText();
					if (year.equalsIgnoreCase("all")) {
						try {
						data_farm.setItems(dm.getFarmReport(fac, id));
						}
						catch (InputMismatchException er) {
							noti_farm.setText(er.getMessage());
						}
						catch (NullPointerException er) {
							noti_farm.setText(er.getMessage());
						}
						catch (Exception er) {
							noti_farm.setText("Error occured, unable to view report with the given output");
						}
					}
					else {
						try {
							int y = Integer.parseInt(year);
							data_farm.setItems(dm.getFarmReport(fac, id, y));
						}
						catch (NumberFormatException er) {
							noti_farm.setText("Invalid year");
						}
						catch (Exception er) {
							noti_farm.setText(er.getMessage());
						}
					}
				}
			});
			
			// Write the file to an output file after this button is pressed
			Button export_farm = new Button("Export to file");
			export_farm.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_farm.setText("");
					String id = farmID_farm.getValue();
					String year = year_farm.getText();
					if (year.equalsIgnoreCase("all")) {
						try {
							fm.writeToFile(dm.getFarmReport(fac, id));
						} catch (IOException e1) {
							noti_farm.setText("Error occured, unable to write to file");
						}
					}
					else {
						try {
							int y = Integer.parseInt(year);
							fm.writeToFile(dm.getFarmReport(fac, id, y));
						}
						catch (NumberFormatException er) {
							noti_farm.setText("Invalid year");
						}
						catch (Exception er) {
							noti_farm.setText(er.getMessage());
						}
					}
				}
			});
			
			// Group all buttons in the bottom
			HBox bot_farm = new HBox();
			bot_farm.getChildren().addAll(back_farm, view_farm, export_farm);
			bot_farm.setSpacing(100);
			
			farm_layout.setTop(new Label("FARM REPORT"));
			farm_layout.setCenter(center_farm);
			farm_layout.setBottom(bot_farm);
		
			// ANNUAL REPORT SCENE
			// Taking user input for a year
			TextField year_annual = new TextField();
			Label year_annual_label = new Label("Year");
			year_annual_label.setLabelFor(year_annual);
			VBox year_annual_box = new VBox();
			year_annual_box.getChildren().addAll(year_annual_label, year_annual);
			year_annual_box.setSpacing(5);
			
			// Instruction to use the table
			Label instruction_annual = new Label("Click into the column headers to sort");
			
			VBox filter_annual = new VBox();
			filter_annual.getChildren().addAll(year_annual_box, instruction_annual);
			filter_annual.setSpacing(50);
			
			Label noti_annual = new Label();
			
			// Table for data visualization
			TableView<Data> data_annual = new TableView<Data>();
			
			TableColumn<Data, String> farm_col_annual = new TableColumn<Data, String>("Farm");
			farm_col_annual.setMinWidth(130);
			farm_col_annual.setCellValueFactory(new PropertyValueFactory<Data, String>("farm"));
			farm_col_annual.setSortable(true);
			
			TableColumn<Data, Double> total_col_annual = new TableColumn<Data, Double>("Total");
			total_col_annual.setMinWidth(130);
			total_col_annual.setCellValueFactory(new PropertyValueFactory<Data, Double>("total_year"));
			total_col_annual.setSortable(true);
			
			TableColumn<Data, Double> avg_col_annual = new TableColumn<Data, Double>("Average");
			avg_col_annual.setMinWidth(130);
			avg_col_annual.setCellValueFactory(new PropertyValueFactory<Data, Double>("average"));
			
			TableColumn<Data, Double> percent_col_annual = new TableColumn<Data, Double>("Share");
			percent_col_annual.setMinWidth(130);
			percent_col_annual.setCellValueFactory(new PropertyValueFactory<Data, Double>("percentTotal_year"));
			
			data_annual.getColumns().addAll(farm_col_annual, total_col_annual, avg_col_annual, percent_col_annual);
			
			// Grouping elements in the center
			VBox mid_annual = new VBox();
			mid_annual.getChildren().addAll(noti_annual, data_annual);
			
			VBox center_annual = new VBox();
			center_annual.getChildren().addAll(filter_annual, mid_annual);
	
			Button back_annual = new Button("_Back");
			back_annual.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_annual.setText("");
					primaryStage.setScene(report_scene);
				}
			});
			
			// Functional buttons
			Button view_annual = new Button("View report");
			view_annual.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_annual.setText("");
					String year = year_annual.getText();
					try {
						int y = Integer.parseInt(year);
						data_annual.setItems(dm.getAnnualReport(fac, y));
					}
					catch (NumberFormatException er) {
						noti_annual.setText("Year must be a number");
					}
					catch (NullPointerException er) {
						noti_annual.setText(er.getMessage());
					}
					catch (Exception er) {
						noti_annual.setText(er.getMessage());
					}
				}
			});
			
			Button export_annual = new Button("Export to file");
			export_annual.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_annual.setText("");
					String year = year_annual.getText();
					try {
						int y = Integer.parseInt(year);
						fm.writeToFile(dm.getAnnualReport(fac, y));
						noti_annual.setText("Successfully write to file output.csv");
					}
					catch (NumberFormatException er) {
						noti_annual.setText("Year must be a number");
					}
					catch (Exception er) {
						noti_annual.setText("Error occured, unable to write to file");
					}
					
				}
			});
			
			// Grouping buttons to the bottom
			HBox bot_annual = new HBox();
			bot_annual.getChildren().addAll(back_annual, view_annual, export_annual);
			bot_annual.setSpacing(150);
			
			annual_layout.setTop(new Label("ANNUAL REPORT"));
			annual_layout.setCenter(center_annual);
			annual_layout.setBottom(bot_annual);
			
			// MONTHLY REPORT SCENE
			// Allow user to write by themselves or choosing options for months
			ComboBox<String> month_monthly = new ComboBox<String>();
			month_monthly.getItems().addAll("01","02","03","04", "05", "06", "07", "08", "09", "10", "11", "12");
			month_monthly.setEditable(true);
			
			Label month_monthly_label = new Label("Month");
			month_monthly_label.setLabelFor(month_monthly);
			
			VBox month_monthly_box = new VBox();
			month_monthly_box.getChildren().addAll(month_monthly_label, month_monthly);
			month_monthly_box.setSpacing(5);
			
			// Let user fill in a year
			TextField year_monthly = new TextField();
			
			Label year_monthly_label = new Label("Year");
			year_monthly_label.setLabelFor(year_monthly);
			
			VBox year_monthly_box = new VBox();
			year_monthly_box.getChildren().addAll(year_monthly_label, year_monthly);
			year_monthly_box.setSpacing(5);
			
			// Instruction
			Label filter_monthly_ins = new Label("Click into the column headers to sort items");
			
			// Grouping all the date elements
			HBox date_monthly = new HBox();
			date_monthly.getChildren().addAll(month_monthly_box, year_monthly_box);
			date_monthly.setSpacing(20);
			
			VBox filter_monthly = new VBox();
			filter_monthly.getChildren().addAll(date_monthly, filter_monthly_ins);
			filter_monthly.setSpacing(10);
			
			Label noti_monthly = new Label();
			
			// Table for data visualization
			TableView<Data> data_monthly = new TableView<Data>();
			
			TableColumn<Data, String> farm_col_monthly = new TableColumn<Data, String>("Farm");
			farm_col_monthly.setMinWidth(130);
			farm_col_monthly.setCellValueFactory(new PropertyValueFactory<Data, String>("farm"));
			farm_col_monthly.setSortable(true);
			
			TableColumn<Data, Double> total_col_monthly = new TableColumn<Data, Double>("Total");
			total_col_monthly.setMinWidth(130);
			total_col_monthly.setCellValueFactory(new PropertyValueFactory<Data, Double>("totalMonth"));
			total_col_monthly.setSortable(true);
			
			TableColumn<Data, Double> avg_col_monthly = new TableColumn<Data, Double>("Average");
			avg_col_monthly.setMinWidth(130);
			avg_col_monthly.setCellValueFactory(new PropertyValueFactory<Data, Double>("average"));
			avg_col_monthly.setSortable(true);
			
			TableColumn<Data, Double> percent_col_monthly = new TableColumn<Data, Double>("Share");
			percent_col_monthly.setMinWidth(130);
			percent_col_monthly.setCellValueFactory(new PropertyValueFactory<Data, Double>("percentTotalMonth"));
			percent_col_monthly.setSortable(true);
			
			data_monthly.getColumns().addAll(farm_col_monthly, total_col_monthly, avg_col_monthly, percent_col_monthly);
			
			// Grouping Elements in the center
			VBox center_monthly = new VBox();
			center_monthly.getChildren().addAll(filter_monthly, noti_monthly, data_monthly);
			
			// Functional Buttons
			Button back_monthly = new Button("_Back");
			back_monthly.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_monthly.setText("");
					primaryStage.setScene(report_scene);
				}
			});
			
			Button view_monthly = new Button("_View report");
			view_monthly.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_monthly.setText("");
					try {
						int m = Integer.parseInt(month_monthly.getValue().toString());
						int y = Integer.parseInt(year_monthly.getText());
						data_monthly.setItems(dm.getMonthlyReport(fac, y, m));
					}
					catch (NumberFormatException er) {
						noti_monthly.setText("Invalid date, month and year must be numbers");
					}
					catch (NullPointerException er) {
						noti_monthly.setText(er.getMessage());
					}
					catch (Exception er) {
						noti_monthly.setText(er.getMessage());
					}
				}
			});
			
			Button export_monthly = new Button("Export to file");
			export_monthly.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_monthly.setText("");
					try {
						int m = Integer.parseInt(month_monthly.getValue().toString());
						int y = Integer.parseInt(year_monthly.getText());
						fm.writeToFile(dm.getMonthlyReport(fac, y, m));
					}
					catch (NumberFormatException er) {
						noti_monthly.setText("Invalid date, month and year must be numbers");
					}
					catch (Exception er) {
						noti_monthly.setText("Error occured, unable to write to file");
					}
				}
			});
			
			HBox bot_monthly = new HBox();
			bot_monthly.getChildren().addAll(back_monthly, view_monthly, export_monthly);
			bot_monthly.setSpacing(150);
			
			monthly_layout.setTop(new Label("MONTHLY REPORT"));
			monthly_layout.setCenter(center_monthly);
			monthly_layout.setBottom(bot_monthly);
			
			// DATE RANGE REPORT SCENE
			// Taking user input for specific from-to date range
			TextField date_from_dr = new TextField();
			date_from_dr.setPromptText("yy-mm-dd");
			Label date_from_label_dr = new Label("From");
			date_from_label_dr.setLabelFor(date_from_dr);
			
			TextField date_to_dr = new TextField();
			date_to_dr.setPromptText("yy-mm-dd");
			Label date_to_label_dr = new Label("To");
			date_to_label_dr.setLabelFor(date_to_dr);
			
			// Grouping date range elements
			GridPane filter_dr = new GridPane();
			filter_dr.addRow(0, date_from_label_dr, date_from_dr);
			filter_dr.addRow(1, date_to_label_dr, date_to_dr);
			
			Label noti_dr = new Label();
			
			// Table for data visualization
			TableView<Data> data_dr = new TableView<Data>();
			
			TableColumn<Data, String> farm_col_dr = new TableColumn<Data, String>("Farm ID");
			farm_col_dr.setMinWidth(150);
			farm_col_dr.setCellValueFactory(new PropertyValueFactory<Data, String>("farm"));
			
			TableColumn<Data, Double> total_col_dr = new TableColumn<Data, Double>("Total");
			total_col_dr.setMinWidth(150);
			total_col_dr.setCellValueFactory(new PropertyValueFactory<Data, Double>("weightDateRange"));
			
			TableColumn<Data, Double> total_percent_dr = new TableColumn<Data, Double>("Share");
			total_percent_dr.setMinWidth(150);
			total_percent_dr.setCellValueFactory(new PropertyValueFactory<Data, Double>("weightDateRangePercent"));
			
			data_dr.getColumns().addAll(farm_col_dr, total_col_dr, total_percent_dr);
			
			// Grouping center elements
			VBox center_dr = new VBox();
			center_dr.getChildren().addAll(filter_dr, noti_dr, data_dr);
			
			// Functional buttons
			Button view_dr = new Button("_View Report");
			view_dr.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					noti_dr.setText("");
					try {
						String from = date_from_dr.getText();
						String to = date_to_dr.getText();
						data_dr.setItems(dm.getDateRangeReport(fac, from, to));
					}
					catch (InputMismatchException er) {
						noti_dr.setText(er.getMessage());
					}
					catch (Exception er) {
						noti_dr.setText("Error occured, unable to display the report");
					}
				}
			});
			
			Button back_dr = new Button("_Back");
			back_dr.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					primaryStage.setScene(report_scene);
				}
			});
			
			Button export_dr = new Button("_Export to file");
			export_dr.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					try {
						String from = date_from_dr.getText();
						String to = date_to_dr.getText();
						fm.writeToFile(dm.getDateRangeReport(fac, from, to));
					}
					catch (InputMismatchException er) {
						noti_monthly.setText(er.getMessage());
					}
					catch (Exception er) {
						noti_monthly.setText("Error occured, unable to write to file");
					}
				}
			});
			
			HBox bot_dr = new HBox();
			bot_dr.getChildren().addAll(back_dr, view_dr, export_dr);
			bot_dr.setSpacing(150);
			
			dr_layout.setTop(new Label("FARM DATE RANGE REPORT"));
			dr_layout.setCenter(center_dr);
			dr_layout.setBottom(bot_dr);
		
    	primaryStage.setTitle(APP_TITLE);
    	primaryStage.setScene(mainMenu);
    	primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
