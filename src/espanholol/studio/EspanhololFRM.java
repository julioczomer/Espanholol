/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espanholol.studio;

import espanholol.*;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import symbol.Simbolo;

/**
 *
 * @author DG
 */
public class EspanhololFRM extends javax.swing.JFrame {

    
    private static int cont = 0;
    private static EspanhololFRM fRMMarotageLanguage;
    private DefaultListModel list = new DefaultListModel();
    
    /**
     * Creates new form MarotageFRM
     */
    public EspanhololFRM() {
        initComponents();
        jtaCodigoFonte.setForeground(Color.GREEN);
        jtaCodigoFonte.setBackground(Color.BLACK);
        
        setExtendedState(MAXIMIZED_BOTH);
        jtaCodigoFonte.setTabSize(4);
        jtaCodigoFonte.setCaretColor(Color.GREEN);        
        //jpResultado.setLayout(new GridBagLayout());
        jsaida.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                      boolean isSelected, boolean cellHasFocus) {
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                 if (value instanceof String)
                    if(((String) value).contains("WAR"))
                          setForeground(Color.yellow);
                    if(((String) value).contains("ERR"))
                          setForeground(Color.RED);
                 return c;
            }

       });
    }
    
     public static synchronized EspanhololFRM getInstance() {

        if (getfRMMarotageLanguage() == null) {
            setfRMMarotageLanguage(new EspanhololFRM());
            System.out.println(cont++);
        }
        return getfRMMarotageLanguage();
    }
     
     /**
     * @return the fRMMarotageLanguage
     */
    public static EspanhololFRM getfRMMarotageLanguage() {
        return fRMMarotageLanguage;
    }
    
    public void adicionarLog(String texto, Color cor){
        String id = "SUC";
        if(cor == Color.YELLOW)
            id = "WAR";
        if(cor == Color.RED)
            id = "ERR";
        
        JLabel label = new JLabel(id + " " + texto);
        label.setForeground(cor);
        list.addElement(id + " " + texto);        
        jsaida.setModel(list);
        jsaida.revalidate();
        this.getContentPane().revalidate();

    }
    
    private void preencherTabelaComSimbolos(List<Simbolo> simbolos, List<String> assemblies) {       
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);        
        String data = ".data\n";
        for (Simbolo simbolo : simbolos) {
            modelo.addRow(new Object[]{
                simbolo.id,
                simbolo.getTipo(),
                simbolo.escopo.getKey().concat(" ").concat(simbolo.escopo.getValue().toString()),                
                simbolo.inicializado,
                simbolo.utilizado,                
                simbolo.parametro,
                simbolo.funcao,
                simbolo.vetor
            });
            if(!simbolo.funcao)
                data = data.concat(
                        "\t"
                        .concat(simbolo.id)
                        .concat(": ")
                        .concat("0\n")
                );
        }
        data = data.concat(".text\n");
        for(String instrucao : assemblies)
            data = data.concat("\t").concat(instrucao).concat("\n");
        data = data.concat("HLT");
        this.assembly.setTabSize(4);
        this.assembly.setText(data);
        this.assembly.revalidate();
        tabela.revalidate();
    }

     /**
     * @param afRMMarotageLanguage the fRMMarotageLanguage to set
     */
    public static void setfRMMarotageLanguage(EspanhololFRM afRMMarotageLanguage) {
        fRMMarotageLanguage = afRMMarotageLanguage;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtaCodigoFonte = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jbtCompilarExecutar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        abrirBTN = new javax.swing.JButton();
        salvarBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jsaida = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        assembly = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ML - Marotage Language");

        jtaCodigoFonte.setColumns(20);
        jtaCodigoFonte.setRows(5);
        jScrollPane1.setViewportView(jtaCodigoFonte);

        jLabel1.setText("Crie seu código fonte");

        jbtCompilarExecutar.setText("Compilar/Executar");
        jbtCompilarExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCompilarExecutarActionPerformed(evt);
            }
        });

        jLabel2.setText("Saída");

        abrirBTN.setText("Abrir");
        abrirBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirBTNActionPerformed(evt);
            }
        });

        salvarBTN.setText("Salvar");
        salvarBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarBTNActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tipo", "Escopo", "Inicializado", "Utilizado", "Parametro", "Funcao", "Vetor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(6).setResizable(false);
        }

        jsaida.setBackground(new java.awt.Color(0, 0, 0));
        jsaida.setForeground(new java.awt.Color(0, 255, 0));
        jScrollPane3.setViewportView(jsaida);

        assembly.setColumns(20);
        assembly.setRows(5);
        jScrollPane4.setViewportView(assembly);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 444, Short.MAX_VALUE)
                        .addComponent(abrirBTN)
                        .addGap(18, 18, 18)
                        .addComponent(salvarBTN)
                        .addGap(17, 17, 17)
                        .addComponent(jbtCompilarExecutar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtCompilarExecutar)
                    .addComponent(abrirBTN)
                    .addComponent(salvarBTN))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(754, 443));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtCompilarExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCompilarExecutarActionPerformed
        /*jpResultado.removeAll();
        jpResultado.revalidate();*/
        list.clear();
        jsaida.revalidate();
        this.getContentPane().revalidate();

        String texto = jtaCodigoFonte.getText();
        Lexico lex = new Lexico(texto);
        Sintatico sin = new Sintatico();
        Semantico sem = new Semantico();
        try {
            sin.parse(lex, sem);
            sem.obterSimbolosNaoUtilizadosInicializados();
            for (String war : sem.warnings)
                adicionarLog(war, Color.YELLOW);
            
            preencherTabelaComSimbolos(sem.simbolos, sem.assemblies);
            System.out.println(sem.assemblies);           
            adicionarLog("Código analisado com sucesso.", Color.GREEN);            
        } catch (LexicalError | SemanticError |SyntaticError ex) {
            //Logger.getLogger(EspanhololStudio.class.getName()).log(Level.SEVERE, null, ex);
            
            sem.obterSimbolosNaoUtilizadosInicializados();
            for (String war : sem.warnings)
                adicionarLog(war, Color.YELLOW);
            adicionarLog(ex.getMessage(), Color.RED);
        } 
    }//GEN-LAST:event_jbtCompilarExecutarActionPerformed

    private void abrirBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirBTNActionPerformed
        final JFileChooser fc = new JFileChooser();
        int option = fc.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            String content = FileIOManager.getFileContent(path);
            jtaCodigoFonte.setText(content);
        }
    }//GEN-LAST:event_abrirBTNActionPerformed

    private void salvarBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarBTNActionPerformed
        final JFileChooser fc = new JFileChooser();
        int option = fc.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            FileIOManager.createFileAndWrite(path, jtaCodigoFonte.getText());
        }
    }//GEN-LAST:event_salvarBTNActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EspanhololFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EspanhololFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EspanhololFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EspanhololFRM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EspanhololFRM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirBTN;
    private javax.swing.JTextArea assembly;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbtCompilarExecutar;
    private javax.swing.JList<String> jsaida;
    private javax.swing.JTextArea jtaCodigoFonte;
    private javax.swing.JButton salvarBTN;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables

   

   
}

