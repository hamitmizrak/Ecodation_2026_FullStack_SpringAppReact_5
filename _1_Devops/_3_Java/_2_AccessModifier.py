"""
S1-) Access Modifier Nedir ?
C1-)
- Bir sınıfın veya üyenin hangi kapsamda erişebileceğinin belirleyen görünürlük mekanizmasıdır.
- Bir sınıfın alanın, metodun, constructorun nereden erişilebilir olduğunu belirleyen anahtar kelimeleridr.
-
-


S2-) Access Temel Amaçı Nedir ?
C2-)
- Kapsülleme (encapsulation)
- Gereksiz bağımlılıkları azaltmak
- Nesnenin iç durumu kontrolünü sağlamak
-




S3-) Access Modifier Çeşitleri Nedir ?
C3-)

Modifier   --- Aynı Sınıf ---Aynı Paket ---Alt Sınıf ---Farklı Paket      --Açıklama
public           +              +           +             +                Heryerden erişimi vardır.
protected        +              +           +             + (alt sınıf)    Kalıtım senaryolarında görünürlüğü sağlar
private          +              -           -             -
default          +              +           -             -


S4-) Encapsulation İle Access Modifier İlişkisi Nedir ?
C4-) Access modifer konusu OOP içindekiş encapsulation ilkesi ile doğrudan bağlantılıdır.
- Nesnenin iç verisi mümkün olduğunca private tutulur.
- Dışarıya yalnızca gerçekten ihtiyaç duyualn davranışı açılır Böylece veri bütünlüğü sağlanmış olunur.
-
-
-


S5-) Top-level class neden private olamaz ?
C5-) Çünkü top-level class dış dünyadan tamamen gizlenemez, java dil kurlaları buna izin vermez.
-
-
-
-
-


"""