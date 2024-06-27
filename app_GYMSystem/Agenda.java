
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Evento> eventos;

    public Agenda() {
        eventos = new ArrayList<>();
    }

    public void agregarEvento(Evento evento) {
        eventos.add(evento);
    }

    public List<Evento> verEventos() {
        return eventos;
    }

    public void eliminarEvento(Evento evento) {
        eventos.remove(evento);
    }

    public void guardarEventos(String archivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(eventos);
        }
    }

    @SuppressWarnings("unchecked")
	public void cargarEventos(String archivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            eventos = (List<Evento>) in.readObject();
        }
    }
}