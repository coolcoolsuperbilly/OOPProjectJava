package app.ui;

import javax.swing.*;
import java.awt.*;

class DashboardUI extends JPanel {
    private static final long serialVersionUID = 1L;

	public DashboardUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.GRAY_BG);
        add(UITheme.headerBar("Blood Donation Camp Management",
                "Welcome — select an option from the menu to get started"), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);
    }

    private JPanel buildBody() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel grid = new JPanel(new GridLayout(2, 3, 18, 18));
        grid.setOpaque(false);

        grid.add(statCard("🩸", "Add Donor",       "Register a new donor with health screening",   UITheme.RED_MID));
        grid.add(statCard("📋", "View Donors",      "Browse all registered donors",                 new Color(0x1565C0)));
        grid.add(statCard("🏥", "Add Camp",         "Schedule a new donation camp",                 new Color(0x2E7D32)));
        grid.add(statCard("🗓", "View Camps",       "View all scheduled donation camps",            new Color(0x6A1B9A)));
        grid.add(statCard("✅", "Register Donor",   "Link an eligible donor to a camp",             new Color(0xE65100)));
        grid.add(statCard("ℹ", "Eligibility Rules", "Age 18-65  •  Weight ≥50 kg\nSugar 70-140  •  BP 100-180", new Color(0x00695C)));

        outer.add(grid);
        return outer;
    }

    private JPanel statCard(String icon, String title, String desc, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(UITheme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 80)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        // top strip
        JPanel strip = new JPanel(new BorderLayout());
        strip.setOpaque(false);

        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        strip.add(ico, BorderLayout.WEST);

        JPanel side = new JPanel(new GridLayout(0, 1, 0, 0));
        side.setOpaque(false);
        side.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel t = new JLabel(title);
        t.setFont(UITheme.FONT_HEAD);
        t.setForeground(accent);
        side.add(t);

        strip.add(side, BorderLayout.CENTER);
        card.add(strip, BorderLayout.NORTH);

        // description (handle newlines)
        JTextArea ta = new JTextArea(desc);
        ta.setFont(UITheme.FONT_SMALL);
        ta.setForeground(UITheme.TEXT_MID);
        ta.setEditable(false);
        ta.setOpaque(false);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setBorder(null);
        card.add(ta, BorderLayout.CENTER);

        // hover:removed

        return card;
    }
}
