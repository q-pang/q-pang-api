#!/usr/bin/zsh

cd config-server && ./gradlew bootJar && cd ../
cd discovery-server && ./gradlew bootJar && cd ../
cd gateway-server && ./gradlew bootJar && cd ../
cd user-service && ./gradlew bootJar && cd ../
cd product-service && ./gradlew bootJar && cd ../
cd order-service && ./gradlew bootJar && cd ../
cd delivery-service && ./gradlew bootJar && cd ../