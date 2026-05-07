package app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Screens
    private static final String SCREEN_DASHBOARD = "DASHBOARD";
    private static final String SCREEN_ADD_DONOR = "ADD_DONOR";
    private static final String SCREEN_VIEW_DONOR= "VIEW_DONOR";
    private static final String SCREEN_ADD_CAMP  = "ADD_CAMP";
    private static final String SCREEN_VIEW_CAMP = "VIEW_CAMP";

    // Panels (kept to refresh)
    private final ViewDonorsUI   viewDonorsUI   = new ViewDonorsUI();
    private final ViewCampsUI    viewCampsUI    = new ViewCampsUI();

    private final CardLayout cards = new CardLayout();
    private final JPanel     screens = new JPanel(cards);

    private JButton activeBtn = null;
    
    // UI init
    public MainUI() {
        super("Blood Donation Camp Management System");
        
        AppData.organizer.readData(); // reads data from disk and pushes to memmory
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { confirmExit(); }
        });
        setSize(1100, 680);
        setMinimumSize(new Dimension(900, 580));
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        add(screens,        BorderLayout.CENTER);

        // register cards
        screens.add(new DashboardUI(),  SCREEN_DASHBOARD);
        screens.add(new DonorUI(),      SCREEN_ADD_DONOR);
        screens.add(viewDonorsUI,       SCREEN_VIEW_DONOR);
        screens.add(new CampUI(),       SCREEN_ADD_CAMP);
        screens.add(viewCampsUI,        SCREEN_VIEW_CAMP);

        cards.show(screens, SCREEN_DASHBOARD);
    }

    // Sidebar
    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(new Color(0x1A1A2E));
        side.setPreferredSize(new Dimension(200, 0));

        // Header
        JLabel lbl = new JLabel("<html><center>🩸<br><b style='color:white;font-size:13px'>"
                + "BloodCamp</b><br><span style='color:#aaa;font-size:10px'>Management System</span></center></html>");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 10));
        side.add(lbl);
        
        // buttons
        side.add(navBtn("Dashboard",SCREEN_DASHBOARD));
        side.add(navBtn("Add Donor", SCREEN_ADD_DONOR));
        side.add(navBtn("View Donors",SCREEN_VIEW_DONOR));
        side.add(navBtn("Add Camp",SCREEN_ADD_CAMP));
        side.add(navBtn("View Camps",SCREEN_VIEW_CAMP));

        side.add(Box.createVerticalGlue()); // Spaceng

        // exit button at bottom
        JButton exitBtn = sideBtn("Exit Application");
        exitBtn.addActionListener(e -> confirmExit());
        side.add(exitBtn);
        side.add(Box.createVerticalStrut(10));

        return side;
    }

    private JButton navBtn(String text, String card) {
        JButton btn = sideBtn(text);
        
        btn.addActionListener(e -> {
            showCard(card);
            setActive(btn);
            // refresh live data panels when navigating to them
            if (card.equals(SCREEN_VIEW_DONOR))  viewDonorsUI.refresh();
            if (card.equals(SCREEN_VIEW_CAMP))   viewCampsUI.refresh();
        });
        
        return btn;
    }

    private JButton sideBtn(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setForeground(new Color(0xBBBBBB));
        b.setBackground(new Color(0x1A1A2E));
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 10));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (b != activeBtn) b.setBackground(new Color(0x2D2D44));
            }
            public void mouseExited(MouseEvent e) {
                if (b != activeBtn) b.setBackground(new Color(0x1A1A2E));
            }
        });
        return b;
    }

    private void setActive(JButton btn) {
        if (activeBtn != null) {
            activeBtn.setBackground(new Color(0x1A1A2E));
            activeBtn.setForeground(new Color(0xBBBBBB));
        }
        btn.setBackground(new Color(0xB71C1C));
        btn.setForeground(Color.WHITE);
        activeBtn = btn;
    }

    private void showCard(String card) {
        cards.show(screens, card);
    }

    private void confirmExit() {
        int r = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (r == JOptionPane.YES_OPTION) {
        	// Store the data to disc;
        	AppData.organizer.saveData();
        	System.exit(0);
        }
    }
    
    private static boolean organizerLogin() {
    	JTextField orgtxtId = new JTextField();
        JTextField orgtxtName = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        
        panel.add(new JLabel("Enter Organizer ID:"));
        panel.add(orgtxtId);

        panel.add(new JLabel("Enter Organizer Name:"));
        panel.add(orgtxtName);
        
        int result = JOptionPane.showConfirmDialog(
        		null,
                panel,
                "Organizer Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION) return false;
        String organizerId = orgtxtId.getText().trim();
        String organizerName = orgtxtName.getText().trim();
        
    	AppData.organizer = new Organizer(organizerId,organizerName);
    	if(organizerId.isEmpty() || organizerName.isEmpty()) return false; // no login
    	return true;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        UITheme.applyGlobalDefaults();

        SwingUtilities.invokeLater(() -> {
        	if(!organizerLogin()) {
        		System.exit(0);
        	};
            MainUI frame = new MainUI();
            frame.setVisible(true);
        });
    }
}
