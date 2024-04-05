$(document).ready(function() {
    cargarHistorialReservasUsuario();
});

async function cargarHistorialReservasUsuario(){
    const request = await fetch('/obtenerHistorialReservasUsuario', {
        method: 'GET',
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
            // 'Authorization' : localStorage.token
        }
    });

    const reservas = await request.json();
    let reservasHTML = generarTablaReservas(reservas);
    if(reservasHTML != "")
        document.querySelector("#tablaReservas tbody").outerHTML = reservasHTML;
    else
        document.querySelector("#tablaReservas tbody").outerHTML = "<tr><td colspan='3' class='td_historial'>Sin resultados...</td></tr>";
}

function generarTablaReservas(reservas){

    $("#tablaReservas tbody").empty();

    let htmlContent = "";
    for (let reserva of reservas){
        //Cambio la fecha al formato dd/mm/yyyy
        let fecha = new Date(reserva.dia).toLocaleString('en-GB', {timeZone: 'UTC'}).substring(0, 10);

        //Si no asistió y ya pasó la fecha (o la hora si es el mismo día) de la reserva
        //ya tiene que aparecer en rojo la clase
        // if(parseInt(reserva.asistio) == -1 &&
        //     compararFechaHora(reserva.dia.toString() + "T" + reserva.hora.toString())) {
        //         htmlContent += "<tr class='item_historial_falta'>";
        // }
        if(parseInt(reserva.asistio) == -1) {
            htmlContent += "<tr class='item_historial_falta'>";
        }
        else{
            htmlContent += "<tr class='item_historial' >";
        }
        htmlContent +=
                "<td class='td_historial'>" + fecha +"</td>" +
                "<td class='td_historial'>" + reserva.hora + "hs " + "</td>" +
                "<td class='td_historial'>" + reserva.cancha.nombre + "</td>" +
            "</tr>";
    }
    return htmlContent;
}

// function compararFechaHora(fechaReserva){
//     let fechaHoraActual = new Date();
//
// // Configurar opciones para formatear la fecha y hora en la zona horaria deseada
//     let opcionesFormato = {
//         year: 'numeric',
//         month: 'numeric',
//         day: 'numeric',
//         hour: 'numeric',
//         minute: 'numeric',
//         second: 'numeric',
//         timeZone: 'UTC'
//     };
//
//     // Obtener cadenas formateadas de las fechas en la zona horaria deseada
//     let fechaHoraActualFormateada = fechaHoraActual.toLocaleString('es-ES', opcionesFormato);
//     // Objeto Date para la reserva actual
//     let fechaHoraReserva = new Date(fechaReserva).toLocaleString('es-ES', {timeZone: 'UTC'});
//
//     if(fechaHoraActualFormateada == fechaHoraReserva)
//         return false;
//
//     return true;
// }