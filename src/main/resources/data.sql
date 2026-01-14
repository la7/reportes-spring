-- data.sql
--INSERT INTO reportes (usuario_id, estado, s3_url, fecha_creacion)
--VALUES (99, 'COMPLETED', 'https://bucket-dummy.s3.aws/fake.csv', NOW());

-- Los nombres de columnas deben coincidir con lo que Hibernate genera (snake_case)
-- id, fecha_creacion, estado, s3_url, usuario_id

INSERT INTO reportes (usuario_id, estado, s3_url, fecha_creacion)
VALUES (101, 'PENDING', NULL, CURRENT_TIMESTAMP());

INSERT INTO reportes (usuario_id, estado, s3_url, fecha_creacion)
VALUES (102, 'COMPLETED', 'https://bucket-dummy.s3.amazonaws.com/reporte_102.csv', CURRENT_TIMESTAMP());

INSERT INTO reportes (usuario_id, estado, s3_url, fecha_creacion)
VALUES (103, 'FAILED', NULL, CURRENT_TIMESTAMP());