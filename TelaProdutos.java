package br.edu.ifpr.dao;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class TelaProdutos extends JFrame {

   private JPanel contentPane;
   private JTable table;
   private JTextField textDescricao;
   private JTextField textEstoque;
   private JTextField textPreco;
   private JTextField barraPesquisa;
   private File imagem;
   private JPanel PainelImagem;
	private JLabel lbImagem;
   private Produto p;
	int idSelecionado = 0;
	int insertUpdate = 0;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               TelaProdutos frame = new TelaProdutos();
               frame.setVisible(true);
            }
            catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public TelaProdutos() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100,100,541,372);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5,5,5,5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(10, 56, 505, 121);
      contentPane.add(scrollPane);
      
      
      table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 1-Recuperar o id do produto
				int linha = table.getSelectedRow();
				idSelecionado = (int) table.getValueAt(linha, 0);
				System.out.println(idSelecionado);
				
				// 2-Obter os dados da linha
				String descricao = (String) table.getValueAt(linha, 1);
				int estoque = (int) table.getValueAt(linha, 2);
				double preco = (double) table.getValueAt(linha, 3);
				
				// 3-Inserir os dados nos campos de digitação
				textDescricao.setText(descricao);
				textEstoque.setText(estoque + "");
				textPreco.setText(preco + "");
				
				insertUpdate = 2; // 1-insert; 2-update
			}
		});
		
	      table.setModel(new DefaultTableModel(
	    	         new Object[][] {
	    	            {111, "arroz", 78.5, 11.55},
	    	            {112, "feijao", 56, 20.90},
	    	            {113, "batata", 82, 13.50},
	    	            {null, null, null, null},
	    	            {null, null, null, null},
	    	            {null, null, null, null},
	    	            {null, null, null, null},
	    	            {null, null, null, null},
	    	            {null, null, null, null},
	    	            {null, null, null, null},
	    	         },
	    	         new String[] {
	    	            "ID", "Descrição", "Estoque", "Preço"
	    	         }
	    	      ));
      
      //2-Preencher o JTable com os dados do BD
      
      DefaultTableModel modelo = new DefaultTableModel();
      //Monta o cabeçario 
      modelo.addColumn("Id");
      modelo.addColumn("Descricao");
      modelo.addColumn("Estoque");
      modelo.addColumn("Preco");
      table.setModel(modelo);
    
      //1-Ler os dados do banco usanado DAO
      ProdutoDao estoque = new ProdutoDao();
      List<Produto> registros = estoque.getTodos();
      //Preenche os dados
      for(int i=0; i<registros.size(); i++) {
         Produto p = registros.get(i);
         Object[] linha = {p.getId(), p.getDescricao(), p.getEstoque(),p.getPreco()};
         modelo.addRow(linha);
      }
      
      scrollPane.setViewportView(table);
      
      JPanel panel = new JPanel();
      panel.setBorder(new LineBorder(new Color(0, 0, 0)));
      panel.setBounds(10, 188, 336, 121);
      contentPane.add(panel);
      panel.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("Descri\u00E7\u00E3o");
      lblNewLabel.setBounds(10, 11, 56, 14);
      panel.add(lblNewLabel);
      
      JLabel lblNewLabel_1 = new JLabel("Estoque");
      lblNewLabel_1.setBounds(10, 36, 46, 14);
      panel.add(lblNewLabel_1);
      
      JLabel lblNewLabel_2 = new JLabel("Pre\u00E7o");
      lblNewLabel_2.setBounds(10, 61, 46, 14);
      panel.add(lblNewLabel_2);
      
      textDescricao = new JTextField();
      textDescricao.setBounds(66, 8, 260, 20);
      panel.add(textDescricao);
      textDescricao.setColumns(10);
      
      textEstoque = new JTextField();
      textEstoque.setBounds(66, 33, 260, 20);
      panel.add(textEstoque);
      textEstoque.setColumns(10);
      
      textPreco = new JTextField();
      textPreco.setBounds(66, 58, 260, 20);
      panel.add(textPreco);
      textPreco.setColumns(10);
      
      JButton BotaoNovo = new JButton("Novo");
      BotaoNovo.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		textDescricao.setText("");
			textEstoque.setText("");
			textPreco.setText("");
			
			// 1-insert 2-update
			insertUpdate = 1; 
		}
      	}
      );
      BotaoNovo.setBounds(71, 89, 71, 23);
      panel.add(BotaoNovo);
      
      JButton BotaoSalvar = new JButton("Salvar");
      BotaoSalvar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
        	 // Pegar os valores digitados nos campos
 				String descricao = textDescricao.getText();
 				String estoque = textEstoque.getText();
 				String preco = textPreco.getText();

 				// Jogar os valores em um objeto Produto
 				Produto p = new Produto();
 				p.setId(idSelecionado);
 				p.setDescricao(descricao);
 				p.setEstoque(Integer.parseInt(estoque));
 				p.setPreco(Double.parseDouble(preco));
 				p.setImagem(getImagem());
 				
 				// Gravar este objeto no banco
 				ProdutoDao estoque2 = new ProdutoDao();
 				if (insertUpdate == 1)					
 					estoque2.inserir(p);
 				else if (insertUpdate == 2)
 					estoque2.atualizar(p);
 				
 				atualizarTabela();
 				
 				JOptionPane.showMessageDialog(null, "Produto inserido");
 			}
 		});
      BotaoSalvar.setBounds(166, 89, 71, 23);
      panel.add(BotaoSalvar);
      
      JButton BoataoApagar = new JButton("Apagar");
      BoataoApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 2-Prencher um objeto produto com o id
				Produto p = new Produto();
				p.setId(idSelecionado);
				
				// 3-Chamar o método remover da classe DAO
				ProdutoDao estoque = new ProdutoDao();
				estoque.remover(p);
				
				atualizarTabela();
			}
		});
      BoataoApagar.setBounds(255, 89, 71, 23);
      panel.add(BoataoApagar);
    
      
      table.setModel(modelo);
      
      scrollPane.setViewportView(table);
      
      
      barraPesquisa = new JTextField();
      barraPesquisa.setBounds(10, 25, 406, 20);
      contentPane.add(barraPesquisa);
      barraPesquisa.setColumns(10);
      
      JButton buscar = new JButton("Buscar");
      buscar.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		ProdutoDao p = new ProdutoDao();
      		String termoBusca = barraPesquisa.getText();
      		Produto produtoEncontrado = p.buscarNoBanco(termoBusca);
            
            if (produtoEncontrado != null) {
                // Exibir os detalhes do produto nos componentes adequados
                
                textDescricao.setText(produtoEncontrado.getDescricao());
                textEstoque.setText(Integer.toString(produtoEncontrado.getEstoque()));
                textPreco.setText(Double.toString(produtoEncontrado.getPreco()));
            } else {
                // Produto não encontrado
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        }
    });
      buscar.setBounds(426, 22, 89, 23);
      contentPane.add(buscar);
      
      JButton uploadButton = new JButton("Carregar Imagem");
      uploadButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		imagem = selecionarImagem();
      		abrirImagem(imagem);
      	}
      });
      uploadButton.setBounds(356, 310, 159, 23);
      contentPane.add(uploadButton);
      
      PainelImagem = new JPanel();
      PainelImagem.setBorder(new LineBorder(new Color(0, 0, 0)));
      PainelImagem.setBounds(356, 188, 159, 121);
      contentPane.add(PainelImagem);
      
      lbImagem = new JLabel("");
      PainelImagem.add(lbImagem);}
   
      public void atualizarTabela() {
         //2-Preencher o JTable com os dados do BD
         DefaultTableModel modelo = new DefaultTableModel();
         //Monta o cabeçario 
         modelo.addColumn("Id");
         modelo.addColumn("Descricao");
         modelo.addColumn("Estoque");
         modelo.addColumn("Preco");
         //Preenche os dados
         ProdutoDao estoque = new ProdutoDao();
         List<Produto> registros = estoque.getTodos();
         for(int i=0; i<registros.size(); i++) {
            Produto p = registros.get(i);
            Object[] linha = {p.getId(), p.getDescricao(), p.getEstoque(),p.getPreco()};
            modelo.addRow(linha);
         }
         
         table.setModel(modelo);
         }
      public File selecionarImagem() {
    	  JFileChooser fileChooser = new JFileChooser();
    	  FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens em JPEG e PNG", "jpg", "png");
    	  fileChooser.addChoosableFileFilter(filtro);
    	  fileChooser.setAcceptAllFileFilterUsed(false);
    	  fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
    	  fileChooser.setCurrentDirectory(new File("c:"));
    	  fileChooser.showOpenDialog(this);
    	  
    	  return fileChooser.getSelectedFile();
    	  
      };
      
      private byte[] getImagem() {
    	  boolean isPng = false;
    	  if (imagem!=null) {
    		  isPng = imagem.getName().endsWith("png");
    		try {
    		  BufferedImage image = ImageIO.read(imagem);		  
    		  ByteArrayOutputStream out = new ByteArrayOutputStream();
    		  int type = BufferedImage.TYPE_INT_RGB;
    		  
    		  if(isPng) {
    			  type = BufferedImage.BITMASK;
    		  }
    		BufferedImage novaImagem = new BufferedImage (PainelImagem.getWidth() - 5 , PainelImagem.getHeight() -10 , type);
    		Graphics2D g = novaImagem.createGraphics();
    		g.setComposite(AlphaComposite.Src);
    		g.drawImage(image, 0, 0, PainelImagem.getWidth() - 5 , PainelImagem.getHeight() -10, null);
    		
    		if(isPng) {
    			ImageIO.write(novaImagem, "png", out);
    		}else {
    			ImageIO.write(novaImagem, "jpg", out);
    		}
    		out.flush();
    		byte[] byteArray = out.toByteArray();
    		out.close();
    		
    		return byteArray;
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    		}
    	  return null;
      };
private void abrirImagem (Object source) {
	if (source instanceof File) {
		ImageIcon icon = new ImageIcon(imagem.getAbsolutePath());
		icon.setImage(icon.getImage().getScaledInstance(PainelImagem.getWidth()- 5 , PainelImagem.getHeight() -10, 100));
		lbImagem.setIcon(icon);
	}else if(source instanceof byte[]) {
		ImageIcon icon = new ImageIcon(p.getImagem());
		icon.setImage(icon.getImage().getScaledInstance(PainelImagem.getWidth()- 5 , PainelImagem.getHeight() -10, 100));
		lbImagem.setIcon(icon);
	}
}
}