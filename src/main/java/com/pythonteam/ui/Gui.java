package com.pythonteam.ui;

import com.pythonteam.compilador.Compilador;
import com.pythonteam.compilador.PilaErrores;
import com.pythonteam.compilador.lexico.Lexico;
import com.pythonteam.compilador.sintactico.Sintactico;
import com.pythonteam.models.TablaSimbolos;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Gui extends JFrame {
    RSyntaxTextArea textArea;
    RandomAccessFile raf;
    private JTable table;
    private JButton btnLex;
    private JButton btnSin;
    private JButton btnSem;
    private final JTextArea area;

    public Gui(){
        JPanel cp = new JPanel();

        textArea = new RSyntaxTextArea(20, 30);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        setJMenuBar(createMenuBar());
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setBounds(0, 0, 398, 343);
        cp.setLayout(new BorderLayout());
        cp.add(sp, BorderLayout.CENTER);
        area = new JTextArea();
        area.setRows(8);
        area.setEditable(false);
        Font font = area.getFont();
        float size = font.getSize() + 4.0f;
        area.setFont( font.deriveFont(size) );
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(new TitledBorder("Consola de Errores"));
        cp.add(scroll, BorderLayout.SOUTH);

        setContentPane(cp);

        setTitle("Cmenos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                activarBotones();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                activarBotones();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });
        setLocationRelativeTo(null);
        setBounds(200,200,600,500);

    }

    private void activarBotones() {
        btnLex.setEnabled(true);
        btnLex.setBackground(Color.ORANGE);
        btnSin.setEnabled(false);
        btnSin.setBackground(Color.ORANGE);
        btnSem.setEnabled(false);
        btnSem.setBackground(Color.ORANGE);
        area.setText("");
    }

    private void activar() {

    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu mnArchivo = new JMenu("Archivo");
        JMenuItem open = new JMenuItem("Abrir archivo");
        mnArchivo.add(open);
        open.addActionListener(e -> abrirArchivo());

        JMenuItem guardar = new JMenuItem("Guardar");
        mnArchivo.add(guardar);
        guardar.addActionListener(e -> guardarUi());
        mb.add(mnArchivo);

        btnLex = new JButton("Lexico");
        btnLex.setHorizontalAlignment(SwingConstants.RIGHT);
        btnLex.setBackground(Color.ORANGE);
        btnLex.addActionListener(e -> hacerLexico());
        mb.add(btnLex);

        btnSin = new JButton("Sintactico");
        btnSin.setHorizontalAlignment(SwingConstants.RIGHT);
        btnSin.addActionListener(e -> hacerSintactico());
        btnSin.setEnabled(false);
        mb.add(btnSin);

        btnSem = new JButton("Semantico");
        btnSem.setHorizontalAlignment(SwingConstants.RIGHT);
        btnSem.addActionListener(e -> hacerSemantico());
        btnSem.setEnabled(false);
        mb.add(btnSem);

        JButton btnCompilar = new JButton("Compilar");
        btnCompilar.setHorizontalAlignment(SwingConstants.RIGHT);
        btnCompilar.setEnabled(false);
        btnCompilar.addActionListener(e -> hacerLexico());
        mb.add(btnCompilar);
        return mb;
    }

    private void guardarUi() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result==JFileChooser.APPROVE_OPTION) {
            guardarArchivo(fileChooser.getSelectedFile().getPath());
        }
    }

    private void hacerSemantico() {
        JOptionPane.showMessageDialog(this,"En construccion");
    }

    private void hacerSintactico() {
        PilaErrores.limpiar();
        Sintactico sintactico = new Sintactico();
        if (!PilaErrores.empty())
        {
            btnSin.setBackground(Color.RED);
            btnSin.setEnabled(false);
            area.setForeground(Color.RED);
            area.setText(area.getText() + "\n Sintacticos" + PilaErrores.getSintacticoErrors());

        }else {
            btnSin.setBackground(Color.GREEN);
            btnSin.setEnabled(false);
            btnSem.setBackground(Color.ORANGE);
            btnSem.setEnabled(true);
        }
    }

    private void hacerLexico()
    {
        guardarArchivo("temp.cm");
        try {
            Lexico lex = new Lexico(raf);
            if (!TablaSimbolos.hayErrores())
            {
                btnLex.setBackground(Color.RED);
                area.setForeground(Color.RED);
                area.setText(TablaSimbolos.getErrors());
            }
            else {
                btnLex.setBackground(Color.GREEN);
                btnLex.setEnabled(false);
                btnSin.setEnabled(true);
                TablaSimbolos.genData();
                Object[][] tableData = TablaSimbolos.getData();
                Object[] das =new Object[]{"ID","ID General","Id Tipo","Lexema","Posicion","Linea", "Valor","Clasificacion"};
                DefaultTableModel dt = new DefaultTableModel(tableData,das);
                JTable tabla = new JTable(dt);
                final JDialog frame = new JDialog(this, "Tabla de simbolos", true);
                frame.setLayout(new BorderLayout());
                frame.getContentPane().add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                        BorderLayout.CENTER);
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            btnLex.setBackground(Color.RED);
        }
    }

    private void guardarArchivo(String nombre)
    {
        try {
            File file = new File(nombre);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(textArea.getText());
            output.close();
            file = new File(nombre);
            raf = new RandomAccessFile(file, "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result==JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                raf = new RandomAccessFile(file, "rw");
                textArea.setText("");
                String c;
                while ((c = raf.readLine()) != null)
                {
                    textArea.append(c+"\n");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void compilar() {

    }
}
