package app.ui;

public class Registration {
    private String registrationId;
    private String date;
    private Donor donor;

    public Registration(String registrationId, String date, Donor donor) {
        this.registrationId = registrationId;
        this.date = date;
        this.donor = donor;
    }

    public String getRegistrationId() { return registrationId; }
    public String getDate() { return date; }
    public Donor getDonor() { return donor; }

    public void displayRegistration() {
        System.out.println("Registration ID: " + registrationId);
        System.out.println("Date: " + date);
        donor.displayDonorDetails();
    }
}
