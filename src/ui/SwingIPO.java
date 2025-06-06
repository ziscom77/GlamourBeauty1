package ui;

import dao.CotizacionDao;
import financial.GlamourFinancial;
import financial.GlamourFinancialBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * SwingFinanzas – interfaz gráfica para el Ejercicio 1
 * ----------------------------------------------------
 * • Lee / escribe cotizaciones vía financial.GlamourFinancialBD  (usa dao.CotizacionDao + MySQL)
 * • Muestra las cotizaciones en una JTable
 * • Calcula Valor de Mercado y P / Valor en Libros
 *
 *  ▸ Si prefieres trabajar sólo en memoria cambia la línea:
 *      new financial.GlamourFinancialBD(...)  -->  new GlamourFinancialImpl(...)
 */
public class SwingIPO{

    /* ----------------- Servicio financiero ----------------- */
    private final GlamourFinancial finanzas =
            new GlamourFinancialBD(50_000_000, 200_000_000);  // acciones, valor libros

    /* ----------------- Modelo de tabla ----------------- */
    private final DefaultTableModel modelo =
            new DefaultTableModel(new Object[]{"Mercado","Precio"}, 0);

    /* ----------------- Constructor: lanza la UI ----------------- */
    public SwingIPO() { SwingUtilities.invokeLater(this::initUI); }

    /* ----------------- Construcción de la ventana ----------------- */
    private void initUI() {

        /* ----- Ventana ----- */
        JFrame f = new JFrame("Finanzas – Glamour Beauty");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 420);

        /* ----- Formulario de alta/actualización ----- */
        JTextField tMerc = new JTextField(6);
        JTextField tPrec = new JTextField(6);
        JButton btnSave  = new JButton("Guardar / Actualizar");

        JPanel form = new JPanel();
        form.add(new JLabel("Mercado")); form.add(tMerc);
        form.add(new JLabel("Precio"));  form.add(tPrec);
        form.add(btnSave);

        /* ----- Tabla de cotizaciones ----- */
        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);

        /* ----- Indicadores ----- */
        JLabel lblVM = new JLabel("Valor de mercado: 0 €");
        JLabel lblPB = new JLabel("P / Valor en libros: 0");

        JPanel south = new JPanel(new GridLayout(2,1));
        south.add(lblVM);
        south.add(lblPB);

        /* ----- Acción del botón ----- */
        btnSave.addActionListener(e -> {
            try {
                String mercado = tMerc.getText().toUpperCase().trim();
                double precio  = Double.parseDouble(tPrec.getText());
                finanzas.actualizarCotizacion(mercado, precio);

                recargaTabla();                       // refresca JTable
                lblVM.setText("Valor de mercado: " + finanzas.calcularValorDeMercado());
                lblPB.setText("P / Valor en libros: " + finanzas.calcularPrecioValorEnLibros());

                tMerc.setText(""); tPrec.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f,
                        "Error: introduce un número válido\n" + ex.getMessage(),
                        "Dato incorrecto", JOptionPane.ERROR_MESSAGE);
            }
        });

        /* ----- Layout ----- */
        f.add(form,  BorderLayout.NORTH);
        f.add(new JScrollPane(tabla), BorderLayout.CENTER);
        f.add(south, BorderLayout.SOUTH);

        recargaTabla();               // carga datos existentes
        f.setVisible(true);
    }

    /* ----------------- Rellenar/actualizar JTable ----------------- */
    private void recargaTabla() {
        try {
            modelo.setRowCount(0);
            new CotizacionDao().findAll()
                    .forEach((mercado, precio) ->
                            modelo.addRow(new Object[]{mercado, precio}));
        } catch (Exception e) { e.printStackTrace(); }
    }

    /* ----------------- main ----------------- */
    public static void main(String[] args) { new SwingIPO(); }
}
