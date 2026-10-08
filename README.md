# Pharmacy Service - Kafka Producer

Bài tập Ss10-Ex02: Triển khai Producer gửi sự kiện Đơn hàng thuốc.

## 1. Yêu cầu bài tập
- **Bối cảnh**: Khi nhân viên hiệu thuốc bấm "Thanh toán", hệ thống cần gửi một sự kiện chứa thông tin đơn hàng để các dịch vụ khác xử lý.
- **Yêu cầu kỹ thuật**:
  - Tại `pharmacy-service`, cấu hình `KafkaTemplate` để gửi dữ liệu dưới dạng JSON.
  - Tạo một lớp `OrderEvent` gồm: `orderId`, `medicineId`, `quantity`, `timestamp`.
  - Khi gọi API bán thuốc, Producer phải gửi đối tượng `OrderEvent` vào topic `medicine-stock-events`.
  - **Thử thách**: Sử dụng **Message Key là `medicineId`** để đảm bảo các đơn hàng của cùng một loại thuốc luôn đi vào cùng một Partition.
- **Kết quả mong muốn**:
  - Khi gọi API thanh toán thì `KafkaTemplate` sẽ gửi tin nhắn lên Kafka Broker và phản hồi thông tin thanh toán thành công.

## 2. Công nghệ sử dụng
- Java 17
- Spring Boot 3.4.3
- Spring Kafka
- Docker Compose (Kafka & Zookeeper)

## 3. Chạy Kafka Broker
Khởi chạy Kafka broker bằng Docker Compose:
```bash
docker-compose up -d
```

## 4. Chạy ứng dụng
```bash
mvn spring-boot:run
```

## 5. API Bán / Thanh toán thuốc (Checkout API)
- **Endpoint**: `POST http://localhost:8081/api/pharmacy/checkout`
- **Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "medicineId": "MED-PARACETAMOL-500",
  "quantity": 10
}
```
- **Response**:
```json
{
  "message": "Thanh toán thành công! Sự kiện đơn hàng thuốc đã được gửi vào Kafka topic 'medicine-stock-events'.",
  "orderEvent": {
    "orderId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "medicineId": "MED-PARACETAMOL-500",
    "quantity": 10,
    "timestamp": "2026-10-08T18:00:00"
  }
}
```

## 6. Kiểm tra Message trên Kafka
Lắng nghe topic `medicine-stock-events`:
```bash
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic medicine-stock-events --from-beginning --property print.key=true
```
Mỗi tin nhắn hiển thị có Key là `medicineId` (ví dụ `MED-PARACETAMOL-500`) và Value là JSON của `OrderEvent`.
