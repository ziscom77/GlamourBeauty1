package ui;

import model.*;
import service.GestorFormacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;


public class SwingFormacion {

    private final GestorFormacion gf = new GestorFormacion();

    /* ================== MODELOS DE TABLA ================== */
    private final DefaultTableModel mAsig = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Créditos"}, 0);
    private final DefaultTableModel mCur  = new DefaultTableModel(
            new String[]{"ID", "Nombre", "AsigId", "Periodo"}, 0);
    private final DefaultTableModel mEmp  = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Depto"}, 0);
    private final DefaultTableModel mMat  = new DefaultTableModel(
            new String[]{"EmpleadoId", "CursoId"}, 0);
    private final DefaultTableModel mNota = new DefaultTableModel(
            new String[]{"EmpleadoId", "CursoId", "Valor"}, 0);
    private final DefaultTableModel mCer  = new DefaultTableModel(
            new String[]{"ID", "EmpleadoId", "Descripción", "Fecha"}, 0);

    /* ================== COMPONENTES GLOBALES ================== */
    private JComboBox<Asignatura> cbAsigCurso;     // para crear curso
    private JComboBox<Empleado>   cbEmpIns;        // inscribir
    private JComboBox<Curso>      cbCurIns;        // inscribir
    private JComboBox<Empleado>   cbEmpNota;       // nota
    private JComboBox<Curso>      cbCurNota;       // nota
    private JComboBox<Empleado>   cbEmpCert;       // certif.

    public SwingFormacion() { SwingUtilities.invokeLater(this::initUI); }

    /* --------------------------------------------------------- */
    private void initUI() {
        JFrame f = new JFrame("Formación · Glamour Beauty");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1100, 650);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Asignaturas",  panelAsignaturas());
        tabs.add("Cursos",       panelCursos());
        tabs.add("Empleados",    panelEmpleados());
        tabs.add("Inscripciones / Notas", panelInscripcionesNotas());
        tabs.add("Certificados", panelCertificados());

        f.add(tabs);
        f.setVisible(true);

