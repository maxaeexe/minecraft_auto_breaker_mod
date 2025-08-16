<h1 align="center">🔨 Auto Breaker Mod</h1>

<p align="center">
⚠️ <strong>Önemli Uyarı:</strong> Bu mod otomatik blok kırma işlevi içerir. Minecraft kullanım şartlarını ihlal edebilir ve bazı sunucularda ban riski olabilir. Kullanımı tamamen sizin sorumluluğunuzdadır.
</p>

---

<p align="center">
<img src="https://img.shields.io/badge/Minecraft-1.16.5-green?style=for-the-badge&logo=minecraft" />
<img src="https://img.shields.io/badge/Forge-Compatible-orange?style=for-the-badge" />
<img src="https://img.shields.io/badge/Java-8+-blue?style=for-the-badge&logo=java" />
</p>

---

### 📋 Açıklama
Auto Breaker Mod, belirli bir alan içerisindeki <strong>yosunlu kırıktaş (mossy cobblestone)</strong> bloklarını otomatik kıran bir Minecraft Forge modudur.  
Mod, kullanıcı kimlik doğrulaması ve SQL Server entegrasyonu ile ekstra güvenlik sağlar.

---

### ✨ Özellikler

<p align="left">
<strong>🔐 Güvenlik</strong><br>
✅ SQL Server tabanlı kullanıcı kimlik doğrulaması<br>
✅ Şifreli veritabanı bağlantısı<br>
✅ IP adresi takibi ve kaydetme<br>
✅ Oturum yönetimi
</p>

<p align="left">
<strong>🎮 Oyun İçi Fonksiyonlar</strong><br>
✅ F6 tuşu ile mod aktif/pasif yapma<br>
✅ Otomatik yosunlu kırıktaş kırma<br>
✅ Akıllı alan taraması (belirli koordinatlar arası)<br>
✅ Otomatik kazma seçimi<br>
✅ Y seviyesi bazında çalışma<br>
✅ Hedef blok bulunamadığında Y seviyesi düşürme
</p>

<p align="left">
<strong>🎨 Görsel Arayüz</strong><br>
✅ Mod durumu göstergesi (açık/kapalı)<br>
✅ Kullanıcı bilgisi gösterimi<br>
✅ Mevcut Y seviyesi gösterimi<br>
✅ Hedef blok bilgisi<br>
✅ Şık giriş ekranı
</p>

---

### 🛠️ Kurulum

**Gereksinimler**  
- Minecraft Forge  
- SQL Server (isteğe bağlı)  
- Java 8+  

**Kurulum Adımları**  
1. `src/main/java/com/example/examplemod/ExampleMod.java` dosyasını inceleyin  
2. **SQL Server kullanıyorsanız:**  
   - DB_URL, DB_USER, DB_PASSWORD değişkenlerini kendi veritabanı bilgilerinizle güncelleyin  
   - Veritabanında `maden_users` tablosunu oluşturun:  

   CREATE TABLE maden_users (  
       id INT IDENTITY(1,1) PRIMARY KEY,  
       kadi NVARCHAR(50) NOT NULL,  
       sifre NVARCHAR(100) NOT NULL,  
       ip NVARCHAR(45),  
       date DATETIME  
   );  

3. **SQL Server kullanmıyorsanız:**  
   - Veritabanı ile ilgili kodları silin veya kullanıcı doğrulamasını kaldırın  
4. Kodu derleyin ve mod dosyasını oluşturun  
5. `.minecraft/mods` klasörüne yerleştirin  

---

### 🎯 Kullanım

**İlk Başlatma**  
- Minecraft'ı başlatın  
- F6 tuşuna basın  
- Giriş ekranında kullanıcı adı ve şifrenizi girin  
- Enter veya "Giriş Yap" butonuna tıklayın  

**Mod Kontrolü**  
- F6: Mod açma/kapama  
- Mod aktifken otomatik çalışır  
- Ekranın sağ üst köşesinde durum göstergeleri görünür  

**Alan Ayarları**  
Varsayılan kırma alanı:  
- Koordinat 1: X=111, Y=47, Z=687  
- Koordinat 2: X=80, Y=46, Z=656  
- Koordinatları değiştirmek için `x1, y1, z1, x2, y2, z2` değişkenlerini düzenleyin  

---

### 📊 Özellik Detayları

**Akıllı Kırma Sistemi**  
- Envanterde kazma arar (Ahşap → Taş → Demir → Altın → Elmas → Netherite)  
- En yakın yosunlu kırıktaş bloğuna odaklanır  
- Blok kırıldıktan sonra bir sonrakine geçer  
- Mevcut seviyede blok kalmadığında Y seviyesini düşürür  

**Hareket Sistemi**  
- Hedefe 4 bloktan uzaksa otomatik yaklaşır  
- Yumuşak kamera döndürme  
- Hassas bakış açısı ayarlaması  

---

### 🛠️ SQL Server Kullanımı Olmayanlar

- Veritabanı ile ilgili kodları silebilir veya kullanıcı doğrulamasını kaldırabilirsiniz.  
- GPT, Qwen gibi yapay zekalardan kullanıcı doğrulamasını kaldırma konusunda destek alabilirsiniz.  
- Kodu derleyin ve mod dosyasını oluşturun  
- `.minecraft/mods` klasörüne yerleştirin

---

### 🤝 Katkıda Bulunma
Katkıda bulunmak isteyenler, modun güvenlik ve oyun işlevlerini geliştirebilir.  

<p align="center">💡 Sunucu kurallarını kontrol edin.</p>

