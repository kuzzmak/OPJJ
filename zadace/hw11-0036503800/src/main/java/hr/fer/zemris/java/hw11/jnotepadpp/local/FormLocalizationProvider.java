package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Razred koji predstavlja most između pružatelja prijevoda i
 * objekta koji trebaju prijevod. Prima objekt prozora koji 
 * prilikom stvaranja doda slušaća prijevoda, a uništi ga
 * prilikom zatvaranja prozora. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class FormLocalizationProvider extends LocalizatoniProviderBridge {

	/**
	 * Konstruktor.
	 * 
	 * @param provider pružatelj prijevoda
	 * @param frame referenca na prozor
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		
		super(provider);
		
		// slušać prozora koji prilikom nastajanja prozora registrira
		// slušaća prijevoda, a uništi ga prilikom zatvaranja
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}
	
}
