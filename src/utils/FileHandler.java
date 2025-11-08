package utils;

import java.io.*;
import java.util.List;
import model.DataManager;
import model.Mahasiswa;

public class FileHandler {
    private static final String FILE_NAME = "data_mahasiswa.dat";
    
    public static void simpanData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FILE_NAME))) {
            oos.writeObject(DataManager.getInstance().getDaftarMahasiswa());
            System.out.println("Data berhasil disimpan!");
        } catch (IOException e) {
            System.err.println("Error menyimpan data: " + e.getMessage());
            // Buat dialog error jika di dalam context GUI
            showErrorDialog("Error menyimpan data: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void muatData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File data tidak ditemukan, akan dibuat baru.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FILE_NAME))) {
            List<Mahasiswa> data = (List<Mahasiswa>) ois.readObject();
            DataManager manager = DataManager.getInstance();
            for (Mahasiswa mhs : data) {
                manager.tambahMahasiswa(mhs);
            }
            System.out.println("Data berhasil dimuat! Total: " + data.size() + " records");
        } catch (FileNotFoundException e) {
            System.out.println("File data tidak ditemukan, akan dibuat baru.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error memuat data: " + e.getMessage());
            showErrorDialog("Error memuat data: " + e.getMessage());
        }
    }
    
    public static void eksporKeCSV(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Header CSV
            writer.println("NIM,Nama,Jurusan,Fakultas,IP Semester,IP Kumulatif,Riwayat Beasiswa,Email,Telepon,Alamat,Semester,Status,Tanggal Input");
            
            // Data
            for (Mahasiswa mhs : DataManager.getInstance().getDaftarMahasiswa()) {
                writer.printf("%s,\"%s\",\"%s\",\"%s\",%.2f,%.2f,\"%s\",\"%s\",\"%s\",\"%s\",%d,\"%s\",\"%s\"%n",
                        mhs.getNim(), 
                        mhs.getNama().replace("\"", "\"\""), 
                        mhs.getJurusan().replace("\"", "\"\""), 
                        mhs.getFakultas().replace("\"", "\"\""),
                        mhs.getIpSemester(), 
                        mhs.getIpKumulatif(), 
                        mhs.getRiwayatBeasiswa().replace("\"", "\"\""),
                        mhs.getEmail().replace("\"", "\"\""), 
                        mhs.getNoTelepon().replace("\"", "\"\""), 
                        mhs.getAlamat().replace("\"", "\"\""),
                        mhs.getSemester(), 
                        mhs.getStatus(),
                        mhs.getFormattedTanggal());
            }
            System.out.println("Data berhasil diekspor ke " + fileName);
        } catch (IOException e) {
            System.err.println("Error ekspor data: " + e.getMessage());
            showErrorDialog("Error ekspor data: " + e.getMessage());
        }
    }
    
    private static void showErrorDialog(String message) {
        // Method untuk menampilkan error dialog (bisa dipanggil dari mana saja)
        javax.swing.JOptionPane.showMessageDialog(null, 
            message, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    // Method untuk backup dengan timestamp
    public static void backupData() {
        String backupName = "backup_mahasiswa_" + 
            System.currentTimeMillis() + ".dat";
        try {
            File original = new File(FILE_NAME);
            if (original.exists()) {
                java.nio.file.Files.copy(original.toPath(), 
                    new File(backupName).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Backup created: " + backupName);
            }
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
}