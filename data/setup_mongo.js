
use mibasedatos;

// Crear colección productos si no existe
db.createCollection("productos");

// Insertar datos de prueba en la colección productos
db.productos.insertMany([
    { "nombre": "Laptop", "precio": 999.99 },
    { "nombre": "Mouse", "precio": 29.99 }
]);

// Crear índice único en nombre si se requiere
db.productos.createIndex({ "nombre": 1 }, { unique: true });
