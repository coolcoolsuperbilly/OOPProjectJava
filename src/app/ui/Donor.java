package app.ui;

public class Donor extends Person {
    private String donorId;
    private String bloodGroup;
    private int age;
    private double weight;
    private double bloodSugar;
    private double bloodPressure;

    public Donor(String donorId, String name, String bloodGroup,
                 int age, double weight, double bloodSugar, double bloodPressure) {
        super(name);
        this.donorId = donorId;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.weight = weight;
        this.bloodSugar = bloodSugar;
        this.bloodPressure = bloodPressure;
    }

    public String getDonorId() { return donorId; }
    public String getBloodGroup() { return bloodGroup; }
    public int getAge() { return age; }
    public double getWeight() { return weight; }
    public double getBloodSugar() { return bloodSugar; }
    public double getBloodPressure() { return bloodPressure; }

    public void setWeight(double weight) {
        if (weight >= 50) {
            this.weight = weight;
        } else {
            System.out.println("Weight must be at least 50 kg.");
        }
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public void setBloodPressure(double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
    
    public void setAge(int age) { this.age = age; }
    public void setBloodGroup(String bg) { this.bloodGroup = bg; }
    public void setDonorId(String id) { this.donorId = id; }

    public void displayDonorDetails() {
        System.out.println("Donor ID: " + donorId);
        System.out.println("Name: " + name);
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight);
        System.out.println("Sugar: " + bloodSugar);
        System.out.println("BP: " + bloodPressure);
    }

    @Override
    public void register() {
        System.out.println("Donor registered successfully.");
    }
}
