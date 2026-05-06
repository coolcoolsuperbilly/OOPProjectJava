package app.ui;

import java.util.ArrayList;

public class Camp {
    private String campId;
    private String campName;
    private String location;
    private String date;
    private ArrayList<Registration> registrations;
    private Organizer organizer;

    public Camp(String campId, Organizer organizer) {
        this.setCampId(campId);
        this.setCampName(campId);
        this.setLocation("TBD");
        this.setDate("");
        this.setOrganizer(organizer);
        this.setRegistrations(new ArrayList<>());
    }

    public Camp(String campId, String campName, String location, String date, Organizer organizer) {
        this.setCampId(campId);
        this.setCampName(campName);
        this.setLocation(location);
        this.setDate(date);
        this.setOrganizer(organizer);
        this.setRegistrations(new ArrayList<>());
    }

    public String getCampId() { return campId; }
    public String getCampName() { return campName; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public Organizer getOrganizer() { return organizer; }
    public ArrayList<Registration> getRegistrations() { return registrations; }

    public void addRegistration(Registration r) {
    	this.registrations.add(r);
    }
    public void removeRegistration(String id) {
        for (int i = 0; i < registrations.size(); i++) {
            if (registrations.get(i).getRegistrationId().equals(id)) {
                registrations.remove(i);
                System.out.println("Registration removed.");
                return;
            }
        }
        System.out.println("Registration not found.");
    }

    public void saveDonorList() {
        AppData.fileManager.saveData(registrations,this.campId);
    }

    public void readDonorList() {
        registrations = AppData.fileManager.readData(this.campId);
    }

    public void displayDonorList() {
        for (Registration r : registrations) {
            r.displayRegistration();
            System.out.println("-------------------");
        }
    }

    public void displayCampDetails() {
        System.out.println("Camp ID: " + campId);
        organizer.manageCamp();
    }

    @Override
    public String toString() {
        return campId + " - " + campName + " @ " + location;
    }
    
    // Getters and extra setters
	public void setRegistrations(ArrayList<Registration> registrations) {
		this.registrations = registrations;
	}
	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setCampName(String campName) {
		this.campName = campName;
	}
	public void setCampId(String campId) {
		this.campId = campId;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
