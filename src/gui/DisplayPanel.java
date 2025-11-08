package gui;

import model.Mahasiswa;
import model.DataManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DisplayPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtCari;
    private JButton btnCari, btnRefresh, btnHapus;
    private JComboBox<String> comboFilter;
    
    public DisplayPanel() {
        initComponents();
        setupLayout();
        setupListeners();
        refreshTable();
    }
    
    private void initComponents() {
        String[] columnNames = {
            "NIM", "Nama", "Jurusan", "Fakultas", "IP Semester", 
            "IP Kumulatif", "Semester", "Status", "Tanggal Input"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        txtCari = new JTextField(20);
        btnCari = new JButton("Cari");
        btnRefresh = new JButton("Refresh");
        btnHapus = new JButton("Hapus Data Terpilih");
        
        String[] filterOptions = {"Semua", "Cum Laude", "Sangat Memuaskan", "Memuaskan", "Cukup"};
        comboFilter = new JComboBox<>(filterOptions);
        
        // Styling
        btnCari.setBackground(new Color(0, 123, 255));
        btnCari.setForeground(Color.WHITE);
        btnRefresh.setBackground(new Color(108, 117, 125));
        btnRefresh.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(220, 53, 69));
        btnHapus.setForeground(Color.WHITE);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Data Mahasiswa"));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Cari:"));
        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        searchPanel.add(new JLabel("Filter Status:"));
        searchPanel.add(comboFilter);
        searchPanel.add(btnRefresh);
        searchPanel.add(btnHapus);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table with scroll
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        btnRefresh.addActionListener(e -> refreshTable());
        
        btnCari.addActionListener(e -> cariData());
        
        btnHapus.addActionListener(e -> hapusData());
        
        comboFilter.addActionListener(e -> filterData());
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Mahasiswa> daftar = DataManager.getInstance().getDaftarMahasiswa();
        
        for (Mahasiswa mhs : daftar) {
            tableModel.addRow(new Object[]{
                mhs.getNim(),
                mhs.getNama(),
                mhs.getJurusan(),
                mhs.getFakultas(),
                String.format("%.2f", mhs.getIpSemester()),
                String.format("%.2f", mhs.getIpKumulatif()),
                mhs.getSemester(),
                mhs.getStatus(),
                mhs.getFormattedTanggal()
            });
        }
        
        JOptionPane.showMessageDialog(this, 
            "Data diperbarui! Total: " + daftar.size() + " mahasiswa", 
            "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cariData() {
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            refreshTable();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Mahasiswa> hasil = DataManager.getInstance().cariMahasiswaByNama(keyword);
        
        for (Mahasiswa mhs : hasil) {
            tableModel.addRow(new Object[]{
                mhs.getNim(),
                mhs.getNama(),
                mhs.getJurusan(),
                mhs.getFakultas(),
                String.format("%.2f", mhs.getIpSemester()),
                String.format("%.2f", mhs.getIpKumulatif()),
                mhs.getSemester(),
                mhs.getStatus(),
                mhs.getFormattedTanggal()
            });
        }
    }
    
    private void filterData() {
        String filter = (String) comboFilter.getSelectedItem();
        if (filter.equals("Semua")) {
            refreshTable();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Mahasiswa> semua = DataManager.getInstance().getDaftarMahasiswa();
        
        for (Mahasiswa mhs : semua) {
            if (mhs.getStatus().equals(filter)) {
                tableModel.addRow(new Object[]{
                    mhs.getNim(),
                    mhs.getNama(),
                    mhs.getJurusan(),
                    mhs.getFakultas(),
                    String.format("%.2f", mhs.getIpSemester()),
                    String.format("%.2f", mhs.getIpKumulatif()),
                    mhs.getSemester(),
                    mhs.getStatus(),
                    mhs.getFormattedTanggal()
                });
            }
        }
    }
    
    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nim = (String) tableModel.getValueAt(selectedRow, 0);
        String nama = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Hapus data " + nama + " (NIM: " + nim + ")?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (DataManager.getInstance().hapusMahasiswa(nim)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            }
        }
    }
}