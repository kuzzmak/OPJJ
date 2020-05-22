package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.text.Document;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;

	private DefaultMultipleDocumentModel dmdm;

	private ImageIcon modified;
	private ImageIcon unmodified;

	// staza dokumenta trenutno aktivne kratice
	private Path currentDocumentPath = null;

	private JButton copy;

	private JLabel line;
	private JLabel column;
	private JLabel selected;

	/**
	 * Konstruktor.
	 * 
	 */
	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
		dmdm.addMultipleDocumentListener(ml);
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

				updateStatusBar(document.getTextComponent());
			}
		});

		createActions();
		createMenus();
		createToolbars();
		createStatusBar();
		setTitle("JNotepad++");

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exitProcedure();
			}
		});

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

					if (JOptionPane.showConfirmDialog(JNotepadPP.this,
							"Dokument: " + "(unnamed)" + " nije spremljen. Spremiti?", "Upozorenje",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						Path path = Paths
								.get(Paths.get("").toAbsolutePath().toString() + File.separator + "unnamed.txt");
						dmdm.saveDocument(doc, path);
					}

				} else {

					if (JOptionPane.showConfirmDialog(JNotepadPP.this,
							"Dokument: " + doc.getFilePath().toString() + " nije spremljen. Spremiti?", "Upozorenje",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						dmdm.saveDocument(doc, doc.getFilePath());
					}
				}
			}
		}

		dispose();
	}

	/**
	 * Akcija koja predstavlja otvaranje datoteke s računala. Ukoliko je odabrana
	 * datoteka već otvorena u nekoj kratici, ne stvara se nova kratica već se
	 * prelazi na već otvorenu kraticu.
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

			// izabrana datoteka koja nije otvorena u nekoj kratici
			if (indexOfAlredyOpened == -1) {

				dmdm.loadDocument(filePath);

			} else {

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

	MultipleDocumentListener ml = new MultipleDocumentListener() {

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			
			int index = dmdm.getSelectedIndex();
			if(index != -1) dmdm.remove(index);
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

			// odabir te kratice, uvijek se dodaje na kraj
			dmdm.setSelectedIndex(dmdm.getTabCount() - 1);
		}

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			
			

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

	/**
	 * Akcija za spremanje dokumenta trenutno aktivne kratice. Ukoliko je trek
	 * stvorena kratica, korisnika se pita za mjesto spremanja, inače se ažurira
	 * stara datoteka.
	 * 
	 */
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

			dmdm.createNewDocument();
		}
	};

	/**
	 * Funkcija za ažuriranje vrijdnosti na statusnoj traci.
	 * 
	 * @param textArea referenca na {@code JTextArea} aktivnog dokumenta
	 */
	private void updateStatusBar(JTextArea textArea) {

		try {
			int caretPos = textArea.getCaretPosition();
			int lineNum = textArea.getLineOfOffset(caretPos);
			int columnNum = caretPos - textArea.getLineStartOffset(lineNum);
			lineNum += 1;
			columnNum += 1;
			int selectedNum = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

			line.setText("Ln: " + lineNum);
			column.setText("Col: " + columnNum);
			selected.setText("Sel: " + selectedNum);

		} catch (BadLocationException ex) {
		}

	}

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
			exitProcedure();
		}
	};

	private Action copyAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JTextArea textArea = dmdm.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

			if (len > 0) {

				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {

					StringSelection stringSelection = new StringSelection(doc.getText(offset, len));
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);

				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	private Action pasteAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JTextArea textArea = dmdm.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();

			int carretPosition = textArea.getCaret().getDot();

			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			Object dataFromClipboard;
			try {
				dataFromClipboard = clipboard.getData(DataFlavor.stringFlavor);

				if (dataFromClipboard instanceof String) {

					String textFromClipboard = (String) dataFromClipboard;
					try {
						doc.insertString(carretPosition, textFromClipboard, null);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}
		}
	};

	private Action cutAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JTextArea textArea = dmdm.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

			if (len > 0) {

				int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());

				try {

					StringSelection stringSelection = new StringSelection(doc.getText(offset, len));
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);

					doc.remove(offset, len);

				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	private Action statisticalInfoAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			String infoString = "Statističke informacije o trenutno otvorenom dokumentu.\n\n";

			JTextArea textArea = dmdm.getCurrentDocument().getTextComponent();

			infoString += "Ukupan broj znakova: " + String.valueOf(textArea.getText().length()) + "\n";
			infoString += "Broj nepraznih znakova: "
					+ String.valueOf(textArea.getText().replaceAll("\\s+", "").length()) + "\n";
			infoString += "Broj linija: " + String.valueOf(textArea.getLineCount());

			JOptionPane.showMessageDialog(JNotepadPP.this, infoString, "Informacije", JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action english = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private Action croatian = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private Action german = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
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

		copyAction.putValue(Action.NAME, "Copy");
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copies selected text in clipboard.");

		pasteAction.putValue(Action.NAME, "Paste");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Pastes text from clipboard.");

		cutAction.putValue(Action.NAME, "Cut");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Cuts selected text.");

		deleteSelectedPartAction.putValue(Action.NAME, "Delete selected text");
		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete the selected part of text.");

		toggleCaseAction.putValue(Action.NAME, "Toggle case");
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.putValue(Action.SHORT_DESCRIPTION,
				"Used to toggle character case in selected part of text or in entire document.");

		statisticalInfoAction.putValue(Action.NAME, "Info");
		statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
		statisticalInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statisticalInfoAction.putValue(Action.SHORT_DESCRIPTION, "Statistical info of opened document.");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit application.");
		
		english.putValue(Action.NAME, "English");
		croatian.putValue(Action.NAME, "Croatian");
		german.putValue(Action.NAME, "German");
	}

	/**
	 * Funkcija za stvaranje izbornika.
	 * 
	 */
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

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));

		JMenu infoMenu = new JMenu("Info");
		infoMenu.add(new JMenuItem(statisticalInfoAction));
		
		menuBar.add(infoMenu);
		
		JMenu languagesMenu = new JMenu("Languages");
		languagesMenu.add(new JMenuItem(english));
		languagesMenu.add(new JMenuItem(croatian));
		languagesMenu.add(new JMenuItem(german));
		
		menuBar.add(languagesMenu);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Funkcija za stvaranje gumba alatne trake.
	 * 
	 */
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

		copy = new JButton(copyAction);
