
import java.applet.AudioClip;
import java.awt.Image;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

/**
 *
 * @author cesar
 */
public class HiloCartas extends Thread {

    private final Ventana puntero;
    private final List<String> cartas = Arrays.asList("El gallo", "El diablito", "La dama", "El catrín", "El paraguas", "La sirena", "La escalera", "La botella", "El barril", "El árbol", "El melón", "El valiente", "El gorrito", "La muerte", "La pera", "La bandera", "El bandolón", "El violoncello", "La garza", "El pájaro", "La mano", "La bota", "La luna", "El cotorro", "El borracho", "El negrito", "El corazón", "La sandía", "El tambor", "El camarón", "Las jaras", "El músico", "La araña", "El soldado", "La estrella", "El cazo", "El mundo", "El apache", "El nopal", "El alacrán", "La rosa", "La calavera", "La campana", "El cantarito", "El venado", "El sol", "La corona", "La chalupa", "El pino", "El pescado", "La palma", "La maceta", "El arpa", "La rana");
    private boolean ejecutar = true;
    private int indice = 0;
    private boolean pausar_reanudar = true;
    private AudioClip audio;
    private boolean buenas = false;

    public HiloCartas(Ventana puntero) {
        this.puntero = puntero;
    }

    public void barajarCartas() {
        Collections.shuffle(cartas);
    }

    @Override
    public void run() {
        super.run();

        while (ejecutar) {
            try {
                if (pausar_reanudar) {

                    if (buenas) {
                        reproducirAudio("Buenas");
                        sleep(1000);
                        --indice;
                        buenas = false;
                    }

                    mostrarTexto(cartas.get(indice), indice);
                    colocarImagen(cartas.get(indice));
                    reproducirAudio(cartas.get(indice));
                    indice++;
                    if (indice == 54) {
                        sleep(3000);
                        terminarJuego();
                        pause();
                        indice = 0;
                    }
                    sleep(3000);
                }
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloCartas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void pause() {
        this.pausar_reanudar = !pausar_reanudar;
    }

    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

    public List<String> cartasRestantes() {
        return cartas.subList(indice, 54);
    }

    private void reproducirAudio(String carta) {
        audio = java.applet.Applet.newAudioClip(getClass().getResource("/Audios/" + carta + ".wav"));
        audio.play();
    }

    public void terminarJuego() {

        DefaultListModel modelo = new DefaultListModel();

        if (indice == 54) {
            modelo.addElement("NO QUEDARON CARTAS RESTANTES");
        } else {
            List<String> cartasRestantes = cartasRestantes();
            modelo.addAll(cartasRestantes);
            indice = 0;
        }

        puntero.jList1.setModel(modelo);
        puntero.jList1.setVisible(true);
        puntero.jButton3.setText("COMENZAR");
        puntero.jLabel1.setText("FIN DEL JUEGO!");
    }

    public void buenas() {
        buenas = true;

        DefaultListModel modelo = new DefaultListModel();

        if (indice == 54) {
            modelo.addElement("NO QUEDARON CARTAS RESTANTES");
        } else {
            List<String> cartasRestantes = cartasRestantes();
            modelo.addAll(cartasRestantes);
        }

        puntero.jList1.setModel(modelo);
        puntero.jList1.setVisible(true);
    }

    private void colocarImagen(String carta) {
        ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/" + carta + ".png"));
        ImageIcon imgPequeno = escalarImagen(img);
        puntero.jLabel2.setIcon(imgPequeno);
    }

    private ImageIcon escalarImagen(ImageIcon img) {
        Image imgLabel = img.getImage().getScaledInstance(puntero.jLabel2.getWidth(), puntero.jLabel2.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(imgLabel);
    }

    private void mostrarTexto(String carta, int i) {
        puntero.jLabel1.setText(carta);
        puntero.setTitle("Carta número: " + (i + 1) + " de 54");
    }

}
