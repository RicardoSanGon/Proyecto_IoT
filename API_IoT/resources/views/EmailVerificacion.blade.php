<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Correo de Verificación</title>
</head>
<body>
<div style="text-align: center;">
    <h2>¡Bienvenido a Nuestra Aplicación {{$name}}!</h2>
    <p>Por favor, haz clic en el botón de abajo para confirmar tu dirección de correo electrónico y activar tu cuenta.</p>
    <a href="{{$verification_link}}" style="display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;">Confirmar</a>
</div>
</body>
</html>
