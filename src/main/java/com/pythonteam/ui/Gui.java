package com.pythonteam.ui;

import com.pythonteam.compilador.Compilador;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;

public class Gui extends JFrame {
    RSyntaxTextArea textArea;
    RandomAccessFile raf;
    private JTable table;
    private JButton btnLex;
    private JButton btnSin;

    public Gui(){
        JPanel cp = new JPanel(new BorderLayout());

        textArea = new RSyntaxTextArea(20, 30);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        setJMenuBar(createMenuBar());
        RTextScrollPane sp = new RTextScrollPane(textArea);
        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(cp.getWidth(), 16));
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        cp.add(statusPanel, BorderLayout.SOUTH);
        JLabel statusLabel = new JLabel("Listo");
        statusPanel.add(statusLabel, BorderLayout.WEST);
        cp.add(sp);

        setContentPane(cp);

        JPanel panel = new JPanel();
        cp.add(panel, BorderLayout.EAST);

        table = new JTable();
        panel.add(table);
        setTitle("Text Editor Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
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
    }

    private void activarBotones() {
        btnLex.setEnabled(true);
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
        btnLex.addActionListener(e -> hacerLexico());
        mb.add(btnLex);

        btnSin = new JButton("Sintactico");
        btnSin.setHorizontalAlignment(SwingConstants.RIGHT);
        btnSin.addActionListener(e -> hacerSintactico());
        btnSin.setEnabled(false);
        mb.add(btnSin);

        JButton btnSem = new JButton("Semantico");
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

    }

    private void hacerSintactico() {
    }

    private void hacerLexico()
    {
        guardarArchivo("temp.cm");
        try {
            Compilador comp = new Compilador(raf);
            btnLex.setBackground(Color.GREEN);
            btnLex.setEnabled(false);
            btnSin.setEnabled(true);
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
