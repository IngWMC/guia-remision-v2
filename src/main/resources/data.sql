-- Insertar roles de prueba
INSERT INTO
    rol (
        nombre,
        descripcion,
        estado,
        usuarioCreacion,
        fechaCreacion
    )
VALUES (
        'ADMIN',
        'Administrador del sistema',
        'A',
        1,
        NOW()
    ),
    (
        'USER',
        'Usuario estándar',
        'A',
        1,
        NOW()
    );

-- Insertar empresa de prueba
INSERT INTO
    empresa (
        nombre,
        ruc,
        direccion,
        telefono,
        email,
        estado,
        usuarioCreacion,
        fechaCreacion
    )
VALUES (
        'WMC Solutions',
        '12345678901',
        'Av. Principal 123',
        '987654321',
        'contacto@wmc.com',
        'A',
        1,
        NOW()
    );

-- Insertar usuarios de prueba
-- Las contraseñas están encriptadas con BCrypt (password: admin123, user123)
INSERT INTO
    usuario (
        empresaId,
        nombre,
        nombreUsuario,
        contrasena,
        estado,
        usuarioCreacion,
        fechaCreacion
    )
VALUES (
        1,
        'Administrador',
        'admin',
        '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj4J/8KzKq2K',
        'A',
        1,
        NOW()
    ),
    (
        1,
        'Usuario',
        'user',
        '$2a$12$N9qo8uLOickgx2ZMRZoMye.IjdQjOj8KzKq2K.LQv3c1yqBWVHxkd0',
        'A',
        1,
        NOW()
    );

-- Asignar roles a usuarios
INSERT INTO
    usuario_roles (usuarioId, rolId)
VALUES (1, 1), -- admin tiene rol ADMIN
    (2, 2);
-- user tiene rol USER