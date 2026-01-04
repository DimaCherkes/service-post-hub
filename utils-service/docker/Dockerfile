FROM openjdk:23-ea-21-oraclelinux9

WORKDIR /srv

COPY target/*.jar .
COPY docker/utils-service.sh .

RUN chmod +x utils-service.sh

EXPOSE 8185

ENTRYPOINT ["./utils-service.sh"]
