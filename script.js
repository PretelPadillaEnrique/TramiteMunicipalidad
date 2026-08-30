function login() {
    let usuario = document.getElementById("usuario").value;
    document.getElementById("mensaje").innerText =
        "Bienvenido " + usuario;
}

function registrar() {
    document.getElementById("expediente").innerText =
        "Expediente registrado";
}

function buscar() {
    document.getElementById("expediente").innerText =
        "Expediente encontrado";
}

function detalle() {
    document.getElementById("expediente").innerText =
        "Código: EXP001 | Estado: Pendiente";
}

function editar() {
    document.getElementById("expediente").innerText =
        "Expediente actualizado";
}

function adjuntar() {
    let archivo = document.getElementById("archivo").files[0];

    if (archivo) {
        document.getElementById("expediente").innerText =
            "Documento: " + archivo.name;
    }
}

function registrarArea() {
    document.getElementById("resultado").innerText =
        "Área registrada";
}

function editarArea() {
    document.getElementById("resultado").innerText =
        "Área actualizada";
}

function desactivar() {
    document.getElementById("resultado").innerText =
        "Área desactivada";
}

function listar() {
    document.getElementById("resultado").innerText =
        "Obras Públicas | Rentas | Administración";
}
