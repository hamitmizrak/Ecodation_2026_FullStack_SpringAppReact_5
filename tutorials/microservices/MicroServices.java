package com.hamitmizrak.microservices;

public class MicroServices {
    /*

S1-) Microservice Nedir ?
C1-) Tek bir uygulamayı, iş kabiliyetlerini etrafında ayrılmış ve bağımsız deploy edilebilir servisler kümesi.
- Her servis kendi procesinde çalışır. ve her bir proje kendi yaşam döngüsünde devam eder.
- Amaç sadece kodu bölmek değildir.

** Ne zaman tercih edilmez ?
- Düşük trafik
- Tek deployment ile çözülen bir kısım için Microservice kullanılmaz
- Küçük ekip
- CI/CD süreçleri maliyetlidir bunu düşünerek yapılmalı

S2-) Monolitic ve Microservice farkları
C2-)
    Monolitic;
        - Küçük ölçekli projelerde
        - Tek deployment edilebilirdir
        - Yönetmesi daha kolaydır
        - Proje büyüdükçe sorunluluk artar.

    Microservice;
     -Orta, büyük ölçekli projelerde : Microservice olmalıdır.
     -Dağıtık olarak çalışabilir.
     -Bağımsız framework vardır.

S2-) Microservice yapılarında sıklıkla kullanılan teknolojiler nelerdir ?
C2-) Microservice;
        1- Spring Cloud,
        2- Redis,
        3- Rest, GraphQL,
        4- Netflix OSS/Eureka,
        5- Kafka,
        6- RabbitMQ
        7- Docker,
        8- Kubernates,
        9- Security




S4-) Temel teknolojiler ve kategorik haritası açıklayınız ?
C4-) Bileşenler;

API Gateway :
    Amaç         : Tek giriş noktası, routing, auth, rate limiting
    Teknolojiler : Spring Cloud Gateway, NGINX


Service Discovery :
    Amaç         : Servislerin birbirini dinamik bulması
    Teknolojiler : Eureka, Zookeeper, Kubernates DNS, Zuul, Ribbon, Hystrix


Config :
    Amaç         : Merkezi configuration
    Teknolojiler : Spring Cloud Config


Sync Communication :
    Amaç         : İstek cevap iletişimi
    Teknolojiler : REST, GraphQL, gRPC


Cache/ Session:
    Amaç         : Hız, session, rate limiting
    Teknolojiler : REDIS


Async Messaging:
    Amaç: Event/ Queue tabanlı iletişim
    Teknolojiler : Kafka, RabbitMQ


Observability:
    Amaç: Trace, metrics, logs
    Teknolojiler: Prometheus, Grafana, Zipkin


Resilience:
    Amaç : Circuit braker
    Teknoloji: Spring Cloud Circuit Breaker


S5-) Cache/ Session olan Redis'i açıklayınız ?
C5-) Redis;
        - in-memory veri yapısı sunucusudur.
        - Cache, session store, rate limiting gibi işlerde çok kullanılır
        - Avantajı: Çok düşük geçikme
        - Riski : Yanlış yerde ana veri deposu gibi kullanılırsa veri kalıcığı ve tutarlılık problemi meydana gelir.
        - Redis veri tipleri sayesinde (string, hash, list, set, sorted set, bitmap) verileri rahatlıkla tutulabilinir.



S6-) Sync Communication  olan GraphQL'i açıklayınız ?
C6-) GraphQL;
        - Modern API'ler için sorgu dilidir.
        - İstemci(Request) ihtiyacı olan alanları seçer
        - Mobil/web istemcinin farklı veri ihtiyaçlarını tek endpoint üzerinden yönetmek için kullanılır.


S7-) Service Discovery  olan Eureka'i açıklayınız ?
C7-) Eureka;
        - Service discovery olan yapılar Servisler registry'e kayıt olur ve diğer servisler bu kayıt sayesinde yani registry'den birbirini bulur.
        - Günecl Spring Cloud ekosiseminde Eureka hala sıklıkla kulllanılır.


S8-) API Gateway'i açıklayınız ?
C8-) API Gateway;
        - İstenci ile servisler arasındaki giriş kapısıdır.
        - Routing, auth, CORS, rate limiting, logging vb alanlar için merkezi yapıları oluşturur.
        - Neden gerekli ? : Çünkü istemcinin her servisi doğrudan bilmesi sistemi yorar.
        - NGINX, Kong

S9-) Configuration Management sistem hakkında bilgiler veriniz ?
C9-) Configuration Management :
        - Dağıtık sistemlerde config kod içine eklemek sıkıntıdır.
        - URL, secret, timeout, frofile vb bu ayarlar merkezi yönetilmelidir.
        - Spring Cloud Config bu iş için çok uygundur.
        - Config server genellikle "Git" tabanlı merkezi konfigurasonu sağlar


S10-) Messaging Kafka, RabbitMQ hakkında bilgiler veriniz ?
C10-) Kafka, RabbitMQ :
        - RabbitMQ: İş emri, task,, routing
        - Kafka: Log/stream merkezli bir yapıdadır.
      İhtiyaca göre Event streaming ve yüksek hacimli akışlarda ==> Kafka
      Routing Job tabanlı kullanımlarda      ==>RabbitMQ


S11-) Security hakkında bilgiler veriniz ?
C11-) Security :
        - İç servisler arası güven sadece dış kullanıcı token'lara bırakılmaz.
        - JWT, OAuth2, policy
        - Keycloak kimlik çözümleri olarak kullanılıe.


S12-) Resilience Patterns hakkında bilgiler veriniz ?
C12-) Resilience Patterns :
        - Network çağrısı başarısız: timeout, retry storm, failure olabilir.
        - Bu yüzden circuit breaker, Resilence4j gibi çözümler microservis'lerde sıklıkla kullanılır.


















     */
}
