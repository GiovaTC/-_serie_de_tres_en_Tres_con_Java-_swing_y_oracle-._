CREATE TABLE serie_numeros (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    numero NUMBER NOT NULL
);

CREATE OR REPLACE PROCEDURE insertar_numero(p_numero IN NUMBER) AS
BEGIN
    INSERT INTO serie_numeros(numero) VALUES(p_numero);
END;
/

COMMIT;