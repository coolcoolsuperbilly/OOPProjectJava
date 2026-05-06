package app.ui;

public class Validator {

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;
    private static final double MIN_WEIGHT = 50.0;
    private static final double MIN_SUGAR = 70.0;
    private static final double MAX_SUGAR = 140.0;
    private static final int MIN_BP = 100;
    private static final int MAX_BP = 180;

    public boolean checkEligibility(Donor d) {
        if (d.getAge() < MIN_AGE || d.getAge() > MAX_AGE) {
            return false;
        }
        if (d.getWeight() < MIN_WEIGHT) {
            return false;
        }
        if (d.getBloodSugar() < MIN_SUGAR || d.getBloodSugar() > MAX_SUGAR) {
            return false;
        }
        if (d.getBloodPressure() < MIN_BP || d.getBloodPressure() > MAX_BP) {
            return false;
        }
        return true;
    }

    // Returns a specific reason string for GUI feedback
    public String getIneligibilityReason(Donor d) {
        if (d.getAge() < MIN_AGE || d.getAge() > MAX_AGE)
            return "Age must be between " + MIN_AGE + " and " + MAX_AGE + " years.";
        if (d.getWeight() < MIN_WEIGHT)
            return "Weight must be at least " + MIN_WEIGHT + " kg.";
        if (d.getBloodSugar() < MIN_SUGAR || d.getBloodSugar() > MAX_SUGAR)
            return "Blood sugar must be between " + MIN_SUGAR + " and " + MAX_SUGAR + " mg/dL.";
        if (d.getBloodPressure() < MIN_BP || d.getBloodPressure() > MAX_BP)
            return "Blood pressure must be between " + MIN_BP + " and " + MAX_BP + " mmHg.";
        return "Eligible";
    }
}
