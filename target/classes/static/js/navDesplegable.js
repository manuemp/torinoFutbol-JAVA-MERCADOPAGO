let navDesplegable = document.querySelector(".navbar_desplegable");
let flag_desplegable = false;

document.getElementsByTagName("main").item(0).addEventListener('click', ()=>{
    flag_desplegable = false;
    navDesplegable.style.display = "none";
})

document.getElementById("boton_desplegable").addEventListener('click', ()=>{
    flag_desplegable = !flag_desplegable;
    if(flag_desplegable)
        navDesplegable.style.display = "block";
    else
        navDesplegable.style.display = "none";
})