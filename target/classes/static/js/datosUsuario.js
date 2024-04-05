$(document).ready(function() {
    obtenerReservasUsuario();
});

// async function obtenerDatosUsuario(){
//
//     const request = await fetch('/obtenerDatosUsuario', {
//         method: 'GET',
//         headers: {
//             'Accept' : 'application/json',
//             'Content-Type' : 'application/json'
//             // 'Authorization' : localStorage.token
//         }
//     });
//     const datosUsuario = await request.json();
//     document.getElementById("rachaUsuario").innerHTML = datosUsuario.racha;
//     document.getElementById("faltasUsuario").innerHTML = datosUsuario.faltas;
// }

async function obtenerReservasUsuario(){
    const request = await fetch('/obtenerCantidadReservasUsuario', {
        method: 'GET',
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
            // 'Authorization' : localStorage.token
        }
    });

    const cantidadReservas = await request.json();
    document.getElementById("reservasUsuario").innerHTML = cantidadReservas;
}

// function generarTablaReservasUsuario(reservas){
//     $("#tablaReservasPendientesUsuario tbody").empty();
//
//     let htmlContent = "";
//     for (let reserva of reservas){
//         //Cambio la fecha al formato dd/mm/yyyy
//         let fecha = new Date(reserva.dia).toLocaleString('en-GB').substring(0, 10);
//
//         htmlContent +=
//             "<div class='reserva'>" +
//             "[id: " +  reserva.id +"] - " +
//             reserva.cancha.nombre + " - " +
//             reserva.dia + " - " +
//             reserva.hora + "hs" +
//             "</div>"
//     }
//     return htmlContent;
// }