/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.ui;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.dto.CursoDTO;
import modelo.dto.TemaDTO;
import modelo.dto.UsuarioDTO;
import vista.controlador.CVMantenerTemas;
import vista.interfaz.InterfazVista;
import vista.interfaz.InterfazVista.Vista;

/**
 *
 * @author Jesus Donaldo
 */
public class VistaConsultarTemas extends javax.swing.JPanel implements 
        AncestorListener, InterfazVista {

    private CVMantenerTemas controlVista;
    //Para poder mostrar vista Modificar
    private InterfazVista padre;
    
    /**
     * Creates new form VistaConsultarTemas
     */
    public VistaConsultarTemas() {
        initComponents();
        this.addAncestorListener(this);
        //Para manipular las listas
        lstCursos.setModel(new DefaultListModel());
        lstTemas.setModel(new DefaultListModel());
        
        //Solo seleccionar uno a la vez
        lstCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstTemas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Manejador de eventos para curso
        lstCursos.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Para saber si se hizo click a la lista
                if(!e.getValueIsAdjusting()) {
                    consultarTemas();
                }
            }
            
        });
    }

    public void setPadre(InterfazVista padre) {
        this.padre = padre;
    }
    
    public void setControlador(CVMantenerTemas controlVista) {
        this.controlVista = controlVista;
    }
    
    /**
     * Limpia los componentes y libera las listas dtos utilizadas
     */
    public void limpiar() {
        //Limpiar las listas
        ((DefaultListModel)lstCursos.getModel()).clear();
        ((DefaultListModel)lstTemas.getModel()).clear();
        //Liberar memoria dto
        controlVista.liberarMemoriaConsultar();
    }
    
    /**
     * Se manda llamar al mostrarse la vista por primera vez (no despues de
     * regresar de modificar)
     */
    private void consultarCursos() {
        List<CursoDTO> cursos = controlVista.obtenerCursos();
        
        if(cursos != null && !cursos.isEmpty()) {
            mostrarCursos(cursos);
        }
        ((DefaultListModel)lstCursos.getModel()).addElement("Otros");
    }
    
    /**
     * Se llama automaticamente despues de seleccionar un curso en lstCursos
     */
    private void consultarTemas() {
        
        List<TemaDTO> temas;
        
        if(lstCursos.getSelectedIndex() == -1) {
            //Evitar que ocurra algo al cambiar de panel
            return;
        }
        //Traer los temas sin asignar
        if(lstCursos.getSelectedValue().toString().equals("Otros")) {
            temas = controlVista.obtenerTemasSinAsignar();
        }
        //Traer los temas del curso, no se tienen en el objeto debido a algo 
        //llamado lazy fetch, por eso se vuelve a llamar a la bd
        else {
            temas = controlVista.obtenerTemasDeCurso(lstCursos
                    .getSelectedValue().toString());
        }
        
        if(temas != null && !temas.isEmpty()) {
            mostrarTemas(temas);
        }
        else {
            JOptionPane.showMessageDialog(this, "No hay temas");
            ((DefaultListModel)lstTemas.getModel()).clear();
        }
    }

    /**
     * Mostrar los cursos que se consultaron en lstCursos
     * @param cursos los objetos curso a mostrar
     */
    private void mostrarCursos(List<CursoDTO> cursos) {
        DefaultListModel listModel = (DefaultListModel) lstCursos.getModel();
        
        listModel.clear();
        //Mostrar cada curso, no remover, si no buscar por medio del for
        for(CursoDTO curso : cursos) {
            listModel.addElement(curso.getNombre());
        }
    }
    
    /**
     * Mostrar los temas que se consultaron en lstTemas
     * @param temas los objetos tema a mostrar
     */
    private void mostrarTemas(List<TemaDTO> temas) {
        DefaultListModel listModel = (DefaultListModel) lstTemas.getModel();
        
        listModel.clear();
        //Mostrar cada tema, no remover, si no buscar por medio del for
        for(TemaDTO tema : temas) {
            listModel.addElement(tema.getNombre());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstCursos = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstTemas = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 579));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Consultar Temas");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Cursos");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Temas");

        jScrollPane1.setViewportView(lstCursos);
        lstCursos.getAccessibleContext().setAccessibleName("");

        jScrollPane2.setViewportView(lstTemas);

        jButton1.setText("Modificar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasarControlVistaModificar(evt);
            }
        });

        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarTema(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel2)
                                .addGap(68, 68, 68))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(98, 98, 98)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(66, 66, 66))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addGap(56, 56, 56)
                                    .addComponent(jButton2))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(138, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Elimina un tema seleccionado de la base de datos
     * @param evt 
     */
    private void eliminarTema(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarTema
        //Eliminar Tema
        //Si se selecciono algo
        if (lstTemas.getSelectedIndex() != -1) {
            controlVista.eliminarTema(lstTemas.getSelectedValue().toString());
            JOptionPane.showMessageDialog(this, "Tema eliminado");
            ((DefaultListModel) lstTemas.getModel())
                    .remove(lstTemas.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un tema");
        }

    }//GEN-LAST:event_eliminarTema

    /**
     * Pasa a la vista modificar para modificar el tema
     * @param evt 
     */
    private void pasarControlVistaModificar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasarControlVistaModificar
        
        //Mostrar la vista modificar
        if(lstTemas.getSelectedIndex() != -1) {
            TemaDTO tema = controlVista.obtenerTema(lstTemas
                    .getSelectedValue().toString());
            
            if(tema != null) {
                //Mostrar la vista modificar tema enviandole el objeto tema
                padre.mostrarVistaModificar(tema, Vista.ModificarTema);
            }
            else {
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error");
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Selecciona un tema");
        }
        
    }//GEN-LAST:event_pasarControlVistaModificar


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstCursos;
    private javax.swing.JList lstTemas;
    // End of variables declaration//GEN-END:variables

    @Override
    public void ancestorAdded(AncestorEvent event) {
        if(isShowing()) {
            //Este evento ocurre cuando se muestra la vista. Aqui llamaremos
            //a la consulta de cursos
            if(((DefaultListModel)lstCursos.getModel()).isEmpty()) {
                //Consultar de nuevo los cursos solo cuando ya no halla cursos
                //en la lista, para evitar que se reinicien al regresar de
                //modificar
                consultarCursos();
            }
        }
    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
        //No implementado
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {
        //No implementado
    }

    @Override
    public void mostrarVistaModificar(Object entidad, Vista vista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarVista(Vista vista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarEntidad(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean confirmarCambio() {
        return true;
    }

    @Override
    public UsuarioDTO obtenerUsuarioActual() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
