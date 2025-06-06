package ui;

import service.GestorIPO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class VentanaIPO_Swing extends javax.swing.JFrame {

    private final GestorIPO gestor = new GestorIPO();

    /* Modelos */
    private final DefaultTableModel mProv = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Contacto"}, 0);
    private final DefaultTableModel mInv  = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Capital"}, 0);
    private final DefaultTableModel mDoc  = new DefaultTableModel(
            new Object[]{"ID", "Tipo", "Descripción", "Prov", "Inv"}, 0);
    private final DefaultTableModel mHit  = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Fecha", "Estado", "DocId"}, 0);

    public VentanaIPO_Swing() { SwingUtilities.invokeLater(this::initUI); }

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

        recargaProv(); recargaInv(); recargaDoc();
    }

    /* ---------------- PANEL PROVEEDORES ---------------- */
    private JPanel panelProveedores() {
        JTextField tNom = new JTextField(12);
        JTextField tCon = new JTextField(12);
        JButton btnAdd = new JButton("Crear");
        JButton btnDel = new JButton("Eliminar seleccionado");

        JTable tabla = new JTable(mProv);

        btnAdd.addActionListener(e -> {
            try {
                gestor.altaProveedor(tNom.getText(), tCon.getText());
                tNom.setText(""); tCon.setText(""); recargaProv();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnDel.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) return;
            int id = (int) tabla.getValueAt(row, 0);
            try { gestor.borrarProveedor(id); recargaProv(); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Nombre")); form.add(tNom);
        form.add(new JLabel("Contacto")); form.add(tCon);
        form.add(btnAdd); form.add(btnDel);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /* ---------------- PANEL INVERSIONISTAS ---------------- */
    private JPanel panelInversionistas() {
        JTextField tNom = new JTextField(10);
        JTextField tCap = new JTextField(6);
        JButton btnAdd = new JButton("Crear");
        JButton btnDel = new JButton("Eliminar seleccionado");

        JTable tabla = new JTable(mInv);

        btnAdd.addActionListener(e -> {
            try {
                double cap = Double.parseDouble(tCap.getText());
                gestor.altaInversionista(tNom.getText(), cap);
                tNom.setText(""); tCap.setText(""); recargaInv();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnDel.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) return;
            int id = (int) tabla.getValueAt(row, 0);
            try { gestor.borrarInversionista(id); recargaInv(); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Nombre")); form.add(tNom);
        form.add(new JLabel("Capital")); form.add(tCap);
        form.add(btnAdd); form.add(btnDel);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /* ---------------- PANEL DOCUMENTOS ---------------- */
    private JPanel panelDocumentos() {
        JTextField tTipo = new JTextField(8);
        JTextField tDesc = new JTextField(10);
        JTextField tProv = new JTextField(3);
        JTextField tInv  = new JTextField(3);
        JButton btnAdd = new JButton("Crear");
        JButton btnDel = new JButton("Eliminar seleccionado");

        JTable tabla = new JTable(mDoc);

        btnAdd.addActionListener(e -> {
            try {
                Integer p = tProv.getText().isBlank() ? null : Integer.parseInt(tProv.getText());
                Integer i = tInv .getText().isBlank() ? null : Integer.parseInt(tInv .getText());
                gestor.altaDocumento(tTipo.getText(), tDesc.getText(), p, i);
                tTipo.setText(""); tDesc.setText(""); tProv.setText(""); tInv.setText("");
                recargaDoc();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnDel.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) return;
            int id = (int) tabla.getValueAt(row, 0);
            try { gestor.borrarDocumento(id); recargaDoc(); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("Tipo")); form.add(tTipo);
        form.add(new JLabel("Desc")); form.add(tDesc);
        form.add(new JLabel("ProvId")); form.add(tProv);
        form.add(new JLabel("InvId")); form.add(tInv);
        form.add(btnAdd); form.add(btnDel);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /* ---------------- PANEL HITOS ---------------- */
    private JPanel panelHitos() {
        JTextField tDoc = new JTextField(3);
        JTextField tNom = new JTextField(10);
        JTextField tDes = new JTextField(10);
        JButton btnAdd = new JButton("Añadir");
        JButton btnDel = new JButton("Eliminar seleccionado");

        JTable tabla = new JTable(mHit);

        btnAdd.addActionListener(e -> {
            try {
                int docId = Integer.parseInt(tDoc.getText());
                gestor.registrarHito(tNom.getText(), tDes.getText(),
                        "Sin iniciar", LocalDate.now(), docId);
                tNom.setText(""); tDes.setText("");
                recargaHitos(docId);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnDel.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1) return;
            int id = (int) tabla.getValueAt(row, 0);
            try {
                gestor.borrarHito(id);
                int docId = Integer.parseInt(tDoc.getText());
                recargaHitos(docId);
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("DocId")); form.add(tDoc);
        form.add(new JLabel("Nombre")); form.add(tNom);
        form.add(new JLabel("Desc")); form.add(tDes);
        form.add(btnAdd); form.add(btnDel);

        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /* ---------------- Recargas ---------------- */
    private void recargaProv() {
        try {
            mProv.setRowCount(0);
            gestor.listaProveedores()
                    .forEach(p -> mProv.addRow(new Object[]{p.id(), p.nombre(), p.contacto()}));
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void recargaInv() {
        try {
            mInv.setRowCount(0);
            gestor.listaInversionistas()
                    .forEach(i -> mInv.addRow(new Object[]{i.id(), i.nombre(), i.capital()}));
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void recargaDoc() {
        try {
            mDoc.setRowCount(0);
            gestor.listaDocumentos()
                    .forEach(d -> mDoc.addRow(new Object[]{
                            d.id(), d.tipo(), d.descripcion(), d.proveedorId(), d.inversionistaId()}));
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void recargaHitos(int docId) {
        try {
            mHit.setRowCount(0);
            gestor.hitosPorDocumento(docId)
                    .forEach(h -> mHit.addRow(new Object[]{
                            h.id(), h.nombre(), h.fecha(), h.estado(), h.documentoId()}));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) { new VentanaIPO_Swing(); }
}
