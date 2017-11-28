package com.pythonteam.ui;

import com.pythonteam.compilador.AST.Sentencia;
import com.pythonteam.compilador.Errores.PilaErrores;
import com.pythonteam.compilador.lexico.Lexico;
import com.pythonteam.compilador.semantico.Semantico;
import com.pythonteam.compilador.sintactico.Sintactico;
import com.pythonteam.models.TablaSimbolos;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Gui extends JFrame {
    private RSyntaxTextArea textArea;
    private RandomAccessFile raf;
    private JTable table;
    private Color color;
    private JButton btnLex;
    private JButton btnSin;
    private JButton btnSem;
    private final JTextArea area;
    private DefaultTableModel dt;
    private Object[][] tableData;
    private final Object[] title;
    private JButton btnCompilar;
    private Sentencia objetitos;

    public Gui(){
        JPanel cp = new JPanel();

        textArea = new RSyntaxTextArea(20, 30);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        setJMenuBar(createMenuBar());
        color = btnSin.getBackground();
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setBounds(0, 0, 398, 343);
        cp.setLayout(new BorderLayout());
        cp.add(sp, BorderLayout.CENTER);
        area = new JTextArea();
        area.setRows(6);
        area.setEditable(false);
        Font font = area.getFont();
        float size = font.getSize() + 4.0f;
        area.setFont( font.deriveFont(size) );
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        TablaSimbolos.genData();
        tableData = TablaSimbolos.getData();
        title = new Object[]{"ID","ID General","Id Tipo","Lexema","Posicion","Linea", "Valor"};
        dt = new DefaultTableModel(tableData, title);
        table = new JTable(dt);
        JScrollPane scroll2 = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(new TitledBorder("Consola de Errores"));
        cp.add(scroll, BorderLayout.SOUTH);
        cp.add(scroll2, BorderLayout.EAST);

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
        setBounds(200,0,1128,650);

    }

    private void activarBotones() {
        PilaErrores.limpiar();
        btnLex.setEnabled(true);
        btnLex.setBackground(Color.ORANGE);
        btnSin.setEnabled(false);
        btnSin.setBackground(color);
        btnSem.setEnabled(false);
        btnSem.setBackground(color);
        area.setText("");
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu mnArchivo = new JMenu("Archivo");
        JMenu mnAyuda = new JMenu("Ayuda");

        JMenuItem ayuda = new JMenuItem("Ayuda");
        ayuda.addActionListener(e -> abrirAyuda());
        JMenuItem about = new JMenuItem("Acerca de");
        about.addActionListener(e -> abrirAbout());

        mnAyuda.add(ayuda);
        mnAyuda.add(about);

        JMenuItem open = new JMenuItem("Abrir archivo");
        mnArchivo.add(open);
        open.addActionListener(e -> abrirArchivo());

        JMenuItem guardar = new JMenuItem("Guardar");
        mnArchivo.add(guardar);
        guardar.addActionListener(e -> guardarUi());
        mb.add(mnArchivo);
        mb.add(mnAyuda);

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

        btnCompilar = new JButton("Compilar");
        btnCompilar.setHorizontalAlignment(SwingConstants.RIGHT);
        btnCompilar.setEnabled(false);
        btnCompilar.addActionListener(e -> compilar());
        mb.add(btnCompilar);
        return mb;
    }

    private void abrirAbout() {

    }

    private void abrirAyuda() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("ayuda.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                System.out.println("No se puede abrir el pdf");
            }
        }
    }

    private void guardarUi() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result==JFileChooser.APPROVE_OPTION) {
            guardarArchivo(fileChooser.getSelectedFile().getPath());
        }
    }

    private void hacerSemantico() {
        new Semantico(objetitos);
        if (!PilaErrores.empty())
        {
            btnSem.setBackground(Color.red);
            area.setForeground(Color.RED);
            area.setText("\n Semanticos" + PilaErrores.getErrores());
        }
        else
        {
            btnSem.setBackground(Color.GREEN);
        }
    }

    private void hacerSintactico() {
        PilaErrores.limpiar();
        Sintactico sintactico = new Sintactico();
        if (!PilaErrores.empty())
        {
            btnSin.setBackground(Color.RED);
            btnSin.setEnabled(false);
            area.setForeground(Color.RED);
            area.setText("\n Sintacticos" + PilaErrores.getErrores());

        }else {
            btnSin.setBackground(Color.GREEN);
            btnSin.setEnabled(false);
            objetitos = sintactico.getArbolito();
            btnSem.setBackground(Color.ORANGE);
            btnSem.setEnabled(true);
        }
    }

    private void hacerLexico()
    {
        guardarArchivo("temp.cm");
        try {
            new Lexico(raf);
            if (!PilaErrores.empty())
            {
                btnLex.setBackground(Color.RED);
                area.setForeground(Color.RED);
                area.setText(PilaErrores.getErrores());
            }
            else {
                btnLex.setBackground(Color.GREEN);
                btnLex.setEnabled(false);
                btnSin.setEnabled(true);
                btnSin.setBackground(Color.ORANGE);
                TablaSimbolos.genData();
                tableData = TablaSimbolos.getData();
                dt = new DefaultTableModel(tableData, title);
                table.setModel(dt);
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
        fileChooser.setCurrentDirectory(new File("./examples"));
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
        guardarArchivo("temp.cm");
        try {
            new Lexico(raf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sintactico sintactico = new Sintactico();
        objetitos = sintactico.getArbolito();
        new Semantico(objetitos);
        if (PilaErrores.empty())
            btnCompilar.setBackground(Color.GREEN);
        else btnCompilar.setBackground(Color.RED);
    }
}
