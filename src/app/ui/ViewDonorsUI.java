package app.ui;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Panel: View all registered donors in a searchable JTable.
 */
class ViewDonorsUI extends JPanel {
    private static final long serialVersionUID = 1L;

	private static final String[] COLS = {
        "Donor ID","Name","Blood Group","Age","Weight (kg)","Sugar (mg/dL)","BP (mmHg)","Eligible?"
    };

    private final DefaultTableModel model = new DefaultTableModel(COLS, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable  table  = new JTable(model);
    private final JTextField tfSearch = UITheme.field(22);
    private final JLabel  lblCount = UITheme.body("0 donors");

    ViewDonorsUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.GRAY_BG);

        add(UITheme.headerBar("View Donors", "All registered donors"), BorderLayout.NORTH);
        add(buildToolbar(), BorderLayout.CENTER);
    }

    private JPanel buildToolbar() {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // ── Search / toolbar row ──────────────────────────────────────────────
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolbar.setOpaque(false);

        JLabel lbl = UITheme.body("Search:");
        toolbar.add(lbl);
        toolbar.add(tfSearch);

        JButton btnSearch  = UITheme.primaryButton("Search");
        JButton btnRefresh = UITheme.secondaryButton("Refresh");
        toolbar.add(btnSearch);
        toolbar.add(btnRefresh);
        toolbar.add(Box.createHorizontalStrut(20));
        toolbar.add(lblCount);

        btnSearch .addActionListener(e -> applySearch());
        btnRefresh.addActionListener(e -> { tfSearch.setText(""); refresh(); });
        tfSearch  .addActionListener(e -> applySearch());

        outer.add(toolbar, BorderLayout.NORTH);

        // ── Table ─────────────────────────────────────────────────────────────
        styleTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UITheme.GRAY_BORDER));
        scroll.getViewport().setBackground(UITheme.WHITE);
        outer.add(scroll, BorderLayout.CENTER);

        refresh();
        return outer;
    }

    private void styleTable() {
        // Column widths
        int[] widths = {80, 160, 100, 50, 100, 110, 100, 90};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
    }

    // ── Data loading ─────────────────────────────────────────────────────────
    void refresh() {
    	ArrayList<Registration> result = new ArrayList<Registration>();
    	for(Camp c:AppData.organizer.getCamps()) {
    		result.addAll(c.getRegistrations());
    	}
        populate(result);
    }

    private void applySearch() {
        String q = tfSearch.getText().trim().toLowerCase();
        if (q.isEmpty()) { refresh(); return; }
        ArrayList<Registration> filtered = new ArrayList<>();
        for(Camp c:AppData.organizer.getCamps()) {
        	for (Registration r : c.getRegistrations()) {
            	Donor d = r.getDonor();
                if (d.getName().toLowerCase().contains(q)
                        || d.getDonorId().toLowerCase().contains(q)
                        || d.getBloodGroup().toLowerCase().contains(q)) {
                    filtered.add(r);
                }
            }
        }
        populate(filtered);
    }

    private void populate(ArrayList<Registration> list) {
        model.setRowCount(0);
        for (Registration r : list) {
        	Donor d = r.getDonor();
            model.addRow(new Object[]{
                d.getDonorId(), d.getName(), d.getBloodGroup(),
                d.getAge(), d.getWeight(), d.getBloodSugar(), d.getBloodPressure(),
                "Yes" // all registered are eligible
            });
        }
        lblCount.setText(list.size() + " donor" + (list.size() == 1 ? "" : "s"));
    }
}
