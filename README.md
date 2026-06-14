# StayEase PBO Web

StayEase adalah aplikasi web sederhana untuk administrasi dan reservasi hotel. Project ini dibuat untuk tugas besar Pemrograman Berorientasi Objek (PBO).

Backend menggunakan Java Spring Boot dan menerapkan konsep PBO seperti encapsulation, inheritance, polymorphism, abstract class, interface, association, dan dependency.

## Fitur

- Login staff berdasarkan role
- Manajemen kamar
- Data tamu
- Reservasi
- Check-in
- Check-out
- Laporan operasional harian
- Manajemen staff dan hak akses

## Perbedaan Hak Akses Role

### Admin
Admin memiliki akses paling lengkap:

- Dashboard
- Kamar: tambah kamar dan ubah status kamar
- Data tamu
- Reservasi
- Check-in
- Check-out
- Laporan harian
- Manajemen staff

### Resepsionis
Resepsionis fokus pada operasional front office:

- Dashboard
- Lihat data kamar
- Data tamu
- Reservasi
- Check-in
- Check-out

Resepsionis tidak bisa mengakses laporan harian dan manajemen staff.

### Housekeeping
Housekeeping fokus pada kondisi kamar:

- Dashboard
- Lihat data kamar
- Ubah status kamar, misalnya TERSEDIA, TERISI, DIBERSIHKAN, dan MAINTENANCE

Housekeeping tidak bisa mengakses data tamu, reservasi, check-in, check-out, laporan, atau manajemen staff.

## Akun Demo

```text
Admin
Username: admin
Password: admin123

Resepsionis
Username: resepsionis
Password: resepsionis123

Housekeeping
Username: housekeeping
Password: housekeeping123
```

## Teknologi

- Java
- Spring Boot
- Thymeleaf
- HTML
- CSS
- Maven
- ArrayList / in-memory data

Catatan: project ini belum menggunakan database MySQL. Data masih disimpan sementara di memori menggunakan ArrayList, sehingga data reset ketika aplikasi dihentikan.

## Cara Menjalankan

Pastikan Java dan Maven sudah terinstall.

Cek Java:

```bash
java -version
```

Cek Maven:

```bash
mvn -v
```

Masuk ke folder project:

```bash
cd stayease-pbo-web
```

Jalankan aplikasi:

```bash
mvn spring-boot:run
```

Jika berhasil, terminal akan menampilkan:

```text
Tomcat started on port 8080
Started StayEaseApplication
```

Buka browser:

```text
http://localhost:8080
```

## Alur Demo yang Disarankan

Gunakan akun Admin atau Resepsionis untuk alur utama:

```text
Login → Kamar → Data Tamu → Reservasi → Check-in → Check-out → Laporan Harian
```

Untuk menunjukkan perbedaan role:

1. Login sebagai Admin, tunjukkan semua menu tampil.
2. Login sebagai Resepsionis, tunjukkan menu laporan dan manajemen staff tidak tampil.
3. Login sebagai Housekeeping, tunjukkan hanya menu kamar yang tampil dan hanya bisa update status kamar.

## Konsep PBO yang Digunakan

### Encapsulation
Atribut class dibuat private/protected dan diakses melalui getter/setter.

### Inheritance
`KamarStandard` dan `KamarVIP` mewarisi abstract class `Kamar`. `Admin`, `Resepsionis`, dan `Housekeeping` mewarisi class `Staff`.

### Polymorphism
Method `hitungTotalHarga()` memiliki implementasi berbeda pada `KamarStandard` dan `KamarVIP`.

### Abstract Class
Class `Kamar` dan `User` dibuat sebagai abstract class.

### Interface
Interface `ManageStaff` digunakan pada fitur manajemen staff.

### Association
Class `Reservasi` berhubungan dengan `Tamu` dan `Kamar`. Class `CheckIn` dan `CheckOut` berhubungan dengan `Reservasi` dan `Staff`.
