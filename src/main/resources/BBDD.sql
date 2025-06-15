DROP TABLE IF EXISTS clasificacion;
DROP TABLE IF EXISTS estadisticas;
DROP TABLE IF EXISTS jornada;
DROP TABLE IF EXISTS trofeo;
DROP TABLE IF EXISTS temporada_liga;
DROP TABLE IF EXISTS temporada;
DROP TABLE IF EXISTS jugador;
DROP TABLE IF EXISTS equipo;
DROP TABLE IF EXISTS noticias;
DROP TABLE IF EXISTS estadio;
DROP TABLE IF EXISTS arbitro;
DROP TABLE IF EXISTS entrenador;
DROP TABLE IF EXISTS liga;
DROP TABLE IF EXISTS token_acceso;
DROP TABLE IF EXISTS usuario;



CREATE TABLE IF NOT EXISTS usuario (
id SERIAL PRIMARY KEY,
username VARCHAR(250) NOT NULL,
password VARCHAR(800) NOT NULL,
correo VARCHAR(200) NOT NULL,
rol INT NOT NULL,
pagado BOOLEAN NOT NULL,
token_restablecimiento VARCHAR(500),
token_expiracion TIMESTAMP,
fecha_registro TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS entrenador (
id SERIAL PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
apellido VARCHAR(250) NOT NULL,
imagen VARCHAR(800) NOT NULL,
dni CHAR(9),
fecha_nacimiento TIMESTAMP NOT NULL,
id_usuario INT NOT NULL,
CONSTRAINT fk_entrenador_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS arbitro (
id SERIAL PRIMARY KEY,
nombre VARCHAR(50) NOT NULL,
apellidos VARCHAR(100) NOT NULL,
num_colegiado VARCHAR(100) NOT NULL,
dni VARCHAR(9) NOT NULL,
id_usuario INT NOT NULL,
CONSTRAINT fk_arbitro_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);

CREATE TABLE IF NOT EXISTS liga (
id SERIAL PRIMARY KEY,
nombre VARCHAR(200) NOT NULL,
num_equipos INT NOT NULL,
descripcion VARCHAR(700) NOT NULL,
fecha_fundacion TIMESTAMP NOT NULL,
imagen VARCHAR(800) NOT NULL
);

CREATE TABLE IF NOT EXISTS equipo (
id SERIAL PRIMARY KEY,
nombre VARCHAR(200) NOT NULL,
descripcion VARCHAR(700) NOT NULL,
fecha_fundacion TIMESTAMP NOT NULL,
imagen VARCHAR(800) NOT NULL,
id_liga INT NOT NULL,
CONSTRAINT fk_equipo_liga FOREIGN KEY(id_liga) REFERENCES liga(id),
id_entrenador INT NOT NULL,
CONSTRAINT fk_equipo_entrenador FOREIGN KEY(id_entrenador) REFERENCES entrenador(id)
);

CREATE TABLE IF NOT EXISTS noticias (
id SERIAL PRIMARY KEY,
imagen VARCHAR(800),
titulo VARCHAR(450) NOT NULL,
descripcion VARCHAR(1000) NOT NULL,
fecha TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS temporada (
id SERIAL PRIMARY KEY,
fecha_inicio TIMESTAMP NOT NULL,
fecha_fin TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS temporada_liga (
id SERIAL PRIMARY KEY,
id_temporada INT NOT NULL,
CONSTRAINT fk_temporada_liga_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id),
id_liga INT NOT NULL,
CONSTRAINT fk_temporada_liga_liga FOREIGN KEY(id_liga) REFERENCES liga(id)
);

CREATE TABLE IF NOT EXISTS trofeo (
id SERIAL PRIMARY KEY,
nombre VARCHAR(300) NOT NULL,
imagen VARCHAR(800) NOT NULL,
id_temporada_liga INT NOT NULL,
CONSTRAINT fk_trofeo_temporada_liga FOREIGN KEY(id_temporada_liga) REFERENCES temporada_liga(id)
);

CREATE TABLE IF NOT EXISTS jugador (
id SERIAL PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
apellido VARCHAR(250) NOT NULL,
dorsal INT NOT NULL,
imagen VARCHAR(800),
fecha_nacimiento TIMESTAMP NOT NULL,
posicion INT NOT NULL,
dni CHAR(9) NOT NULL,
expulsado BOOLEAN NOT NULL,
activo BOOLEAN NOT NULL,
id_usuario INT NOT NULL,
CONSTRAINT fk_jugador_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id),
id_equipo INT NOT NULL,
CONSTRAINT fk_jugador_equipo FOREIGN KEY(id_equipo) REFERENCES equipo(id)
);

CREATE TABLE IF NOT EXISTS estadio (
id SERIAL PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
direccion VARCHAR(500) NOT NULL,
aforo INT
);

CREATE TABLE IF NOT EXISTS jornada (
id SERIAL PRIMARY KEY,
gol_local INT NOT NULL,
gol_visitante INT NOT NULL,
fecha TIMESTAMP NOT NULL,
id_equipo_local INT NOT NULL,
CONSTRAINT fk_jornada_equipo_local FOREIGN KEY(id_equipo_local) REFERENCES equipo(id),
id_equipo_visitante INT NOT NULL,
CONSTRAINT fk_jornada_equipo_visitante FOREIGN KEY(id_equipo_visitante) REFERENCES equipo(id),
id_temporada INT NOT NULL,
CONSTRAINT fk_jornada_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id),
id_arbitro INT,
CONSTRAINT fk_jornada_arbitro FOREIGN KEY(id_arbitro) REFERENCES arbitro(id),
id_estadio INT NOT NULL,
CONSTRAINT fk_jornada_estadio FOREIGN KEY(id_estadio) REFERENCES estadio(id)
);

CREATE TABLE IF NOT EXISTS estadisticas (
id SERIAL PRIMARY KEY,
partidos_jugados INT,
goles INT,
asistencias INT,
tarjetas_amarillas INT,
tarjetas_rojas INT,
porteria_cero INT,
id_temporada INT NOT NULL,
CONSTRAINT fk_estadisticas_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id),
id_jugador INT NOT NULL,
CONSTRAINT fk_estadisticas_jugador FOREIGN KEY(id_jugador) REFERENCES jugador(id)
);

CREATE TABLE IF NOT EXISTS clasificacion (
id SERIAL PRIMARY KEY,
puesto INT NOT NULL,
partidos_jugados INT NOT NULL,
victorias INT NOT NULL,
empates INT NOT NULL,
derrotas INT NOT NULL,
goles_a_favor INT NOT NULL,
goles_en_contra INT NOT NULL,
goles_diferencia INT NOT NULL,
puntos INT NOT NULL,
id_equipo INT NOT NULL,
CONSTRAINT fk_datos_equipo_equipo FOREIGN KEY(id_equipo) REFERENCES equipo(id),
id_temporada INT NOT NULL,
CONSTRAINT fk_datos_equipo_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id)
);
