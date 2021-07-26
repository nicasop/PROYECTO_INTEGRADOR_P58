
CREATE TABLE public.Indicadores_Economicos (
                id_indicador_economico INTEGER NOT NULL,
                nombre_indicador VARCHAR(50) NOT NULL,
                CONSTRAINT indicadores_economicos_pk PRIMARY KEY (id_indicador_economico)
);


CREATE TABLE public.Indicadores_Sociales (
                id_indicador_social INTEGER NOT NULL,
                nombre_indicador VARCHAR(50) NOT NULL,
                CONSTRAINT indicadores_sociales_pk PRIMARY KEY (id_indicador_social)
);


CREATE TABLE public.Tipo_Usuario (
                id_tipo INTEGER NOT NULL,
                nombre_tipo VARCHAR(20) NOT NULL,
                CONSTRAINT tipo_usuario_pk PRIMARY KEY (id_tipo)
);


CREATE TABLE public.Pais (
                id_pais INTEGER NOT NULL,
                nombre_pais VARCHAR(50) NOT NULL,
                CONSTRAINT pais_pk PRIMARY KEY (id_pais)
);


CREATE TABLE public.Usuario (
                id_usuario INTEGER NOT NULL,
                usuario VARCHAR(20) NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                email VARCHAR(50) NOT NULL,
                fecha_nacimiento DATE NOT NULL,
                genero VARCHAR(10) NOT NULL,
                contrasena VARCHAR(12) NOT NULL,
                id_tipo INTEGER NOT NULL,
                activo INTEGER NOT NULL,
                CONSTRAINT usuario_pk PRIMARY KEY (id_usuario)
);


CREATE TABLE public.Indicadores_Sociales_Valores (
                id_pais INTEGER NOT NULL,
                id_indicador_social INTEGER NOT NULL,
                valor_indicador NUMERIC(16,2) NOT NULL
);


CREATE TABLE public.Indicadores_Economicos_Valores (
                id_pais INTEGER NOT NULL,
                id_indicador_economico INTEGER NOT NULL,
                valor_indicador NUMERIC(16,2) NOT NULL
);


ALTER TABLE public.Indicadores_Economicos_Valores ADD CONSTRAINT incadores_ec_indcadores_economicos_fk
FOREIGN KEY (id_indicador_economico)
REFERENCES public.Indicadores_Economicos (id_indicador_economico)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Indicadores_Sociales_Valores ADD CONSTRAINT indicadores_so_indicadores_sociales_fk
FOREIGN KEY (id_indicador_social)
REFERENCES public.Indicadores_Sociales (id_indicador_social)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Usuario ADD CONSTRAINT tipo_usuario_usuario_fk
FOREIGN KEY (id_tipo)
REFERENCES public.Tipo_Usuario (id_tipo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Indicadores_Sociales_Valores ADD CONSTRAINT pais_indicadores_sociales_fk
FOREIGN KEY (id_pais)
REFERENCES public.Pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Indicadores_Economicos_Valores ADD CONSTRAINT pais_indcadores_economicos_fk
FOREIGN KEY (id_pais)
REFERENCES public.Pais (id_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
