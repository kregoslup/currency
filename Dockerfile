FROM java:openjdk-8
WORKDIR /app
COPY build/libs/scraper-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD java -jar /app/app.jar \
--spring.datasource.url=$DB_URL \
--spring.datasource.username=$DB_USER \
--spring.datasource.password=$DB_PASS \
--circuit.breaker.failure.threshold=$CIRCUIT_BREAKER_FAILURE_THRESHOLD \
--circuit.breaker.delay=$CIRCUIT_BREAKER_DELAY \
--retry.sleep.interval=$RETRY_SLEEP_INTERVAL \
--retry.pool.size=$RETRY_POOL_SIZE \
--rest.template.connect.timeout=$REST_TEMPLATE_CONNECT_TIMEOUT \
--rest.template.read.timeout=$REST_TEMPLATE_READ_TIMEOUT \
--scraper.base_address=$SCRAPER_BASE_ADDRESS \
--scraper.rss_address=$SCRAPER_RSS_ADDRESS
