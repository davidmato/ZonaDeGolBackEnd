# Usa una imagen oficial de OpenJDK con Maven
FROM amazoncorretto:21-alpine-jdk

ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia Maven Wrapper correctamente
COPY .mvn/ .mvn/
COPY mvnw mvnw.cmd pom.xml ./

# Da permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Descarga las dependencias para mejorar la cacheabilidad
RUN ./mvnw dependency:resolve dependency:go-offline -Dfile.encoding=UTF-8

# Copia el código fuente
COPY src/ src/

# Compila la aplicación con codificación forzada a UTF-8
RUN ./mvnw clean package -DskipTests -Dfile.encoding=UTF-8

# Verifica que el JAR se haya generado
RUN ls -l target/

# Expone el puerto 8080
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-Dfile.encoding=UTF-8", "-jar", "target/safajobs-0.0.1-SNAPSHOT.jar"]
