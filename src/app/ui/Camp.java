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
        this.campId = campId;
        this.campName = campId;
        this.location = "TBD";
        this.date = "";
        this.organizer = organizer;
        this.registrations = new ArrayList<>();
    }

    public Camp(String campId, String campName, String location, String date, Organizer organizer) {
        this.campId = campId;
        this.campName = campName;
        this.location = location;
        this.date = date;
        this.organizer = organizer;
        this.registrations = new ArrayList<>();
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
}
