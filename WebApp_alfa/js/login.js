document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('form-login');

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        if (username === 'admin' && password === 'admin') {
            window.location.href = 'admin.html';
        } else {
            // Simulate employee login check
            const employees = JSON.parse(localStorage.getItem('employees')) || [];
            const employee = employees.find(emp => emp.usuario === username && emp.contraseña === password);

            if (employee) {
                window.location.href = 'employee.html';
            } else {
                alert('Credenciales incorrectas');
            }
        }
    });
});