#!/usr/bin/zsh

cd config-server && ./gradlew bootJar -x test && cd ../
cd discovery-server && ./gradlew bootJar -x test && cd ../
cd gateway-server && ./gradlew bootJar -x test && cd ../
cd user-service && ./gradlew bootJar -x test && cd ../
cd product-service && ./gradlew bootJar -x test && cd ../
cd order-service && ./gradlew bootJar -x test && cd ../
cd delivery-service && ./gradlew bootJar -x test && cd ../