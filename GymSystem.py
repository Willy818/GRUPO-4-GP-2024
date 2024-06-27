#eventos
class Evento:
    def __init__(self, fecha, hora, descripcion):
        self.fecha = fecha
        self.hora = hora
        self.descripcion = descripcion

    def __str__(self):
        return f"{self.fecha} {self.hora}: {self.descripcion}"
#agenda    
class Agenda:
    def __init__(self):
        self.eventos = []

    def agregar_evento(self, evento):
        self.eventos.append(evento)
        self.eventos.sort(key=lambda e: (e.fecha, e.hora))

    def eliminar_evento(self, evento):
        self.eventos.remove(evento)

    def listar_eventos(self):
        return self.eventos

    def guardar_eventos(self, archivo):
        with open(archivo, 'w') as f:
            for evento in self.eventos:
                f.write(f"{evento.fecha},{evento.hora},{evento.descripcion}\n")

    def cargar_eventos(self, archivo):
        self.eventos = []
        with open(archivo, 'r') as f:
            for linea in f:
                fecha, hora, descripcion = linea.strip().split(',')
                self.agregar_evento(Evento(fecha, hora, descripcion))
#
import tkinter as tk
from tkinter import messagebox, filedialog
from datetime import datetime

class GYMSystemApp:
    def __init__(self, root):
        self.agenda = Agenda()
        self.root = root
        self.root.title("GYMSystem - Agenda Electrónica")
        self.crear_widgets()

    def crear_widgets(self):
        self.frame = tk.Frame(self.root)
        self.frame.pack(pady=10)

        self.boton_agregar_evento = tk.Button(self.frame, text="Agregar Evento", command=self.mostrar_ventana_agregar_evento)
        self.boton_agregar_evento.grid(row=0, column=0, padx=5)

        self.boton_guardar_eventos = tk.Button(self.frame, text="Guardar Eventos", command=self.guardar_eventos)
        self.boton_guardar_eventos.grid(row=0, column=1, padx=5)

        self.boton_cargar_eventos = tk.Button(self.frame, text="Cargar Eventos", command=self.cargar_eventos)
        self.boton_cargar_eventos.grid(row=0, column=2, padx=5)

        self.lista_eventos = tk.Listbox(self.root, width=50)
        self.lista_eventos.pack(pady=10)
        self.lista_eventos.bind('<Double-1>', self.mostrar_detalles_evento)

        self.boton_eliminar_evento = tk.Button(self.root, text="Eliminar Evento", command=self.eliminar_evento)
        self.boton_eliminar_evento.pack(pady=5)

    def mostrar_ventana_agregar_evento(self):
        self.ventana_agregar_evento = tk.Toplevel(self.root)
        self.ventana_agregar_evento.title("Agregar Evento")

        tk.Label(self.ventana_agregar_evento, text="Fecha (YYYY-MM-DD):").grid(row=0, column=0, padx=5, pady=5)
        tk.Label(self.ventana_agregar_evento, text="Hora (HH:MM):").grid(row=1, column=0, padx=5, pady=5)
        tk.Label(self.ventana_agregar_evento, text="Descripción:").grid(row=2, column=0, padx=5, pady=5)

        self.entrada_fecha = tk.Entry(self.ventana_agregar_evento)
        self.entrada_fecha.grid(row=0, column=1, padx=5, pady=5)

        self.entrada_hora = tk.Entry(self.ventana_agregar_evento)
        self.entrada_hora.grid(row=1, column=1, padx=5, pady=5)

        self.entrada_descripcion = tk.Entry(self.ventana_agregar_evento)
        self.entrada_descripcion.grid(row=2, column=1, padx=5, pady=5)

        self.boton_guardar_evento = tk.Button(self.ventana_agregar_evento, text="Guardar", command=self.agregar_evento)
        self.boton_guardar_evento.grid(row=3, column=0, columnspan=2, pady=10)

    def agregar_evento(self):
        fecha = self.entrada_fecha.get()
        hora = self.entrada_hora.get()
        descripcion = self.entrada_descripcion.get()

        if not (fecha and hora and descripcion):
            messagebox.showwarning("Campos Vacíos", "Todos los campos son obligatorios.")
            return

        try:
            datetime.strptime(fecha, '%Y-%m-%d')
            datetime.strptime(hora, '%H:%M')
        except ValueError:
            messagebox.showerror("Formato Incorrecto", "Fecha u hora en formato incorrecto.")
            return

        evento = Evento(fecha, hora, descripcion)
        self.agenda.agregar_evento(evento)
        self.actualizar_lista_eventos()
        self.ventana_agregar_evento.destroy()

    def actualizar_lista_eventos(self):
        self.lista_eventos.delete(0, tk.END)
        for evento in self.agenda.listar_eventos():
            self.lista_eventos.insert(tk.END, evento)

    def mostrar_detalles_evento(self, event):
        indice_seleccionado = self.lista_eventos.curselection()
        if indice_seleccionado:
            evento = self.agenda.listar_eventos()[indice_seleccionado[0]]
            messagebox.showinfo("Detalles del Evento", str(evento))

    def eliminar_evento(self):
        indice_seleccionado = self.lista_eventos.curselection()
        if indice_seleccionado:
            evento = self.agenda.listar_eventos()[indice_seleccionado[0]]
            self.agenda.eliminar_evento(evento)
            self.actualizar_lista_eventos()

    def guardar_eventos(self):
        archivo = filedialog.asksaveasfilename(defaultextension=".txt", filetypes=[("Archivos de texto", "*.txt")])
        if archivo:
            self.agenda.guardar_eventos(archivo)

    def cargar_eventos(self):
        archivo = filedialog.askopenfilename(filetypes=[("Archivos de texto", "*.txt")])
        if archivo:
            self.agenda.cargar_eventos(archivo)
            self.actualizar_lista_eventos()

if __name__ == "__main__":
    root = tk.Tk()
    app = GYMSystemApp(root)
    root.mainloop()
