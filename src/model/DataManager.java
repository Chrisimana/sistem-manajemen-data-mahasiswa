package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {
    private List<Mahasiswa> daftarMahasiswa;
    private static DataManager instance;
    
    private DataManager() {
        this.daftarMahasiswa = new ArrayList<>();
    }
    
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    public void tambahMahasiswa(Mahasiswa mahasiswa) {
        daftarMahasiswa.add(mahasiswa);
    }
    
    public List<Mahasiswa> getDaftarMahasiswa() {
        return new ArrayList<>(daftarMahasiswa);
    }
    
    public List<Mahasiswa> getDaftarMahasiswaSorted() {
        return daftarMahasiswa.stream()
                .sorted(Comparator.comparing(Mahasiswa::getNama))
                .collect(Collectors.toList());
    }
    
    public Mahasiswa cariMahasiswaByNIM(String nim) {
        return daftarMahasiswa.stream()
                .filter(m -> m.getNim().equals(nim))
                .findFirst()
                .orElse(null);
    }
    
    public List<Mahasiswa> cariMahasiswaByNama(String nama) {
        return daftarMahasiswa.stream()
                .filter(m -> m.getNama().toLowerCase().contains(nama.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Mahasiswa> filterByStatus(String status) {
        return daftarMahasiswa.stream()
                .filter(m -> m.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    
    public List<Mahasiswa> filterByJurusan(String jurusan) {
        return daftarMahasiswa.stream()
                .filter(m -> m.getJurusan().toLowerCase().contains(jurusan.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public boolean hapusMahasiswa(String nim) {
        return daftarMahasiswa.removeIf(m -> m.getNim().equals(nim));
    }
    
    public int getJumlahMahasiswa() {
        return daftarMahasiswa.size();
    }
    
    public double getRataRataIPK() {
        if (daftarMahasiswa.isEmpty()) return 0.0;
        return daftarMahasiswa.stream()
                .mapToDouble(Mahasiswa::getIpKumulatif)
                .average()
                .orElse(0.0);
    }
    
    public void clearAllData() {
        daftarMahasiswa.clear();
    }
}