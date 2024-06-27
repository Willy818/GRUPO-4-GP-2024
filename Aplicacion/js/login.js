document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('form-login');
    const adminKeyLabel = document.getElementById('admin-key-label');
    const adminKeyInput = document.getElementById('admin-key');

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const adminKey = document.getElementById('admin-key').value;

        if (username === 'admin' && password === 'admin') {
            if (adminKey === 'claveAdmin123') { // Clave de administrador predefinida
                window.location.href = 'admin.html';
            } else {
                alert('Clave de administrador incorrecta');
            }
        } else {
            const employees = JSON.parse(localStorage.getItem('employees')) || [];
            const employee = employees.find(emp => emp.usuario === username && emp.contraseña === password);

            if (employee) {
                window.location.href = 'employee.html';
            } else {
                alert('Credenciales incorrectas');
            }
        }
    });

    // Mostrar campo de clave de administrador si el usuario es "admin"
    document.getElementById('username').addEventListener('input', function() {
        if (this.value === 'admin') {
            adminKeyLabel.style.display = 'block';
            adminKeyInput.style.display = 'block';
        } else {
            adminKeyLabel.style.display = 'none';
            adminKeyInput.style.display = 'none';
        }
    });
});

