Deployment Link: https://successful-danica-adpro2026-f827b48d.koyeb.app/product/list

# Modul 1

# REFLECTION 1

Pada pengembangan aplikasi ini, saya telah mengimplementasikan dua fitur baru, yaitu edit product dan delete product, menggunakan framework Spring Boot dengan pendekatan arsitektur Model–View–Controller (MVC). Implementasi ini dilakukan dengan memperhatikan prinsip clean code serta praktik secure coding yang telah dipelajari dalam modul ini.

## Penerapan Prinsip Clean Code

Beberapa prinsip clean code yang telah diterapkan pada kode sumber aplikasi ini antara lain:

1. Separation of Concerns (SoC)
Aplikasi dibagi ke dalam beberapa lapisan dengan tanggung jawab yang jelas, yaitu:

- - Controller: menangani HTTP request dan response.

- - Service: berisi logika bisnis aplikasi.

- - Repository: bertanggung jawab terhadap pengelolaan data.

- - Model: merepresentasikan struktur data produk.

Pemisahan ini membuat kode lebih mudah dipahami, diuji, dan dikembangkan di masa depan.

2. Penamaan yang Jelas dan Konsisten
Penamaan kelas, method, dan variabel menggunakan nama yang deskriptif, seperti editProduct, findProductById, dan productListPage, sehingga maksud dari setiap bagian kode dapat dipahami tanpa perlu komentar tambahan.

3. Penghindaran Duplikasi Kode
Logika pengambilan dan manipulasi data produk dipusatkan pada layer service dan repository, sehingga tidak terjadi pengulangan kode di controller.

4. Struktur Method yang Sederhana
Setiap method dibuat dengan satu tujuan yang jelas (single responsibility), misalnya method editProductPost hanya bertanggung jawab untuk memproses perubahan data produk.

## Penerapan Secure Coding Practices

Dalam pengembangan fitur edit dan delete, beberapa praktik keamanan juga telah diterapkan, antara lain:

1. Penggunaan Path Variable untuk Identifikasi Data
Penghapusan dan pengeditan produk dilakukan menggunakan productId sebagai identifier, sehingga operasi hanya dilakukan pada data yang spesifik.

2. Penghindaran Manipulasi ID oleh User
Pada halaman edit, productId dikirim melalui input tersembunyi (hidden field), sehingga tidak dapat diubah secara langsung oleh pengguna melalui antarmuka.

3. Validasi Alur Aplikasi
Setiap request edit dan delete dipetakan secara eksplisit melalui controller (@GetMapping dan @PostMapping), sehingga mencegah akses endpoint yang tidak terdefinisi.

## Evaluasi dan Perbaikan Kode

Selama proses pengembangan, ditemukan beberapa hal yang dapat diperbaiki, antara lain:

- Product ID Tidak Diinisialisasi Saat Create

Ini merupakan kesalahan saya pada awalnya implementasi. Awalnya, productId tidak dihasilkan secara otomatis saat pembuatan produk. Hal ini menyebabkan error ketika melakukan edit karena URL membutuhkan ID yang valid.
Perbaikan dilakukan dengan menghasilkan productId secara otomatis menggunakan UUID, sehingga setiap produk memiliki identifier yang unik.

- Belum Menggunakan Validasi Input

Saat ini aplikasi belum menggunakan validasi seperti @NotNull atau @Min pada atribut produk. Ke depannya, validasi ini dapat ditambahkan untuk meningkatkan keandalan aplikasi.

- Penyimpanan Data Masih In-Memory

Repository masih menggunakan struktur data List, sehingga data akan hilang saat aplikasi dihentikan. Untuk pengembangan selanjutnya, aplikasi dapat diintegrasikan dengan database menggunakan Spring Data JPA.

# REFLECTION 2

## Refleksi Setelah Menulis Unit Test

Setelah menulis unit test, saya merasa lebih percaya diri terhadap stabilitas dan kebenaran logika program yang dikembangkan. Unit test membantu saya memahami alur eksekusi kode secara lebih mendalam, terutama dalam memastikan bahwa setiap method bekerja sesuai dengan tanggung jawabnya. Selain itu, unit test juga mempermudah proses refactoring karena kesalahan dapat terdeteksi lebih awal.

Jumlah unit test dalam satu kelas tidak dapat ditentukan secara pasti, karena bergantung pada kompleksitas dan tanggung jawab kelas tersebut. Namun, prinsip yang sebaiknya diikuti adalah **setiap perilaku (behavior) penting dari sebuah method harus diuji**. Idealnya, satu test case memverifikasi satu skenario atau satu kondisi spesifik agar mudah dipahami dan dipelihara.

