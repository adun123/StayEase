# Laporan Tugas Besar PBO - StayEase

## 3.3 Perancangan Basis Data

Pada tahap pengembangan saat ini, aplikasi StayEase belum menggunakan database relasional seperti MySQL. Seluruh data disimpan secara sementara di memori menggunakan struktur data ArrayList yang dikelola oleh class `DataStore`. Pendekatan ini dipilih untuk menyederhanakan proses pengembangan dan fokus pada penerapan konsep Pemrograman Berorientasi Objek. Berikut adalah perancangan struktur data yang digunakan pada masing-masing entitas dalam sistem.

### 3.3.1 Perancangan Struktur Data Kamar

Struktur data kamar dirancang untuk menyimpan informasi terkait kamar hotel yang tersedia dalam sistem. Setiap kamar memiliki nomor unik sebagai identitas, harga per malam, status ketersediaan, serta tipe kamar yang membedakan antara kamar standar dan kamar VIP. Tabel berikut menjelaskan atribut yang digunakan pada struktur data kamar.

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| nomorKamar | String | Nomor unik kamar (PK), contoh: "101", "201" |
| hargaKamar | double | Harga kamar per malam dalam Rupiah |
| statusKamar | StatusKamar (enum) | Status kamar: TERSEDIA, TERISI, DIBERSIHKAN, MAINTENANCE |
| tipeKamar | String | Tipe kamar: "STANDARD" atau "VIP" |
| biayaTambahan | double | Biaya tambahan khusus kamar VIP |

Berdasarkan tabel di atas, setiap kamar memiliki status yang direpresentasikan menggunakan enum `StatusKamar`. Status ini digunakan untuk mengontrol ketersediaan kamar dalam proses reservasi dan operasional hotel. Berikut adalah penjelasan masing-masing status kamar.

Enum `StatusKamar`:
- `TERSEDIA` — Kamar siap ditempati
- `TERISI` — Kamar sedang ditempati tamu
- `DIBERSIHKAN` — Kamar sedang dalam proses pembersihan
- `MAINTENANCE` — Kamar sedang dalam perbaikan

Dengan adanya enum status ini, sistem dapat memvalidasi apakah suatu kamar dapat dipesan atau tidak, serta memudahkan petugas housekeeping dalam memperbarui kondisi kamar.

### 3.3.2 Perancangan Struktur Data Tamu

Struktur data tamu dirancang untuk menyimpan informasi identitas tamu yang melakukan reservasi di hotel. Data tamu menjadi referensi utama saat proses pembuatan reservasi. Tabel berikut menjelaskan atribut yang digunakan pada struktur data tamu.

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| idTamu | String | ID unik tamu (PK), format: "T001", "T002", dst |
| nama | String | Nama lengkap tamu |
| noTelepon | String | Nomor telepon tamu |

Berdasarkan tabel di atas, setiap tamu diidentifikasi dengan ID unik yang di-generate secara otomatis oleh sistem. Data tamu ini kemudian digunakan sebagai relasi dalam pembuatan reservasi, sehingga sistem dapat melacak reservasi milik tamu tertentu.

### 3.3.3 Perancangan Struktur Data Reservasi

Struktur data reservasi merupakan entitas inti dalam sistem yang menghubungkan antara data tamu dengan data kamar. Reservasi mencatat informasi lengkap mengenai pemesanan kamar, termasuk durasi menginap dan total biaya yang dihitung secara otomatis oleh sistem. Tabel berikut menjelaskan atribut yang digunakan pada struktur data reservasi.

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| idReservasi | String | ID unik reservasi (PK), format: "R001", "R002", dst |
| tamu | Tamu | Referensi ke objek Tamu (FK) |
| kamar | Kamar | Referensi ke objek Kamar (FK) |
| tanggalCheckIn | LocalDate | Tanggal rencana check-in |
| tanggalCheckOut | LocalDate | Tanggal rencana check-out |
| durasiMenginap | int | Jumlah malam menginap (dihitung otomatis) |
| totalBiaya | double | Total biaya reservasi (dihitung otomatis) |
| statusReservasi | StatusReservasi (enum) | Status: DIBOOKING, CHECK_IN, CHECK_OUT, DIBATALKAN |

