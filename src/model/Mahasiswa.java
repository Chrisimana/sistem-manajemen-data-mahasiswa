package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mahasiswa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nama;
    private String nim;
    private String jurusan;
    private String fakultas;
    private double ipSemester;
    private double ipKumulatif;
    private String riwayatBeasiswa;
    private String email;
    private String noTelepon;
    private String alamat;
    private int semester;
    private String status;
    private LocalDateTime tanggalInput;
    
    // Constructor
    public Mahasiswa(String nama, String nim, String jurusan, String fakultas, 
                    double ipSemester, double ipKumulatif, String riwayatBeasiswa,
                    String email, String noTelepon, String alamat, int semester) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
        this.fakultas = fakultas;
        this.ipSemester = ipSemester;
        this.ipKumulatif = ipKumulatif;
        this.riwayatBeasiswa = riwayatBeasiswa;
        this.email = email;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
        this.semester = semester;
        this.status = tentukanStatus();
        this.tanggalInput = LocalDateTime.now();
    }
    
    private String tentukanStatus() {
        if (ipKumulatif >= 3.5) {
            return "Cum Laude";
        } else if (ipKumulatif >= 3.0) {
            return "Sangat Memuaskan";
        } else if (ipKumulatif >= 2.5) {
            return "Memuaskan";
        } else {
            return "Cukup";
        }
    }
    
    // Getters
    public String getNama() { return nama; }
    public String getNim() { return nim; }
    public String getJurusan() { return jurusan; }
    public String getFakultas() { return fakultas; }
    public double getIpSemester() { return ipSemester; }
    public double getIpKumulatif() { return ipKumulatif; }
    public String getRiwayatBeasiswa() { return riwayatBeasiswa; }
    public String getEmail() { return email; }
    public String getNoTelepon() { return noTelepon; }
    public String getAlamat() { return alamat; }
    public int getSemester() { return semester; }
    public String getStatus() { return status; }
    public LocalDateTime getTanggalInput() { return tanggalInput; }
    
    public String getFormattedTanggal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return tanggalInput.format(formatter);
    }
    
    public void tampilkanData() {
        System.out.println("Identitas Mahasiswa");
        System.out.println("Nama     : " + this.nama);
        System.out.println("NIM      : " + this.nim);
        System.out.println("Jurusan  : " + this.jurusan);
        System.out.println("Fakultas : " + this.fakultas);
    }
    
    public void tampilkanDataPrestasi() {
        System.out.println("\nPrestasi Akademik");
        System.out.println("Nama             : " + this.nama);
        System.out.println("IP Semester      : " + this.ipSemester);
        System.out.println("IP Kumulatif     : " + this.ipKumulatif);
        System.out.println("Riwayat Beasiswa : " + this.riwayatBeasiswa);
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nim, nama, jurusan);
    }
}