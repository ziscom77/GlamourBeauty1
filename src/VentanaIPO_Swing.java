import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class VentanaIPO_Swing extends JFrame {

    private final GestorIPO gestor = new GestorIPO();

    private final DefaultTableModel mProv = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Contacto"}, 0);
    private final DefaultTableModel mInv  = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Capital"}, 0);
    private final DefaultTableModel mDoc  = new DefaultTableModel(
            new Object[]{"ID", "Tipo", "Descripción", "Prov", "Inv"}, 0);
    private final DefaultTableModel mHit  = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Fecha", "Estado", "DocId"}, 0);

    public VentanaIPO_Swing() {
        SwingUtilities.invokeLater(this::initUI);
    }

    private void initUI() {
        JFrame f = new JFrame("Gestión IPO – Glamour Beauty");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900, 550);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Proveedores",  panelProveedores());
        tabs.add("Inversionistas", panelInversionistas());
        tabs.add("Documentos",   panelDocumentos());
        tabs.add("Hitos",        panelHitos());

        f.add(tabs);
        f.setVisible(true);

        /* Carga inicial de datos */
        recargaProv(); recargaInv(); recargaDoc();
    }

    private JPanel panelProveedores() {
        JTextField tNombre = new JTextField(12);
        JTextField tContacto = new JTextField(12);
        JButton btnAdd = new JButton("Crear");

        JTable tabla = new JTable(mProv);
        tabla.setFillsViewportHeight(true);

        btnAdd.addActionListener(e -> {
            try {
                gestor.altaProveedor(tNombre.getText(), tContacto.getText());
                tNombre.setText(""); tContacto.setText("");
                recargaProv();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Nombre")); form.add(tNombre);
        form.add(new JLabel("Contacto")); form.add(tContacto);
        form.add(btnAdd);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    private JPanel panelInversionistas() {
        JTextField tNombre = new JTextField(10);
        JTextField tCapital = new JTextField(6);
        JButton btnAdd = new JButton("Crear");

        JTable tabla = new JTable(mInv);

        btnAdd.addActionListener(e -> {
            try {
                double cap = Double.parseDouble(tCapital.getText());
                gestor.altaInversionista(tNombre.getText(), cap);
                tNombre.setText(""); tCapital.setText("");
                recargaInv();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Nombre")); form.add(tNombre);
        form.add(new JLabel("Capital")); form.add(tCapital);
        form.add(btnAdd);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    private JPanel panelDocumentos() {
        JTextField tTipo = new JTextField(8);
        JTextField tDesc = new JTextField(10);
        JTextField tProvId = new JTextField(3);
        JTextField tInvId  = new JTextField(3);
        JButton btnAdd = new JButton("Crear");

        JTable tabla = new JTable(mDoc);

        btnAdd.addActionListener(e -> {
            try {
                Integer pId = tProvId.getText().isBlank()? null : Integer.parseInt(tProvId.getText());
                Integer iId = tInvId .getText().isBlank()? null : Integer.parseInt(tInvId .getText());
                gestor.altaDocumento(tTipo.getText(), tDesc.getText(), pId, iId);
                tTipo.setText(""); tDesc.setText(""); tProvId.setText(""); tInvId.setText("");
                recargaDoc();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Tipo")); form.add(tTipo);
        form.add(new JLabel("Descripción")); form.add(tDesc);
        form.add(new JLabel("ProvId")); form.add(tProvId);
        form.add(new JLabel("InvId")); form.add(tInvId);
        form.add(btnAdd);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /* ======================================================================
       PANEL HITOS
       ====================================================================== */
    private JPanel panelHitos() {
        JTextField tDocId = new JTextField(3);
        JTextField tNombre = new JTextField(10);
        JTextField tDesc   = new JTextField(10);
        JButton btnAdd = new JButton("Añadir");

        JTable tabla = new JTable(mHit);

        btnAdd.addActionListener(e -> {
            try {
                int docId = Integer.parseInt(tDocId.getText());
                gestor.registrarHito(tNombre.getText(), tDesc.getText(),
                        "Sin iniciar", LocalDate.now(), docId);
                tNombre.setText(""); tDesc.setText("");
                recargaHitos(docId);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("DocId")); form.add(tDocId);
        form.add(new JLabel("Nombre")); form.add(tNombre);
        form.add(new JLabel("Descripción")); form.add(tDesc);
        form.add(btnAdd);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /* ======================================================================
       MÉTODOS DE RECARGA DE TABLAS
       ====================================================================== */
    private void recargaProv() {
        try {
            mProv.setRowCount(0);
            gestor.listaProveedores()
                    .forEach(pr -> mProv.addRow(new Object[]{pr.id(), pr.nombre(), pr.contacto()}));
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void recargaInv() {
        try {
            mInv.setRowCount(0);
            gestor.listaInversionistas()
                    .forEach(in -> mInv.addRow(new Object[]{in.id(), in.nombre(), in.capital()}));
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void recargaDoc() {
        try {
            mDoc.setRowCount(0);
            gestor.listaDocumentos()
                    .forEach(d -> mDoc.addRow(new Object[]{
                            d.id(), d.tipo(), d.descripcion(), d.proveedorId(), d.inversionistaId()
                    }));
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void recargaHitos(int docId) {
        try {
            mHit.setRowCount(0);
            gestor.hitosPorDocumento(docId)
                    .forEach(h -> mHit.addRow(new Object[]{
                            h.id(), h.nombre(), h.fecha(), h.estado(), h.documentoId()
                    }));
        } catch (Exception e) { e.printStackTrace(); }
    }

    /* ---------- main ---------- */
    public static void main(String[] args) {
        new VentanaIPO_Swing();   // crea y lanza la GUI
    }
}
