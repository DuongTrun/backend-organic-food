# --- Giai đoạn 1: Build file .jar ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Lệnh này sẽ đóng gói code thành file .jar và bỏ qua bước test để đỡ lỗi vặt
RUN mvn clean package -DskipTests

# --- Giai đoạn 2: Chạy ứng dụng ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy file .jar từ giai đoạn 1 sang giai đoạn 2
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]