//		copy.setEnabled(false);
		copy.setIcon(createImageIcon("icons/copy.png", 20));
		toolBar.add(copy);

		JButton paste = new JButton(pasteAction);
		paste.setIcon(createImageIcon("icons/paste.png", 20));
		toolBar.add(paste);

		JButton cut = new JButton(cutAction);
		cut.setIcon(createImageIcon("icons/cut.png", 20));
		toolBar.add(cut);

		JButton info = new JButton(statisticalInfoAction);
		info.setIcon(createImageIcon("icons/info.png", 20));
		toolBar.add(info);

		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Funkcija za stvaranje statusne trake koja se nalazi na donjoj strani prozora
	 * i sadrži informacije o trenutnoj poziciji kursora, ukupnom broju linija,
	 * trenutnom vremenu.
	 * 
	 */
	private void createStatusBar() {

		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.black));

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		leftSide.setBackground(Color.green);

		int width = 50;
		int height = 16;

		line = new JLabel("Ln: 0", JLabel.LEFT);
		line.setPreferredSize(new Dimension(width, height));

		column = new JLabel("Col: 0", JLabel.LEFT);
		column.setPreferredSize(new Dimension(width, height));

		selected = new JLabel("Sel: 0", JLabel.LEFT);
		selected.setPreferredSize(new Dimension(width, height));

		leftSide.add(line);
		leftSide.add(column);
		leftSide.add(selected);

		statusBar.add(leftSide, BorderLayout.LINE_START);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		JLabel dateAndTime = new JLabel();
		statusBar.add(dateAndTime, BorderLayout.LINE_END);

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