Berdasarkan tabel di atas, reservasi memiliki siklus hidup yang direpresentasikan melalui enum `StatusReservasi`. Status ini berubah seiring dengan proses operasional hotel, mulai dari pemesanan hingga tamu meninggalkan hotel. Berikut adalah penjelasan masing-masing status reservasi.

Enum `StatusReservasi`:
- `DIBOOKING` — Reservasi baru dibuat dan menunggu proses check-in
- `CHECK_IN` — Tamu sudah melakukan check-in dan menempati kamar
- `CHECK_OUT` — Tamu sudah melakukan check-out dan meninggalkan hotel
- `DIBATALKAN` — Reservasi dibatalkan oleh petugas

Alur perubahan status ini memastikan bahwa setiap reservasi hanya dapat di-check-in jika statusnya masih DIBOOKING, dan hanya dapat di-check-out jika statusnya sudah CHECK_IN. Hal ini menjaga konsistensi data dalam sistem.

### 3.3.4 Perancangan Struktur Data User

Struktur data user dirancang untuk menyimpan informasi staff hotel yang memiliki akses ke dalam sistem. Setiap user memiliki role yang menentukan hak akses terhadap fitur-fitur tertentu. Tabel berikut menjelaskan atribut yang digunakan pada struktur data user.

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| idUser | String | ID unik user/staff (PK), format: "S001", "S002", dst |
| nama | String | Nama lengkap staff |
| username | String | Username untuk login |
| email | String | Email staff |
| password | String | Password untuk login |
| jabatan | String | Jabatan: "Admin", "Resepsionis", "Housekeeping" |
| role | Role | Referensi ke objek Role yang berisi daftar permission |

Selain atribut utama user, sistem juga menggunakan struktur data pendukung berupa Role dan Permission untuk mengatur hak akses. Tabel berikut menjelaskan struktur data role.

| Atribut | Tipe Data | Keterangan |
|---------|-----------|------------|
| namaRole | String | Nama role: "ADMIN", "RESEPSIONIS", "HOUSEKEEPING" |
| daftarPermission | List\<Permission\> | Daftar hak akses yang dimiliki role |

Setiap role memiliki kumpulan permission yang berbeda sesuai dengan tanggung jawab jabatan masing-masing. Tabel berikut menunjukkan matriks permission untuk setiap role dalam sistem.

| Permission | Admin | Resepsionis | Housekeeping |
|------------|:-----:|:-----------:|:------------:|
| ROOM_VIEW | ✓ | ✓ | ✓ |
| ROOM_MANAGE | ✓ | - | - |
| HOUSEKEEPING_UPDATE | ✓ | - | ✓ |
| GUEST | ✓ | ✓ | - |
| RESERVATION | ✓ | ✓ | - |
| CHECKIN | ✓ | ✓ | - |
| CHECKOUT | ✓ | ✓ | - |
| REPORT | ✓ | - | - |
| STAFF | ✓ | - | - |

Berdasarkan matriks di atas, Admin memiliki akses penuh terhadap seluruh fitur sistem. Resepsionis hanya dapat mengakses fitur operasional front office seperti data tamu, reservasi, check-in, dan check-out. Sementara Housekeeping hanya dapat melihat dan memperbarui status kamar. Pembagian hak akses ini menerapkan prinsip least privilege untuk menjaga keamanan dan integritas data sistem.

> **Catatan:** Jika nanti aplikasi dikembangkan lebih lanjut menggunakan database MySQL, bagian perancangan struktur data ini dapat diganti menjadi Entity Relationship Diagram (ERD) dengan tabel-tabel relasional yang sesuai.

---

## 3.4 Perancangan Class Diagram

