package com.pythonteam.ui;

import com.pythonteam.compilador.Compilador;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.*;

public class Gui extends JFrame {
    RSyntaxTextArea textArea;
    RandomAccessFile raf;
    private JTable table;

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
        setLocationRelativeTo(null);
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu mnArchivo = new JMenu("Archivo");
        JMenuItem open = new JMenuItem("Abrir archivo");
        mnArchivo.add(open);
        open.addActionListener(e -> abrirArchivo());
        
        JMenuItem guardar = new JMenuItem("Guardar");
        mnArchivo.add(guardar);
        mnArchivo.addSeparator();
        mb.add(mnArchivo);
        
        JButton btnLex = new JButton("Lexico");
        btnLex.setHorizontalAlignment(SwingConstants.RIGHT);
        mb.add(btnLex);
        
        JButton btnSin = new JButton("Sintactico");
        btnSin.setHorizontalAlignment(SwingConstants.RIGHT);
        mb.add(btnSin);
        
        JButton btnSem = new JButton("Semantico");
        btnSem.setHorizontalAlignment(SwingConstants.RIGHT);
        mb.add(btnSem);
        
        JButton btnCompilar = new JButton("Compilar");
        btnCompilar.setHorizontalAlignment(SwingConstants.RIGHT);
        mb.add(btnCompilar);
        return mb;
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
        try {
            File file = new File("temp.cm");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(textArea.getText());
            output.close();
            file = new File("temp.cm");
            raf = new RandomAccessFile(file, "rw");
            Compilador comp = new Compilador(raf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
