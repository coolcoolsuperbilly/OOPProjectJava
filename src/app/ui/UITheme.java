package app.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Central place for all visual constants so every panel looks consistent.
 */
class UITheme {

    // ── Palette ────────────────────────────────────────────────────────────
    static final Color RED_DARK   = new Color(0xB71C1C);
    static final Color RED_MID    = new Color(0xD32F2F);
    static final Color RED_LIGHT  = new Color(0xEF5350);
    static final Color RED_FAINT  = new Color(0xFFEBEE);
    static final Color WHITE      = Color.WHITE;
    static final Color GRAY_BG    = new Color(0xF5F5F5);
    static final Color GRAY_BORDER= new Color(0xDDDDDD);
    static final Color TEXT_DARK  = new Color(0x212121);
    static final Color TEXT_MID   = new Color(0x616161);
    static final Color SUCCESS    = new Color(0x388E3C);
    static final Color WARNING    = new Color(0xF57C00);

    // ── Fonts ───────────────────────────────────────────────────────────────
    static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD,  22);
    static final Font FONT_HEAD   = new Font("Segoe UI", Font.BOLD,  15);
    static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);
    static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD,  13);
    static final Font FONT_TABLE  = new Font("Segoe UI", Font.PLAIN, 12);

    // ── Spacing ─────────────────────────────────────────────────────────────
    static final int PAD = 12;

    // ── Button factory ──────────────────────────────────────────────────────
    static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(RED_MID);
        b.setForeground(WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(RED_DARK); }
            public void mouseExited (java.awt.event.MouseEvent e) { b.setBackground(RED_MID);  }
        });
        return b;
    }

    static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BTN);
        b.setBackground(GRAY_BG);
        b.setForeground(RED_MID);
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(GRAY_BG); }
            public void mouseExited (java.awt.event.MouseEvent e) { b.setBackground(GRAY_BG);  }
        });
        b.setBorder(BorderFactory.createCompoundBorder(
        	BorderFactory.createLineBorder(RED_MID, 1, true),
        	BorderFactory.createEmptyBorder(3, 10, 3, 10)
        ));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        return b;
    }

    // ── Label factory ───────────────────────────────────────────────────────
    static JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_HEAD);
        l.setForeground(RED_DARK);
        return l;
    }

    static JLabel body(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_DARK);
        return l;
    }

    // ── Field factory ───────────────────────────────────────────────────────
    static JTextField field(int cols) {
        JTextField f = new JTextField(cols);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRAY_BORDER),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return f;
    }

    static JComboBox<String> combo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(FONT_BODY);
        c.setBackground(WHITE);
        return c;
    }

    // ── Panel / card helpers ─────────────────────────────────────────────────
    static JPanel card(String title) {
        JPanel p = new JPanel();
        p.setBackground(WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRAY_BORDER),
                BorderFactory.createEmptyBorder(PAD, PAD, PAD, PAD)));
        if (title != null) {
            p.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(RED_LIGHT),
                            title, TitledBorder.LEFT, TitledBorder.TOP,
                            FONT_HEAD, RED_DARK),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        }
        return p;
    }

    // ── Header bar ───────────────────────────────────────────────────────────
    static JPanel headerBar(String title, String subtitle) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(RED_DARK);
        bar.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel t = new JLabel(title);
        t.setFont(FONT_TITLE);
        t.setForeground(WHITE);

        JLabel s = new JLabel(subtitle);
        s.setFont(FONT_SMALL);
        s.setForeground(new Color(255, 200, 200));

        JPanel txt = new JPanel(new GridLayout(2, 1, 0, 2));
        txt.setOpaque(false);
        txt.add(t);
        txt.add(s);
        bar.add(txt, BorderLayout.WEST);

        // blood-drop icon (drawn as text emoji fallback)
        JLabel icon = new JLabel("🩸", SwingConstants.RIGHT);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        bar.add(icon, BorderLayout.EAST);
        return bar;
    }

    // ── Apply global L&F defaults ─────────────────────────────────────────────
    static void applyGlobalDefaults() {
        UIManager.put("OptionPane.messageFont",   FONT_BODY);
        UIManager.put("OptionPane.buttonFont",    FONT_BTN);
        UIManager.put("Table.font",               FONT_TABLE);
        UIManager.put("TableHeader.font",         new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("Table.selectionBackground",RED_LIGHT);
        UIManager.put("Table.gridColor",          GRAY_BORDER);
        UIManager.put("Table.alternateRowColor",  RED_FAINT);
        UIManager.put("ScrollBar.width",          8);
    }
}