Perancangan class diagram merupakan bagian penting dalam pengembangan sistem berorientasi objek. Class diagram menggambarkan struktur statis dari sistem, meliputi class-class yang digunakan beserta atribut, method, dan relasi antar class. Pada aplikasi StayEase, class diagram dirancang dengan menerapkan konsep-konsep PBO seperti abstract class, inheritance, polymorphism, encapsulation, interface, dan association. Berikut adalah perancangan class diagram untuk setiap class utama dalam sistem.

### 3.4.1 Class Kamar (Abstract)

Class `Kamar` merupakan abstract class yang menjadi parent class bagi seluruh jenis kamar dalam sistem. Class ini mendefinisikan atribut dan method dasar yang dimiliki oleh semua kamar, serta mendeklarasikan method abstract yang harus diimplementasikan oleh subclass-nya. Penerapan abstract class di sini memastikan bahwa setiap jenis kamar memiliki struktur yang konsisten namun tetap fleksibel dalam implementasi perhitungan harga. Berikut adalah diagram class Kamar.

```
┌─────────────────────────────────────────────┐
│            <<abstract>> Kamar               │
├─────────────────────────────────────────────┤
│ - nomorKamar: String                        │
│ - hargaKamar: double                        │
│ - statusKamar: StatusKamar                  │
├─────────────────────────────────────────────┤
│ + Kamar(nomorKamar, hargaKamar, statusKamar)│
│ + getNomorKamar(): String                   │
│ + getHargaKamar(): double                   │
│ + getStatusKamar(): StatusKamar             │
│ + setHargaKamar(double): void               │
│ + setStatusKamar(StatusKamar): void         │
│ + hitungTotalHarga(int): double {abstract}  │
│ + getTipeKamar(): String {abstract}         │
└─────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class Kamar menerapkan konsep encapsulation dengan menjadikan seluruh atribut sebagai private dan menyediakan getter/setter untuk mengaksesnya. Method `hitungTotalHarga()` dan `getTipeKamar()` dideklarasikan sebagai abstract sehingga setiap subclass wajib memberikan implementasi sendiri sesuai dengan karakteristik tipe kamar masing-masing.

### 3.4.2 Class KamarStandard

Class `KamarStandard` merupakan subclass dari `Kamar` yang merepresentasikan kamar bertipe standar. Class ini mengimplementasikan method abstract dari parent class dengan logika perhitungan harga sederhana yaitu harga per malam dikalikan durasi menginap tanpa biaya tambahan. Berikut adalah diagram class KamarStandard.

```
┌───────────────────────────────────────────────────────┐
│                   KamarStandard                       │
│               (extends Kamar)                         │
├───────────────────────────────────────────────────────┤
│ (tidak ada atribut tambahan)                          │
├───────────────────────────────────────────────────────┤
│ + KamarStandard(nomorKamar, hargaKamar, statusKamar)  │
│ + hitungTotalHarga(int): double                       │
│   → return hargaKamar * durasiMenginap                │
│ + getTipeKamar(): String                              │
│   → return "STANDARD"                                 │
└───────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class KamarStandard tidak memiliki atribut tambahan dan langsung mewarisi seluruh atribut dari class Kamar. Perhitungan total harga pada kamar standar hanya mengalikan harga kamar per malam dengan durasi menginap, sehingga menghasilkan biaya yang lebih terjangkau dibandingkan kamar VIP.

### 3.4.3 Class KamarVIP

Class `KamarVIP` merupakan subclass dari `Kamar` yang merepresentasikan kamar bertipe VIP. Berbeda dengan kamar standar, kamar VIP memiliki atribut tambahan berupa biaya tambahan (surcharge) yang ditambahkan ke harga dasar kamar sebelum dikalikan durasi menginap. Hal ini menunjukkan penerapan polymorphism dimana method `hitungTotalHarga()` memiliki implementasi yang berbeda pada setiap subclass. Berikut adalah diagram class KamarVIP.

