# Decision-Tree
C4.5 ve ID3 algoritmalarına sahip, ağaç çizme özelliğine sahip, csv ve arff datasetlerini okuma özelliğine sahip karar ağacı uygulaması.

Arayüz içerisinden CSV ve Arrf dosya türündeki datsetleri seçme özelliğine sahiptir.
Seçilen dosyadaki veri türüne göre ilgili performansdaki algoritmayı kullanarak karar ağacını oluşturma özelliğine sahiptir.
Ağaçta ön budama özelliği bulunmaktadır. Kullancıdan  ön budama için threshold değeri alır. 
Datasetin içeriği verilerin türüne göre algoritmayı belirler. C4.5 algoritması sayısal verileri daha iyi işlediğinden dataset içerisinde sayısal veriler var ise C4.5 algoritması işletilir.
Eğer Dataset içerisinde sadece sözel veriler var ise ID3 algoritması işletilir.
Datasetlerdeki boş veriler işleme alınmaz.
Datasetin %75'i train %25'i test olarak kullanılmaktadır.
