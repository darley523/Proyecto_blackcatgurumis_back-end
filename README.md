blackcatgurumis es un emprendimiento que vende articulos hechos a mano, actualmente solo cuenta con una pagina de instagram y por ello busca expandirse a traves de una tienda de e-commerce. En esta version el administrador puede añadir productos desde la misma pagina (que se guardan en la bdd) puede eliminar usuarios, puede ver los diversos pedidos que han sido hechos ademas de poder editar sus estados. Ademas las contraseñas son encriptadas al guardarse en la base de datos

Tecnologias usadas: Springboot para el backend, Bootstrap (para el panel de administracion principalmente), react, vite, javaScript, jwt para la autenticacion de los usuarios, spring security para proteger rutas privadas.

Requisitos para ejecutar el proyecto completo (backend-frontend)-> Vs code (con extensiones: extension pack for java y spring boot extension pack) XAMPP, MySqlWorkbench, npm, vite y Java minimo 17

1.Abrir Xampp y dar start en los modulos de apache y MySqlWorkbench
2.Abrir MySqlWorkbench y crear una conexion de usuario root y contraseña vacia
3.En vs code abrir proyecto_blackcatugurumis_back-end 
4.En vs code buscar a la izquierda el icono de Spring Boot Dashboard y darle a ejecutar a blackend-blackcatgurumis
5.-Inicializar proyecto react con npm run dev (en caso de error ejecutar npm install)

Documentación Swagger
http://localhost:8080/swagger-ui/index.html
