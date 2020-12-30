package paneluser;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dominio.Usuario;
import panelProf.PnlProf;
import util.Colores;
import util.Fecha;
import util.Fuente;

public class PnlCalendario extends JPanel {

    private Usuario usuario;
    private static int diaActivo = Fecha.getDia();
    private static int mesActivo = Fecha.getMes();
    private static int anioActivo = Fecha.getAnio();
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor del panel el cual llamara al metodo {@link establecerVentana} y
     * al metodo{@link setProfesor}
     * 
     * @param usuario usuario que a iniciado sesion
     */
    public PnlCalendario(Usuario usuario) {
        setUsuario(usuario);
        this.establecerVentana();
    }

    /**
     * metodo que inicializa el atributo usuario asociado
     * 
     * @param usuario que puede ser un profesor o un alumno
     */
    private void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void establecerVentana() {

        // establecemos el dia actual como activos
        diaActivo = Fecha.getDia();
        mesActivo = Fecha.getMes();
        anioActivo = Fecha.getAnio();

        // panel principal
        this.setLayout(new GridLayout(2,1)); //se establece el layout 

        // panel que muestra las fechas
        JPanel pnlFecha = new JPanel(new FlowLayout());

        // panel que sirve para cambiar de fecha
        JPanel pnlCambioFecha = new JPanel(new FlowLayout());

        //boton para ir hacia atras en los meses
        JButton btnRetroceder = new JButton("<");
        Fuente.setFuente(btnRetroceder);
        btnRetroceder.setOpaque(false);
        Colores.setBGTransparente(btnRetroceder);
        pnlCambioFecha.add(btnRetroceder);

        //texto que muestra el año y la fecha actual
        JLabel lblMes = new JLabel(Fecha.getMesToString(mesActivo) + "  " + anioActivo);
        pnlCambioFecha.add(lblMes);

        //boton que sirve para avanzar en los meses
        JButton btnAvanzar = new JButton(">");
        Fuente.setFuente(btnAvanzar);
        btnAvanzar.setOpaque(false);
        Colores.setBGTransparente(btnAvanzar);
        pnlCambioFecha.add(btnAvanzar);

        pnlFecha.add(pnlCambioFecha);

        // panel que muestra la disposicion de los dias de un mes
        
        
        JPanel pnlMes = this.establecerMes();

        pnlFecha.add(pnlMes);
        this.add(pnlFecha);

        // panel de botones
        JPanel pnlBotones = new JPanel(new GridLayout(4, 1, 0, 5));


        // boton crear tutoria
        JButton btnCrearTutoria = new JButton("Crear Tutoria");
        Fuente.setFuente(btnCrearTutoria);
        btnCrearTutoria.setOpaque(false);
        Colores.setBGTransparente(btnCrearTutoria);
        pnlBotones.add(btnCrearTutoria);

        //boton eliminar tutoria
        JButton btnEliminarTutoria = new JButton("Eliminar Tutoria");
        Fuente.setFuente(btnEliminarTutoria);
        btnEliminarTutoria.setOpaque(false);
        Colores.setBGTransparente(btnEliminarTutoria);
        pnlBotones.add(btnEliminarTutoria);

        //boton para cambiar el horario de un dia
        JButton btnCambiarHorario = new JButton("Cambiar Horario");
        Fuente.setFuente(btnCambiarHorario);
        btnCambiarHorario.setOpaque(false);
        Colores.setBGTransparente(btnCambiarHorario);
        pnlBotones.add(btnCambiarHorario);

        //boton para actualizar
        JButton btnActualizar = new JButton("Actualizar");
        Fuente.setFuente(btnActualizar);
        btnActualizar.setOpaque(false);
        Colores.setBGTransparente(btnActualizar);
        pnlBotones.add(btnActualizar);

        this.add(pnlBotones);

        //Acciones de los botones
        
        
        btnRetroceder.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (mesActivo == 0){
                    anioActivo--;
                    mesActivo = 11;
                }else{
                    mesActivo--;
                }
                diaActivo = 1;
                lblMes.setText(Fecha.getMesToString(mesActivo) + "  " + anioActivo);
                pnlFecha.removeAll();
                pnlFecha.add(pnlCambioFecha);
                pnlFecha.add(establecerMes());
                pnlFecha.updateUI();
                int dia= Fecha.getDiaSemana(diaActivo, mesActivo, anioActivo);
                PnlProf.pnlProf.setPnlHorario(dia, Fecha.fechaString(diaActivo, mesActivo, anioActivo));
                updateUI();
            }
        });

        btnAvanzar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (mesActivo == 11){
                    anioActivo++;
                    mesActivo = 0;
                }else{
                    mesActivo++;
                }
                
                diaActivo = 1;
                lblMes.setText(Fecha.getMesToString(mesActivo) + "  " + anioActivo);
                pnlFecha.removeAll();
                pnlFecha.add(pnlCambioFecha);
                pnlFecha.add(establecerMes());
                pnlFecha.updateUI();
                int dia= Fecha.getDiaSemana(diaActivo, mesActivo, anioActivo);
                PnlProf.pnlProf.setPnlHorario(dia, Fecha.fechaString(diaActivo, mesActivo, anioActivo));
                updateUI();
            }
        });

    }

    private JPanel establecerMes(){

        JPanel pnlMes = new JPanel(new FlowLayout());
        JPanel pnlDias = new JPanel(new GridLayout(Fecha.getSemanasMes(mesActivo, anioActivo) + 1, 7));
        JLabel txtDias[] = new JLabel[7];
        JButton btnDias[] = new JButton[Fecha.getUltimoDiaMes(mesActivo, anioActivo)];

        txtDias[0] = new JLabel("L", SwingConstants.CENTER);
        txtDias[1] = new JLabel("M", SwingConstants.CENTER);
        txtDias[2] = new JLabel("X", SwingConstants.CENTER);
        txtDias[3] = new JLabel("J", SwingConstants.CENTER);
        txtDias[4] = new JLabel("V", SwingConstants.CENTER);
        txtDias[5] = new JLabel("S", SwingConstants.CENTER);
        txtDias[6] = new JLabel("D", SwingConstants.CENTER);

        for (int i = 0; i < 7; i++) {
            txtDias[i].setOpaque(true);
            Colores.setBGAzul(txtDias[i]);
            pnlDias.add(txtDias[i]);
        }

        for (int i = 1; i < Fecha.getUltimoDiaMes(mesActivo, anioActivo) + 1; i++) {
            btnDias[i - 1] = new JButton(String.valueOf(i));
        }
        
        
        int primerDiaMes = Fecha.getPrimerDiaMes(mesActivo, anioActivo);
        int dia=1;
        for (int i= 0; i<Fecha.getSemanasMes(mesActivo, anioActivo); i++)
            for (int j = 0; j<7; j++){
                if( i== 0 && j < primerDiaMes){
                    JLabel txtVacio = new JLabel();
                    txtVacio.setOpaque(true);
                    Colores.setBGCarne(txtVacio);
                    pnlDias.add(txtVacio);
                }
                else{
                    if(dia <= Fecha.getUltimoDiaMes(mesActivo,anioActivo)){
                        if (diaActivo == dia){
                            Colores.setBGVerde(btnDias[dia-1]);
                            pnlDias.add(btnDias[dia-1]);
                            dia++;
                        }
                        else{
                            Colores.setBGCarne(btnDias[dia-1]);
                            pnlDias.add(btnDias[dia-1]);
                            dia++;
                        }
                    }
                    else{
                        JLabel txtVacio = new JLabel();
                        txtVacio.setOpaque(true);
                        Colores.setBGCarne(txtVacio);
                        pnlDias.add(txtVacio);
                    }
                    
                }
               
            }
        pnlMes.add(pnlDias);
        
        //acciones de los botones de los dias
        for (JButton btnDia: btnDias){
            btnDia.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    Colores.setBGCarne(btnDias[diaActivo-1]);
                    diaActivo = Integer.valueOf(btnDia.getText());
                    Colores.setBGVerde(btnDias[diaActivo-1]);
                    int dia= Fecha.getDiaSemana(diaActivo, mesActivo, anioActivo);
                    PnlProf.pnlProf.setPnlHorario(dia, Fecha.fechaString(diaActivo, mesActivo, anioActivo));
                    updateUI();
                }
            });
        }

        return pnlMes;
    }
}
