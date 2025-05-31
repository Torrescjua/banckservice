# Usa una imagen oficial de OpenJDK como base
FROM eclipse-temurin:17-jdk AS build

# Copia el WAR generado al contenedor
COPY target/banckservice-0.0.1-SNAPSHOT.war /app/banckservice.war

# Usa una imagen ligera de OpenJDK para producción
FROM eclipse-temurin:17-jre

# Crea el directorio de la app
WORKDIR /app

# Copia el WAR desde la etapa de build
COPY --from=build /app/banckservice.war .

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "banckservice.war"]