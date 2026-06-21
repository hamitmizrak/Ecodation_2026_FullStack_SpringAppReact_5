"""
S1-) Java Nedir ?
C1-) Java,
-  Java 1995 yılında tanıtılan nesneye dayalı, paltformdan bağımsız çalışmayı hedefleyen kurumsal sistemlerde çok yagun kullanılan üst seviy4e bir dildir
-
-
-


S2-) Java Özellikleri Nelerdir ?
C2-)
- Nesneye dayalı
-Platformdan bağımsız
- Güvenli
- Çok iş parçacığı bulunmaktadır.
- Otomatik bellek yönetimi vardır.
- Kurumsal uygunlu vardır (Spring, Jakarta EE, Hibernate)



S3-) WORA nedir ?
C3-)
Write Once Run Anywhere (WORA):
- Kaynak kod bir kez derlenir ve oluşan bytecode, uygun JVM olan farklı ortamlarda çalıaşbilir.
-
-



S4-) Java'yı diğer programlama dillerinden ayıran özellikler nelerdir ?
C4-) Java;
- Java otomatik pointer yapar
- JVM
- Hem interpreter hemde compiler özelliği vardır
- Kaynak kod bir kez derlenir(compiler) ve oluşan bytecode yorumlama(interpreter)



S5-) JDK, JRE, JVM bunları açıklayınız ?
C5-) JDK> JRE > JVM

- JVM (Java Virtula Machine) : Bytecode'u okuyan ve çalıştıran sanal makinedir.
- JVM : Class loading, verification, execution, Garbarage collection görevlerini yapar

- JRE (Java Run time environment): Java uygulamasını çalıştırmak için runtime paketidir.
- JRE: JVM + Temel kütüphaneleri burada yer alır

- JDK (Java Virtual Machine): Java geliştirmek için gerekn araçlardır.
- JDK : javac derleyicisi, debugger, javadoc ve diğer geliştirme araçları mevcuttur.



S6-) Java Mimarisi Açıklayınız ?
C6-)
- Deneme.java (Kaynak Kodu)  ==> Compiler (Javac) ==> Bytecode (Deneme.class) ==>
  ==> JRE içindeki JVM ==> Class Loader+ Byte Verifier +Execution Engine +Garbarage Collection

-
-

S7-) Compiler, Interpreter, JIT Nedir ve ilişkileri
C7-)
- Compiler : Yüksek seviyedeki bir dilin alt seviyeye dönüştürmesidir.
- Compiler: Kaynak kod -> byte code

- Java hibrit bir dildir: hem interpreter hemde compiler
- javac ==> java dosyaları => .class byte code'lara çevirir
-

S8-) Java Uygualamasını Yaşam döngüsü ?
C8-)
- Kaynak kod: .java dosayasını oalrak yazılı.
- Javac derleyicisi kodu bytecoda'a çevirir ve .class dosyasını üretir
- JVM, Class Loader yardımıyla sınıfları belleğe yükler
- Execution Engine bytecode'u interpreter veya JIT ile çalıştırır.
- Runtime sırasında nesneler heap'te method frame'ler stack'te tutulur
- Kullanılmayan nesneler Garbage collector tarafından temzilenir.
- Process sonlandığında runtime kaynakları serbest bırakılır.

S9-) Stack Memory,  Heap Memory
C9-)
Stack Memory:
- Method çağrılarını örnek:  String deneme= fullName();
- Parametreleri      örnek: String deneme= fullName(name, surname);
- Local değişken     örnek: private int data;
- Her thread'in kendi stack'i vardır.

Heap Memory:
- Diziler(Array)
- Nesnelerin  örnek: Student std;
- instance    örnek: Student std= new Student();





S2-) Java Referans olayı nedir ?
C2-)
Java'da object değişkenleri nesnenin kendisini değil, nesneye erişim için kullanın referansı tutar

Örnek:
Employee e1= new Employee();
Employee e2= new Employee();
e1 ve e2 iki ayrı nesne değildir yani aynı nesneyi gösteren iki farklı referanstır


-
-
-

S2-) Primitive Type
C2-) Primitive Type
- En temel verilerdir
- Object değildir
byte   8 bit
short 16 bit
int   32 bit
long  64 bit

float   32 bit
double  64 bit

char    16 bit




"""