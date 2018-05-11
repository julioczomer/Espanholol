/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espanholol.studio;

import espanholol.*;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import symbol.Simbolo;

/**
 *
 * @author DG
 */
public class EspanhololFRM extends javax.swing.JFrame {

    
    private static int cont = 0;
    private static EspanhololFRM fRMMarotageLanguage;
    
    /**
     * Creates new form MarotageFRM
     */
    public EspanhololFRM() {
        initComponents();
        jtaCodigoFonte.setForeground(Color.GREEN);
        jtaCodigoFonte.setBackground(Color.BLACK);
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[] {200, 1};
        //layout.
        jpResultado.setLayout(layout);
        setExtendedState(MAXIMIZED_BOTH);
        jtaCodigoFonte.setTabSize(4);
        jtaCodigoFonte.setCaretColor(Color.GREEN);
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
        int id = jpResultado.getComponentCount();
        JLabel label = new JLabel("[" + id + "] " + texto);
        label.setForeground(cor);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = id;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.VERTICAL;
        
        jpResultado.add(label, c);
        jpResultado.revalidate();
        this.getContentPane().revalidate();

    }
    
    private void preencherTabelaComSimbolos(List<Simbolo> simbolos) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);
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
        }
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
        jpResultado = new javax.swing.JPanel();
        abrirBTN = new javax.swing.JButton();
        salvarBTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

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

        jpResultado.setBackground(new java.awt.Color(0, 0, 0));
        jpResultado.setForeground(new java.awt.Color(255, 255, 255));
        jpResultado.setEnabled(false);
        jpResultado.setPreferredSize(new java.awt.Dimension(0, 150));

        javax.swing.GroupLayout jpResultadoLayout = new javax.swing.GroupLayout(jpResultado);
        jpResultado.setLayout(jpResultadoLayout);
        jpResultadoLayout.setHorizontalGroup(
            jpResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpResultadoLayout.setVerticalGroup(
            jpResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 115, Short.MAX_VALUE)
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1))
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jpResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(jpResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
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
        try {
            jpResultado.removeAll();
            String texto = jtaCodigoFonte.getText();
            Lexico lex = new Lexico(texto);
            Sintatico sin = new Sintatico();
            Semantico sem = new Semantico();
            sin.parse(lex, sem);
            
            
            sem.obterSimbolosNaoUtilizadosInicializados();
            for (String war : sem.warnings)
                adicionarLog(war, Color.YELLOW);
            
            preencherTabelaComSimbolos(sem.simbolos);
                        
            adicionarLog("Código analisado com sucesso.", Color.GREEN);
        } catch (LexicalError | SemanticError |SyntaticError ex) {
            //Logger.getLogger(EspanhololStudio.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtCompilarExecutar;
    private javax.swing.JPanel jpResultado;
    private javax.swing.JTextArea jtaCodigoFonte;
    private javax.swing.JButton salvarBTN;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables

   

   
}

