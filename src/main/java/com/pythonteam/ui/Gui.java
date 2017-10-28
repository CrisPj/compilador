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

    public Gui(){
        JPanel cp = new JPanel(new BorderLayout());

        textArea = new RSyntaxTextArea(20, 60);
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
        JProgressBar progressBar = new JProgressBar();
        statusPanel.add(progressBar, BorderLayout.EAST);
        cp.add(sp);

        setContentPane(cp);
        setTitle("Text Editor Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem open = new JMenuItem("Abrir archivo");
        menu.add(open);
        open.addActionListener(e -> abrirArchivo());
        JMenuItem compilar = new JMenuItem("Compilar");
        compilar.addActionListener(e -> compilar());
        menu.add(compilar);
        menu.addSeparator();
        mb.add(menu);
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
