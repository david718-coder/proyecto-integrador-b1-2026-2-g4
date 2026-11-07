INSERT INTO usuarios (nombre, apellido, correo, telefono, direccion, rol, estado) VALUES
 ('Laura',  'Gómez',   'laura@example.com',  '3001112233', 'Cra 50 #40-10, Bello', 'usuario', 'activo'),
 ('Carlos', 'Pérez',   'carlos@example.com', '3004445566', 'Calle 45 #30-22, Bello', 'admin',  'activo'),
 ('Refugio', 'Patitas', 'patitas@example.com', '3007778899', 'Zamora, Bello',         'refugio', 'activo');

INSERT INTO mascotas (nombre, tipo, raza, tamano, descripcion, id_usuario) VALUES
 ('Max',   'perro', 'Golden Retriever', 'grande',  'Pelaje dorado, collar rojo', 1),
 ('Luna',  'gato',  'Criollo',          'pequeño', 'Gata gris con manchas blancas', 1),
 (NULL,    'perro', NULL,               'mediano', 'Perro café encontrado cerca del parque', 3);

INSERT INTO reportes (id_mascota, id_usuario, estado, barrio, latitud, longitud, contacto_publico, estado_publicacion) VALUES
 (1, 1, 'perdida',     'Niquía',   6.3373, -75.5579, 'WhatsApp 3001112233', 'activo'),
 (3, 3, 'encontrada',  'Zamora',   6.3410, -75.5530, 'WhatsApp 3007778899', 'pendiente');

INSERT INTO fotos_reporte (id_reporte, url_foto) VALUES
 (1, 'https://example.com/fotos/max-1.jpg'),
 (1, 'https://example.com/fotos/max-2.jpg');

INSERT INTO avistamientos (id_reporte, id_usuario, descripcion, barrio, latitud, longitud) VALUES
 (1, 2, 'Lo vi caminando cerca de la estación del metro', 'Niquía', 6.3380, -75.5590);
