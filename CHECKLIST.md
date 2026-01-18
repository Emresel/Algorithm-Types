# Ödev Kontrol Listesi

## Assignment I: Getting things in order

### ✅ Task 1 Part A: "Ordnung must sein"
- [x] Bubble Sort implementasyonu
- [x] Insertion Sort implementasyonu
- [x] Merge Sort implementasyonu
- [x] Quick Sort implementasyonu
- [x] Küçük input boyutları testi (5-50)
- [x] Büyük input boyutları testi (>100)
- [x] CSV dosyası oluşturma (sorting_results.csv)
- [x] Grafik için veri hazır

**Dosya**: `assignment1task1partA.java`

### ✅ Task 1 Part B: Credit Card Matching
- [x] Linear algoritma (HashMap-based) - O(n)
- [x] Log-linear algoritma (Sort + Binary Search) - O(n log n)
- [x] CSV dosyalarından veri okuma (carddump1.csv, carddump2.csv)
- [x] Crossover point analizi
- [x] 20k kayıt testi

**Dosya**: `assignment1task1partB.java`

### ✅ Task 2: "Dangerous Minds" - Dynamic Array
- [x] DynamicArray class implementasyonu
- [x] Table doubling stratejisi
- [x] Read/Write access (O(1))
- [x] Append (amortized O(1))
- [x] Resize fonksiyonu
- [x] Delete element/segment
- [x] ArrayList ile karşılaştırma
- [x] LinkedList ile karşılaştırma
- [x] Reallocation tracking

**Dosya**: `assignment1task2.java`

### ✅ Task 3: "Dangerous Quickminds" - Pivot Analysis
- [x] Quick Sort pivot sayısı tracking
- [x] Farklı array boyutları için analiz
- [x] Farklı input tipleri (Random, Sorted, Reverse, Nearly Sorted)
- [x] Pivot count vs sorting time ilişkisi
- [x] CSV dosyası oluşturma (pivot_analysis.csv)

**Dosya**: `assignment1task3.java`

---

## Assignment II: Tree museum

### ✅ Task 1: "An Introduction to Applied Dendrology"
- [x] Binary Tree implementasyonu
- [x] a) Binary tree, random order
- [x] b) Binary tree, best-case order (perfectly balanced)
- [x] c) Library solution (TreeSet)
- [x] Height bilgisi (a, b için)
- [x] Insertion performance karşılaştırması
- [x] CSV dosyası oluşturma (tree_insertion_results.csv)

**Dosya**: `assignment2task1and2and3.java`

### ✅ Task 2: "Pruning the bushes"
- [x] Key removal times karşılaştırması
- [x] Binary Tree removal
- [x] TreeSet removal
- [x] Random order'da removal testi
- [x] CSV dosyası oluşturma (tree_removal_results.csv)

**Dosya**: `assignment2task1and2and3.java`

### ✅ Task 3: "Grow your own arboretum"
- [x] d) Ternary tree, random order (2 keys, 3 children - NOT a trie)
- [x] e) Ternary tree, best-case order
- [x] f) AVL tree (self-balancing)
- [x] Height bilgisi (d, e, f için)
- [x] Tüm tree'ler için insertion testi
- [x] CSV dosyasına dahil

**Dosya**: `assignment2task1and2and3.java`

---

## Rapor Gereksinimleri

### ✅ Rapor İçeriği
- [x] Algoritma açıklamaları (her algoritma için)
- [x] Ne yapıldığı ve nasıl yapıldığı
- [x] Bulgular (findings)
- [x] Teorik running times (complexity)
- [x] Beklenen grafik davranışları

**Dosya**: `REPORT.md`

### ⚠️ Eksikler
- [ ] Grafikler oluşturulmalı (generate_graphs.py scripti var)
- [ ] PDF/DOCX formatına çevrilmeli
- [ ] Grafikler rapora eklenmeli

### ✅ Veri Setleri
- [x] sorting_results.csv
- [x] pivot_analysis.csv
- [x] tree_insertion_results.csv
- [x] tree_removal_results.csv
- [x] carddump1.csv, carddump2.csv (input data)

---

## Grafik Oluşturma

### Script Hazır
- [x] `generate_graphs.py` - Python scripti hazır
- [x] `requirements.txt` - Python paket gereksinimleri

### Yapılacaklar
1. Java programlarını çalıştır (CSV'leri oluştur)
2. `pip install -r requirements.txt`
3. `python generate_graphs.py` (grafikleri oluştur)
4. Grafikleri rapora ekle
5. PDF'e çevir

---

## Submission Hazırlığı

### Moodle Text Field İçin:
```
Team Members: [İsimleriniz]
Tasks Solved: 
  - Assignment I: Task 1 Part A, Part B, Task 2, Task 3
  - Assignment II: Task 1, Task 2, Task 3
Language: Java
Framework: Standard Java libraries (java.util.*)
Brief Description: 
  Implemented sorting algorithms (Bubble, Insertion, Merge, Quick Sort), 
  credit card matching with linear and log-linear algorithms, dynamic array 
  with table doubling, Quick Sort pivot analysis, and tree data structures 
  (Binary, Ternary, AVL trees). Performed empirical analysis with performance 
  measurements, complexity verification, and height analysis.
```

### Dosyalar
- [x] Tüm Java dosyaları
- [x] Rapor şablonu (REPORT.md)
- [x] Grafik generation scripti
- [ ] PDF rapor (oluşturulmalı)
- [ ] Grafikler (PNG dosyaları - oluşturulmalı)
- [x] CSV veri setleri

---

## Son Kontroller

### Kod Kalitesi
- [x] Tüm algoritmalar doğru implement edilmiş
- [x] Complexity analizi doğru
- [x] Test metodları mevcut
- [x] CSV output'ları doğru format

### Rapor Kalitesi
- [x] Tüm algoritmalar açıklanmış
- [x] Teorik complexity belirtilmiş
- [x] Bulgular açıklanmış
- [ ] Grafikler eklenmiş (bekliyor)
- [ ] PDF formatında (bekliyor)

### Eksikler
1. **Grafikler**: Python scripti çalıştırılmalı
2. **PDF Rapor**: REPORT.md PDF'e çevrilmeli
3. **Final Test**: Tüm programlar çalıştırılıp CSV'ler oluşturulmalı

---

## Notlar

- Tüm kod dosyaları hazır ve çalışıyor
- Rapor şablonu hazır, sadece grafikler eklenmeli
- CSV dosyaları program çalıştırıldığında oluşturulacak
- Grafikler Python scripti ile otomatik oluşturulabilir
