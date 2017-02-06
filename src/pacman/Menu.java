package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private int nbCompo;
	
	public Menu(int nbComposants){
		this.setBackground(Color.BLUE);
		this.setLayout(new GridLayout(nbComposants, 1));
		nbCompo = nbComposants;
	}
	
	public void addBouton(String name, ActionListener ecoute){
		JPanel item = new JPanel();
		item.setBackground(Color.BLUE);
		JButton bout = new JButton(name);
		bout.addActionListener(ecoute);
		bout.setFont(new Font("Tahoma", Font.BOLD, 30));
		item.add(bout);
		this.add(item);
	}
	
	public void addBouton(String name, ActionListener ecoute, ImageIcon icone){
		JPanel item = new JPanel();
		item.setBackground(Color.BLUE);
		JButton bout = new JButton(name, icone);
		bout.addActionListener(ecoute);
		bout.setFont(new Font("Tahoma", Font.BOLD, 30));
		item.add(bout);
		this.add(item);
	}
	
	public void addBouton(String name, ActionListener ecoute, boolean enab){
		JPanel item = new JPanel();
		item.setBackground(Color.BLUE);
		JButton bout = new JButton(name);
		bout.addActionListener(ecoute);
		bout.setFont(new Font("Tahoma", Font.BOLD, 30));
		bout.setEnabled(enab);
		item.add(bout);
		this.add(item);
	}
	
	public void addLabel(String name){
		JPanel item = new JPanel();
		item.setBackground(Color.BLUE);
		JLabel lab = new JLabel(name);
		lab.setFont(new Font("Tahoma", Font.BOLD, 30));
		item.add(lab);
		this.add(item);
	}
	
	public void addLabel(String name, ImageIcon icon){
		JPanel item = new JPanel();
		item.setBackground(Color.BLUE);
		JLabel lab = new JLabel(name);
		lab.setFont(new Font("Tahoma", Font.BOLD, 30));
		item.add(lab);
		this.add(item);
	}

	public int getNbCompo() {
		return nbCompo;
	}

}
