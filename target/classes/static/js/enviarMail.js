async function enviarMail(asunto, mensaje) {
    let data = {
        asunto: asunto,
        body: mensaje
    }

    let request = await fetch('/send', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })

    // let response = await request.json();
    //
    // console.log(response);
}