-- Eliminar tablas si existen
DROP TABLE IF EXISTS clasificacion;
DROP TABLE IF EXISTS estadisticas;
DROP TABLE IF EXISTS jornada;
DROP TABLE IF EXISTS temporada;
DROP TABLE IF EXISTS noticias;
DROP TABLE IF EXISTS jugador;
DROP TABLE IF EXISTS trofeo;
DROP TABLE IF EXISTS equipo;
DROP TABLE IF EXISTS liga;
DROP TABLE IF EXISTS entrenador;
DROP TABLE IF EXISTS token_acceso;
DROP TABLE IF EXISTS usuario;


-- Crear tablas
CREATE TABLE IF NOT EXISTS usuario(
id SERIAL PRIMARY KEY,
username VARCHAR(250) NOT NULL,
password VARCHAR (800) NOT NULL,
correo VARCHAR (200) NOT NULL,
rol INT NOT NULL,
pagado BOOLEAN NOT NULL,*
activo BOOLEAN NOT NULL,*
token_restablecimiento VARCHAR(500) NOT NULL,
token_expiracion TIMESTAMP NOT NULL,
fecha_registro TIMESTAMP NOT NULL,
);


CREATE TABLE IF NOT EXISTS token_acceso (
id SERIAL PRIMARY KEY,
token VARCHAR(500) NOT NULL,
fecha_expiracion TIMESTAMP NOT NULL,
id_usuario INT NOT NULL,
CONSTRAINT fk_token_acceso_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);


CREATE TABLE IF NOT EXISTS entrenador(
id SERIAL PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
apellido VARCHAR(250) NOT NULL,
imagen VARCHAR(800) NOT NULL,
dni CHAR(9),
fecha_nacimiento TIMESTAMP NOT NULL,
id_usuario INT NOT NULL,
CONSTRAINT fk_entrenador_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);


CREATE TABLE IF NOT EXISTS liga(
id SERIAL PRIMARY KEY,
nombre VARCHAR(200) NOT NULL,
num_equipos INT NOT NULL,
descripcion VARCHAR(700) NOT NULL,
fecha_fundacion TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS equipo(
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


CREATE TABLE IF NOT EXISTS trofeo(
id SERIAL PRIMARY KEY,
nombre VARCHAR(300) NOT NULL,
imagen VARCHAR(800) NOT NULL,
id_liga INT NOT NULL,
CONSTRAINT fk_trofeo_liga FOREIGN KEY(id_liga) REFERENCES liga(id),
id_equipo INT NOT NULL,
CONSTRAINT fk_trofeo_equipo FOREIGN KEY(id_equipo) REFERENCES equipo(id)
);


CREATE TABLE IF NOT EXISTS jugador(
id SERIAL PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
apellido VARCHAR(250) NOT NULL,
dorsal INT NOT NULL,
imagen VARCHAR(800),
fecha_nacimiento TIMESTAMP NOT NULL,
posicion INT NOT NULL,
dni CHAR(9) NOT NULL,
expulsado BOOLEAN NOT NULL,*
activo BOOLEAN NOT NULL,*
id_usuario INT NOT NULL,
CONSTRAINT fk_jugador_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id),
id_equipo INT NOT NULL,
CONSTRAINT fk_jugador_equipo FOREIGN KEY(id_equipo) REFERENCES equipo(id)
);


CREATE TABLE IF NOT EXISTS noticias(
id SERIAL PRIMARY KEY,
imagen VARCHAR(800),
titulo VARCHAR(450) NOT NULL,
descripcion VARCHAR(1000) NOT NULL
);


CREATE TABLE IF NOT EXISTS temporada(
id SERIAL PRIMARY KEY,
fecha_inicio TIMESTAMP NOT NULL,
fecha_fin TIMESTAMP NOT NULL,
id_liga INT NOT NULL,
CONSTRAINT fk_temporada_liga FOREIGN KEY(id_liga) REFERENCES liga(id)
);


CREATE TABLE IF NOT EXISTS jornada(
id SERIAL PRIMARY KEY,
gol_local INT NOT NULL,
gol_visitante INT NOT NULL,
fecha TIMESTAMP NOT NULL,
id_equipo_local INT NOT NULL,
CONSTRAINT fk_jornada_equipo_local FOREIGN KEY(id_equipo_local) REFERENCES equipo(id),
id_equipo_visitante INT NOT NULL,
CONSTRAINT fk_jornada_equipo_visitante FOREIGN KEY(id_equipo_visitante) REFERENCES equipo(id),
id_temporada INT NOT NULL,
CONSTRAINT fk_jornada_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id)
);


CREATE TABLE IF NOT EXISTS estadisticas(
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
CONSTRAINT fk_estadisticas_jugador FOREIGN KEY (id_jugador) REFERENCES jugador(id)
);


CREATE TABLE IF NOT EXISTS clasificacion (
 id SERIAL PRIMARY KEY,
 puesto INT NOT NULL,
 victorias INT NOT NULL,
 empates INT NOT NULL,
 derrotas INT NOT NULL,
 goles_a_favor INT NOT NULL,
 goles_en_contra INT NOT NULL,
 goles_diferencia INT NOT NULL,
 puntos INT NOT NULL,
 partidos_jugados INT NOT NULL,
 id_equipo INT NOT NULL,
 CONSTRAINT fk_datos_equipo_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id),
id_temporada INT NOT NULL,
CONSTRAINT fk_datos_equipo_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id)
);

create table temporada_liga(
id serial primary key,
id_temporada int not null,
constraint fk_temporada_liga_temporada foreign key(id_temporada) references temporada(id),
id_liga int not null,
constraint fk_temporada_liga_liga foreign key(id_liga) references liga(id)
);

