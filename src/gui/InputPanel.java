package gui;

import model.Mahasiswa;
import model.DataManager;
import utils.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {
    private JTextField txtNama, txtNIM, txtJurusan, txtFakultas, txtEmail, txtTelepon, txtAlamat;
    private JTextField txtIpSemester, txtIpKumulatif, txtRiwayatBeasiswa, txtSemester;
    private JButton btnSimpan, btnReset;
    
    public InputPanel() {
        initComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initComponents() {
        txtNama = new JTextField(20);
        txtNIM = new JTextField(20);
        txtJurusan = new JTextField(20);
        txtFakultas = new JTextField(20);
        txtEmail = new JTextField(20);
        txtTelepon = new JTextField(20);
        txtAlamat = new JTextField(20);
        txtIpSemester = new JTextField(10);
        txtIpKumulatif = new JTextField(10);
        txtRiwayatBeasiswa = new JTextField(20);
        txtSemester = new JTextField(5);
        
        btnSimpan = new JButton("Simpan Data");
        btnReset = new JButton("Reset Form");
        
        // Styling buttons
        btnSimpan.setBackground(new Color(34, 139, 34));
        btnSimpan.setForeground(Color.WHITE);
        btnReset.setBackground(new Color(220, 53, 69));
        btnReset.setForeground(Color.WHITE);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Input Data Mahasiswa"));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNama, gbc);
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNIM, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Jurusan:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtJurusan, gbc);
        
        // Row 3
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Fakultas:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtFakultas, gbc);
        
        // Row 4
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);
        
        // Row 5
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Telepon:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTelepon, gbc);
        
        // Row 6
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtAlamat, gbc);
        
        // Row 7
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("IP Semester:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtIpSemester, gbc);
        
        // Row 8
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(new JLabel("IP Kumulatif:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtIpKumulatif, gbc);
        
        // Row 9
        gbc.gridx = 0; gbc.gridy = 9;
        formPanel.add(new JLabel("Riwayat Beasiswa:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtRiwayatBeasiswa, gbc);
        
        // Row 10
        gbc.gridx = 0; gbc.gridy = 10;
        formPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSemester, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnReset);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanData();
            }
        });
        
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
    }
    
    private void simpanData() {
        try {
            // Validasi input
            if (txtNama.getText().trim().isEmpty() || txtNIM.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan NIM harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cek duplikasi NIM
            if (DataManager.getInstance().cariMahasiswaByNIM(txtNIM.getText().trim()) != null) {
                JOptionPane.showMessageDialog(this, "NIM sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Mahasiswa mhs = new Mahasiswa(
                txtNama.getText().trim(),
                txtNIM.getText().trim(),
                txtJurusan.getText().trim(),
                txtFakultas.getText().trim(),
                Double.parseDouble(txtIpSemester.getText().trim()),
                Double.parseDouble(txtIpKumulatif.getText().trim()),
                txtRiwayatBeasiswa.getText().trim(),
                txtEmail.getText().trim(),
                txtTelepon.getText().trim(),
                txtAlamat.getText().trim(),
                Integer.parseInt(txtSemester.getText().trim())
            );
            
            DataManager.getInstance().tambahMahasiswa(mhs);
            FileHandler.simpanData();
            
            JOptionPane.showMessageDialog(this, "Data mahasiswa berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            resetForm();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetForm() {
        txtNama.setText("");
        txtNIM.setText("");
        txtJurusan.setText("");
        txtFakultas.setText("");
        txtEmail.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
        txtIpSemester.setText("");
        txtIpKumulatif.setText("");
        txtRiwayatBeasiswa.setText("");
        txtSemester.setText("");
        txtNama.requestFocus();
    }
}