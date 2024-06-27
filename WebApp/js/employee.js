document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('form-agregar');
    const listaEventos = document.getElementById('lista-eventos');
    
    let eventos = JSON.parse(localStorage.getItem('events')) || [];

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const fechaHora = document.getElementById('fecha-hora').value;
        const descripcion = document.getElementById('descripcion').value;

        if (fechaHora && descripcion) {
            const nuevoEvento = { fechaHora, descripcion };
            eventos.push(nuevoEvento);
            localStorage.setItem('events', JSON.stringify(eventos));
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
                if (confirm('¿Está seguro que desea eliminar este evento?')) {
                    eliminarEvento(index);
                }
            });
            li.appendChild(botonEliminar);
            listaEventos.appendChild(li);
        });
    }

    function eliminarEvento(index) {
        eventos.splice(index, 1);
        localStorage.setItem('events', JSON.stringify(eventos));
        actualizarListaEventos();
    }

    actualizarListaEventos();
});