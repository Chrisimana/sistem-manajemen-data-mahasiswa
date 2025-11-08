package gui;

import java.awt.*;
import javax.swing.*;
import utils.FileHandler;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        initComponents();
        setupFrame();
        FileHandler.muatData(); // Load existing data
    }
    
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        
        // Create panels
        InputPanel inputPanel = new InputPanel();
        DisplayPanel displayPanel = new DisplayPanel();
        HistoryPanel historyPanel = new HistoryPanel();
        
        // Add tabs dengan icon sederhana (tanpa emoji yang mungkin bermasalah)
        tabbedPane.addTab("Input Data", inputPanel);
        tabbedPane.addTab("Lihat Data", displayPanel);
        tabbedPane.addTab("Riwayat & Statistik", historyPanel);
        
        // Set tooltips
        tabbedPane.setToolTipTextAt(0, "Input data mahasiswa baru");
        tabbedPane.setToolTipTextAt(1, "Lihat dan kelola data mahasiswa");
        tabbedPane.setToolTipTextAt(2, "Lihat riwayat dan statistik");
    }
    
    private void setupFrame() {
        setTitle("Sistem Manajemen Data Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 800));
        
        // Create menu bar dengan fitur tambahan
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        // Add tabbed pane to frame
        add(tabbedPane);
        
        pack();
        setLocationRelativeTo(null); // Center the frame
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu File
        JMenu menuFile = new JMenu("File");
        JMenuItem menuBackup = new JMenuItem("Backup Data");
        JMenuItem menuEkspor = new JMenuItem("Ekspor ke CSV");
        JMenuItem menuExit = new JMenuItem("Keluar");
        
        menuBackup.addActionListener(e -> backupData());
        menuEkspor.addActionListener(e -> eksporData());
        menuExit.addActionListener(e -> {
            FileHandler.simpanData();
            System.exit(0);
        });
        
        menuFile.add(menuBackup);
        menuFile.add(menuEkspor);
        menuFile.addSeparator();
        menuFile.add(menuExit);
        
        // Menu Help
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuAbout = new JMenuItem("Tentang");
        menuAbout.addActionListener(e -> showAbout());
        menuHelp.add(menuAbout);
        
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        
        return menuBar;
    }
    
    private void backupData() {
        FileHandler.simpanData();
        JOptionPane.showMessageDialog(this, 
            "Backup data berhasil dilakukan!", "Backup", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eksporData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai CSV");
        fileChooser.setSelectedFile(new java.io.File("data_mahasiswa.csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            if (!fileName.toLowerCase().endsWith(".csv")) {
                fileName += ".csv";
            }
            
            FileHandler.eksporKeCSV(fileName);
            JOptionPane.showMessageDialog(this, 
                "Data berhasil diekspor ke: " + fileName, 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showAbout() {
        JOptionPane.showMessageDialog(this,
            "Sistem Manajemen Data Mahasiswa\n\n" +
            "Fitur Super:\n" +
            "• Manajemen data mahasiswa lengkap\n" +
            "• GUI modern dan user-friendly\n" +
            "• Penyimpanan otomatis\n" +
            "• Pencarian dan filter data\n" +
            "• Statistik dan reporting\n" +
            "• Ekspor data ke CSV\n\n" +
            "Dibuat dengan Java Swing",
            "Tentang Aplikasi",
            JOptionPane.INFORMATION_MESSAGE);
    }
}