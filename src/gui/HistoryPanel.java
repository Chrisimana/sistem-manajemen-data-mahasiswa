package gui;

import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import model.DataManager;
import model.Mahasiswa;
import utils.FileHandler;

public class HistoryPanel extends JPanel {
    private JTextArea txtHistory;
    private JButton btnTampilkan, btnEkspor, btnStatistik;
    
    public HistoryPanel() {
        initComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initComponents() {
        txtHistory = new JTextArea(20, 50);
        txtHistory.setEditable(false);
        txtHistory.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        btnTampilkan = new JButton("Tampilkan Riwayat");
        btnEkspor = new JButton("Ekspor ke CSV");
        btnStatistik = new JButton("Tampilkan Statistik");
        
        // Styling
        btnTampilkan.setBackground(new Color(0, 123, 255));
        btnTampilkan.setForeground(Color.WHITE);
        btnEkspor.setBackground(new Color(40, 167, 69));
        btnEkspor.setForeground(Color.WHITE);
        btnStatistik.setBackground(new Color(255, 193, 7));
        btnStatistik.setForeground(Color.BLACK);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Riwayat dan Statistik"));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnTampilkan);
        buttonPanel.add(btnEkspor);
        buttonPanel.add(btnStatistik);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        // Text area with scroll
        JScrollPane scrollPane = new JScrollPane(txtHistory);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        btnTampilkan.addActionListener(e -> tampilkanRiwayat());
        btnEkspor.addActionListener(e -> eksporData());
        btnStatistik.addActionListener(e -> tampilkanStatistik());
    }
    
    private void tampilkanRiwayat() {
        List<Mahasiswa> daftar = DataManager.getInstance().getDaftarMahasiswa();
        StringBuilder sb = new StringBuilder();
        
        sb.append("RIWAYAT DATA MAHASISWA\n");
        sb.append("=".repeat(100)).append("\n\n");
        
        for (Mahasiswa mhs : daftar) {
            sb.append(String.format("NIM          : %s\n", mhs.getNim()));
            sb.append(String.format("Nama         : %s\n", mhs.getNama()));
            sb.append(String.format("Jurusan      : %s\n", mhs.getJurusan()));
            sb.append(String.format("Fakultas     : %s\n", mhs.getFakultas()));
            sb.append(String.format("IP Semester  : %.2f\n", mhs.getIpSemester()));
            sb.append(String.format("IP Kumulatif : %.2f\n", mhs.getIpKumulatif()));
            sb.append(String.format("Status       : %s\n", mhs.getStatus()));
            sb.append(String.format("Beasiswa     : %s\n", mhs.getRiwayatBeasiswa()));
            sb.append(String.format("Input        : %s\n", mhs.getFormattedTanggal()));
            sb.append("-".repeat(50)).append("\n\n");
        }
        
        sb.append(String.format("Total Mahasiswa: %d\n", daftar.size()));
        
        txtHistory.setText(sb.toString());
    }
    
    private void tampilkanStatistik() {
        List<Mahasiswa> daftar = DataManager.getInstance().getDaftarMahasiswa();
        
        if (daftar.isEmpty()) {
            txtHistory.setText("Tidak ada data untuk dianalisis.");
            return;
        }
        
        // Hitung statistik
        int cumLaude = 0, sangatMemuaskan = 0, memuaskan = 0, cukup = 0;
        double totalIPS = 0, totalIPK = 0;
        
        for (Mahasiswa mhs : daftar) {
            totalIPS += mhs.getIpSemester();
            totalIPK += mhs.getIpKumulatif();
            
            switch (mhs.getStatus()) {
                case "Cum Laude": cumLaude++; break;
                case "Sangat Memuaskan": sangatMemuaskan++; break;
                case "Memuaskan": memuaskan++; break;
                case "Cukup": cukup++; break;
            }
        }
        
        double rataIPS = totalIPS / daftar.size();
        double rataIPK = totalIPK / daftar.size();
        
        StringBuilder sb = new StringBuilder();
        sb.append("STATISTIK DATA MAHASISWA\n");
        sb.append("=".repeat(50)).append("\n\n");
        sb.append(String.format("Total Mahasiswa    : %d\n", daftar.size()));
        sb.append(String.format("Rata-rata IP Semester  : %.2f\n", rataIPS));
        sb.append(String.format("Rata-rata IP Kumulatif: %.2f\n", rataIPK));
        sb.append("\nDistribusi Status Akademik:\n");
        sb.append(String.format("Cum Laude         : %d (%.1f%%)\n", cumLaude, (cumLaude * 100.0 / daftar.size())));
        sb.append(String.format("Sangat Memuaskan  : %d (%.1f%%)\n", sangatMemuaskan, (sangatMemuaskan * 100.0 / daftar.size())));
        sb.append(String.format("Memuaskan         : %d (%.1f%%)\n", memuaskan, (memuaskan * 100.0 / daftar.size())));
        sb.append(String.format("Cukup             : %d (%.1f%%)\n", cukup, (cukup * 100.0 / daftar.size())));
        
        txtHistory.setText(sb.toString());
    }
    
    private void eksporData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan sebagai CSV");
        fileChooser.setSelectedFile(new File("data_mahasiswa.csv"));
        
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
}