$(document).ready(function() {
    cargarReservasPendientes("", "", "");
});

async function cargarReservasPendientes(dia, cancha, email){

    const request = await fetch('/reservasPendientes', {
        method: 'GET',
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
            // 'Authorization' : localStorage.token
        }
    });

    const reservas = await request.json();
    let reservasHTML = generarTablaReservas(reservas, dia, cancha, email);
    if(reservasHTML != "")
        document.querySelector("#tablaReservas tbody").innerHTML = reservasHTML;
    else
        document.querySelector("#tablaReservas tbody").innerHTML = "<tr class='item_historial'><td class='td_historial' colspan='5'>Sin resultados...</td></tr>";
}

function generarTablaReservas(reservas, dia, cancha, email){

    console.log(new Date());
    $("#tablaReservas tbody").empty();

    let htmlContent = "";
    for (let reserva of reservas){

        //Solamente muestro reservas pendientes.
        if(reserva.asistio >= 0) {
            //Creo fecha con el formato dd/mm/yyyy
            let fecha = new Date(reserva.dia).toLocaleString('en-GB', {timeZone: "UTC"}).substring(0, 10);
            let argumentosEliminar = reserva.id +
                                     ", \"" + reserva.usuario.nombre + "\"," +
                                     " \"" + reserva.usuario.apellido + "\"," +
                                     " \"" + reserva.usuario.email + "\"," +
                                     " \"" + fecha + "\"," +
                                     " \"" + reserva.cancha.nombre + "\"," +
                                     " \"" + reserva.hora + "\"";

            //Corroborar que todos los filtros coincidan con la búsqueda
            if(
                (dia === "" || reserva.dia === dia) &&
                (cancha === "" || reserva.cancha.id.toString() === cancha) &&
                (email === "" || reserva.usuario.email.includes(email))
            ){
                if(reserva.asistio  == 1){
                    htmlContent += "<tr class='item_historial asistio'>"
                }
                else{
                    if(compararFechaHora(reserva.dia)){
                        htmlContent += "<tr class='item_historial hoy'>"
                    } else {
                        htmlContent += "<tr class='item_historial'>"
                    }
                }
                htmlContent +=
                    // "<tr class='item_historial'>" +
                    "<td class='td_historial id'>" + reserva.id + "</td>" +
                    "<td class='td_historial email'>" + reserva.usuario.email +"</td>" +
                    "<td class='td_historial cancha'>" + reserva.cancha.nombre + "</td>" +
                    "<td class='td_historial dia'>" + fecha + "</td>" +
                    "<td class='td_historial hora'>" + reserva.hora + "hs " + "</td>" +
                    "<td class='td_historial botones'>" +
                        "<div class='boton_asistio' onclick='setAsistio(" + 1 + "," + reserva.id + "," + reserva.usuario.id + ")'>ASISTIO</div>" +
                        "<div class='boton_falta' onclick='confirmarFalta(" + reserva.usuario.id + ", \"" + reserva.usuario.nombre + "\", \"" + reserva.usuario.apellido + "\", " + reserva.id + ")'>FALTA</div>" +
                        "<div class='boton_eliminar'><img src='../imgs/eliminar.png' class='icono_eliminar' alt='' onclick='confirmarEliminar("+ argumentosEliminar +")'></div>" +
                    "</td>" +
                    "</tr>" +
                    "<tr class='item_historial item_responsive'>" +
                        "<td>" +
                            "<div class='botonera'>" +
                            "<div class='boton_asistio' onclick='setAsistio(" + 1 + "," + reserva.id + "," + reserva.usuario.id + ")'>ASISTIO</div>" +
                            "<div class='boton_falta' onclick='confirmarFalta(" + reserva.usuario.id + ", \"" + reserva.usuario.nombre + "\", \"" + reserva.usuario.apellido + "\", " + reserva.id + ")'>FALTA</div>" +
                            "<div class='boton_eliminar'><img src='../imgs/eliminar.png' class='icono_eliminar' alt='' onclick='confirmarEliminar("+ argumentosEliminar +")'></div>" +
                            "</div>" +
                        "</td>" +
                    "</tr>"

            }
        }
    }
    return htmlContent;
}

function compararFechaHora(fechaReserva){
    // let fechaHoraActual = new Date().toLocaleString('en-GB', {timeZone: 'UTC'}).substring(0, 10);
    let fechaActual = new Date();
    let fechaActualString = fechaActual.getFullYear() + "-" + (fechaActual.getMonth() + 1).toString().padStart(2, "0") + "-" + fechaActual.getDate().toString().padStart(2, "0");
    return fechaActualString == fechaReserva;
}