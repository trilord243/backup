/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.redes_sociales.vistas;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.redes_sociales.controladores.ControladorGrafo;
import com.redes_sociales.modelos.Grafo;
import com.redes_sociales.modelos.Usuario;
import com.redes_sociales.estructura.ListaEnlazada;
import com.redes_sociales.modelos.Relacion;


import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph; 
import com.redes_sociales.controladores.ControladorArchivo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Escal
 */
public class VistaPrincipal extends javax.swing.JFrame {
    private ControladorArchivo controladorArchivo;
     private ControladorGrafo controlador;
     private Grafo grafo;

     

    
     public VistaPrincipal(Grafo grafo, ControladorArchivo controladorArchivo) {
        this.controladorArchivo = controladorArchivo;
        this.controlador = new ControladorGrafo(grafo);
        this.grafo=grafo;
         initComponents();
        
    
     }
    
    
     public void agregarUsuario() {
                String idString = JOptionPane.showInputDialog("Introduce el id del usuario");
                String nombre = JOptionPane.showInputDialog("Introduce el nombre del usuario");
                if (idString != null && !idString.isEmpty() && nombre != null && !nombre.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idString);
                        Usuario nuevoUsuario = new Usuario(id, nombre);
                        controlador.agregarUsuario(nuevoUsuario);
                        JOptionPane.showMessageDialog(null, "Usuario agregado con éxito!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese un ID válido (número entero)");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El id y el nombre del usuario no pueden estar vacíos");
                }
            }
        
    
    
    public void agregarRelacion() {
                String idUsuario1Str = JOptionPane.showInputDialog("Introduce el ID del primer usuario");
                String idUsuario2Str = JOptionPane.showInputDialog("Introduce el ID del segundo usuario");
                String tiempoAmistadStr = JOptionPane.showInputDialog("Introduce el tiempo de amistad");

                try {
                    int idUsuario1 = Integer.parseInt(idUsuario1Str);
                    int idUsuario2 = Integer.parseInt(idUsuario2Str);
                    int tiempoAmistad = Integer.parseInt(tiempoAmistadStr);

                    Usuario usuario1 = controlador.buscarUsuario(idUsuario1);
                    Usuario usuario2 = controlador.buscarUsuario(idUsuario2);

                    if (usuario1 == null || usuario2 == null) {
                        JOptionPane.showMessageDialog(null, "Uno o ambos usuarios no existen");
                    } else {
                        controlador.agregarRelacion(usuario1, usuario2, tiempoAmistad);
                        JOptionPane.showMessageDialog(null, "Relación agregada con éxito!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido para el ID del usuario y el tiempo de amistad");
                }
            }
    
    
    
    public void eliminarRelacion() {
                String idUsuarioStr = JOptionPane.showInputDialog("Introduce el ID del usuario a eliminar");

                try {
                    int idUsuario = Integer.parseInt(idUsuarioStr);
                    Usuario usuario = controlador.buscarUsuario(idUsuario);

                    if (usuario == null) {
                        JOptionPane.showMessageDialog(null, "El usuario no existe");
                    } else {
                        controlador.eliminarUsuario(usuario);
                        JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduce un número válido para el ID del usuario");
                }
            }
    
    
    
    
    
    
    public void mostrarUsuarios() {
                ListaEnlazada<Usuario> usuarios = controlador.obtenerUsuarios();
                if (usuarios.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay usuarios para mostrar");
                } else {
                    StringBuilder usuariosStr = new StringBuilder();
                    for (int i = 0; i < usuarios.size(); i++) {
                        Usuario usuario = usuarios.get(i);
                        usuariosStr.append(usuario.getId()).append(" - ").append(usuario.getNombre()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, usuariosStr.toString(), "Usuarios", JOptionPane.INFORMATION_MESSAGE);
                }
            }
    
    
    
    public void contarIslas() {
        // Pide al usuario que seleccione entre BFS y DFS
        Object[] options = {"BFS", "DFS"};
        int n = JOptionPane.showOptionDialog(VistaPrincipal.this,
            "¿Cómo te gustaría contar las islas?",
            "Selecciona un método",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        // Obtiene el usuario inicial (esto dependerá de cómo esté implementado tu grafo)
        Usuario usuarioInicial = grafo.getUsuarios().get(0);

        // Cuenta las islas usando el método seleccionado
        int numIslas = grafo.contarIslas(n == 0); // Aquí he eliminado el segundo argumento

        // Muestra el número de islas
        JOptionPane.showMessageDialog(VistaPrincipal.this, "El número de islas es: " + numIslas);
    }
    public void encontrarPuentes() {
                ListaEnlazada<Relacion> puentes = controlador.identificarPuentes();
                StringBuilder puentesStr = new StringBuilder();
                for (int i = 0; i < puentes.size(); i++) {
                    Relacion relacion = puentes.get(i);
                    puentesStr.append(relacion.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, "Los puentes en el grafo son: \n" + puentesStr.toString());
            }
        
     private void mostrarGrafo() {
    mxGraph graph = new mxGraph();
    Object parent = graph.getDefaultParent();
    graph.getModel().beginUpdate();

    try {
        ListaEnlazada<Usuario> usuarios = controlador.obtenerUsuarios();
        ListaEnlazada<Object> vertices = new ListaEnlazada<>();

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            Object vertice = graph.insertVertex(parent, null, usuario.getNombre(), 0, 0, 80, 30);
            vertices.add(vertice);
        }

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            ListaEnlazada<Relacion> relaciones = controlador.obtenerRelaciones(usuario);
            for (int j = 0; j < relaciones.size(); j++) {
                Relacion relacion = relaciones.get(j);
                Usuario usuario1 = relacion.getUsuario1();
                Usuario usuario2 = relacion.getUsuario2();
                int index1 = usuarios.indexOf(usuario1);
                int index2 = usuarios.indexOf(usuario2);
                if (index1 != -1 && index2 != -1) {
                    Object v1 = vertices.get(index1);
                    Object v2 = vertices.get(index2);
                    graph.insertEdge(parent, null, "", v1, v2); // Aquí se ha cambiado la etiqueta a una cadena vacía
                }
            }
        }
    } finally {
        graph.getModel().endUpdate();
    }

    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    mxHierarchicalLayout layout = new mxHierarchicalLayout(graph); // Aquí se ha cambiado el layout a mxHierarchicalLayout
    layout.execute(parent);

    JFrame frame = new JFrame();
    frame.getContentPane().add(graphComponent);
    frame.setSize(1200, 800); // Aquí se ha aumentado el tamaño de la ventana
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
}
     public void guardar() {
        controlador.guardarCambios(controladorArchivo);
        JOptionPane.showMessageDialog(null, "Cambios guardados con éxito!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jToggleButton9 = new javax.swing.JToggleButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jToggleButton10 = new javax.swing.JToggleButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jToggleButton11 = new javax.swing.JToggleButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jToggleButton14 = new javax.swing.JToggleButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jToggleButton13 = new javax.swing.JToggleButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jToggleButton12 = new javax.swing.JToggleButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(153, 204, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 20, -1));

        jLabel2.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Agregar Usuario ");
        jPanel7.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/redes_sociales/vistas/v915-wit-011-k.jpg"))); // NOI18N
        jLabel9.setText("jLabel9");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-68, -79, 5760, 2860));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jLabel11.setText("al grafo");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel12.setText("Agrega un usario");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jToggleButton2.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton2.setFont(new java.awt.Font("Calibri", 3, 12)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton2.setText("AGREGAR");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 120, 170));

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(153, 204, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Agregar Relacion");
        jPanel10.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jToggleButton9.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton9.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jToggleButton9.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton9.setText("AGREGAR");
        jToggleButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton9ActionPerformed(evt);
            }
        });
        jPanel3.add(jToggleButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jLabel13.setText("Agrega una Relacion");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel14.setText("al grafo");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 120, 170));

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(153, 204, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Eliminar Usuario");
        jPanel11.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel4.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jToggleButton10.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton10.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jToggleButton10.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton10.setText("ELIMINAR");
        jToggleButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton10ActionPerformed(evt);
            }
        });
        jPanel4.add(jToggleButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jLabel15.setText("Elimina un usuario");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel16.setText("del grafo");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 120, 170));

        jPanel5.setBackground(new java.awt.Color(0, 102, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(153, 204, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Mostrar Usuarios");
        jPanel12.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jPanel5.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jToggleButton11.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton11.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jToggleButton11.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton11.setText("MOSTRAR");
        jToggleButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton11ActionPerformed(evt);
            }
        });
        jPanel5.add(jToggleButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jLabel17.setText("Muestra los usuarios");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel18.setText("del grafo");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 120, 170));

        jPanel6.setBackground(new java.awt.Color(0, 102, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(153, 204, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Contar Islas");
        jPanel13.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jToggleButton14.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton14.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jToggleButton14.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton14.setText("MOSTRAR");
        jToggleButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton14ActionPerformed(evt);
            }
        });
        jPanel6.add(jToggleButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jLabel19.setText("del grafo");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel22.setText("Cuenta las islas");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 120, 170));

        jPanel8.setBackground(new java.awt.Color(0, 102, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(153, 204, 255));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Encontrar Puentes");
        jPanel14.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, -1));

        jPanel8.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jToggleButton13.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton13.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jToggleButton13.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton13.setText("MOSTRAR");
        jToggleButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton13ActionPerformed(evt);
            }
        });
        jPanel8.add(jToggleButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jLabel20.setText("del grafo");
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel23.setText("Cuenta las islas");
        jPanel8.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 120, 170));

        jPanel9.setBackground(new java.awt.Color(0, 102, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(153, 204, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Mostrar Grafo ");
        jPanel15.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jPanel9.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jToggleButton12.setBackground(new java.awt.Color(0, 204, 255));
        jToggleButton12.setFont(new java.awt.Font("Calibri", 3, 14)); // NOI18N
        jToggleButton12.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton12.setText("MOSTRAR");
        jToggleButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton12ActionPerformed(evt);
            }
        });
        jPanel9.add(jToggleButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 120, 40));

        jLabel21.setText("del grafo");
        jPanel9.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel24.setText("Representacion");
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 120, 170));

        jToggleButton1.setBackground(new java.awt.Color(0, 102, 254));
        jToggleButton1.setFont(new java.awt.Font("Calibri", 1, 15)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setText("Guardar Cambios");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 440, -1, 60));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/redes_sociales/vistas/Modern_abstract_blue_transparent_crystal_pattern_background.jpg"))); // NOI18N
        jLabel10.setText("jLabel10");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(-90, -20, -1, -1));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 770, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
       guardar();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
      agregarUsuario();        
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton9ActionPerformed
       agregarRelacion();
    }//GEN-LAST:event_jToggleButton9ActionPerformed

    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed
       eliminarRelacion();
    }//GEN-LAST:event_jToggleButton10ActionPerformed

    private void jToggleButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton11ActionPerformed
        mostrarUsuarios();
    }//GEN-LAST:event_jToggleButton11ActionPerformed

    private void jToggleButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton12ActionPerformed
        mostrarGrafo();
    }//GEN-LAST:event_jToggleButton12ActionPerformed

    private void jToggleButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton13ActionPerformed
        encontrarPuentes();
    }//GEN-LAST:event_jToggleButton13ActionPerformed

    private void jToggleButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton14ActionPerformed
        contarIslas();
    }//GEN-LAST:event_jToggleButton14ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    /*
    public static void main(String args[]) {
    
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaPrincipal().setVisible(true);
            }
        });
    }
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton11;
    private javax.swing.JToggleButton jToggleButton12;
    private javax.swing.JToggleButton jToggleButton13;
    private javax.swing.JToggleButton jToggleButton14;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton9;
    // End of variables declaration//GEN-END:variables
}