Untuk memastikan bahwa unit test sudah cukup dalam memverifikasi program, salah satu metrik yang dapat digunakan adalah **code coverage**. Code coverage menunjukkan seberapa besar bagian dari kode sumber yang telah dieksekusi oleh test. Meskipun memiliki code coverage 100% terdengar ideal, hal tersebut **tidak menjamin bahwa kode bebas dari bug atau error**. Code coverage hanya mengukur apakah baris kode dijalankan, bukan apakah seluruh kemungkinan logika, edge case, atau kesalahan desain telah diuji dengan benar. Oleh karena itu, kualitas test case jauh lebih penting daripada sekadar angka coverage.

## Refleksi terhadap Pembuatan Functional Test Tambahan

Jika setelah membuat `CreateProductFunctionalTest.java` saya diminta untuk membuat functional test baru untuk memverifikasi jumlah item dalam product list, lalu saya membuat kelas baru dengan setup dan variabel yang hampir sama, maka dari sisi clean code terdapat beberapa potensi masalah.

### Potensi Masalah Clean Code

1. **Duplikasi Kode (Code Duplication)**  
   Setup seperti inisialisasi WebDriver, konfigurasi URL dasar, dan proses setup/teardown kemungkinan akan ditulis ulang di kelas functional test yang baru. Duplikasi ini melanggar prinsip *DRY (Don't Repeat Yourself)* dan dapat menyulitkan pemeliharaan kode.

2. **Tight Coupling antar Test Suite**  
   Jika setiap functional test mengatur sendiri konfigurasi lingkungan (misalnya URL, timeout, atau browser), maka perubahan kecil pada konfigurasi akan membutuhkan perubahan di banyak kelas test.

3. **Menurunnya Readability dan Maintainability**  
   Kode yang berulang dan panjang membuat test suite menjadi lebih sulit dibaca, terutama ketika jumlah functional test bertambah. Hal ini dapat menurunkan kualitas kode secara keseluruhan.

### Saran Perbaikan

Untuk meningkatkan kebersihan dan kualitas kode, beberapa perbaikan yang dapat dilakukan adalah:

- **Membuat Base Functional Test Class**  
  Setup umum seperti inisialisasi WebDriver, konfigurasi browser, dan teardown dapat dipindahkan ke satu kelas abstrak (misalnya `BaseFunctionalTest`) yang kemudian diwarisi oleh semua functional test suite.

- **Menggunakan Utility atau Helper Class**  
  Interaksi yang sering dilakukan, seperti membuka halaman tertentu atau mengisi form, dapat diekstrak ke dalam helper method agar test case lebih ringkas dan fokus pada tujuan pengujian.

- **Menjaga Satu Tanggung Jawab per Test Class**  
  Setiap functional test class sebaiknya hanya menguji satu fitur utama, misalnya create product atau verifikasi product list, sehingga test tetap jelas dan terstruktur.

Dengan menerapkan perbaikan tersebut, functional test suite akan menjadi lebih bersih, mudah dikembangkan, dan tetap selaras dengan prinsip clean code meskipun jumlah test semakin bertambah.

# Modul 2

# Reflection

Pada bagian ini, saya mengimplementasi Continuous Integration dan Continuous Deployment (CI/CD) yang telah dilakukan menggunakan GitHub Actions dan platform PaaS (Koyeb).

## 1. Code Quality / Security Issues dan Strategi Penyelesaian
Selama pengerjaan exercise ini, salah satu isu kualitas dan keamanan yang saya perbaiki adalah Pinned Dependencies pada workflow GitHub Actions.

- Isunya adalah tag yang digunakan pada action GitHub dan image Docker sebelumnya bersifat mutable (dapat berubah dari waktu ke waktu, misalnya menggunakan tag @v4 atau latest). Hal ini menimbulkan celah keamanan tingkat menengah/tinggi, karena jika versi action tersebut diubah oleh pihak ketiga (baik karena update biasa atau disusupi kode berbahaya), alur eksekusi CI/CD aplikasi bisa rusak atau terkompromi tanpa disadari.

- Strategi penyelesaian yang saya lakukan adalah dengan mengikuti rekomendasi Security Hardening dari GitHub dan The Open Source Security Foundation (OpenSSF). Saya memodifikasi file workflow dengan mengganti tag versi mutable menjadi full-length commit SHA yang spesifik dan immutable (tidak dapat diubah). Proses ini dibantu melalui automasi Pull Request dari StepSecurity Bot. Dengan mengunci (pinning) dependensi ke commit SHA tertentu, saya memastikan bahwa pipeline selalu menjalankan kode pihak ketiga yang persis sama dan sudah diverifikasi keamanannya pada setiap eksekusi.

## 2. Evaluasi Implementasi CI/CD
Menurut saya, implementasi workflow GitHub dan konfigurasi repositori saat ini sudah memenuhi definisi dan standar dari Continuous Integration (CI) maupun Continuous Deployment (CD). Alasannya:

- Pertama, dari sisi Continuous Integration, workflow telah dikonfigurasi agar setiap kali ada kode yang di-push atau diajukan melalui Pull Request. Kemudian, GitHub Actions secara otomatis akan menjalankan proses build, mengeksekusi seluruh test suites (unit dan functional test), serta melakukan analisis kualitas dan keamanan kode (lewat OSSF Scorecard). Hal ini memastikan bahwa setiap perubahan kode divalidasi kebenarannya secara terus-menerus sebelum digabungkan ke branch utama.

- Kedua, dari sisi Continuous Deployment, repositori GitHub ini telah diintegrasikan secara langsung dengan Platform as a Service (PaaS) yaitu Koyeb menggunakan pendekatan Pull-based deployment (atau Auto-deploy). Begitu kode berhasil melewati tahap CI dan di-merge ke branch main, PaaS akan secara otomatis mendeteksi perubahan tersebut, menarik kode atau image terbaru, lalu men-deploy aplikasi ke environment produksi tanpa memerlukan intervensi manual sama sekali. Alur ini menciptakan siklus rilis perangkat lunak yang cepat, aman, dan konsisten.

# Modul 3

# Reflection

## S — Single Responsibility Principle (SRP)
Apakah sudah diimplementasikan?
- Ya, setelah refactor

Prinsip SRP: Sebuah class hanya boleh memiliki satu alasan untuk berubah (one class → one responsibility)

Artinya:
- Controller → hanya mengatur HTTP request/response
- Service → hanya mengatur business logic
- Repository → hanya mengatur akses database

Sebelum Refactor
- ProductController dan CarController berada dalam satu file
- Controller mengakses repository langsung
- Controller menangani lebih dari satu domain

Hal ini menyebabkan:
- Jika logic Car berubah → ProductController ikut berubah
- Satu class punya lebih dari satu responsibility

Setelah Refactor

Struktur dipisah:
```
controller/
    ProductController.java
    CarController.java

service/
    ProductService.java
    CarService.java

service/impl/
    ProductServiceImpl.java
    CarServiceImpl.java

repository/
    ProductRepository.java
    CarRepository.java
```

Keterangan:
- CarController hanya menangani request Car
- ProductController hanya menangani request Product
- Tidak ada class dengan lebih dari satu alasan berubah

SRP sudah terpenuhi

## O — Open/Closed Principle (OCP)
Apakah sudah diimplementasikan?
- Ya, setelah menghilangkan inheritance antar controller

Prinsip OCP: Software entity harus open for extension tetapi closed for modification

Artinya:
- Kita bisa menambah behavior
- Tanpa mengubah source code yang sudah ada

Sebelum Refactor
- public class CarController extends ProductController

Masalah:
- Coupling tidak perlu
- Jika ubah ProductController → CarController ikut terpengaruh
- Untuk tambah fitur baru harus memodifikasi parent class

Setelah Refactor
- Tidak ada inheritance antar controller
- Masing-masing berdiri sendiri
- Behavior baru ditambahkan melalui service layer

Contoh:
Jika ingin tambah MotorcycleController, kita cukup buat:

```java
@Controller
@RequestMapping("/motorcycle")
public class MotorcycleController
```

Tanpa mengubah ProductController.

OCP sudah terpenuhi.

## L — Liskov Substitution Principle (LSP)
Apakah sudah diimplementasikan?
- Ya, karena inheritance yang salah sudah dihapus

Prinsip LSP: Subclass harus bisa menggantikan superclass tanpa merusak kebenaran program.

Sebelum Refactor
```java
CarController extends ProductController
```

Padahal:
- CarController tidak benar-benar "is-a" ProductController
- Behavior tidak identik
- Tidak bisa menggantikan parent secara logis

Ini melanggar LSP

Setelah Refactor
- Tidak ada inheritance yang tidak valid
- Semua controller berdiri sendiri
- Substitusi tidak diperlukan

Inheritance hanya digunakan jika benar-benar hubungan "is-a".

Contoh yang benar:
```java
abstract class Vehicle
class Car extends Vehicle
class Motorcycle extends Vehicle
```

Bukan antar controller.

LSP terpenuhi.

## I — Interface Segregation Principle (ISP)
Apakah sudah diimplementasikan?
- Ya, sejak awal sudah baik.

Prinsip ISP: Client tidak boleh dipaksa mengimplementasikan method yang tidak ia gunakan.

Implementasi di Project
- CarService
- ProductService

Dibuat secara terpisah.

Tidak ada interface besar yang memaksa semua concrete class untuk mengimplementasi semua method.

Karena interface sudah kecil dan spesifik, ISP terpenuhi.

## D — Dependency Inversion Principle (DIP)
Apakah sudah diimplementasikan?
- Ya, setelah menghilangkan dependency ke concrete class.

Prinsip DIP: High-level module tidak boleh bergantung pada low-level module. Keduanya harus bergantung pada abstraction.

Sebelum Refactor
```java
private CarServiceImpl carService;

private 
```
Dan controller meng-import repository langsung.

Masalah: 
- Controller tergantung concrete implementation
- Tight coupling
- Sulit di-test

Setelah Refactor
```java
private final CarService carService;
```

Dan:

```java
@Service
public class CarServiceImpl implements CarService
```

Sekarang:
- Controller bergantung pada interface
- Implementation bisa diganti tanpa ubah controller
- Mudah di-mock saat testing

## Keuntungan (advantages) menerapkan prinsip SOLID pada proyek:

Maintainability (mudah dipelihara)
- Kode yang menerapkan SRP dan DIP lebih mudah diperbaiki atau ditambah fitur tanpa risiko merusak bagian lain.
Contoh: Setelah memisahkan CarController dari ProductController, perubahan pada endpoint Product tidak akan memengaruhi Car—jadi bug fix atau penambahan fitur bisa dilakukan lokal pada controller yang tepat.

Testability (mudah diuji)
- Bergantung pada interface (DIP) + constructor injection membuat unit test dan mocking menjadi sederhana.
Contoh: ProductController sekarang menggunakan constructor injection pada ProductService → di test Anda cukup mock ProductService dan inject ke controller. Functional/unit test jadi deterministic dan cepat.

Extensibility / Reusability (mudah diperluas dan digunakan ulang)
- OCP membuat modul terbuka untuk penambahan perilaku tanpa mengubah kode lama.
Contoh: Untuk menambah fitur export CSV pada entitas Car, Anda bisa menambahkan method baru di CarController atau menambah decorator/service baru tanpa memodifikasi ProductController atau repository.

Robustness / Safety (mengurangi risiko regresi)
- Menghindari pewarisan yang salah (mengikuti LSP) dan pemisahan tanggung jawab mengurangi efek samping tersembunyi ketika merubah kode.
Contoh: Sebelumnya CarController extends ProductController — menambah endpoint di ProductController bisa menyebabkan perilaku tak terduga pada Car. Setelah dipisah, perubahan bersifat lokal.

Clearer API/Contract (ISP & DIP)
- Interface terpisah (ProductService, CarService) membuat kontrak jelas: controller hanya tahu operasi yang dibutuhkan. Ini mencegah controller terpaut pada method yang tidak relevan.
Contoh: Controller hanya memiliki metode create/find/edit/delete yang relevan — coder lain cepat paham apa yang tersedia.

## Kerugian (disadvantages) jika tidak menerapkan prinsip SOLID — dengan contoh nyata dari proyek
Coupling tinggi
- Jika kelas bergantung pada implementasi konkret atau banyak tanggung jawab digabung, perubahan sederhana dapat menimbulkan efek domino.
Contoh nyata: CarController extends ProductController — mengubah routing/redirect di ProductController dapat memecahkan endpoint car/listCar sehingga menghasilkan 404/NoResourceFoundException.

Sulit di-test (hard-to-test)
- Ketergantungan langsung pada implementasi konkret membuat mocking sulit dan test menjadi integration-heavy.
Contoh nyata: Jika controller meng-@Autowired CarServiceImpl langsung, unit test harus membuat instance CarServiceImpl (atau kompleks mocking), bukannya cukup mock interface CarService.

Fragile code / Risiko regresi tinggi
- Kode yang melanggar OCP/LSP seringkali rusak ketika dikembangkan lebih lanjut karena perubahan mempengaruhi subclass atau pengguna lain.
Contoh nyata: Menaruh dua controller di satu file dan saling mewarisi menyebabkan runtime mapping yang tidak terduga — Spring mungkin tidak mendaftarkan bean seperti yang diharapkan → muncul error runtime (NoResourceFoundException).

Duplikasi/Redundansi & Sulit dipelihara (violates DRY indirectly)
- Tanpa ISP, interface besar memaksa klien mengimplementasikan/mengetahui method yang tidak diperlukan → implementasi menjadi besar dan berantakan.
Contoh: Bila ada satu service yang mengurus Product+Car+Order dalam satu interface, controller akan menerima method yang tak perlu dan implementasinya jadi lebih sulit dipisah.

Kesulitan integrasi CI/CD dan deployment aman
- Kode rapuh/terkait erat mempersulit pembuatan test suite yang cepat dan andal; akibatnya pipeline CI/CD sering gagal atau butuh test environment berat.
Contoh: Functional tests (Selenium) bergantung pada redirect/route yang stabil; jika kontroler memakai relative redirect atau mapping tumpang tindih, functional test flakey dan deployment otomatis rawan gagal.

# Modul 4

### Refleksi TDD berdasarkan Percival (2017)
Berdasarkan submodul "Evaluating Your Testing Objectives" oleh Percival, pendekatan TDD yang saya lakukan dalam latihan ini sangat berguna dan efektif untuk memastikan kebenaran logika bisnis (business logic) sebelum implementasi penuh dilakukan.

Hal ini sangat berguna bagi saya karena ada beberapa hal yang penting:
- Kejelasan Spesifikasi: Dengan menulis test case terlebih dahulu, saya dipaksa mendefinisikan apa yang harus dilakukan oleh method (seperti updateStatus atau createOrder) sebelum memikirkan cara mengimplementasikannya. Ini menghilangkan ambiguitas.

- Keamanan dalam Refactoring: Saat saya melakukan perubahan pada struktur class untuk memenuhi prinsip SOLID, saya merasa jauh lebih aman karena saya memiliki test suite yang dapat langsung memberikan umpan balik jika ada perubahan yang merusak fungsi yang sudah ada.

Hal yang perlu saya perbaiki di masa depan:

- Cakupan Edge Cases: Terkadang saya terlalu fokus pada happy path dan unhappy path yang sederhana. Di masa depan, saya perlu melakukan analisis lebih mendalam terhadap boundary conditions (misalnya input string kosong, atau ID yang tidak ada di tengah batch operasi) agar cakupan tes lebih menyeluruh.

- Mocking yang Lebih Spesifik: Saya perlu memastikan bahwa penggunaan mocking tidak berlebihan (over-mocking) yang justru mengikat tes terlalu kuat pada implementasi internal, bukan pada perilaku (behavior).

### Refleksi F.I.R.S.T. Principles
Saya telah meninjau unit test yang saya buat dan mengevaluasinya berdasarkan prinsip F.I.R.S.T.:

- Fast (Cepat): Ya, unit test saya berjalan sangat cepat karena menggunakan mocking (Mockito) dan tidak bergantung pada database eksternal atau framework yang berat.

- Independent (Mandiri): Ya, setiap test method memiliki setup sendiri atau menggunakan mock yang terisolasi, sehingga urutan eksekusi tidak memengaruhi hasil.

- Repeatable (Dapat Diulang): Ya, hasil tes konsisten di lingkungan lokal maupun dalam pipeline CI/CD (GitHub Actions).

- Self-Validating (Memvalidasi Sendiri): Ya, setiap tes memiliki assertion (assertEquals, assertTrue, assertThrows) yang memberikan output "pass" atau "fail" secara eksplisit.

- Thorough (Menyeluruh): Bagi saya, ini adalah bagian yang perlu saya tingkatkan. Meskipun saya sudah mencakup skenario dasar, saya merasa belum sepenuhnya thorough.

Di masa depan, saya akan lebih disiplin untuk menulis tes bagi setiap kemungkinan error handling (seperti NoSuchElementException atau skenario null pointer) secara lebih detail. Saya juga akan memastikan bahwa setiap branch di dalam if-else di kode utama memiliki tes yang bersesuaian agar code coverage tidak hanya tinggi, tetapi juga berkualitas.
