async function infoUsuario(email) {

    if(email != "")
    {
        let request = await fetch(`/obtenerDatosUsuarioEmail/${email}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        try{
            let response = await request.json();
            document.getElementById("modal_background_info").style.display = "block"
            document.getElementById("modal_info_usuario").style.display = "block";
            document.getElementById("nombre_info_usuario").innerHTML = response.nombre;
            document.getElementById("apellido_info_usuario").innerHTML = response.apellido;
            document.getElementById("email_info_usuario").innerHTML = response.email;
            document.getElementById("telefono_info_usuario").innerHTML = response.telefono;
            document.getElementById("faltas_info_usuario").innerHTML = response.faltas;
            document.getElementById("racha_info_usuario").innerHTML = response.racha;
            let requestNivel = await fetch(`/getNivel/${response.racha}`, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
            let responseNivel = await requestNivel.json();
            document.getElementById("nivel_info_usuario").innerHTML = responseNivel.nombre;
        }
        catch(error){
            console.log("No se encontraron resultados")
        }
    }
}

async function buscarReservasEmail(email) {

    document.getElementById("cargando_modal_email").innerHTML = "Cargando...";

    let data = {
        email: email
    }
    let arrayRespuesta = [];
    let respuestaHTML = "";

    let request = await fetch('/obtenerReservasEmail', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            document.getElementById("cargando_modal_email").innerHTML = "";
            return response.json();
        })
        .then(data => {
            for(let d of data) {
                arrayRespuesta.push(d);
            }
        });

        respuestaHTML = generarTablaModalAdmin(arrayRespuesta);

        if(arrayRespuesta.length == 0)
            document.querySelector("#tabla_admin_buscar_email tbody").innerHTML = "<tr class='item_historial'><td colspan='3' class='td_historial'>No se encontraron elementos</td></tr>";
        else
            document.querySelector("#tabla_admin_buscar_email tbody").innerHTML = respuestaHTML;
}

async function buscarReservasID(id) {
    let data = {
        id: id
    }

    let arrayRespuesta = [];
    let respuestaHTML = "";
    let responseError = false;

    let request = await fetch('/obtenerReservaID', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
                arrayRespuesta.push(data);
        })
        .catch(error =>{
            responseError = true;
        });

    if(responseError)
        document.querySelector("#tabla_admin_buscar_id tbody").innerHTML = "<tr class='item_historial'><td colspan='3' class='td_historial'>No se encontraron elementos</td></tr>";
    else{
        respuestaHTML = generarTablaModalAdmin(arrayRespuesta)
        document.querySelector("#tabla_admin_buscar_id tbody").innerHTML = respuestaHTML;
    }
}

function generarTablaModalAdmin(arrayRespuesta){

    let respuestaHTML = "";
    for(let reserva of arrayRespuesta){
        let fecha = new Date(reserva.dia).toLocaleString('en-GB', {timeZone: "UTC"}).substring(0, 10);
        let argumentosEliminar = reserva.id +
            ", \"" + reserva.usuario.nombre + "\"," +
            " \"" + reserva.usuario.apellido + "\"," +
            " \"" + reserva.usuario.email + "\"," +
            " \"" + fecha + "\"," +
            " \"" + reserva.cancha.nombre + "\"," +
            " \"" + reserva.hora + "\"";

        respuestaHTML +=
            "<tr class='item_historial'>" +
                "<td class='td_historial email'>" + reserva.usuario.email +"</td>" +
                "<td class='td_historial dia'>" + reserva.dia +"</td>" +
                "<td class='td_historial cancha'>" + reserva.cancha.nombre +"</td>" +
                "<td class='td_historial hora'>" + reserva.hora +"</td>" +
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
    return respuestaHTML;
}
