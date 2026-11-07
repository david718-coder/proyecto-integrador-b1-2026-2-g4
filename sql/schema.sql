DROP TABLE IF EXISTS avistamientos, fotos_reporte, reportes, mascotas, usuarios CASCADE;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre         VARCHAR(80)  NOT NULL,
    apellido       VARCHAR(80)  NOT NULL,
    correo         VARCHAR(120) NOT NULL UNIQUE,
    telefono       VARCHAR(20),
    direccion      VARCHAR(150),
    rol            VARCHAR(20)  NOT NULL DEFAULT 'usuario'
                   CHECK (rol IN ('admin', 'usuario', 'refugio')),
    estado         VARCHAR(20)  NOT NULL DEFAULT 'activo'
                   CHECK (estado IN ('activo', 'inactivo', 'baneado')),
    fecha_registro TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mascotas (
    id_mascota  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre      VARCHAR(80),
    tipo        VARCHAR(20) NOT NULL CHECK (tipo IN ('perro', 'gato', 'otro')),
    raza        VARCHAR(80),
    tamano      VARCHAR(20) NOT NULL CHECK (tamano IN ('pequeño', 'mediano', 'grande')),
    descripcion TEXT,
    id_usuario  BIGINT NOT NULL REFERENCES usuarios (id_usuario)
);

CREATE TABLE IF NOT EXISTS reportes (
    id_reporte         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_mascota         BIGINT NOT NULL REFERENCES mascotas (id_mascota),
    id_usuario         BIGINT NOT NULL REFERENCES usuarios (id_usuario),
    estado             VARCHAR(20)  NOT NULL
                       CHECK (estado IN ('perdida', 'encontrada', 'en_adopcion')),
    barrio             VARCHAR(80)  NOT NULL,
    latitud            DOUBLE PRECISION NOT NULL CHECK (latitud BETWEEN -90 AND 90),
    longitud           DOUBLE PRECISION NOT NULL CHECK (longitud BETWEEN -180 AND 180),
    fecha_reporte      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    contacto_publico   VARCHAR(120) NOT NULL,
    estado_publicacion VARCHAR(20)  NOT NULL DEFAULT 'pendiente'
                       CHECK (estado_publicacion IN ('pendiente', 'activo', 'cerrado', 'moderado'))
);

CREATE TABLE IF NOT EXISTS fotos_reporte (
    id_foto    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_reporte BIGINT NOT NULL REFERENCES reportes (id_reporte) ON DELETE CASCADE,
    url_foto   VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS avistamientos (
    id_avistamiento    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_reporte         BIGINT NOT NULL REFERENCES reportes (id_reporte),
    id_usuario         BIGINT NOT NULL REFERENCES usuarios (id_usuario),
    descripcion        TEXT NOT NULL,
    barrio             VARCHAR(80) NOT NULL,
    latitud            DOUBLE PRECISION NOT NULL CHECK (latitud BETWEEN -90 AND 90),
    longitud           DOUBLE PRECISION NOT NULL CHECK (longitud BETWEEN -180 AND 180),
    fecha_avistamiento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices sobre las llaves foráneas (aceleran las consultas con JOIN)
CREATE INDEX IF NOT EXISTS idx_mascotas_usuario   ON mascotas (id_usuario);
CREATE INDEX IF NOT EXISTS idx_reportes_mascota   ON reportes (id_mascota);
CREATE INDEX IF NOT EXISTS idx_reportes_usuario   ON reportes (id_usuario);
CREATE INDEX IF NOT EXISTS idx_fotos_reporte      ON fotos_reporte (id_reporte);
CREATE INDEX IF NOT EXISTS idx_avist_reporte      ON avistamientos (id_reporte);
CREATE INDEX IF NOT EXISTS idx_avist_usuario      ON avistamientos (id_usuario);
