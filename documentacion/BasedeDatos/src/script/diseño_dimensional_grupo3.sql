
CREATE TABLE public.DIM_INDICADOR_ECONOMICO (
                sk_indicador_economico INTEGER NOT NULL,
                pk_indicador_economico INTEGER NOT NULL,
                indicador VARCHAR(50) NOT NULL,
                valido_desde DATE NOT NULL,
                valido_hasta DATE NOT NULL,
                version INTEGER NOT NULL,
                CONSTRAINT dim_indicador_economico_pk PRIMARY KEY (sk_indicador_economico)
);


CREATE TABLE public.DIM_INDICADOR_SOCIAL (
                sk_indicador_social INTEGER NOT NULL,
                pk_indicador_social INTEGER NOT NULL,
                indicador VARCHAR(50) NOT NULL,
                valido_desde DATE NOT NULL,
                valido_hasta DATE NOT NULL,
                version INTEGER NOT NULL,
                CONSTRAINT dim_indicador_social_pk PRIMARY KEY (sk_indicador_social)
);


CREATE TABLE public.DIM_PAIS (
                sk_pais INTEGER NOT NULL,
                pk_pais INTEGER NOT NULL,
                pais VARCHAR(50) NOT NULL,
                valido_desde DATE NOT NULL,
                valido_hasta DATE NOT NULL,
                version INTEGER NOT NULL,
                CONSTRAINT dim_pais_pk PRIMARY KEY (sk_pais)
);


CREATE TABLE public.FACT_PAISES (
                sk_pais INTEGER NOT NULL,
                sk_indicador_social INTEGER NOT NULL,
                sk_indicador_economico INTEGER NOT NULL,
                valor_indicador_economico NUMERIC(16,2) NOT NULL,
                valor_indicador_social NUMERIC(16,2) NOT NULL,
                CONSTRAINT fact_paises_pk PRIMARY KEY (sk_pais, sk_indicador_social, sk_indicador_economico)
);


ALTER TABLE public.FACT_PAISES ADD CONSTRAINT dim_indicador_economico_fact_paises_fk
FOREIGN KEY (sk_indicador_economico)
REFERENCES public.DIM_INDICADOR_ECONOMICO (sk_indicador_economico)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FACT_PAISES ADD CONSTRAINT dim_indicador_social_fact_paises_fk
FOREIGN KEY (sk_indicador_social)
REFERENCES public.DIM_INDICADOR_SOCIAL (sk_indicador_social)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FACT_PAISES ADD CONSTRAINT dim_pais_fact_paises_fk
FOREIGN KEY (sk_pais)
REFERENCES public.DIM_PAIS (sk_pais)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
