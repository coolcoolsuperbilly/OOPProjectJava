package app.ui;

import javax.swing.*;

import java.awt.*;

/**
 * Panel: Add a new Blood Donation Camp.
 */
class CampUI extends JPanel {
    private static final long serialVersionUID = 1L;
	private final JTextField tfName     = UITheme.field(22);
    private final JTextField tfLocation = UITheme.field(22);
    private final JTextField tfDate     = UITheme.field(14);
    private final JLabel     lblStatus  = new JLabel(" ");

    CampUI() {
        setLayout(new BorderLayout());
        setBackground(UITheme.GRAY_BG);
        
        add(UITheme.headerBar("Add Camp", "Schedule a new blood donation camp"), BorderLayout.NORTH);
        add(buildForm(), BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JPanel card = UITheme.card("Camp Details");
        card.setLayout(new GridBagLayout());

        GridBagConstraints lc = new GridBagConstraints();
        lc.insets  = new Insets(9, 6, 9, 10);
        lc.anchor  = GridBagConstraints.EAST;

        GridBagConstraints fc = new GridBagConstraints();
        fc.insets  = new Insets(9, 0, 9, 6);
        fc.anchor  = GridBagConstraints.WEST;
        fc.fill    = GridBagConstraints.HORIZONTAL;
        fc.weightx = 1.0;

        String[][] rows = {
            {"Camp Name *",  "tfName"},
            {"Location *",   "tfLocation"},
            {"Date (YYYY-MM-DD) *", "tfDate"},
        };
        Component[] fields = {tfName, tfLocation, tfDate};

        for (int i = 0; i < rows.length; i++) {
            lc.gridy = i; fc.gridy = i;
            lc.gridx = 0; fc.gridx = 1;
            card.add(UITheme.body(rows[i][0]), lc);
            card.add(fields[i], fc);
        }

        // placeholder hint
        tfDate.setToolTipText("e.g. 2026-07-20");

        JButton btnAdd   = UITheme.primaryButton("  Add Camp  ");
        JButton btnClear = UITheme.secondaryButton("  Clear  ");
        btnAdd  .addActionListener(e -> handleSubmit());
        btnClear.addActionListener(e -> clearForm());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btnRow.setOpaque(false);
        btnRow.add(btnAdd);
        btnRow.add(btnClear);

        GridBagConstraints bc = new GridBagConstraints();
        bc.gridy = rows.length; bc.gridx = 0; bc.gridwidth = 2;
        bc.insets = new Insets(10, 0, 6, 0);
        card.add(btnRow, bc);

        lblStatus.setFont(UITheme.FONT_BODY);
        GridBagConstraints sc = new GridBagConstraints();
        sc.gridy = rows.length + 1; sc.gridx = 0; sc.gridwidth = 2;
        sc.anchor = GridBagConstraints.CENTER;
        sc.insets = new Insets(0, 6, 4, 6);
        card.add(lblStatus, sc);

        outer.add(card);
        return outer;
    }

    private void handleSubmit() {
        String name     = tfName.getText().trim();
        String location = tfLocation.getText().trim();
        String date     = tfDate.getText().trim();

        if (name.isEmpty() || location.isEmpty() || date.isEmpty()) {
            show("Please fill in all fields.", false);
            return;
        }
        // basic date format check
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            show("Date must be in YYYY-MM-DD format.", false);
            return;
        }
        Camp camp = new Camp(AppData.nextCampId(), name, location, date, AppData.organizer);
        AppData.organizer.addCamp(camp);
        show("Camp '" + name + "' added successfully!", true);
        JOptionPane.showMessageDialog(this,
                "Camp added!\n\nName: " + name + "\nLocation: " + location + "\nDate: " + date,
                "Camp Added", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void clearForm() {
        tfName.setText(""); tfLocation.setText(""); tfDate.setText("");
        lblStatus.setText(" ");
    }

    private void show(String msg, boolean ok) {
        lblStatus.setText(msg);
        lblStatus.setForeground(ok ? UITheme.SUCCESS : UITheme.RED_MID);
    }
}