```
┌─────────────────────────────────────────────────────────────────┐
│                         KamarVIP                                │
│                     (extends Kamar)                              │
├─────────────────────────────────────────────────────────────────┤
│ - biayaTambahan: double                                         │
├─────────────────────────────────────────────────────────────────┤
│ + KamarVIP(nomorKamar, hargaKamar, statusKamar, biayaTambahan)  │
│ + getBiayaTambahan(): double                                    │
│ + setBiayaTambahan(double): void                                │
│ + hitungTotalHarga(int): double                                 │
│   → return (hargaKamar + biayaTambahan) * durasiMenginap        │
│ + getTipeKamar(): String                                        │
│   → return "VIP"                                                │
└─────────────────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class KamarVIP memiliki atribut tambahan `biayaTambahan` yang bersifat private. Rumus perhitungan total harga pada kamar VIP adalah (hargaKamar + biayaTambahan) × durasiMenginap. Perbedaan implementasi method `hitungTotalHarga()` antara KamarStandard dan KamarVIP ini merupakan bentuk nyata dari penerapan konsep polymorphism dalam sistem.

### 3.4.4 Class Tamu

Class `Tamu` merepresentasikan data tamu hotel yang melakukan reservasi. Class ini bersifat independen dan tidak memiliki hubungan inheritance dengan class lain, namun memiliki relasi association dengan class Reservasi. Setiap tamu memiliki identitas unik yang di-generate otomatis oleh sistem. Berikut adalah diagram class Tamu.

```
┌─────────────────────────────────────────┐
│                 Tamu                     │
├─────────────────────────────────────────┤
│ - idTamu: String                        │
│ - nama: String                          │
│ - noTelepon: String                     │
├─────────────────────────────────────────┤
│ + Tamu(idTamu, nama, noTelepon)         │
│ + getIdTamu(): String                   │
│ + getNama(): String                     │
│ + getNoTelepon(): String                │
│ + setNama(String): void                 │
│ + setNoTelepon(String): void            │
└─────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class Tamu menerapkan encapsulation dengan menjadikan seluruh atribut sebagai private. Atribut `idTamu` tidak memiliki setter karena ID bersifat tetap setelah objek dibuat, sedangkan `nama` dan `noTelepon` dapat diubah melalui setter yang disediakan.

### 3.4.5 Class Reservasi

Class `Reservasi` merupakan class yang menghubungkan antara data tamu dengan data kamar dalam konteks pemesanan hotel. Class ini menerapkan konsep association karena memiliki referensi langsung ke objek Tamu dan objek Kamar. Selain itu, class ini juga memiliki method untuk menghitung durasi menginap dan total biaya secara otomatis berdasarkan data yang diberikan. Berikut adalah diagram class Reservasi.

