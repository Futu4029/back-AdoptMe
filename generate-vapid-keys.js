const webpush = require('web-push');

const vapidKeys = webpush.generateVAPIDKeys();

console.log('Clave Pública: ', vapidKeys.publicKey);
console.log('Clave Privada: ', vapidKeys.privateKey);


//Clave Pública:  BM3tH-xTaH4xUekOt34I9RkfkwOD2_hj_jtTG1KjtXcBSYtOURtjK2Ao0q48v74tCowwG8Y7kHvv4nSHwogqo1c
//Clave Privada:  GqjECivU7ND7hZCKRLqYXk01tAq9qlTBFvGXzHvAle4
