// Espera a que cargue la página
document.addEventListener("DOMContentLoaded", function () {

    // Busca los botones para desactivar
    const botones = document.querySelectorAll(".desactivar");

    // Agrega una confirmación
    botones.forEach(function (boton) {

        boton.addEventListener("click", function () {

            confirm("¿Desea desactivar este registro?");

        });

    });

});