```
┌──────────────────────────────────────────────────────────────┐
│                        Reservasi                             │
├──────────────────────────────────────────────────────────────┤
│ - idReservasi: String                                        │
│ - tamu: Tamu                                                 │
│ - kamar: Kamar                                               │
│ - tanggalCheckIn: LocalDate                                  │
│ - tanggalCheckOut: LocalDate                                 │
│ - durasiMenginap: int                                        │
│ - totalBiaya: double                                         │
│ - statusReservasi: StatusReservasi                           │
├──────────────────────────────────────────────────────────────┤
│ + Reservasi(idReservasi, tamu, kamar, tglCheckIn, tglCheckOut)│
│ + getIdReservasi(): String                                   │
│ + getTamu(): Tamu                                            │
│ + getKamar(): Kamar                                          │
│ + getTanggalCheckIn(): LocalDate                             │
│ + getTanggalCheckOut(): LocalDate                            │
│ + getDurasiMenginap(): int                                   │
│ + getTotalBiaya(): double                                    │
│ + getStatusReservasi(): StatusReservasi                      │
│ + setStatusReservasi(StatusReservasi): void                  │
│ + hitungDurasiMenginap(): void                               │
│ + hitungTotalBiaya(): void                                   │
│ + konfirmasiReservasi(): void                                │
│ + batalkanReservasi(): void                                  │
└──────────────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class Reservasi memiliki relasi association dengan class Tamu dan class Kamar. Satu reservasi dimiliki oleh satu tamu dan mengacu pada satu kamar. Method `hitungTotalBiaya()` memanfaatkan polymorphism dengan memanggil `kamar.hitungTotalHarga(durasiMenginap)` yang akan memberikan hasil berbeda tergantung apakah kamar bertipe Standard atau VIP. Method `batalkanReservasi()` juga mengubah status kamar kembali menjadi TERSEDIA, menunjukkan interaksi antar objek yang erat.

### 3.4.6 Class User (Abstract)

Class `User` merupakan abstract class yang menjadi parent class tertinggi dalam hierarki pengguna sistem. Class ini mendefinisikan atribut dasar identitas pengguna dan mendeklarasikan method abstract `login()`. Di bawah User terdapat abstract class `Staff` yang menambahkan atribut spesifik untuk staff hotel seperti username, jabatan, dan role. Berikut adalah diagram class User dan Staff.

```
┌─────────────────────────────────────────────────┐
│             <<abstract>> User                   │
├─────────────────────────────────────────────────┤
│ # idUser: String                                │
│ # nama: String                                  │
│ # email: String                                 │
│ # password: String                              │
├─────────────────────────────────────────────────┤
│ + User(idUser, nama, email, password)           │
│ + getIdUser(): String                           │
│ + getNama(): String                             │
│ + getEmail(): String                            │
│ + getPassword(): String                         │
│ + setNama(String): void                         │
│ + setEmail(String): void                        │
│ + login(): void {abstract}                      │
└─────────────────────────────────────────────────┘
            │
            ▼ (inheritance)
