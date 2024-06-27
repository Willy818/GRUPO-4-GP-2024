
document.addEventListener('DOMContentLoaded', function() {
    const formAgregarEmpleado = document.getElementById('form-agregar-empleado');
    const listaEventos = document.getElementById('lista-eventos');

    let empleados = JSON.parse(localStorage.getItem('employees')) || [];
    let eventos = JSON.parse(localStorage.getItem('events')) || [];

    formAgregarEmpleado.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const usuario = document.getElementById('empleado-usuario').value;
        const contraseña = document.getElementById('empleado-contraseña').value;

        if (usuario && contraseña) {
            empleados.push({ usuario, contraseña });
            localStorage.setItem('employees', JSON.stringify(empleados));
            formAgregarEmpleado.reset();
            alert('Empleado agregado');
        }
    });

    function actualizarListaEventos() {
        listaEventos.innerHTML = '';
        eventos.forEach((evento, index) => {
            const li = document.createElement('li');
            li.textContent = `${evento.fechaHora} - ${evento.descripcion}`;
            listaEventos.appendChild(li);
        });
    }

    actualizarListaEventos();
});