/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * multiple_alignment.java
 *
 * Created on 17.05.2010, 13:45:03
 */

package DNA_zu_Protein;

/**
 *
 * @author admin
 */
public class multiple_alignment extends javax.swing.JFrame {

    /** Creates new form multiple_alignment */
    public multiple_alignment() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        al_1 = new javax.swing.JTextField();
        al_3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        multi_out = new javax.swing.JTextArea();
        al_4 = new javax.swing.JTextField();
        al_5 = new javax.swing.JTextField();
        al_2 = new javax.swing.JTextField();
        strafe = new javax.swing.JTextField();
        match = new javax.swing.JTextField();
        mismatch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        baum = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        al_1.setText("DNA 1");

        al_3.setText("DNA 3");

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        multi_out.setColumns(20);
        multi_out.setRows(5);
        jScrollPane1.setViewportView(multi_out);

        al_4.setText("DNA 4");

        al_5.setText("DNA 5");

        al_2.setText("DNA 2");

        strafe.setText("Strafe");

        match.setText("Match");

        mismatch.setText("Mismatch");

        jButton2.setText("Default");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        baum.setText("Baum zeichnen");
        baum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(strafe, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addComponent(baum)
                    .addComponent(al_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addComponent(al_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addComponent(al_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addComponent(al_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addComponent(al_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(match, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(mismatch, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(strafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(match, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mismatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(al_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(al_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(al_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(al_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(al_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(88, 88, 88))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(baum)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.multiple_alignment();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        al_1.setText("ATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTA");
        al_2.setText("TGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATG");
        al_3.setText("TAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGA");
        al_4.setText("AATGAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAA");
        al_5.setText("TGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTG");
        strafe.setText("-1");
        match.setText("2");
        mismatch.setText("-2");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void baumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baumActionPerformed
        // TODO add your handling code here:
        //AusgabePanel ap = new AusgabePanel("Phylogenie-Baum", 1000, 1000);
        this.abstandBerechnen();
        
    }//GEN-LAST:event_baumActionPerformed
    public int kl_Abstand_i;
    public int kl_Abstand_j;
    public int maximum;
    public void abstandBerechnen(){
        int[][] m_a = this.multiple_alignment_ohne_ausgabe();
        int[][] vgl_matrix = new int[m_a.length-2][m_a.length-2];
        int counter = 0;
        int new_max = -1000000;
        for(int a=0;a<5;a++){
            for(int i=0;i<m_a.length;i++){ //Finden der am nächsten Verwandten Sequenzen.
                for(int j=i+1;j<m_a.length;j++){
                    if(m_a[i][j]!=0&&m_a[i][j]>new_max){
                        new_max = m_a[i][j];
                        this.kl_Abstand_i = i;
                        this.kl_Abstand_j = j;
                        this.maximum = m_a[i][j];
                    }
                }
            }
        
        m_a[kl_Abstand_i][kl_Abstand_j] = 0;//setzt auf 0, damit es beim nächsten Suchdurchlauf nicht wieder als Minimum erkannt wird.
        if(counter==0){
            //System.out.print(new_max+"(i="+kl_Abstand_i+", j="+kl_Abstand_j+")"+"\t");
            vgl_matrix[0][0] = 0;
            new_max = -1000000;
            counter++;
        }
        else{
        //System.out.print(new_max+"(i="+kl_Abstand_i+", j="+kl_Abstand_j+")"+"\t");
        new_max = -1000000;
        for(int i=0;i<kl_Abstand_i;i++){
            vgl_matrix[i][0] = (m_a[i][kl_Abstand_j]+m_a[i][kl_Abstand_i])/2;
            System.out.print(vgl_matrix[i][0]+"\t");// 102 stimmt als Ergebnis!
        }
        for(int i=kl_Abstand_i;i<vgl_matrix.length;i++){
            vgl_matrix[i][0] = (m_a[i][kl_Abstand_j]+m_a[i][kl_Abstand_i])/2;
            System.out.print(vgl_matrix[i][0]+"\t");
        }
        System.out.println("");
        }
        }
        char x = this.abcde()[kl_Abstand_i-1];
        char y = this.abcde()[kl_Abstand_j];
        String b = "";
        for(int i=0;i<5;i++){
            if(i==kl_Abstand_i-1||i==kl_Abstand_j){
                }
            else{
                b += "\t"+this.abcde()[i];
            }
        }
        System.out.println(x+"+"+y+b);
    }
    char[] abcde(){
        char[] a_e = new char[5];
        a_e[0] = 'A';
        a_e[1] = 'B';
        a_e[2] = 'C';
        a_e[3] = 'D';
        a_e[4] = 'E';
        return a_e;
    }
    /**
    * @param args the command line arguments
    */
    DNA_Protein_homo_sapiens m = new DNA_Protein_homo_sapiens();
    

    public int[][] multiple_alignment_ohne_ausgabe(){
    int [][] m_a = new int [6][6];
    for(int i=0;i<6;i++)
        for(int j=0;j<6;j++){
            if(i==j)
                m_a[i][j] = 0;
        }
    String[] s = new String[5];
    s[0] = al_1.getText();
    s[1] = al_2.getText();
    s[2] = al_3.getText();
    s[3] = al_4.getText();
    s[4] = al_5.getText();
    int[][] a;
    for(int i=0;i<5;i++)
        for(int j=i+1;j<5;j++){
            a = m.aehnlichkeit_ohne_ausgabe(s[i],s[j],Integer.parseInt(strafe.getText()),Integer.parseInt(match.getText()),Integer.parseInt(mismatch.getText()));
            m_a[i+1][j+1] = a[a.length-1][a[a.length-1].length-1];
        }
    return m_a;
    }
    public int[][] multiple_alignment(){
    int [][] m_a = new int [5][5];
    for(int i=0;i<5;i++)
        for(int j=0;j<5;j++){
            if(i==j)
                m_a[i][j] = 0;
        }
    String[] s = new String[5];
    s[0] = al_1.getText();
    s[1] = al_2.getText();
    s[2] = al_3.getText();
    s[3] = al_4.getText();
    s[4] = al_5.getText();
    int[][] a;
    for(int i=0;i<5;i++)
        for(int j=i+1;j<5;j++){
            a = m.aehnlichkeit_ohne_ausgabe(s[i],s[j],Integer.parseInt(strafe.getText()),Integer.parseInt(match.getText()),Integer.parseInt(mismatch.getText()));
            m_a[i][j] = a[a.length-1][a[a.length-1].length-1];
        }
    //Ausgabe:
    String out_string = new String();
    out_string+=" A\tB\tC\tD\tE\t\t\n";
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++){
                if(j<4){
                    if(m_a[i][j]<0)
                        out_string+=(m_a[i][j]+"\t");
                    else
                        out_string+=((m_a[i][j])+"\t\b");
                }
                else
                    out_string+=(m_a[i][j]+"\n\n");
            }
    multi_out.setText(out_string);
    return m_a;
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new multiple_alignment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField al_1;
    private javax.swing.JTextField al_2;
    private javax.swing.JTextField al_3;
    private javax.swing.JTextField al_4;
    private javax.swing.JTextField al_5;
    private javax.swing.JButton baum;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField match;
    private javax.swing.JTextField mismatch;
    private javax.swing.JTextArea multi_out;
    private javax.swing.JTextField strafe;
    // End of variables declaration//GEN-END:variables

}
