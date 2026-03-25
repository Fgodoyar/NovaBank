CREATE TABLE IF NOT EXISTS Clientes(
	id_cliente SERIAL PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	apellidos VARCHAR(150) NOT NULL,
	dni VARCHAR(20) NOT NULL,
	email VARCHAR(150) NOT NULL,
	telefono VARCHAR (20) NOT NULL,
	fecha_creacion TIMESTAMP,
	UNIQUE (dni, email, telefono)
);

CREATE TABLE IF NOT EXISTS Cuentas(
	id_cuenta SERIAL PRIMARY KEY,
	numero_cuenta VARCHAR(34) UNIQUE NOT NULL,
	id_cliente INT,
	saldo NUMERIC(15,2) DEFAULT 0,
	fecha_creacion TIMESTAMP,
	FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE IF NOT EXISTS Movimientos(
	id_movimiento SERIAL PRIMARY KEY,
	id_cuenta INT,
	tipo VARCHAR(50) CHECK(tipo IN('DEPOSITO', 'RETIRO', 'TRANSFERENCIA_SALIENTE', 'TRANSFERENCIA_ENTRANTE')),
	cantidad NUMERIC(15,2) CHECK(cantidad > 0),
	fecha TIMESTAMP,
	FOREIGN KEY (id_cuenta) REFERENCES Cuentas(id_cuenta)
);