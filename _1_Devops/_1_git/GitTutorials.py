"""
S1-) Git Nedir ?
c1-) Git,
-Dağıtık bir (VCS) sürüm kontrol sistemidir.
-Kaynak kodundaki değişiklikleri takip eder, gelişitiricilerin paralel çalışmasını mümkün kılar
- Git'i günlük geliştirme akışının omurgasıdır.
- Git doğru kullanmak proje kalitesini doğrudan etkiler

S2-) Git Mimarisi Hakkında Bilgiler Veriniz ?
C2-) Git Mimarisi,
- Working Tree      : Geliştiricinin diskte gördüğü mevcut çalışma ortamıdır
- Staging Area      : Bir sonraki commit'e gidecek içeeriğin hazırlanması ara katmandır (git add .)
- Local Repository  : Commit nesnelerin referanslarını tutludğu yerel depodur.
- Remote Repository : Git sybycybda bulunan verilerin uzak serverlarda tutulmasıdır (GitHub, GitLab,Bitbucket)


S-3)Git Nesne Modeli Hakkında Bilgi veriniz ?
C-3)Git, içeriği ağırlıklı olarak nesneler ve referanslar üzerinden saklar.
- blob : Dosyanın içeriğini bulundurğu yerdir.
- tree : Dizin yapısını ve blob/tree referanslarıdır
- commit :Geçmişisn ana omurgasını oluşturur.
- tag : Veriyonlama ve release için kullanılır.
-

S-4) Ref, HEAD, Branch kavramları hakkında bilgi veriniz.
C-4)
- Branch: Bir commit kopyası değildir. Commit zicirindeki son noktayı gösterir.
- HEAD : Aktif çalışma bağlamını gösteren özel referanstır.
- Ref: Referanslar
-
-

S-5) Git Yaşam Döngüsü Açıklayınız ?
C-5) Git Yaşam Döngüsü;
- Dosya Working Tree'de düzenlenir
- git status ile değişiklikleri gözlemle
- git add ile değişikliklier staging area alanına taşı
- git commit ile repository geçmişini kalıcı kayıt olmasını sağla
- git push ile paylaşılan uzak depoya gönderilir.



S-6) Neden SVN yerine Git Kullanıyoruz ?
C-6)  Git;
- Yerelde hızlı çalışır
- Branch ucuzdur.
- Çevrim dışı commit atılabilir.
- Dağıtık yapıya sahiptir.
- Daha esnektir

S-7) HEAD, Repository, Working Tree, Staging Area nedir ?
C-7)
- HEAD: Aktif brach veya doğrudan seçili commit'i gösteren referanstır.
- Örneğin: git reflog

- Repository: Versiyonların geçmişininin tutulduğu yerdir.
- Working Tree: Dosyaları fiilen düzenlendiği çalışma alanıdır.
- Staging Area: Bir sonraki commit'e alınacak değişiklikleri seçildiği yerdir.

S-8) Yanlışlıkla silinen commit'i nasıl bulursunuz ?
C-8) Önce  `git reflog` reflog ile HEAD hareketine bakarım
-


S-9) Git'te iyi bir commit nasıl olmalıdır ?
C-9) İyi bir Commit;
- Atomik değişikliği anlatmalı
- Kısa ve açıklayıcı olsun
- İzlenebilir olmalı
- Mümkünse birbiriyle ilişkisi olayan modüller gönderelim.


S-10) pull request neden önemli ?
C-10) pull request
- Kod inceleme
- Kalite kapısı
- Bilgi paylaşımı
- Audit izi sağlar


S-11) Git ile CI/CD ilişkisi nedir ?
C-11) Branch, tag ve merge olayları pipeline'lar ile tetiklenir.


S-12) Branch ile Tag arasındaki farklar ?
C-12)
- Branch: Hareketlidir Yeni commit geldikçe ilerler
- Tag   : Çoğunlukla sabittir ve release(sürüm) noktasını işaret eder.


S-13) Detached HEAD nednen tehlikelidir ?
C-13) Branch üstünde değil doğrudan commit üstünden çalışdığı için yeni commitlerin sonradan kaybolması gibi görüğlebilir.
- Yani Branch oluşturmadan bıraklırsa takip zorlaşır.

S-14) Cherry-pick ile merge arasındaki fark nedir ?
C-14) Cherry-pick - Merge;
- Cherry-pick: Seçilen comiti tekil taşır.
- Merge      : Dal geçmişini bütünsel olarak entegre eder.


S-15) Conflict sonrasında yapılaması gerekenler nelerdir ?
C-15)
-
-
-
-
-


S-16) Merge conflict yaşamamak için ne yaparsınız ?
C-16)
-
-
-
-
-



S-17) Force Push hangi koşulalrda kabul edilir ?
C-17)
-
-
-
-
-


S-18) Büyük binary dosyalar için standart Git yeterli midir  ?
C-18)
-
-
-
-
-


S-19) Conflict sonrası işlemler
C-19)
- git pull origin main
- git add .
- git commit -m "Conflict çözüldü"



S-20) Git komutlarını açıklayınız ?
C-20) Aşağıda verilen komutların ne işe yaradığını yazınız ?
- git config --global user.name "Hamit Mızrak"
- git config --global user.name
- git config --global user.email "hamitmizrak@gmail.com"
- git config --global user.email
- git config --list
-
- git init
- git clone <URL>
- git status

- git add <DOSYA_ADI>
- git add .
- git add -A             ==> Silinen, değişen ve yeni eklenen tüm dosyaları stage eder
- git add -u             ==> Sadece takip edilen dosyadaki değişiklikleri stage eder yani yeni dosyaları almaz

- git commit -m "Login validation bug fixed"
- git commit -am "Dosya eklemeden add ve message"
- git commit --amend                      ==> Son commitin düzeltir veya commit içeriğini güncellemek
- git commit --amend -m "Mesaj değiştir"  ==> Son commitin mesajını düzeltir

- git log
- git log --decorate --oneline --graph --all

- git log
- git show 5a2227030
- git diff          ==> Stage edilmemiş(-) değişiklileri gösterir.
- git diff --staged ==> Stage edilmiş(+) henüz commit edilmemiş()

- git grep "arama"              ==> Proje içinde metin veya ifade arar
- git rm <DOSYA_ADI>            ==> Hem git hemde klasörden kaldırılır
- git rm --cached  <DOSYA_ADI>  ==> Dosyayı silmeden sadece Git takibini çıkarır.
examples: git rm --cached .env

- git mv <ESKI_DOSYA_ADI>  <YENI_DOSYA_ADI>  ==> Hem dosya adını hemde yerini değiştirmek istersek


- git bisect start       ==> Hatanı hangi commit'e geldiğini bulmak için binary search sürecini başlatır
- git bisect good        ==> Sorunsuz commit'i işaretler
- git bisect bad         ==> Hatalı  commit'i işaretler
- git bisect reset       ==> Bisect işlemini bitirir ve nomral duruma döner

- git shortlog           ==> Commit katkı özetini gösterir
- git shortlog -sn       ==> Kimin kaç commit attığını sayısal oalrak gösterir

- git blame _1_Devops//_1_git/GitTutorials.py ==> Dosyanın satıurlarını en son hangi commit ve kişi bilgisini gösterir
- git reflog  ==> HEAD hareketlerini geçmişini gösterir Yanlış reset veya silinen branch kurtamadan çok önemlidir.

- git branch                     ==> Branch listele
- git branch Backend             ==> Yeni bir branch oluştur.

- git branch -d Backend          ==> Merge edilmiş branch'i siler
- git branch -D Backend          ==> Merge edilsin veya edilmesin ZORLA siler

- git branch -m main             ==> Aktif branchin adını değiştirmek

- git branch -a                  ==> Local ve remote branch'leri listeler

- git checkout MicroService      ==> Eski ama hala çok kullanılan branch değiştirme komutudur.
- git checkout -b  Microservice  ==> Eski kullanımda ve Yeni branch oluştur ve o branche dallan

- git switch Backend             ==> Var olan branch'e geçiş yapılır
- git switch -c Frontend         ==> Yeni branch oluştur ve o branche dallan

- git add .
- git commit -m "backend datası"
- git switch master
- git merge Backend           ==> Ana branch ile "Backend" brachi bağlarız
- git merge --no-ff Backend   ==> Branch geçmişini görünür kılmak istiyorsak

- git rebase Frontend         ==> Daha temiz doğrusal bir geçmiş
- git rebase -i HEAD~3        ==> Son 3 commit üzerinde interactive rebase yapar

- git cherry-pick <commit>    ==> Belirli bir commit'i bir branch'e taşımak için kullanırız.

- git remote -v                 ==> UZak depo bağlantılarını gösterir
- git remote add origin <URL>   ==> Uzak depo ekler
- git remote remove origin      ==> Uzak depo silmek
- git remote set-url origin <URL>   ==> Uzak depo URL değiştirmek

- git fetch                    ==> Uzak depo değişikliği indirir, ancak branch'e UYGULAMAZ
- git pull                     ==> Uzak depo değişikliği indirir, ancak branch'e UYGULAR
- git pull origin main
- git pull --rebase

- git push
- git push origin main
- git push -u origin main
- git push --force              ==> Uzak branch'i zorla ezerek gönderirir (Tehlikelidir)
- git push --force-with-lease   ==> Daha güvenlidir. Yani başkasınını yeni commitini ezmemeye çalışır

- git stash       ==> O anda ki değişiklikleri geçici olarak saklamaya yarar
- git stash save "yarım kalan login işlemi"
- git stash list   ==> Kaydedilmiş stash listesini gösterir.
- git stash apply  ==> Son stash'ı geri getirir ama stash kaydını SİLMEZZZZZ
- git stash pop    ==> Son stash'ı geri getirir ama stash kaydını SİLER
- git stash list
- git stash drop stash@{0}
- git stash clear  ==> Tüm stash kayıtlarını siler


- git tag         ==> Etiketleri listeler
- git tag v1.0.0  ==> v1.0.0 adında tag oluştur
- git tag -a v1.0.0 -m "ilk versiyon"
- git push origin v1.0.0 ==> Belirli tag'ı uzak repository göndermek
- git push origin --tags ==> Tüm  tag'leri  uzak repository göndermek


- git restore --staged <DOSYA>  ==> Stage'e alınmış (git add) dosyayı stage'ten çıkarır
- git restore          <DOSYA>  ==> Dosyada commit edilmemiş değişiklikleri geri alır

- git reset --soft  HEAD~1  ==> Son commit'i geri alır ama değişiklikleri staged bırakır
- git reset --mixed HEAD~1  ==> Son commit'i geri alır ama değişiklikleri staged dışına çıkarır (DEFAULT)
- git reset --hard  HEAD~1  ==> Son commit'i geri alır ve tüm değişiklikleri siler (TEHLIKELIDIR)

"""