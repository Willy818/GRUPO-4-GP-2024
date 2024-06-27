document.addEventListener('DOMContentLoaded', function() {
    const formAddEmployee = document.getElementById('form-add-employee');
    const employeeList = document.getElementById('employee-list');
    const eventList = document.getElementById('event-list');

    formAddEmployee.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('employee-username').value;
        const password = document.getElementById('employee-password').value;

        let employees = JSON.parse(localStorage.getItem('employees')) || [];
        employees.push({ usuario: username, contraseña: password });
        localStorage.setItem('employees', JSON.stringify(employees));

        alert('Empleado agregado con éxito');
        formAddEmployee.reset();
        loadEmployees();
    });

    function loadEmployees() {
        let employees = JSON.parse(localStorage.getItem('employees')) || [];
        employeeList.innerHTML = '';

        employees.forEach((employee, index) => {
            const employeeElement = document.createElement('div');
            employeeElement.className = 'employee';
            employeeElement.innerHTML = `
                <div class="username">${employee.usuario}</div>
                <button class="delete-button" data-index="${index}">Eliminar</button>
            `;
            employeeList.appendChild(employeeElement);
        });

        document.querySelectorAll('.delete-button').forEach(button => {
            button.addEventListener('click', function() {
                const index = this.getAttribute('data-index');
                employees.splice(index, 1);
                localStorage.setItem('employees', JSON.stringify(employees));
                loadEmployees();
            });
        });
    }

    function loadEvents() {
        let events = JSON.parse(localStorage.getItem('events')) || [];
        eventList.innerHTML = '';

        events.forEach((event, index) => {
            const eventElement = document.createElement('div');
            eventElement.className = 'event';
            eventElement.innerHTML = `
                <div class="description">${event.descripcion}</div>
                <div class="date">${event.fecha}</div>
                <div class="time">${event.hora}</div>
            `;
            eventList.appendChild(eventElement);
        });
    }

    loadEmployees();
    loadEvents();
});

