print('Start #################################################################');

db = db.getSiblingDB('pedido');
db.createUser(
    {
        user: 'pedido',
        pwd: 'pedido',
        roles: [{ role: 'readWrite', db: 'pedido' }],
    },
);
db.createCollection('pedido');