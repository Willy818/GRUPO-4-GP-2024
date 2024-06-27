document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('form-agregar');
    const listaEventos = document.getElementById('lista-eventos');
    
    let eventos = [];

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const fechaHora = document.getElementById('fecha-hora').value;
        const descripcion = document.getElementById('descripcion').value;

        if (fechaHora && descripcion) {
            const nuevoEvento = { fechaHora, descripcion };
            eventos.push(nuevoEvento);
            actualizarListaEventos();
            form.reset();
        }
    });

    function actualizarListaEventos() {
        listaEventos.innerHTML = '';
        eventos.forEach((evento, index) => {
            const li = document.createElement('li');
            li.textContent = `${evento.fechaHora} - ${evento.descripcion}`;
            const botonEliminar = document.createElement('button');
            botonEliminar.textContent = 'Eliminar';
            botonEliminar.addEventListener('click', function() {
                eliminarEvento(index);
            });
            li.appendChild(botonEliminar);
            listaEventos.appendChild(li);
        });
    }

    function eliminarEvento(index) {
        eventos.splice(index, 1);
        actualizarListaEventos();
    }
});