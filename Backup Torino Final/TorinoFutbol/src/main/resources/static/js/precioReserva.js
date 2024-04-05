async function calcularPrecioReserva(idCancha){

    let data = {
        idCancha : idCancha
    }

    let request = await fetch("/obtenerPrecioFinal", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    let precio = await(request.json());
    let descuento = parseInt(document.getElementById("descuento").innerHTML);

    let total = precio - precio * (descuento / 100);
    document.getElementById("precio_final").innerHTML =
        total.toLocaleString("en-EN", {style: "currency", currency: "USD", minimumFractionDigits: 2});

}