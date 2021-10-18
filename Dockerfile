FROM openjdk:11

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/spring_boot

COPY /target/cadastro-clientes*.jar cadastro-clientes.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 5005:5005
EXPOSE 8080:8080

CMD java ${ADDITIONAL_OPTS} -jar cadastro-clientes.jar --spring.profiles.active=${PROFILE}