┌─────────────────────────────────────────────────────────────────┐
│                    <<abstract>> Staff                            │
│                     (extends User)                               │
├─────────────────────────────────────────────────────────────────┤
│ - username: String                                              │
│ - jabatan: String                                               │
│ - role: Role                                                    │
├─────────────────────────────────────────────────────────────────┤
│ + Staff(idStaff, nama, username, email, password, jabatan, role)│
│ + getIdStaff(): String                                          │
│ + getUsername(): String                                         │
│ + getJabatan(): String                                          │
│ + getRole(): Role                                               │
│ + setRole(Role): void                                           │
│ + login(String, String): boolean                                │
│ + tampilkanMenu(): void {abstract}                              │
└─────────────────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class User menggunakan access modifier protected (#) pada atributnya sehingga dapat diakses langsung oleh subclass. Class Staff mewarisi seluruh atribut dari User dan menambahkan atribut spesifik seperti username untuk login, jabatan sebagai keterangan posisi, dan role yang berisi daftar permission. Method `login(String, String)` pada Staff melakukan validasi username/email dan password, sementara method abstract `tampilkanMenu()` akan diimplementasikan berbeda oleh setiap subclass.

### 3.4.7 Class Admin

Class `Admin` merupakan subclass dari `Staff` yang merepresentasikan staff dengan jabatan administrator. Admin memiliki hak akses penuh terhadap seluruh fitur dalam sistem, termasuk manajemen kamar, data tamu, reservasi, operasional check-in/check-out, laporan harian, dan manajemen staff. Berikut adalah diagram class Admin.

```
┌──────────────────────────────────────────────────────────┐
│                        Admin                             │
│                   (extends Staff)                         │
├──────────────────────────────────────────────────────────┤
│ (tidak ada atribut tambahan)                             │
├──────────────────────────────────────────────────────────┤
│ + Admin(idStaff, nama, username, email, password, role)  │
│ + tampilkanMenu(): void                                  │
│   → "Menu Admin"                                         │
└──────────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class Admin tidak memiliki atribut tambahan karena seluruh informasi yang dibutuhkan sudah tersedia melalui inheritance dari class Staff dan User. Pembeda utama Admin dengan role lain terletak pada permission yang dimiliki role-nya, yaitu: ROOM_VIEW, ROOM_MANAGE, HOUSEKEEPING_UPDATE, GUEST, RESERVATION, CHECKIN, CHECKOUT, REPORT, dan STAFF.

### 3.4.8 Class Resepsionis

Class `Resepsionis` merupakan subclass dari `Staff` yang merepresentasikan staff dengan jabatan resepsionis. Resepsionis bertanggung jawab pada operasional front office hotel, yaitu mengelola data tamu, membuat reservasi, serta memproses check-in dan check-out. Berikut adalah diagram class Resepsionis.

```
┌─────────────────────────────────────────────────────────────────┐
│                       Resepsionis                                │
│                     (extends Staff)                              │
├─────────────────────────────────────────────────────────────────┤
│ (tidak ada atribut tambahan)                                    │
├─────────────────────────────────────────────────────────────────┤
│ + Resepsionis(idStaff, nama, username, email, password, role)   │
│ + tampilkanMenu(): void                                         │
│   → "Menu Resepsionis"                                          │
└─────────────────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class Resepsionis memiliki struktur yang sama dengan Admin namun dengan permission yang lebih terbatas. Permission yang dimiliki Resepsionis adalah: ROOM_VIEW, GUEST, RESERVATION, CHECKIN, dan CHECKOUT. Resepsionis tidak dapat mengakses fitur laporan harian dan manajemen staff.

### 3.4.9 Class Housekeeping

Class `Housekeeping` merupakan subclass dari `Staff` yang merepresentasikan staff dengan jabatan housekeeping. Housekeeping bertanggung jawab terhadap kondisi fisik kamar, yaitu memantau dan memperbarui status kamar seperti mengubah status dari DIBERSIHKAN menjadi TERSEDIA setelah proses pembersihan selesai. Berikut adalah diagram class Housekeeping.

```
┌─────────────────────────────────────────────────────────────────┐
│                      Housekeeping                               │
│                     (extends Staff)                              │
├─────────────────────────────────────────────────────────────────┤
│ (tidak ada atribut tambahan)                                    │
├─────────────────────────────────────────────────────────────────┤
│ + Housekeeping(idStaff, nama, username, email, password, role)  │
│ + tampilkanMenu(): void                                         │
│   → "Menu Housekeeping"                                         │
└─────────────────────────────────────────────────────────────────┘
```

Berdasarkan diagram di atas, class Housekeeping memiliki permission yang paling terbatas yaitu hanya ROOM_VIEW dan HOUSEKEEPING_UPDATE. Dengan permission ini, housekeeping hanya dapat melihat daftar kamar dan memperbarui status kamar. Pembatasan ini sesuai dengan prinsip least privilege dimana setiap role hanya diberikan akses sesuai dengan tanggung jawab pekerjaannya.

---

### Ringkasan Relasi Antar Class

Berikut adalah ringkasan relasi antar class dalam sistem StayEase yang menggambarkan hubungan inheritance, association, dependency, dan composition antar seluruh class yang telah dirancang.

```
User (abstract)
 └── Staff (abstract)
      ├── Admin
      ├── Resepsionis
      └── Housekeeping

Kamar (abstract)
 ├── KamarStandard
 └── KamarVIP

Reservasi ──→ Tamu (association)
Reservasi ──→ Kamar (association)
CheckIn   ──→ Reservasi (association)
CheckIn   ──→ Staff (association)
CheckOut  ──→ Reservasi (association)
CheckOut  ──→ Staff (association)

Staff ──→ Role (dependency)
Role  ──→ Permission (composition)

<<interface>> ManageStaff
 └── StaffManager (implements)
```

Berdasarkan diagram relasi di atas, sistem StayEase memiliki dua hierarki inheritance utama yaitu hierarki User-Staff dan hierarki Kamar. Class Reservasi menjadi penghubung utama antara data tamu dan data kamar melalui relasi association. Class CheckIn dan CheckOut memiliki relasi association dengan Reservasi dan Staff, menunjukkan bahwa setiap proses operasional dilakukan oleh petugas tertentu terhadap reservasi tertentu. Interface ManageStaff diimplementasikan oleh class StaffManager untuk mengelola data staff, menerapkan prinsip abstraksi melalui interface.
