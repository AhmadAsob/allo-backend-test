# Allo Bank Backend Developer Take-Home Test

## Deskripsi Singkat
Aplikasi ini adalah Spring Boot REST API yang menggabungkan data dari beberapa resource eksternal Frankfurter Exchange Rate API. Fokus utama adalah data mata uang Rupiah (IDR). API ini menerapkan **Strategy Pattern** untuk menangani tiga jenis resource secara dinamis.

## Endpoint
**GET /api/finance/data/{resourceType}**

Parameter `resourceType` dapat diisi dengan:
- `latest_idr_rates` → Data kurs terbaru IDR, dilengkapi dengan `USD_BuySpread_IDR`.
- `historical_idr_usd` → Data historis kurs IDR ke USD.
- `supported_currencies` → Daftar kode mata uang yang didukung.

**Contoh cURL:**
```bash
curl -X GET http://localhost:8080/api/finance/data/latest_idr_rates
curl -X GET http://localhost:8080/api/finance/data/historical_idr_usd
curl -X GET http://localhost:8080/api/finance/data/supported_currencies
