# 4.4 Pengujian Sistem

Pengujian sistem dilakukan untuk memastikan bahwa setiap fitur pada aplikasi StayEase berjalan sesuai dengan perancangan. Metode pengujian yang digunakan adalah black-box testing, yaitu menguji fungsionalitas sistem berdasarkan input dan output yang diharapkan tanpa melihat struktur internal kode. Berikut adalah hasil pengujian pada masing-masing modul.

## 4.4.1 Pengujian Login

Pengujian login bertujuan untuk memverifikasi bahwa proses autentikasi berjalan dengan benar, termasuk validasi username/password dan pembatasan akses berdasarkan role.

| No | Skenario Pengujian | Input | Output yang Diharapkan | Status |
|----|--------------------|-------|------------------------|--------|
| 1 | Login sebagai Admin dengan kredensial benar | Username: admin, Password: admin123 | Berhasil masuk ke dashboard, semua menu tampil | ☐ |
| 2 | Login sebagai Resepsionis dengan kredensial benar | Username: resepsionis, Password: resepsionis123 | Berhasil masuk ke dashboard, menu Laporan dan Manajemen Staff tidak tampil | ☐ |
| 3 | Login sebagai Housekeeping dengan kredensial benar | Username: housekeeping, Password: housekeeping123 | Berhasil masuk ke dashboard, hanya menu Kamar yang tampil | ☐ |
| 4 | Login dengan password salah | Username: admin, Password: salah123 | Menampilkan pesan error "Username/email atau password salah." | ☐ |
| 5 | Login dengan username yang tidak terdaftar | Username: tamu, Password: tamu123 | Menampilkan pesan error "Username/email atau password salah." | ☐ |
| 6 | Login menggunakan email | Username: admin@stayease.com, Password: admin123 | Berhasil masuk ke dashboard sebagai Admin | ☐ |
| 7 | Logout | Klik tombol Logout | Kembali ke halaman login, session berakhir | ☐ |
| 8 | Akses halaman tanpa login | Buka /dashboard langsung di browser | Redirect ke halaman login | ☐ |

## 4.4.2 Pengujian Manajemen Kamar

Pengujian manajemen kamar bertujuan untuk memverifikasi fitur tambah kamar dan perubahan status kamar, serta memastikan pembatasan akses sesuai role.

| No | Skenario Pengujian | Input | Output yang Diharapkan | Status |
|----|--------------------|-------|------------------------|--------|
| 1 | Tambah kamar Standard (login Admin) | Nomor: 103, Tipe: STANDARD, Harga: 400000 | Kamar berhasil ditambahkan, muncul di daftar kamar | ☐ |
| 2 | Tambah kamar VIP (login Admin) | Nomor: 203, Tipe: VIP, Harga: 900000, Biaya tambahan: 250000 | Kamar VIP berhasil ditambahkan dengan biaya tambahan | ☐ |
| 3 | Update status kamar (login Admin) | Kamar 101, Status: MAINTENANCE | Status kamar 101 berubah menjadi MAINTENANCE | ☐ |
| 4 | Update status kamar (login Housekeeping) | Kamar 101, Status: TERSEDIA | Status kamar 101 berubah menjadi TERSEDIA | ☐ |
| 5 | Housekeeping tidak bisa tambah kamar | Login Housekeeping, coba akses form tambah kamar | Form tambah kamar tidak tampil | ☐ |
| 6 | Resepsionis hanya bisa lihat kamar | Login Resepsionis, buka halaman kamar | Daftar kamar tampil tanpa form tambah dan tanpa tombol update status | ☐ |
| 7 | Housekeeping ubah status DIBERSIHKAN ke TERSEDIA | Kamar 101, Status: DIBERSIHKAN → TERSEDIA | Status berhasil diubah | ☐ |

## 4.4.3 Pengujian Reservasi

Pengujian reservasi bertujuan untuk memverifikasi proses pembuatan dan pembatalan reservasi, termasuk validasi ketersediaan kamar.

| No | Skenario Pengujian | Input | Output yang Diharapkan | Status |
|----|--------------------|-------|------------------------|--------|
| 1 | Tambah data tamu baru | Nama: Budi Santoso, No Telepon: 081300000001 | Tamu berhasil ditambahkan dengan ID T003 | ☐ |
| 2 | Buat reservasi dengan data valid | Tamu: T001, Kamar: 101, Check-in: 2026-06-15, Check-out: 2026-06-17 | Reservasi berhasil dibuat, durasi 2 malam, total Rp 700.000 | ☐ |
| 3 | Buat reservasi kamar VIP | Tamu: T002, Kamar: 201, Check-in: 2026-06-15, Check-out: 2026-06-18 | Reservasi berhasil, total = (750000 + 150000) × 3 = Rp 2.700.000 | ☐ |
| 4 | Kamar yang sudah dibooking tidak muncul di dropdown | Setelah kamar 101 di-booking | Kamar 101 tidak tampil di pilihan kamar pada form reservasi | ☐ |
| 5 | Batalkan reservasi | Klik tombol Batal pada reservasi R001 | Status berubah menjadi DIBATALKAN, kamar kembali TERSEDIA | ☐ |
| 6 | Kamar yang dibatalkan muncul kembali di dropdown | Setelah reservasi dibatalkan | Kamar tersebut tersedia kembali untuk reservasi baru | ☐ |
| 7 | Housekeeping tidak bisa akses reservasi | Login Housekeeping, akses /reservasi | Redirect ke dashboard dengan pesan akses ditolak | ☐ |

## 4.4.4 Pengujian Check-In

Pengujian check-in bertujuan untuk memverifikasi proses check-in tamu, termasuk perubahan status reservasi dan status kamar.

