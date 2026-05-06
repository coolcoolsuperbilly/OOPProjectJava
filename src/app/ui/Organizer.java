package app.ui;

import java.util.ArrayList;

public class Organizer extends person{
    private String organizerId;
    private ArrayList<Camp> camps;

    public Organizer(String organizerId, String name) {
        this.organizerId = organizerId;
        this.camps = new ArrayList<>();
    }
    public String getOrganizerId() {
        return organizerId;
    }

    public void manageCamp() {
        System.out.println("Organizer " + organizerId + " is managing the camp.");
    }

	public void saveData() {
		AppData.fileManager.saveCampData(camps);
		AppData.fileManager.clearData(); // clear data before writing.
		for(Camp c:camps) {
			c.saveDonorList();
		}
	}
	public void readData() {
		this.camps = AppData.fileManager.readCampData();
		for(Camp c:camps) {
			c.readDonorList();
		}
	}
	public ArrayList<Camp> getCamps() {
		return this.camps;
	}
	public void addCamp(Camp c) {
		this.camps.add(c);
	}
}
