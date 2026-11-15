-- Datos locales de demostración. Se cargan al iniciar la aplicación en la base H2 en memoria.
INSERT INTO AREA (id, nombre, descripcion, estado) VALUES
(1, 'Obras Públicas', 'Gestión de obras e infraestructura', 'Activo'),
(2, 'Rentas', 'Administración tributaria municipal', 'Activo'),
(3, 'Desarrollo Urbano', 'Licencias y permisos urbanos', 'Activo');

INSERT INTO CATEGORIA (id, nombre, plazoAtencion, estado, area_id) VALUES
(1, 'Licencia de funcionamiento', 15, 'Activo', 2),
(2, 'Permiso de construcción', 30, 'Activo', 1),
(3, 'Certificado de parámetros', 20, 'Activo', 3);

-- Solo se crea una cuenta para iniciar sesión como administrador.
INSERT INTO USUARIO (id, nombre, correo, password, rol, estado, area_id) VALUES
(1, 'Administrador del Sistema', 'admin@gmail.com', '123456', 'Administrador', 'Activo', 1);

-- 15 expedientes ficticios con fechas entre el 15 de septiembre y el 25 de octubre de 2026.
-- Fechas escalonadas para mostrar variaciones semanales en los gráficos.
INSERT INTO EXPEDIENTE (id, folio, ciudadanoNombre, ciudadanoDni, ciudadanoContacto, fechaRegistro, fechaLimite, motivoReprogramacion, estado, categoria_id, areaActual_id, usuarioRegistro_id, usuarioUltimoCambio_id) VALUES
(1, 'EXP-LOCAL-001', 'Alejandro Navarro', '70000001', 'ciudadano1@correo.com', DATEADD('DAY', 0, DATE '2026-09-15'), DATEADD('DAY', 30, DATE '2026-09-15'), NULL, 'Pendiente', 1, 2, 1, 1),
(2, 'EXP-LOCAL-002', 'Jean Urquiaga', '70000002', 'ciudadano2@correo.com', DATEADD('DAY', 2, DATE '2026-09-15'), DATEADD('DAY', 32, DATE '2026-09-15'), NULL, 'Aprobado', 2, 1, 1, 1),
(3, 'EXP-LOCAL-003', 'Hector Lucero', '70000003', 'ciudadano3@correo.com', DATEADD('DAY', 5, DATE '2026-09-15'), DATEADD('DAY', 35, DATE '2026-09-15'), NULL, 'Observado', 3, 3, 1, 1),
(4, 'EXP-LOCAL-004', 'Zidane Diaz', '70000004', 'ciudadano4@correo.com', DATEADD('DAY', 7, DATE '2026-09-15'), DATEADD('DAY', 37, DATE '2026-09-15'), NULL, 'Rechazado', 1, 2, 1, 1),
(5, 'EXP-LOCAL-005', 'Lucia Campos', '70000005', 'ciudadano5@correo.com', DATEADD('DAY', 9, DATE '2026-09-15'), DATEADD('DAY', 39, DATE '2026-09-15'), NULL, 'Aprobado', 2, 1, 1, 1),
(6, 'EXP-LOCAL-006', 'Maria Rojas', '70000006', 'ciudadano6@correo.com', DATEADD('DAY', 12, DATE '2026-09-15'), DATEADD('DAY', 42, DATE '2026-09-15'), NULL, 'Observado', 3, 3, 1, 1),
(7, 'EXP-LOCAL-007', 'Pedro Salazar', '70000007', 'ciudadano7@correo.com', DATEADD('DAY', 15, DATE '2026-09-15'), DATEADD('DAY', 45, DATE '2026-09-15'), NULL, 'Rechazado', 1, 2, 1, 1),
(8, 'EXP-LOCAL-008', 'Daniela Torres', '70000008', 'ciudadano8@correo.com', DATEADD('DAY', 18, DATE '2026-09-15'), DATEADD('DAY', 48, DATE '2026-09-15'), NULL, 'Pendiente', 2, 1, 1, 1),
(9, 'EXP-LOCAL-009', 'Luis Mendoza', '70000009', 'ciudadano9@correo.com', DATEADD('DAY', 21, DATE '2026-09-15'), DATEADD('DAY', 51, DATE '2026-09-15'), NULL, 'Observado', 3, 3, 1, 1),
(10, 'EXP-LOCAL-010', 'Rosa Castillo', '70000010', 'ciudadano10@correo.com', DATEADD('DAY', 24, DATE '2026-09-15'), DATEADD('DAY', 54, DATE '2026-09-15'), NULL, 'Rechazado', 1, 2, 1, 1),
(11, 'EXP-LOCAL-011', 'Carlos Flores', '70000011', 'ciudadano11@correo.com', DATEADD('DAY', 27, DATE '2026-09-15'), DATEADD('DAY', 57, DATE '2026-09-15'), NULL, 'Pendiente', 2, 1, 1, 1),
(12, 'EXP-LOCAL-012', 'Ana Ramirez', '70000012', 'ciudadano12@correo.com', DATEADD('DAY', 30, DATE '2026-09-15'), DATEADD('DAY', 60, DATE '2026-09-15'), NULL, 'Aprobado', 3, 3, 1, 1),
(13, 'EXP-LOCAL-013', 'Jorge Paredes', '70000013', 'ciudadano13@correo.com', DATEADD('DAY', 33, DATE '2026-09-15'), DATEADD('DAY', 63, DATE '2026-09-15'), NULL, 'Rechazado', 1, 2, 1, 1),
(14, 'EXP-LOCAL-014', 'Sofia Vargas', '70000014', 'ciudadano14@correo.com', DATEADD('DAY', 36, DATE '2026-09-15'), DATEADD('DAY', 66, DATE '2026-09-15'), NULL, 'Pendiente', 2, 1, 1, 1),
(15, 'EXP-LOCAL-015', 'Miguel Ruiz', '70000015', 'ciudadano15@correo.com', DATEADD('DAY', 40, DATE '2026-09-15'), DATEADD('DAY', 70, DATE '2026-09-15'), NULL, 'Aprobado', 3, 3, 1, 1);
