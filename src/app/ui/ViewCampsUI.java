package app.ui;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Panel: View all camps.
 */
class ViewCampsUI extends JPanel {
    private static final long serialVersionUID = 1L;

	private static final String[] COLS = {
        "Camp ID", "Camp Name", "Location", "Date", "Organizer", "Registrations"
    };

    private final DefaultTableModel model = new DefaultTableModel(COLS, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable    table   = new JTable(model);
    private final JTextField tfSearch = UITheme.field(22);
    private final JLabel    lblCount  = UITheme.body("0 camps");

    ViewCampsUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.GRAY_BG);
        add(UITheme.headerBar("View Camps", "All scheduled donation camps"), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private JPanel buildContent() {
        JPanel outer = new JPanel(new BorderLayout(0, 8));
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        // toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolbar.setOpaque(false);
        toolbar.add(UITheme.body("Search:"));
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

        // table
        styleTable();
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(UITheme.GRAY_BORDER));
        scroll.getViewport().setBackground(UITheme.WHITE);
        outer.add(scroll, BorderLayout.CENTER);

        refresh();
        return outer;
    }

    private void styleTable() {
        int[] w = {80, 180, 160, 110, 100, 110};
        for (int i = 0; i < w.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(w[i]);
    }

    void refresh() {
        populate(AppData.organizer.getCamps());
    }

    private void applySearch() {
        String q = tfSearch.getText().trim().toLowerCase();
        if (q.isEmpty()) { refresh(); return; }
        ArrayList<Camp> filtered = new ArrayList<>();
        for (Camp c : AppData.organizer.getCamps()) {
            if (c.getCampName().toLowerCase().contains(q)
                    || c.getCampId().toLowerCase().contains(q)
                    || c.getLocation().toLowerCase().contains(q)) {
                filtered.add(c);
            }
        }
        populate(filtered);
    }

    private void populate(ArrayList<Camp> list) {
        model.setRowCount(0);
        for (Camp c : list) {
            model.addRow(new Object[]{
                c.getCampId(), c.getCampName(), c.getLocation(),
                c.getDate(), c.getOrganizer().getOrganizerId(),
                c.getRegistrations().size()
            });
        }
        lblCount.setText(list.size() + " camp" + (list.size() == 1 ? "" : "s"));
    }
}
