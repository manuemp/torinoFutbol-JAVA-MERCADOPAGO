$(document).ready(function() {
    getNivel();
});

async function getNivel(){
    let request = await fetch("/getNivelUsuario", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })

    let response = await request.json();
    document.getElementById("nivelUsuario").innerHTML = response.nombre;
}