        recargaTodo();      // primera carga
    }

    /* ==========================================================
                           P E S T A Ñ A S
       ========================================================== */
    /* ---------- 1. ASIGNATURAS ---------- */
    private JPanel panelAsignaturas() {
        JTextField tNom  = new JTextField(14);
        JSpinner  spCred = new JSpinner(new SpinnerNumberModel(3, 1, 30, 1));
        JButton btnAdd = new JButton("Crear");
        JButton btnDel = new JButton("Borrar");

        JTable tabla = new JTable(mAsig);

        btnAdd.addActionListener(e -> {
            try {
                gf.altaAsignatura(tNom.getText(), (int) spCred.getValue());
                tNom.setText("");
                recargaAsig();
                recargaComboBoxFuentes();          // refresca combos dependientes
            } catch (Exception ex) { mostrarError(ex); }
        });

        btnDel.addActionListener(e ->
                borrarFila(tabla, () -> {
                    try {
                        int id = (int) tabla.getValueAt(tabla.getSelectedRow(), 0);
                        gf.borrarAsignatura(id);
                        recargaAsig();
                        recargaComboBoxFuentes();
                    } catch (Exception ex) { mostrarError(ex); }
                })
        );


        JPanel form = new JPanel();
        form.add(new JLabel("Nombre"));
        form.add(tNom);
        form.add(new JLabel("Créditos"));
        form.add(spCred);
        form.add(btnAdd); form.add(btnDel);

        return wrap(form, tabla);
    }

    /* ---------- 2. CURSOS ---------- */
    private JPanel panelCursos() {
        JTextField tNom = new JTextField(12);
        cbAsigCurso = new JComboBox<>();
        JTextField tPer = new JTextField(6);
        JButton btnAdd = new JButton("Crear");
        JButton btnDel = new JButton("Borrar");

        JTable tabla = new JTable(mCur);

        btnAdd.addActionListener(e -> {
            try {
                Asignatura a = (Asignatura) cbAsigCurso.getSelectedItem();
                if (a == null) return;
                gf.altaCurso(tNom.getText(), a.id(), tPer.getText());
                tNom.setText(""); tPer.setText("");
                recargaCursos();
                recargaComboBoxFuentes();
            } catch (Exception ex) { mostrarError(ex); }
        });

        btnDel.addActionListener(e ->
                borrarFila(tabla, () -> {
                    try {
                        int id = (int) tabla.getValueAt(tabla.getSelectedRow(), 0);
                        gf.borrarCurso(id);
                        recargaCursos();
                        recargaComboBoxFuentes();
                    } catch (Exception ex) { mostrarError(ex); }
                })
        );


        JPanel form = new JPanel();
        form.add(new JLabel("Nombre"));
        form.add(tNom);
        form.add(new JLabel("model.Asignatura"));
        form.add(cbAsigCurso);
        form.add(new JLabel("Periodo"));
        form.add(tPer);
        form.add(btnAdd); form.add(btnDel);

        return wrap(form, tabla);
    }

    /* ---------- 3. EMPLEADOS ---------- */
    private JPanel panelEmpleados() {
        JTextField tNom = new JTextField(12);
        JTextField tDep = new JTextField(8);
        JButton btnAdd = new JButton("Crear");
        JButton btnDel = new JButton("Borrar");

        JTable tabla = new JTable(mEmp);

        btnAdd.addActionListener(e -> {
            try {
                gf.altaEmpleado(tNom.getText(), tDep.getText());
                tNom.setText(""); tDep.setText("");
                recargaEmps();
                recargaComboBoxFuentes();
            } catch (Exception ex) { mostrarError(ex); }
        });

        btnDel.addActionListener(e ->
                borrarFila(tabla, () -> {
                    try {
                        int id = (int) tabla.getValueAt(tabla.getSelectedRow(), 0);
                        gf.borrarEmpleado(id);
                        recargaEmps();
                        recargaComboBoxFuentes();
                    } catch (Exception ex) { mostrarError(ex); }
                })
        );


        JPanel form = new JPanel();
        form.add(new JLabel("Nombre"));
        form.add(tNom);
        form.add(new JLabel("Depto"));
        form.add(tDep);
        form.add(btnAdd); form.add(btnDel);

        return wrap(form, tabla);
    }

    /* ---------- 4. INSCRIPCIONES Y NOTAS ---------- */
    private JPanel panelInscripcionesNotas() {

        /* ---- sub-panel Inscripción ---- */
        cbEmpIns = new JComboBox<>();
        cbCurIns = new JComboBox<>();
        JButton btnIns = new JButton("Inscribir");
        JButton btnDes = new JButton("Desinscribir");

        btnIns.addActionListener(e -> {
            try {
                Empleado emp = (Empleado) cbEmpIns.getSelectedItem();
                Curso    cur = (Curso) cbCurIns.getSelectedItem();
                if (emp == null || cur == null) return;
                gf.inscribir(emp.id(), cur.id());
                recargaMat();
            } catch (Exception ex) { mostrarError(ex); }
        });

        btnDes.addActionListener(e -> {
            try {
                Empleado emp = (Empleado) cbEmpIns.getSelectedItem();
                Curso    cur = (Curso) cbCurIns.getSelectedItem();
                if (emp == null || cur == null) return;
                gf.desinscribir(emp.id(), cur.id());
                recargaMat();
            } catch (Exception ex) { mostrarError(ex); }
        });

        JPanel pIns = new JPanel();
        pIns.add(new JLabel("model.Empleado"));
        pIns.add(cbEmpIns);
        pIns.add(new JLabel("model.Curso"));
        pIns.add(cbCurIns);
        pIns.add(btnIns);
        pIns.add(btnDes);

        /* ---- sub-panel model.Nota ---- */
        cbEmpNota = new JComboBox<>();
        cbCurNota = new JComboBox<>();
        JSpinner spNota = new JSpinner(new SpinnerNumberModel(0.0, 0, 10, 0.5));
        JButton btnNota = new JButton("Registrar model.Nota");

        btnNota.addActionListener(e -> {
            try {
                Empleado emp = (Empleado) cbEmpNota.getSelectedItem();
                Curso    cur = (Curso) cbCurNota.getSelectedItem();
                if (emp == null || cur == null) return;
                double val = ((Number) spNota.getValue()).doubleValue();
                gf.registrarNota(emp.id(), cur.id(), val);
                recargaNotas();
            } catch (Exception ex) { mostrarError(ex); }
        });

        JPanel pNota = new JPanel();
        pNota.add(new JLabel("model.Empleado"));
        pNota.add(cbEmpNota);
        pNota.add(new JLabel("model.Curso"));
        pNota.add(cbCurNota);
        pNota.add(new JLabel("Valor"));
        pNota.add(spNota);
        pNota.add(btnNota);

        JTable tMat  = new JTable(mMat);
        JTable tNota = new JTable(mNota);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tMat), new JScrollPane(tNota));
        split.setDividerLocation(160);

        JPanel north = new JPanel(new GridLayout(2, 1));
        north.add(pIns);
        north.add(pNota);

        JPanel p = new JPanel(new BorderLayout());
        p.add(north, BorderLayout.NORTH);
        p.add(split, BorderLayout.CENTER);
        return p;
    }

    /* ---------- 5. CERTIFICADOS ---------- */
    private JPanel panelCertificados() {
        cbEmpCert = new JComboBox<>();
        JTextField tDesc = new JTextField(15);
        JButton btnEmit = new JButton("Emitir");

        btnEmit.addActionListener(e -> {
            try {
                Empleado emp = (Empleado) cbEmpCert.getSelectedItem();
                if (emp == null) return;
                gf.emitirCertificado(emp.id(), tDesc.getText());
                tDesc.setText("");
                recargaCert();
            } catch (Exception ex) { mostrarError(ex); }
        });

        JPanel form = new JPanel();
        form.add(new JLabel("model.Empleado"));
        form.add(cbEmpCert);
        form.add(new JLabel("Descripción"));
        form.add(tDesc);
        form.add(btnEmit);

        JTable tabla = new JTable(mCer);
        return wrap(form, tabla);
    }

    /* ==========================================================
                          R E C A R G A R
       ========================================================== */
    private void recargaAsig() {
        try {
            mAsig.setRowCount(0);
            for (Asignatura a : gf.listaAsignaturas())
                mAsig.addRow(new Object[]{a.id(), a.nombre(), a.creditos()});
        } catch (Exception ex) { mostrarError(ex); }
    }

    private void recargaCursos() {
        try {
            mCur.setRowCount(0);
            for (Curso c : gf.listaCursos())
                mCur.addRow(new Object[]{c.id(), c.nombre(), c.asignaturaId(), c.periodo()});
        } catch (Exception ex) { mostrarError(ex); }
    }

    private void recargaEmps() {
        try {
            mEmp.setRowCount(0);
            for (Empleado e : gf.listaEmpleados())
                mEmp.addRow(new Object[]{e.id(), e.nombre(), e.depto()});
        } catch (Exception ex) { mostrarError(ex); }
    }

    private void recargaMat() {
        try {
            mMat.setRowCount(0);
            for (Matricula m : gf.listaMatriculas())
                mMat.addRow(new Object[]{m.empleadoId(), m.cursoId()});
        } catch (Exception ex) { mostrarError(ex); }
    }

    private void recargaNotas() {
        try {
            mNota.setRowCount(0);
            for (Nota n : gf.listaNotas())
                mNota.addRow(new Object[]{n.empleadoId(), n.cursoId(), n.valor()});
        } catch (Exception ex) { mostrarError(ex); }
    }

    private void recargaCert() {
        try {
            mCer.setRowCount(0);
            for (Certificado c : gf.listaCertificados())
                mCer.addRow(new Object[]{c.id(), c.empleadoId(), c.descripcion(), LocalDate.now()});
        } catch (Exception ex) { mostrarError(ex); }
    }

    /** Recarga combos (Asignaturas, Cursos, Empleados) cuando cambian las fuentes */
    private void recargaComboBoxFuentes() {
        try {
            cbAsigCurso.removeAllItems();
            for (Asignatura a : gf.listaAsignaturas()) cbAsigCurso.addItem(a);

            cbCurIns.removeAllItems();
            cbCurNota.removeAllItems();
            for (Curso c : gf.listaCursos()) { cbCurIns.addItem(c); cbCurNota.addItem(c); }

            cbEmpIns.removeAllItems();
            cbEmpNota.removeAllItems();
            cbEmpCert.removeAllItems();
            for (Empleado e : gf.listaEmpleados()) {
                cbEmpIns.addItem(e); cbEmpNota.addItem(e); cbEmpCert.addItem(e);
            }
        } catch (Exception ex) { mostrarError(ex); }
    }

    private void recargaTodo() {
        recargaAsig();
        recargaCursos();
        recargaEmps();
        recargaMat();
        recargaNotas();
        recargaCert();
        recargaComboBoxFuentes();
    }

    /* ==========================================================
                        U T I L I D A D E S
       ========================================================== */
    /** Devuelve un panel BorderLayout con form arriba y tabla centro */
    private JPanel wrap(JPanel form, JTable tabla) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        p.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return p;
    }

    /** Borra fila seleccionada solo si el usuario ha hecho clic */
    private void borrarFila(JTable tabla, Runnable accion) {
        if (tabla.getSelectedRow() == -1) return;
        try { accion.run(); } catch (Exception ex) { mostrarError(ex); }
    }

    private void mostrarError(Throwable ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) { new SwingFormacion(); }
}
