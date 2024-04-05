async function confirmarFalta(id, nombre, apellido, idReserva){
    document.getElementById("usuario_falta").innerHTML = nombre + " " + apellido;
    document.getElementById("modal_admin_confirmar_falta").style.display = "block";
    // document.getElementById("modal_background").style.display = "block";
    document.getElementById("modal_admin_confirmar_falta").style.display = "block";
    document.getElementById("modal_background_confirmar").style.display = "block";
    document.getElementById("aceptar_falta").setAttribute("onclick", "aplicarFalta(" + id + "," + idReserva + ")");
}

async function aplicarFalta(idUsuario, idReserva){

    let dataUsuario = {
        idUsuario : idUsuario,
        idReserva: idReserva
    }

    await fetch("/aplicarFalta", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataUsuario)
    })

    //Una vez aplicada la falta al usuario, indico en la reserva que NO asistió para que figure
    //en su panel que la falta fue aplicada
    await setAsistio(-1, idReserva, idUsuario)

    alert("La falta ha sido aplicada!");


}

async function confirmarEliminar(idReserva, nombre, apellido, email, dia, cancha, hora){
    document.getElementById("id_eliminar").innerHTML = idReserva;
    document.getElementById("usuario_eliminar").innerHTML = nombre + " " + apellido;
    document.getElementById("mail_eliminar").innerHTML = email;
    document.getElementById("fecha_eliminar").innerHTML = dia;
    document.getElementById("cancha_hora_eliminar").innerHTML = cancha + " - " + hora + "hs";
    document.getElementById("modal_background_confirmar").style.display = "block";
    document.getElementById("modal_admin_confirmar_eliminar").style.display = "block";
    modales_abiertos++;
    document.getElementById("aceptar_eliminar").setAttribute("onclick", "eliminarReserva(" + idReserva + ")");
}

async function eliminarReserva(id){

    let datos = {
        id : id
    }

    let request = await fetch("/eliminarReserva", {
        method: 'POST',
        headers:{
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
    await cargarReservasPendientes("", "", "");
}


//Cuando un usuario falta a la cancha, asistió se setea en -1 con esta función.
async function setAsistio(asistio, idReserva, idUsuario){
    let dataReserva = {
        asistio: asistio,
        reserva: idReserva,
        usuario: idUsuario
    }

    let requestFaltas = await fetch("/setAsistio", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataReserva)
    })

    await cargarReservasPendientes("", "", "")
}