| No | Skenario Pengujian | Input | Output yang Diharapkan | Status |
|----|--------------------|-------|------------------------|--------|
| 1 | Proses check-in reservasi valid | Pilih reservasi dengan status DIBOOKING | Check-in berhasil, status reservasi berubah ke CHECK_IN, status kamar berubah ke TERISI | ☐ |
| 2 | Dropdown hanya menampilkan reservasi DIBOOKING | Buka halaman check-in | Hanya reservasi dengan status DIBOOKING yang muncul di dropdown | ☐ |
| 3 | Riwayat check-in tampil dengan benar | Setelah proses check-in | Data check-in muncul di tabel riwayat (ID, reservasi, tamu, tanggal, petugas) | ☐ |
| 4 | Check-in tanpa memilih reservasi | Klik tombol Proses Check-in tanpa memilih | Muncul alert "Mohon pilih data reservasi terlebih dahulu" | ☐ |
| 5 | Nama petugas yang melakukan check-in tercatat | Login Admin, proses check-in | Kolom petugas menampilkan nama staff yang login | ☐ |
| 6 | Housekeeping tidak bisa akses check-in | Login Housekeeping, akses /checkin | Redirect ke dashboard dengan pesan akses ditolak | ☐ |

## 4.4.5 Pengujian Check-Out

Pengujian check-out bertujuan untuk memverifikasi proses check-out tamu, perhitungan tagihan, dan perubahan status kamar setelah tamu keluar.

| No | Skenario Pengujian | Input | Output yang Diharapkan | Status |
|----|--------------------|-------|------------------------|--------|
| 1 | Proses check-out reservasi yang sudah check-in | Pilih reservasi dengan status CHECK_IN | Check-out berhasil, status reservasi berubah ke CHECK_OUT, status kamar berubah ke DIBERSIHKAN | ☐ |
| 2 | Dropdown hanya menampilkan reservasi CHECK_IN | Buka halaman check-out | Hanya reservasi dengan status CHECK_IN yang muncul di dropdown | ☐ |
| 3 | Total tagihan dihitung dengan benar (Standard) | Kamar Standard Rp 350.000, durasi 2 malam | Total tagihan = Rp 700.000 | ☐ |
| 4 | Total tagihan dihitung dengan benar (VIP) | Kamar VIP Rp 750.000 + biaya tambahan Rp 150.000, durasi 3 malam | Total tagihan = Rp 2.700.000 | ☐ |
| 5 | Riwayat check-out tampil dengan benar | Setelah proses check-out | Data check-out muncul di tabel riwayat (ID, reservasi, tamu, tanggal, tagihan, petugas) | ☐ |
| 6 | Check-out tanpa memilih reservasi | Klik tombol Proses Check-out tanpa memilih | Muncul alert validasi | ☐ |
| 7 | Housekeeping tidak bisa akses check-out | Login Housekeeping, akses /checkout | Redirect ke dashboard dengan pesan akses ditolak | ☐ |

## 4.4.6 Pengujian Laporan

Pengujian laporan bertujuan untuk memverifikasi fitur generate laporan harian, termasuk keakuratan data statistik dan perhitungan pendapatan.

| No | Skenario Pengujian | Input | Output yang Diharapkan | Status |
|----|--------------------|-------|------------------------|--------|
| 1 | Generate laporan hari ini | Tanggal: (hari ini) | Laporan tampil dengan jumlah kamar tersedia, terisi, check-in, check-out, dan pendapatan | ☐ |
| 2 | Jumlah kamar tersedia sesuai data aktual | Generate laporan setelah ada reservasi & check-in | Jumlah kamar tersedia sesuai dengan kamar berstatus TERSEDIA | ☐ |
| 3 | Jumlah kamar terisi sesuai data aktual | Generate laporan setelah ada check-in | Jumlah kamar terisi sesuai dengan kamar berstatus TERISI | ☐ |
| 4 | Jumlah check-in hari ini benar | Generate laporan setelah proses check-in hari ini | Jumlah check-in sesuai dengan check-in yang dilakukan pada tanggal tersebut | ☐ |
| 5 | Jumlah check-out hari ini benar | Generate laporan setelah proses check-out hari ini | Jumlah check-out sesuai dengan check-out yang dilakukan pada tanggal tersebut | ☐ |
| 6 | Total pendapatan dihitung dari check-out | Generate laporan setelah ada check-out | Total pendapatan = jumlah tagihan semua check-out pada tanggal tersebut | ☐ |
| 7 | Generate laporan tanggal lain | Tanggal: 2026-06-10 | Laporan tampil untuk tanggal tersebut (bisa 0 jika tidak ada aktivitas) | ☐ |
| 8 | Resepsionis tidak bisa akses laporan | Login Resepsionis, akses /laporan | Redirect ke dashboard dengan pesan akses ditolak | ☐ |
| 9 | Housekeeping tidak bisa akses laporan | Login Housekeeping, akses /laporan | Redirect ke dashboard dengan pesan akses ditolak | ☐ |

---

## Ringkasan Hasil Pengujian

| Modul | Jumlah Test Case | Berhasil | Gagal |
|-------|:----------------:|:--------:|:-----:|
| Login | 8 | ☐ | ☐ |
| Manajemen Kamar | 7 | ☐ | ☐ |
| Reservasi | 7 | ☐ | ☐ |
| Check-In | 6 | ☐ | ☐ |
| Check-Out | 7 | ☐ | ☐ |
| Laporan | 9 | ☐ | ☐ |
| **Total** | **44** | ☐ | ☐ |

> **Catatan:** Kolom status diisi ✓ (berhasil) atau ✗ (gagal) setelah pengujian dilakukan secara manual pada aplikasi yang berjalan.
