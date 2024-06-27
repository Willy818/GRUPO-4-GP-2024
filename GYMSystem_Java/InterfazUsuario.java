

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InterfazUsuario {
    private Agenda agenda;
    private JFrame frame;
    private JTextArea textArea;

    public InterfazUsuario() {
        agenda = new Agenda();
        frame = new JFrame("GYMSystem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        
        // Configurar logo
        ImageIcon logo = new ImageIcon("logoempresa.png");
        frame.setIconImage(logo.getImage());

        JPanel panel = new JPanel();
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton agregarBtn = new JButton("Agregar Evento");
        agregarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarEvento();
            }
        });
        panel.add(agregarBtn);

        JButton verBtn = new JButton("Ver Eventos");
        verBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verEventos();
            }
        });
        panel.add(verBtn);

        JButton eliminarBtn = new JButton("Eliminar Evento");
        eliminarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarEvento();
            }
        });
        panel.add(eliminarBtn);

        JButton guardarBtn = new JButton("Guardar Eventos");
        guardarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarEventos();
            }
        });
        panel.add(guardarBtn);

        JButton cargarBtn = new JButton("Cargar Eventos");
        cargarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarEventos();
            }
        });
        panel.add(cargarBtn);

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        frame.setVisible(true);
    }

    private void agregarEvento() {
        String fechaHoraStr = JOptionPane.showInputDialog("Ingrese la fecha y hora del evento (yyyy-MM-dd HH:mm):");
        if (fechaHoraStr != null) {
            try {
                LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String descripcion = JOptionPane.showInputDialog("Ingrese la descripción del evento:");
                if (descripcion != null) {
                    Evento evento = new Evento(fechaHora, descripcion);
                    agenda.agregarEvento(evento);
                    JOptionPane.showMessageDialog(frame, "Evento agregado correctamente.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Formato de fecha y hora incorrecto. Use yyyy-MM-dd HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verEventos() {
        List<Evento> eventos = agenda.verEventos();
        if (eventos.isEmpty()) {
            textArea.setText("No hay eventos.");
        } else {
            StringBuilder eventosStr = new StringBuilder();
            for (Evento evento : eventos) {
                eventosStr.append(evento).append("\n");
            }
            textArea.setText(eventosStr.toString());
        }
    }

    private void eliminarEvento() {
        String fechaHoraStr = JOptionPane.showInputDialog("Ingrese la fecha y hora del evento a eliminar (yyyy-MM-dd HH:mm):");
        if (fechaHoraStr != null) {
            try {
                LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Evento eventoAEliminar = null;
                for (Evento evento : agenda.verEventos()) {
                    if (evento.getFechaHora().isEqual(fechaHora)) {
                        eventoAEliminar = evento;
                        break;
                    }
                }
                if (eventoAEliminar != null) {
                    agenda.eliminarEvento(eventoAEliminar);
                    JOptionPane.showMessageDialog(frame, "Evento eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Formato de fecha y hora incorrecto. Use yyyy-MM-dd HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarEventos() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String archivo = fileChooser.getSelectedFile().getPath();
            try {
                agenda.guardarEventos(archivo);
                JOptionPane.showMessageDialog(frame, "Eventos guardados correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error al guardar eventos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarEventos() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            String archivo = fileChooser.getSelectedFile().getPath();
            try {
                agenda.cargarEventos(archivo);
                JOptionPane.showMessageDialog(frame, "Eventos cargados correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error al cargar eventos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfazUsuario();
            }
        });
    }
}