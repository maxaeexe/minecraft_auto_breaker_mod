# Auto Breaker Mod 🔨

## ⚠️ Önemli Uyarı
Bu mod otomatik blok kırma işlevi içermektedir. Minecraft'ın kullanım şartlarını ihlal edebilir ve bazı sunucularda ban ile sonuçlanabilir. Kullanımı tamamen kendi sorumluluğunuzdadır.

## 📋 Açıklama
Auto Breaker Mod, belirli bir alan içerisindeki yosunlu kırıktaş (mossy cobblestone) bloklarını otomatik olarak kıran bir Minecraft Forge modudur. Mod, kullanıcı kimlik doğrulaması ve SQL Server entegrasyonu ile güvenlik sağlamaktadır.

## ✨ Özellikler

### 🔐 Güvenlik
- SQL Server tabanlı kullanıcı kimlik doğrulaması
- Şifreli veritabanı bağlantısı
- IP adresi takibi ve kaydetme
- Oturum yönetimi

### 🎮 Oyun İçi Fonksiyonlar
- **F6 tuşu** ile mod aktif/pasif yapma
- Otomatik yosunlu kırıktaş kırma
- Akıllı alan taraması (belirli koordinatlar arası)
- Otomatik kazma seçimi
- Y seviyesi bazında çalışma
- Hedef blok bulunamadığında otomatik Y seviyesi düşürme

### 🎨 Görsel Arayüz
- Mod durumu göstergesi (açık/kapalı)
- Kullanıcı bilgisi gösterimi
- Mevcut Y seviyesi gösterimi
- Hedef blok bilgisi
- Şık giriş ekranı

## 🛠️ Kurulum

### Gereksinimler
- Minecraft Forge
- SQL Server (isteğe bağlı)
- Java 8+

### Adımlar
1. `src/main/java/com/example/examplemod/ExampleMod.java` dosyasını inceleyin
2. **SQL Server kullanıyorsanız:**
   - `DB_URL`, `DB_USER`, `DB_PASSWORD` değişkenlerini kendi veritabanı bilgilerinizle güncelleyin
   - Veritabanında `maden_users` tablosunu oluşturun:
   ```sql
   CREATE TABLE maden_users (
       id INT IDENTITY(1,1) PRIMARY KEY,
       kadi NVARCHAR(50) NOT NULL,
       sifre NVARCHAR(100) NOT NULL,
       ip NVARCHAR(45),
       date DATETIME
   );
   ```
3. **SQL Server kullanmıyorsanız:**
   - Veritabanı ile ilgili kodları silin veya AI yardımı ile kullanıcı doğrulamasını kaldırın
4. Kodu derleyin ve mod dosyası oluşturun
5. `.minecraft/mods` klasörüne yerleştirin

## 🎯 Kullanım

### İlk Başlatma
1. Minecraft'ı başlatın
2. **F6** tuşuna basın
3. Giriş ekranında kullanıcı adı ve şifrenizi girin
4. **Enter** veya "Giriş Yap" butonuna tıklayın

### Mod Kontrolü
- **F6**: Mod açma/kapama
- Mod aktifken otomatik olarak çalışır
- Ekranın sağ üst köşesinde durum göstergeleri görünür

### Alan Ayarları
Varsayılan kırma alanı:
- **Koordinat 1**: X=111, Y=47, Z=687
- **Koordinat 2**: X=80, Y=46, Z=656
- Bu koordinatları değiştirmek için kodda `x1, y1, z1, x2, y2, z2` değişkenlerini düzenleyin

## 🔧 Yapılandırma

### Veritabanı Ayarları
```java
private static final String DB_URL = "jdbc:sqlserver://YOUR_SERVER;databaseName=YOUR_DB;encrypt=true;trustServerCertificate=true;";
private static final String DB_USER = "YOUR_USERNAME";
private static final String DB_PASSWORD = "YOUR_PASSWORD";
```

### Alan Koordinatları
```java
private static final int x1 = 111, y1 = 47, z1 = 687; // İlk köşe
private static final int x2 = 80, y2 = 46, z2 = 656;  // İkinci köşe
```

## 📊 Özellik Detayları

### Akıllı Kırma Sistemi
- Oyuncu envanterde kazma arar (ahşap > taş > demir > altın > elmas > netherite)
- En yakın yosunlu kırıktaş bloğuna odaklanır
- Blok kırıldıktan sonra bir sonrakine geçer
- Mevcut seviyede blok kalmadığında Y seviyesini düşürür

### Hareket Sistemi
- Hedefe 4 bloktan uzaksa otomatik yaklaşır
- Yumuşak kamera döndürme
- Hassas bakış açısı ayarlaması

## ⚠️ Güvenlik ve Uyarılar

### Yasal Uyarılar
- Bu mod Minecraft'ın EULA'sını ihlal edebilir
- Çoğu sunucuda kullanımı yasaktır
- Anti-cheat sistemler tarafından tespit edilebilir
- Hesap banı riski vardır

### Güvenlik Önlemleri
- Sadece güvendiğiniz kişilerle paylaşın
- Veritabanı şifrelerini güvenli tutun
- Genel sunucularda kullanmayın

## 🐛 Sorun Giderme

### Veritabanı Bağlantı Sorunları
- SQL Server'ın çalıştığından emin olun
- Bağlantı bilgilerini kontrol edin
- Firewall ayarlarını kontrol edin

### Mod Çalışmıyor
- Forge versiyonunu kontrol edin
- Log dosyalarını inceleyin
- F3 tuşu ile koordinatları kontrol edin

## 📝 Lisans
Bu proje eğitim amaçlı olarak geliştirilmiştir. Ticari kullanım önerilmez.

## 🤝 Katkıda Bulunma
Bu mod sadece eğitim amaçlı olduğu için katkıda bulunma önerilmez. Bunun yerine yasal Minecraft modları geliştirmenizi öneririz.

---

**Not**: Bu mod hile kategorisindedir ve çoğu Minecraft sunucusunda kullanımı yasaktır. Kullanmadan önce sunucu kurallarını kontrol edin ve riski göze alın.
