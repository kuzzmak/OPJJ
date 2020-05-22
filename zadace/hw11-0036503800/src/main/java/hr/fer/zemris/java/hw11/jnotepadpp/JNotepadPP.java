package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;

	private DefaultMultipleDocumentModel dmdm;

	private ImageIcon modified;
	private ImageIcon unmodified;

	// staza dokumenta trenutno aktivne kratice
	private Path currentDocumentPath = null;

	/**
	 * Konstruktor.
	 * 
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(800, 600);

		modified = createImageIcon("icons/modified.png", 20);
		unmodified = createImageIcon("icons/unmodified.png", 20);

		initGUI();
	}

	/**
	 * Funkcija za incijalizaciju grafičkog sučelja {@code JNotepad++}
	 * 
	 */
	private void initGUI() {

		editor = new JTextArea();

		getContentPane().setLayout(new BorderLayout());
		dmdm = new DefaultMultipleDocumentModel();
		getContentPane().add(dmdm, BorderLayout.CENTER);

		dmdm.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				int selectedIndex = dmdm.getSelectedIndex();
				SingleDocumentModel document = dmdm.getDocument(selectedIndex);
				if (document == null)
					return;
				else {
					currentDocumentPath = document.getFilePath();
				}

				if (currentDocumentPath != null) {
					setTitle(currentDocumentPath.toString() + " - JNotepad++");
				} else {
					setTitle("(unnamed)" + " - JNotepad++");
				}
			}
		});

		createActions();
		createMenus();
		createToolbars();
		setTitle("JNotepad++");
	}

	/**
	 * Akcija koja predstavlja otvaranje datoteke s računala. Ukoliko
	 * je odabrana datoteka već otvorena u nekoj kratici, ne stvara
	 * se nova kratica već se prelazi na već otvorenu kraticu.
	 * 
	 */
	private Action openDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();

			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Datoteka " + filePath + " ne postoji!", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// pronalazak već otvorenog dokumenta ako postoji
			int indexOfAlredyOpened = -1;
			int i = 0;
			Iterator<SingleDocumentModel> iter = dmdm.iterator();
			while (iter.hasNext()) {
				
				SingleDocumentModel model = iter.next();
				
				if (model.getFilePath() != null) {
					if (model.getFilePath().equals(filePath)) {
						indexOfAlredyOpened = i;
					}
				}
				i++;
			}

			// odabrana datoteka nije već otvorena u JNotepadd++
			if (indexOfAlredyOpened == -1) {

				SingleDocumentModel newDocument = dmdm.loadDocument(filePath);
				newDocument.addSingleDocumentListener(sl);
				// stvaranje nove kratice
				dmdm.addTab(filePath.getFileName().toString(), unmodified,
						new JScrollPane(newDocument.getTextComponent()));
				// odabir te kratice, uvijek se dodaje na kraj
				dmdm.setSelectedIndex(dmdm.getTabCount() - 1);

			} else {
				// odabrana datoteka postoji, odabire se
				dmdm.setSelectedIndex(indexOfAlredyOpened);
			}
		}
	};

	SingleDocumentListener sl = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			if (model.isModified()) {
				dmdm.setIconAt(dmdm.getSelectedIndex(), modified);
			} else {
				dmdm.setIconAt(dmdm.getSelectedIndex(), unmodified);
			}
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			setTitle(model);
		}
	};

	private Action saveAsDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (saveFileWithNoPath()) {
				dmdm.saveDocument(dmdm.getCurrentDocument(), currentDocumentPath);
				dmdm.setIconAt(dmdm.getSelectedIndex(), unmodified);
				dmdm.setTitleAt(dmdm.getSelectedIndex(),
						dmdm.getCurrentDocument().getFilePath().getFileName().toString());
			}
		}
	};

	private Action saveDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean writeOK = true;

			if (currentDocumentPath == null)
				writeOK = saveFileWithNoPath();

			if (writeOK) {
				dmdm.saveDocument(dmdm.getCurrentDocument(), currentDocumentPath);
				dmdm.setIconAt(dmdm.getSelectedIndex(), unmodified);
			}
		}
	};

	/**
	 * Metoda za spremanje trenutno aktivne datoteke na način da se ponudi prozor za
	 * odabir imena i mjesta spremanje datoteke.
	 * 
	 * @return je li uspješno odabrano ime i mjesto spremanja dototeke
	 */
	private boolean saveFileWithNoPath() {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Ništa nije snimljeno.", "Upozorenje",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		currentDocumentPath = jfc.getSelectedFile().toPath();

		File file = currentDocumentPath.toFile();

		if (file.exists()) {

			// ako odabrano ime datoteke postoji, pita se korisnika hoće li se prepisati
			// stara datoteka
			if (JOptionPane.showConfirmDialog(JNotepadPP.this,
					"Odabrano ime datoteke već posjeduje druga datoteka. Prepisati?", "Upozorenje",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			} else { // stara datoteka se ne smije prepisati
				return false;
			}
		}

		return true;
	}

	private Action deleteSelectedPartAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if (len == 0)
				return;
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};

	private Action toggleCaseAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};

	private Action newDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			SingleDocumentModel newDocument = dmdm.createNewDocument();
			newDocument.addSingleDocumentListener(sl);
			dmdm.addTab("(unmodified)", unmodified, new JScrollPane(newDocument.getTextComponent()));
		}
	};

	private Action closeTabAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			dmdm.closeDocument(dmdm.getCurrentDocument());
		}
	};

	private Action exitAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	private void createActions() {

		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

		newDocumentAction.putValue(Action.NAME, "New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new file.");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		saveAsDocumentAction.putValue(Action.NAME, "SaveAs");
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save current file to disk.");

		closeTabAction.putValue(Action.NAME, "Close tab");
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		closeTabAction.putValue(Action.SHORT_DESCRIPTION, "Closes current tab.");

		deleteSelectedPartAction.putValue(Action.NAME, "Delete selected text");
		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete the selected part of text.");

		toggleCaseAction.putValue(Action.NAME, "Toggle case");
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to toggle character case in selected part of text or in entire document.");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
	}

	private void createMenus() {

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(exitAction));

		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));

		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {

		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		JButton openButton = new JButton(openDocumentAction);
		openButton.setIcon(createImageIcon("icons/open.png", 20));
		toolBar.add(openButton);

		JButton newDocumentButton = new JButton(newDocumentAction);
		newDocumentButton.setIcon(createImageIcon("icons/new.png", 20));
		toolBar.add(newDocumentButton);

		JButton saveDocumentButton = new JButton(saveDocumentAction);
		saveDocumentButton.setIcon(createImageIcon("icons/save.png", 20));
		toolBar.add(saveDocumentButton);

		JButton saveAsDocumentButton = new JButton(saveAsDocumentAction);
		saveAsDocumentButton.setIcon(createImageIcon("icons/saveas.png", 20));
		toolBar.add(saveAsDocumentButton);

		JButton closeTabButton = new JButton(closeTabAction);
		closeTabButton.setIcon(createImageIcon("icons/closetab.png", 20));
		toolBar.add(closeTabButton);

		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
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

		Path modelPath = model.getFilePath();

		if (modelPath != null) {
			setTitle(modelPath.toString() + " - JNotepad++");
		} else {
			setTitle("(unnamed)" + " - JNotepad++");
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
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
