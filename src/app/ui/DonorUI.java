package app.ui;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.time.LocalDate;

/**
 * Panel: Add a new Donor.
 * Validates using Validator (eligibility) plus basic field checks.
 */
class DonorUI extends JPanel {

    private static final long serialVersionUID = 1L;
	// ── Form fields ──────────────────────────────────────────────────────────
    private final JTextField tfName     = UITheme.field(20);
    private final JTextField tfAge      = UITheme.field(8);
    private final JTextField tfWeight   = UITheme.field(8);
    private final JTextField tfSugar    = UITheme.field(8);
    private final JTextField tfBP       = UITheme.field(8);
    
    // combo boxes
    private final JComboBox<String> cbBlood = UITheme.combo(new String[]{"A+","A-","B+","B-","AB+","AB-","O+","O-"});
    private final JComboBox<String> cbCamp = UITheme.combo(new String[]{});

    // ── Status label ─────────────────────────────────────────────────────────
    private final JLabel lblStatus = new JLabel(" ");

    DonorUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.GRAY_BG);

        add(UITheme.headerBar("Add Donor", "Register a new blood donor"), BorderLayout.NORTH);
        
        // UI
        add(buildForm(),   BorderLayout.CENTER);
    }

    private void refreshCampsCB() {
    	cbCamp.removeAllItems();
    	for (Camp c : AppData.organizer.getCamps()) cbCamp.addItem(c.getCampId() + "  |  " + c.getCampName() + "  @  " + c.getLocation());
    }
    // ── Form panel ────────────────────────────────────────────────────────────
    private JPanel buildForm() {
        JPanel outer = new JPanel(new GridBagConstraints().getClass() != null
                ? new GridBagLayout() : new GridBagLayout());
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JPanel card = UITheme.card("Donor Information");
        card.setLayout(new GridBagLayout());

        GridBagConstraints lc = new GridBagConstraints();
        lc.insets  = new Insets(7, 6, 7, 10);
        lc.anchor  = GridBagConstraints.EAST;
        lc.fill    = GridBagConstraints.NONE;

        GridBagConstraints fc = new GridBagConstraints();
        fc.insets  = new Insets(7, 0, 7, 6);
        fc.anchor  = GridBagConstraints.WEST;
        fc.fill    = GridBagConstraints.HORIZONTAL;
        fc.weightx = 1.0;
        
        cbCamp.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                refreshCampsCB();
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
        refreshCampsCB();
        Object[][] rows = {
        	{"Camp *", cbCamp},
            {"Full Name *",    tfName},
            {"Age *",          tfAge},
            {"Blood Group *",  cbBlood},
            {"Weight (kg) *",  tfWeight},
            {"Blood Sugar *",  tfSugar},
            {"Blood Pressure *",tfBP},
        };

        for (int i = 0; i < rows.length; i++) {
            lc.gridy = i; fc.gridy = i;
            lc.gridx = 0; fc.gridx = 1;

            JLabel lbl = UITheme.body((String) rows[i][0]);
            card.add(lbl, lc);
            card.add((Component) rows[i][1], fc);
        }

        // ── Hint row ─────────────────────────────────────────────────────────
        GridBagConstraints hc = new GridBagConstraints();
        hc.gridy = rows.length; hc.gridx = 0; hc.gridwidth = 2;
        hc.insets = new Insets(4, 6, 4, 6);
        hc.anchor = GridBagConstraints.WEST;
        JLabel hint = new JLabel("* Sugar: 70-140 mg/dL  |  BP: 100-180 mmHg  |  Weight ≥ 50 kg  |  Age: 18-65");
        hint.setFont(UITheme.FONT_SMALL);
        hint.setForeground(UITheme.TEXT_MID);
        card.add(hint, hc);

        // ── Buttons ───────────────────────────────────────────────────────────
        JButton btnSubmit = UITheme.primaryButton("  Add Donor  ");
        JButton btnClear  = UITheme.secondaryButton("  Clear  ");
        btnSubmit.addActionListener(e -> handleSubmit());
        btnClear .addActionListener(e -> clearForm());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btnRow.setOpaque(false);
        btnRow.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));
        btnRow.add(btnSubmit);
        btnRow.add(btnClear);

        GridBagConstraints bc = new GridBagConstraints();
        bc.gridy = rows.length + 1; bc.gridx = 0; bc.gridwidth = 2;
        bc.insets = new Insets(6, 0, 4, 0);
        card.add(btnRow, bc);

        // ── Status ────────────────────────────────────────────────────────────
        lblStatus.setFont(UITheme.FONT_BODY);
        GridBagConstraints sc = new GridBagConstraints();
        sc.gridy = rows.length + 2; sc.gridx = 0; sc.gridwidth = 2;
        sc.insets = new Insets(0, 6, 4, 6);
        sc.anchor = GridBagConstraints.CENTER;
        card.add(lblStatus, sc);

        outer.add(card);
        return outer;
    }

    // ── Handlers ─────────────────────────────────────────────────────────────
    private void handleSubmit() {
        String name = tfName.getText().trim();
        String ageStr    = tfAge.getText().trim();
        String weightStr = tfWeight.getText().trim();
        String sugarStr  = tfSugar.getText().trim();
        String bpStr     = tfBP.getText().trim();
        String blood     = (String) cbBlood.getSelectedItem();
        int campIndex = cbCamp.getSelectedIndex();


        // Basic presence check
        if (name.isEmpty() || ageStr.isEmpty() || weightStr.isEmpty()
                || sugarStr.isEmpty() || bpStr.isEmpty()) {
            showStatus("Please fill in all fields.", false);
            return;
        }

        // Parse numerics
        int age; double weight, sugar, bp;
        try {
            age    = Integer.parseInt(ageStr);
            weight = Double.parseDouble(weightStr);
            sugar  = Double.parseDouble(sugarStr);
            bp     = Double.parseDouble(bpStr);
        } catch (NumberFormatException ex) {
            showStatus("Age must be an integer; Weight / Sugar / BP must be numbers.", false);
            return;
        }
        Camp  camp = AppData.organizer.getCamps().get(campIndex);
        // Build donor & check eligibility via Validator
        String id = AppData.nextDonorId(camp);
        Donor d = new Donor(id, name, blood, age, weight, sugar, bp);
        Validator v = AppData.validator;

        if (!v.checkEligibility(d)) {
            showStatus("Not eligible: " + v.getIneligibilityReason(d), false);
            return;
        }

        // All good – save
        d.register();          // original method (prints to console)
        String regId = AppData.nextRegId(camp);
        String today = LocalDate.now().toString();
        Registration reg = new Registration(regId, today, d);
        reg.displayRegistration();
        
        camp.addRegistration(reg);
        showStatus("✔  Donor " + id + " added successfully!", true);
        JOptionPane.showMessageDialog(this,
                "Donor added!\n\nID: " + id + "\nName: " + name
                + "\nBlood Group: " + blood + "\nEligibility: Eligible",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void clearForm() {
        tfName.setText(""); tfAge.setText(""); tfWeight.setText("");
        tfSugar.setText(""); tfBP.setText("");
        cbBlood.setSelectedIndex(0);
        lblStatus.setText(" ");
    }

    private void showStatus(String msg, boolean ok) {
        lblStatus.setText(msg);
        lblStatus.setForeground(ok ? UITheme.SUCCESS : UITheme.RED_MID);
    }
}
