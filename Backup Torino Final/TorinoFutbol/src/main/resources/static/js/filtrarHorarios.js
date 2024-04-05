async function cargarReservasDia(dia, cancha){
    let selectHorarios = document.getElementById("selectHorario");
    let horariosOcupados = [];
    let horarios = ["10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00",
                    "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00",
                    "20:00:00", "21:00:00", "22:00:00"];
    let diaHoraActual = new Date();

    const data = {
        dia: dia,
        cancha: cancha
    }

    const request = await fetch('/obtenerReservasDia', {
        method: 'POST',
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            for(let d of data){
                horariosOcupados.push(d.hora);
            }
            //Reinicio el select por cada petición para no repetir los <option>
            selectHorarios.innerHTML = "";

            horarios.forEach(function(horario){

                if(!(horariosOcupados.includes(horario)))
                    //Lo comparo con el día en formato ingles, porque así se guarda en SQL

                    horaActual = diaHoraActual.toLocaleString("es-ES", {timeZone: 'America/Argentina/Buenos_Aires'}).substring(11);
                    //Si es la madrugada del día actual el primer número aparece por defecto sin el 0 adelante
                    //y JavaScript los compara mal, por lo que tengo que agregarlo a mano para que aparezcan bien los horarios
                    if(horaActual.length == 7)
                        horaActual = "0" + horaActual;

                    if(dia === diaHoraActual.toISOString().substring(0, 10)) {
                        if(horario > horaActual) {
                            let opcion = document.createElement("option");
                            opcion.value = horario;
                            opcion.innerText = horario;
                            selectHorarios.append(opcion);
                        }
                    }
                    else{
                        let opcion = document.createElement("option");
                        opcion.value = horario;
                        opcion.innerText = horario;
                        selectHorarios.append(opcion);
                    }
            })
        })
        .catch(error => console.log(error));

}