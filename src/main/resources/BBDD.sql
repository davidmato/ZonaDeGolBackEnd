drop table trofeo;
drop table entrenador;
drop table clasificacion;
drop table equipo_temporada;
drop table equipo_liga;
drop table equipo_jugador;
drop table noticias;
drop table jornada;
drop table estadisticas;
drop table temporada;
drop table liga;
drop table equipo;
drop table jugador;
drop table token_acceso;
drop table usuario;



create table usuario(
                        id serial primary key,
                        username varchar(250) not null,
                        password varchar (800) not null,
                        correo varchar (200) not null,
                        rol int not null
);

create table token_acceso (
                              id serial primary key,
                              token varchar(500) not null,
                              fecha_expiracion timestamp not null,
                              id_usuario int not null,
                              constraint fk_token_acceso_usuario foreign key(id_usuario) references usuario(id)
);


create table jugador(
                        id serial primary key,
                        nombre varchar(100) not null,
                        apellido varchar(250) not null,
                        dorsal int not null,
                        imagen varchar(800),
                        fecha_nacimiento timestamp not null,
                        posicion int not null,
                        dni char(9) not null,
                        id_usuario int not null,
                        constraint fk_jugador_usuario foreign key(id_usuario) references usuario(id)
);


create table entrenador(
                           id serial primary key,
                           nombre varchar(100) not null,
                           apellido varchar(250) not null,
                           imagen varchar(800) not null,
                           dni char(9) not null,
                           fecha_nacimiento timestamp not null,
                           id_usuario int not null,
                           constraint fk_entrenador_usuario foreign key(id_usuario) references usuario(id)
);

create table equipo(
                       id serial primary key,
                       nombre varchar(200) not null,
                       descripcion varchar(700) not null,
                       fecha_fundacion timestamp not null,
                       imagen varchar(800) not null,
                       id_entrenador int not null,
                       constraint fk_equipo_entrenador foreign key(id_entrenador) references entrenador(id)
);

create table trofeo(
                       id serial primary key,
                       nombre varchar(300) not null,
                       imagen varchar(800) not null
);

create table liga(
                     id serial primary key,
                     nombre varchar(200) not null,
                     num_equipos int not null,
                     descripcion varchar(700) not null,
                     fecha_fundacion timestamp not null,
                     id_trofeo int not null,
                     constraint fk_liga_trofeo foreign key(id_trofeo) references trofeo(id)
);



create table noticias(
                         id serial primary key,
                         imagen varchar(800),
                         titulo varchar(450) not null,
                         descripcion varchar(1000) not null
);

create table temporada(
                          id serial primary key,
                          fecha_inicio timestamp not null,
                          fecha_fin timestamp not null,
                          id_liga int not null,
                          constraint fk_temporada_liga foreign key(id_liga) references liga(id)
);

create table jornada(
                        id serial primary key,
                        fecha timestamp not null,
                        id_equipo_local int not null,
                        constraint fk_jornada_equipo_local foreign key(id_equipo_local) references equipo(id),
                        id_equipo_visitante int not null,
                        constraint fk_jornada_equipo_visitante foreign key(id_equipo_visitante) references equipo(id),
                        id_temporada INT NOT null,
                        CONSTRAINT fk_jornada_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id)
);

create table estadisticas(
                             id serial primary key,
                             partidos_jugados int,
                             goles int,
                             asistencias int,
                             tarjetas_amarillas int,
                             tarjetas_rojas int,
                             porteria_cero int,
                             id_temporada INT NOT null,
                             CONSTRAINT fk_estadisticas_temporada FOREIGN KEY(id_temporada) REFERENCES temporada(id),
                             id_jugador int not null,
                             constraint fk_estadisticas_jugador foreign key (id_jugador) references jugador(id)
);


CREATE TABLE equipo_temporada (
                                  id SERIAL PRIMARY KEY,
                                  id_equipo INT NOT NULL,
                                  id_temporada INT NOT NULL,
                                  CONSTRAINT fk_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id),
                                  CONSTRAINT fk_temporada FOREIGN KEY (id_temporada) REFERENCES temporada(id)
);

CREATE TABLE equipo_jugador (
                                id SERIAL PRIMARY KEY,
                                id_equipo INT NOT NULL,
                                id_jugador INT NOT NULL,
                                CONSTRAINT fk_equipo FOREIGN KEY(id_equipo) REFERENCES equipo(id),
                                CONSTRAINT fk_jugador FOREIGN KEY(id_jugador) REFERENCES jugador(id)
);

CREATE TABLE equipo_liga (
                             id SERIAL PRIMARY KEY,
                             id_equipo INT NOT NULL,
                             id_liga INT NOT NULL,
                             id_temporada INT NOT NULL,
                             CONSTRAINT fk_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id),
                             CONSTRAINT fk_liga FOREIGN KEY (id_liga) REFERENCES liga(id),
                             CONSTRAINT fk_temporada FOREIGN KEY (id_temporada) REFERENCES temporada(id),
                             UNIQUE (id_equipo, id_liga, id_temporada)
);

CREATE TABLE clasificacion (
                               id SERIAL PRIMARY KEY,
                               id_equipo_liga INT NOT NULL,
                               puesto INT NOT NULL,
                               victorias INT NOT NULL,
                               empates INT NOT NULL,
                               derrotas INT NOT NULL,
                               goles_a_favor INT NOT NULL,
                               goles_en_contra INT NOT NULL,
                               goles_diferencia INT NOT NULL,
                               puntos INT NOT NULL,
                               CONSTRAINT fk_clasificacion_equipo_liga FOREIGN KEY (id_equipo_liga) REFERENCES equipo_liga(id)
);



