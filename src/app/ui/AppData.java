package app.ui;

public class AppData {
	// Contains Static Objects / Method that connects Backend and UI (Used for Association)
    // ── Backend singletons ───────────────────────────────────────────────────
    public final static Organizer   organizer   = new Organizer("ORG101");
    public final static Validator   validator   = new Validator();
    public final static FileManager fileManager = new FileManager("data");
    
    private AppData() {}
    // ── Donors ───────────────────────────────────────────────────────────────
    public static String nextDonorId(Camp c)  { return "D" + String.format("%03d", c.getRegistrations().size()+1) + c.getCampId(); } // D+NUMBER+CAMPID
    // ── Camps ────────────────────────────────────────────────────────────────
    public static String nextCampId()  { return "C" + String.format("%03d", organizer.getCamps().size()+1); } // C+NUMBER
    // ── Registrations ────────────────────────────────────────────────────────
    public static String nextRegId(Camp c)  { return "R" + String.format("%03d", c.getRegistrations().size()+1) + c.getCampId(); } // R+NUMBER+CAMPID
}
