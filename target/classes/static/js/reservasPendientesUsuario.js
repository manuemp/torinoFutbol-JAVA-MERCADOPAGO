$(document).ready(function() {
    cargarReservasPendientesUsuario();
});

async function cargarReservasPendientesUsuario(){

    const request = await fetch('/reservasPendientesUsuario', {
        method: 'GET',
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
            // 'Authorization' : localStorage.token
        }
    });
    const reservas = await request.json();
    console.log(reservas);
    let reservasHTML = generarTablaReservasUsuario(reservas);
    if(reservasHTML != ""){
        document.querySelector("#container_reservas_responsive").innerHTML = reservasHTML;
        document.querySelector("#container_reservas").innerHTML = reservasHTML;
    }
    else{
        document.querySelector("#container_reservas").innerHTML = "<div class='reserva'>No tenés reservas pendientes...</div>";
        document.querySelector("#container_reservas_responsive").innerHTML = "<div class='reserva'>No tenés reservas pendientes...</div>";
    }
}

function generarTablaReservasUsuario(reservas){
    $("#tablaReservasPendientesUsuario tbody").empty();

    let htmlContent = "";
    for (let reserva of reservas){
        //Cambio la fecha al formato dd/mm/yyyy
        let fecha = new Date(reserva.dia).toLocaleString('en-GB', {timeZone: 'UTC'}).substring(0, 10);

        if(reserva.asistio != 1){
            if(reserva.asistio == 0){
                htmlContent += "<div class='reserva'>";
            }
            if(reserva.asistio == -1){
                htmlContent += "<div class='reserva_perdida'>"
            }

            htmlContent +=
                "[id: " +  reserva.id +"] - " +
                reserva.cancha.nombre + " - " +
                fecha + " - " +
                reserva.hora + "hs" + " - " +
                reserva.cancha.precio.toLocaleString("en-EN", {style: "currency", currency: "USD", minimumFractionDigits: 2}) +
                "</div>"
        }
    }
    return htmlContent;
}

