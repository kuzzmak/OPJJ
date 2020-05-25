package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.IDataGetter;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatisticalnfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJMenu;

/**
 * Razred jednostavnog uređivača teksta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel dmdm;

	// ikone koje ukazuju na modifikaciju dokumenta
	public static ImageIcon modified;
	public static ImageIcon unmodified;

	// trenutni pokazatelji kursora
	private JLabel length;
	private JLabel line;
	private JLabel column;
	private JLabel selected;
	private JLabel lengthNum;
	private JLabel lineNum;
	private JLabel columnNum;
	private JLabel selectedNum;

	private FormLocalizationProvider flp;
	
	// akcije
	private LocalizableAction openDocumentAction;
	private LocalizableAction newDocumentAction;
	private LocalizableAction saveDocumentAction;
	private LocalizableAction saveAsDocumentAction;
	private LocalizableAction closeTabAction;
	private LocalizableAction copyAction;
	private LocalizableAction pasteAction;
	private LocalizableAction cutAction;
	private LocalizableAction statisticalInfoAction;
	private LocalizableAction upperCaseAction;
	private LocalizableAction lowerCaseAction;
	private LocalizableAction invertCaseAction;
	private LocalizableAction descendingSortAction;
	private LocalizableAction ascendingSortAction;
	private LocalizableAction uniqueLinesAction;
	private LocalizableAction english;
	private LocalizableAction german;
	private LocalizableAction croatian;
	private LocalizableAction exitAction;
	
	private JButton openButton;
	private JButton newDocumentButton;
	private JButton saveDocumentButton;
	private JButton saveAsDocumentButton;
	private JButton closeTabButton;
	private JButton copy;
	private JButton paste;
	private JButton cut;
	private JButton info;
	private JButton lowerCaseButton;
	private JButton upperCaseButton;
	private JButton invertCaseButton;
	private JButton descendingSortButton;
	private JButton ascendingSortButton;
	private JButton uniqueLinesButton;
	
	// lista akcija koje se onemoguće ukoliko nikakav tekst nije označen
	private List<Action> textActions;
	private List<Action> tabActions;
	
	/**
	 * Konstruktor.
	 * 
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

		initGUI();
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(1300, 500));
	}

	/**
	 * Funkcija za incijalizaciju grafičkog sučelja {@code JNotepad++}.
	 * 
	 */
	private void initGUI() {
		
		modified = createImageIcon("icons/modified.png", 20);
		unmodified = createImageIcon("icons/unmodified.png", 20);
		
		getContentPane().setLayout(new BorderLayout());
		
		dmdm = new DefaultMultipleDocumentModel();
		dmdm.addMultipleDocumentListener(ml);
		getContentPane().add(dmdm, BorderLayout.CENTER);

		dmdm.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				int selectedIndex = dmdm.getSelectedIndex();
				
				if(selectedIndex != -1) {
					SingleDocumentModel document = dmdm.getDocument(selectedIndex);
					setTitle(document);
					updateStatusBar(document.getTextComponent());
					
				}else {
					updateStatusBar(null);
				}
			}
		});

		createActions();
		createMenus();
		createToolbars();
		createStatusBar();
		setTitle("JNotepad++");
		
		textActions = new ArrayList<>(Arrays.asList(
				copyAction, 
				cutAction, 
				lowerCaseAction, 
				upperCaseAction, 
				invertCaseAction,
				descendingSortAction,
				ascendingSortAction,
				uniqueLinesAction));
		
		tabActions = new ArrayList<>(Arrays.asList(
				closeTabAction, 
				saveDocumentAction, 
				saveAsDocumentAction, 
				statisticalInfoAction,
				pasteAction));
		
		changeTextActionsState(false);
		changeTabActionsState(false);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exitProcedure();
			}
		});

	}

	/**
	 * Funkcija za omogućavanje akcija čije djelovanje ovisi
	 * o odabranom tekstu, kad ima odabranog teksta, omoguće se,
	 * a inače su onemmogućene.
	 * 
	 * @param enable jesu li akcije omogućene ili nisu
	 */
	private void changeTextActionsState(boolean enable) {
		
		textActions.forEach(a -> a.setEnabled(enable));
	}
	
	/**
	 * Funkcija za promjenu stanja akcija koje trebaju imati
	 * barem jednu kraticu otvorenu. Ukoliko nema niti jedne
	 * kratice, onemogućene su, inače su omogučene.
	 * 
	 * @param enable jesu li akcije omogućene ili nisu
	 */
	private void changeTabActionsState(boolean enable) {
		
		tabActions.forEach(a -> a.setEnabled(enable));
	}
	
	/**
	 * Funkcija koja se poziva pritiskom na gumb za izlazak iz programa. Prvo se
	 * provjeri ima li nekog nespremljenog dokumenta te ako ima, pita se korisnika
	 * treba li ga spremiti. Nakon potencijalnog spremanja dokumenata, prozor se
	 * uništava.
	 * 
	 */
	public void exitProcedure() {

		Iterator<SingleDocumentModel> docIter = dmdm.iterator();

		while (docIter.hasNext()) {

			SingleDocumentModel doc = docIter.next();

			if (doc.isModified()) {

				if (doc.getFilePath() == null) {

					if (JOptionPane.showConfirmDialog(
							JNotepadPP.this,
							flp.getString("document") + ": (unnamed) " + flp.getString("notSavedSave"), 
							flp.getString("optionPaneNotSavedTitle"),
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						Path path = Paths
								.get(Paths.get("").toAbsolutePath().toString() + File.separator + "unnamed.txt");
						dmdm.saveDocument(doc, path);
					}

				} else {

					if (JOptionPane.showConfirmDialog(
							JNotepadPP.this,
							flp.getString("document") + doc.getFilePath().toString() + flp.getString("notSavedSave"),  
							flp.getString("optionPaneNotSavedTitle"),
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						dmdm.saveDocument(doc, doc.getFilePath());
					}
				}
			}
		}

		dispose();
	}

	SingleDocumentListener sl = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {

			if (model.isModified()) {
				dmdm.setIconAt(dmdm.getSelectedIndex(), modified);
				
			} else {
				dmdm.setIconAt(dmdm.getSelectedIndex(), unmodified);
				
				JOptionPane.showMessageDialog(JNotepadPP.this, 
						flp.getString("optionPaneFileSavedMessage"), 
						flp.getString("optionPaneFileSavedTitle"), 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			setTitle(model);
		}
	};

	
	MultipleDocumentListener ml = new MultipleDocumentListener() {

		@Override
		public void documentRemoved(SingleDocumentModel model) {

			int index = dmdm.getSelectedIndex();
			if (index != -1)
				dmdm.remove(index);
			
			// onemogućavanje gumba za dohvat informacija o trenutnom dokumentu
			if(dmdm.getTabCount() == 0) {
				changeTabActionsState(false);
				setTitle("JNotepad++");
			}
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {

			JTextArea textArea = model.getTextComponent();
			textArea.addCaretListener(new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent e) {
					updateStatusBar(textArea);
				}
			});

			model.addSingleDocumentListener(sl);

			// stvaranje nove kratice
			if (model.getFilePath() == null) {
				dmdm.addTab("(unnamed)", unmodified, new JScrollPane(textArea));
			} else {
				dmdm.addTab(model.getFilePath().getFileName().toString(), unmodified, new JScrollPane(textArea));
			}

			// odabir tog dokumenta, uvijek se dodaje na kraj
			dmdm.setSelectedIndex(dmdm.getTabCount() - 1);
			
			changeTabActionsState(true);
		}

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {

		}
	};

	/**
	 * Funkcija za ažuriranje vrijdnosti na statusnoj traci.
	 * 
	 * @param textArea referenca na {@code JTextArea} aktivnog dokumenta
	 */
	private void updateStatusBar(JTextArea textArea) {
		
		if(textArea != null) {
			
			try {
				int caretPos = textArea.getCaretPosition();
				int lineNum = textArea.getLineOfOffset(caretPos);
				int columnNum = caretPos - textArea.getLineStartOffset(lineNum);
				int selectedNum = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				lineNum += 1;
				columnNum += 1;
	
				this.lengthNum.setText(": " + textArea.getText().length());
				this.lineNum.setText(": " + lineNum);
				this.columnNum.setText(": " + columnNum);
				this.selectedNum.setText(": " + selectedNum);
				
				if(selectedNum == 0) {
					changeTextActionsState(false);
				}else {
					changeTextActionsState(true);
				}
	
			} catch (BadLocationException ex) {
			}
			
		}else {
			this.lengthNum.setText(": " + 0);
			this.lineNum.setText(": " + 0);
			this.columnNum.setText(": " + 0);
			this.selectedNum.setText(": " + 0);
			changeTextActionsState(false);
		}
	}

	/**
	 * Funkcija za stvaranje akcija.
	 * 
	 */
	private void createActions() {
		
		IDataGetter data = new IDataGetter() {
			
			@Override
			public JFrame getFrame() {
				return JNotepadPP.this;
			}
			
			@Override
			public DefaultMultipleDocumentModel getDmdm() {
				return dmdm;
			}
		};
		
		openDocumentAction = new OpenDocumentAction("open", flp, data);
		newDocumentAction = new NewDocumentAction("new", flp, data);
		saveDocumentAction = new SaveDocumentAction("save", flp, data, false);
		saveAsDocumentAction = new SaveDocumentAction("saveas", flp, data, true);
		closeTabAction = new CloseTabAction("close", flp, data);
		
		copyAction = new CopyAction("copy", flp, data);
		pasteAction = new PasteAction("paste", flp, data);
		cutAction = new CutAction("cut", flp, data);
		upperCaseAction = new ChangeCaseAction("uppercase", flp, data);
		lowerCaseAction = new ChangeCaseAction("lowercase", flp, data);
		invertCaseAction = new ChangeCaseAction("invertcase", flp, data);
		statisticalInfoAction = new StatisticalnfoAction("info", flp, data);
		
		descendingSortAction = new SortAction("descending", flp, data);
		ascendingSortAction = new SortAction("ascending", flp, data);
		uniqueLinesAction = new SortAction("unique", flp, data);
		
		english = new LanguageAction("en", flp);
		german = new LanguageAction("de", flp);
		croatian = new LanguageAction("hr", flp);
		
		exitAction = new LocalizableAction("exit", flp) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				exitProcedure();
			}
		};
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				exitAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("exit" + "desc"));
			}
		});
	}

	/**
	 * Funkcija za stvaranje izbornika.
	 * 
	 */
	private void createMenus() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.add(new JMenuItem(cutAction));

		JMenu infoMenu = new LJMenu("info", flp);
		infoMenu.add(new JMenuItem(statisticalInfoAction));

		menuBar.add(infoMenu);

		JMenu languagesMenu = new LJMenu("languages", flp);
		languagesMenu.add(new JMenuItem(english));
		languagesMenu.add(new JMenuItem(croatian));
		languagesMenu.add(new JMenuItem(german));

		menuBar.add(languagesMenu);
		
		JMenu toolsMenu = new LJMenu("tools", flp);
		toolsMenu.add(new JMenuItem(upperCaseAction));
		toolsMenu.add(new JMenuItem(lowerCaseAction));
		toolsMenu.add(new JMenuItem(invertCaseAction));
		
		menuBar.add(toolsMenu);

		JMenu sortMenu = new LJMenu("sort", flp);
		toolsMenu.add(new JMenuItem(descendingSortAction));
		toolsMenu.add(new JMenuItem(ascendingSortAction));
		toolsMenu.add(new JMenuItem(uniqueLinesAction));
		
		menuBar.add(sortMenu);
		
		setJMenuBar(menuBar);
	}

	/**
	 * Funkcija za stvaranje gumba alatne trake.
	 * 
	 */
	private void createToolbars() {
		
		int iconPercent = 15;

		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		openButton = new JButton(openDocumentAction);
		openButton.setIcon(createImageIcon("icons/open.png", iconPercent));
		toolBar.add(openButton);

		newDocumentButton = new JButton(newDocumentAction);
		newDocumentButton.setIcon(createImageIcon("icons/new.png", iconPercent));
		toolBar.add(newDocumentButton);

		saveDocumentButton = new JButton(saveDocumentAction);
		saveDocumentButton.setIcon(createImageIcon("icons/save.png", iconPercent));
		toolBar.add(saveDocumentButton);

		saveAsDocumentButton = new JButton(saveAsDocumentAction);
		saveAsDocumentButton.setIcon(createImageIcon("icons/saveas.png", iconPercent));
		toolBar.add(saveAsDocumentButton);

		closeTabButton = new JButton(closeTabAction);
		closeTabButton.setIcon(createImageIcon("icons/closetab.png", iconPercent));
		toolBar.add(closeTabButton);

		toolBar.addSeparator();

		copy = new JButton(copyAction);
		copy.setIcon(createImageIcon("icons/copy.png", iconPercent));
		toolBar.add(copy);

		paste = new JButton(pasteAction);
		paste.setIcon(createImageIcon("icons/paste.png", iconPercent));
		toolBar.add(paste);

		cut = new JButton(cutAction);
		cut.setIcon(createImageIcon("icons/cut.png", iconPercent));
		toolBar.add(cut);

		info = new JButton(statisticalInfoAction);
		info.setIcon(createImageIcon("icons/info.png", iconPercent));
		toolBar.add(info);
		
		toolBar.addSeparator();
		
		lowerCaseButton = new JButton(lowerCaseAction);
		lowerCaseButton.setIcon(createImageIcon("icons/lowercase.png", iconPercent));
		toolBar.add(lowerCaseButton);
		
		upperCaseButton = new JButton(upperCaseAction);
		upperCaseButton.setIcon(createImageIcon("icons/uppercase.png", iconPercent));
		toolBar.add(upperCaseButton);
		
		invertCaseButton = new JButton(invertCaseAction);
		invertCaseButton.setIcon(createImageIcon("icons/invertcase.png", iconPercent));
		toolBar.add(invertCaseButton);
		
		descendingSortButton = new JButton(descendingSortAction);
		descendingSortButton.setIcon(createImageIcon("icons/descending.png", iconPercent));
		toolBar.add(descendingSortButton);
		
		ascendingSortButton = new JButton(ascendingSortAction);
		ascendingSortButton.setIcon(createImageIcon("icons/ascending.png", iconPercent));
		toolBar.add(ascendingSortButton);
		
		uniqueLinesButton = new JButton(uniqueLinesAction);
		uniqueLinesButton.setIcon(createImageIcon("icons/one.png", iconPercent));
		toolBar.add(uniqueLinesButton);

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Funkcija za stvaranje statusne trake koja se nalazi na donjoj strani prozora
	 * i sadrži informacije o trenutnoj poziciji kursora, ukupnom broju linija,
	 * trenutnom vremenu.
	 * 
	 */
	private void createStatusBar() {

		JPanel statusBar = new JPanel(new BorderLayout());
		statusBar.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black));

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));

		length = new LJLabel("length", flp);
		lengthNum = new JLabel(":0");
		
		line = new LJLabel("ln", flp);
		lineNum = new JLabel(":0");
		column = new LJLabel("col", flp);
		columnNum  = new JLabel(":0");
		selected = new LJLabel("sel", flp);
		selectedNum  = new JLabel(":0");
		
		JPanel lengthPanel = new JPanel();
		lengthPanel.add(length);
		lengthPanel.add(lengthNum);

		JPanel linePanel = new JPanel();
		linePanel.add(line);
		linePanel.add(lineNum);
		
		JPanel columnPanel = new JPanel();
		columnPanel.add(column);
		columnPanel.add(columnNum);
		
		JPanel selectedPanel = new JPanel();
		selectedPanel.add(selected);
		selectedPanel.add(selectedNum);
		
		leftSide.add(lengthPanel);
		leftSide.add(linePanel);
		leftSide.add(columnPanel);
		leftSide.add(selectedPanel);

		statusBar.add(leftSide, BorderLayout.WEST);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		JPanel dateAndTimePanel = new JPanel();
		
		JLabel dateAndTime = new JLabel();
		dateAndTime.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		dateAndTimePanel.add(dateAndTime);
		
		statusBar.add(dateAndTimePanel, BorderLayout.EAST);

		getContentPane().add(statusBar, BorderLayout.SOUTH);

		// ažuriranje trenutnog vremena
		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDateTime now = LocalDateTime.now();
				dateAndTime.setText(now.format(dtf));
			}
		}).start();
	}

	/**
	 * Funkcija za učitavanje i stvaranje objekta {@code ImageIcon} iz predane staze
	 * do željene sličice {@code path} uz željeno skaliranje {@code scalePercent}.
	 * 
	 * @param path         staza do slike koja se učitava
	 * @param scalePercent postotak na koj se skalira izvorna slika
	 * @return objekt tipa {@code ImageIcon}
	 */
	public ImageIcon createImageIcon(String path, int scalePercent) {

		try (InputStream is = this.getClass().getResourceAsStream(path)) {

			ImageIcon im = new ImageIcon(is.readAllBytes());
			return new ImageIcon(im.getImage().getScaledInstance(scalePercent, scalePercent, Image.SCALE_DEFAULT));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Funkcija za postavljanje staze otvorene datoteke u naslovnu traku.
	 * 
	 * @param model referenca do trenutno aktivnog modela
	 */
	public void setTitle(SingleDocumentModel model) {

		if(model != null) {
			Path modelPath = model.getFilePath();
		
			if (modelPath != null) {
				setTitle(modelPath.toString() + " - JNotepad++");
			} else {
				setTitle("(unnamed)" + " - JNotepad++");
			}
		}
	}

	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args argumenti glavnog programa
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage("en");
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
