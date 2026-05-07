package app.ui;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FileManager {
    private String fileLocation;
	private Connection dbConnection;
    private Statement dbStatement;

    public FileManager(String fileLocation) {
        this.fileLocation = fileLocation;
        
        try {
        	dbConnection = DriverManager.getConnection("jdbc:sqlite:data.db");
        	dbStatement = dbConnection.createStatement();
        	dbStatement.execute(
        		    "CREATE TABLE IF NOT EXISTS registrationData("
        		    + "registrationId TEXT,"
        		    + "campId TEXT,"
        		    + "date TEXT,"
        		    + "donorId TEXT,"
        		    + "name TEXT,"
        		    + "bloodGroup TEXT,"
        		    + "age INTEGER,"
        		    + "weight REAL,"
        		    + "bloodSugar REAL,"
        		    + "bloodPressure REAL"
        		    + ")"
        	);
        	dbStatement.execute(
        		    "CREATE TABLE IF NOT EXISTS campsData("
        		    + "campId TEXT,"
        		    + "campName TEXT,"
        		    + "location TEXT,"
        		    + "date TEXT,"
        		    + "organizer TEXT"
        		    + ")"
        	);
        } catch (Exception e) {
            System.out.println("Database Creation Error. Trace:");
            e.printStackTrace();
        }
    }
    
    public void clearData() {
    	// Remove all previous data [Add roll-back if necessary]
    	try {
			dbStatement.executeUpdate("DELETE FROM registrationData");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public void saveData(ArrayList<Registration> list,String campId) {
    	System.out.println("[Saving] Camp:"+campId);
        try {
			PreparedStatement statementSave = dbConnection.prepareStatement(
				"INSERT INTO registrationData VALUES (?,?,?,?,?,?,?,?,?,?)"
			);
			for (Registration reg : list) {
			    statementSave.setString(1, reg.getRegistrationId());
			    statementSave.setString(2, campId);
			    statementSave.setString(3, reg.getDate());
			    statementSave.setString(4, reg.getDonor().getDonorId());
			    statementSave.setString(5, reg.getDonor().getName());
			    statementSave.setString(6, reg.getDonor().getBloodGroup());
			    statementSave.setInt(7, reg.getDonor().getAge());
			    statementSave.setDouble(8, reg.getDonor().getWeight());
			    statementSave.setDouble(9, reg.getDonor().getBloodSugar());
			    statementSave.setDouble(10, reg.getDonor().getBloodPressure());

			    statementSave.addBatch();
			}
			statementSave.executeBatch();
			statementSave.clearBatch();
		} catch (SQLException e) {
			System.out.println("An Error Occured,Save Failure. Trace:");
			e.printStackTrace();
		}
    }

    public ArrayList<Registration> readData(String campId) {
    	System.out.println("[Reading] Camp:"+campId);
        try {
			ResultSet readResults = dbStatement.executeQuery("SELECT * FROM registrationData WHERE campId='" + campId + "'");
			ArrayList<Registration> data = new ArrayList<Registration>();
			while(readResults.next()) {
				data.add(
					new Registration(
						readResults.getString("registrationId"),
						readResults.getString("date"), 
						new Donor(
							readResults.getString("donorId"),
							readResults.getString("name"),
							readResults.getString("bloodGroup"),
							readResults.getInt("age"),
							readResults.getDouble("weight"),
							readResults.getDouble("bloodSugar"),
							readResults.getDouble("bloodPressure")
						)
					)
				);
			}
			return data;
		} catch (SQLException e) {
			System.out.println("An Error Occured,Read Failure. Trace:");
			e.printStackTrace();
			return new ArrayList<>();
		}
    }
    public void close() {
    	try {
    		if(dbConnection!=null) 
    			dbConnection.close();
    		if(dbStatement!=null) 
    			dbStatement.close();	
    	} catch (SQLException e) {
    		System.out.println("An Error Occured,Close Failure. Trace:");
    		e.printStackTrace();
    	}
    }
    public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public void saveCampData(ArrayList<Camp> camps) {
		System.out.println("Saving data to: " + fileLocation);
        try {
			PreparedStatement statementSave = dbConnection.prepareStatement(
				"INSERT INTO campsData VALUES (?,?,?,?,?)"
			);
			// Remove all previous data [Add roll-back if necessary]
			dbStatement.executeUpdate("DELETE FROM campsData");
			for (Camp camp : camps) {
			    statementSave.setString(1, camp.getCampId());
			    statementSave.setString(2, camp.getCampName());
			    statementSave.setString(3, camp.getLocation());
			    statementSave.setString(4, camp.getDate());
			    statementSave.setString(5, camp.getOrganizer().getOrganizerId());

			    statementSave.addBatch();
			}
			statementSave.executeBatch();
			statementSave.clearBatch();
		} catch (SQLException e) {
			System.out.println("An Error Occured,Save Failure. Trace:");
			e.printStackTrace();
		}
	}
	public ArrayList<Camp> readCampData() {
		try {
			ResultSet readResults = dbStatement.executeQuery("SELECT * FROM campsData");
			ArrayList<Camp> data = new ArrayList<Camp>();
			while(readResults.next()) {
				data.add(
					new Camp(
						readResults.getString("campId"),
						readResults.getString("campName"), 
						readResults.getString("location"),
						readResults.getString("date"),
						AppData.organizer // TODO: Its totally extra referencing
					)
				);
			}
			return data;
		} catch (SQLException e) {
			System.out.println("An Error Occured,Read Failure. Trace:");
			e.printStackTrace();
			return new ArrayList<>();
		}	
	}
}

