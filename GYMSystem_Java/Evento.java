
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento implements Serializable {
    private LocalDateTime fechaHora;
    private String descripcion;

    public Evento(LocalDateTime fechaHora, String descripcion) {
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return fechaHora.format(formatter) + ": " + descripcion;
    }
}