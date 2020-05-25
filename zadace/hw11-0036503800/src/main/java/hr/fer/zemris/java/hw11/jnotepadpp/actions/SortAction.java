package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Razred koji predstavlja akcije sortiranje teksta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class SortAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private IDataGetter data;
	private String key;
	private Comparator<String> comparator;

	/**
	 * Konstruktor.
	 * 
	 * @param key  ključ prijevoda za ime akcije
	 * @param flp  referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public SortAction(String key, ILocalizationProvider flp, IDataGetter data) {

		super(key, flp);

		if (key.equals("ascending")) {

			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		} else if (key.equals("descending")) {

			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		} else {

			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		}

		putValue(Action.SHORT_DESCRIPTION, flp.getString(key + "desc"));

		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString(key + "desc"));
			}
		});

		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(locale);

		comparator = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return collator.compare(o1, o2);
			}
		};

		this.data = data;
		this.key = key;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JTextArea textArea = data.getDmdm().getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();

		String text = textArea.getText();
		List<String> lines = new ArrayList<>(Arrays.asList(text.split("\n")));

		int caretPos = textArea.getCaretPosition();

		try {
			int lineNumCaret = textArea.getLineOfOffset(caretPos);
			int lineNumMark = textArea.getLineOfOffset(textArea.getCaret().getMark());

			// indeksi redaka (od, do) koji su označeni za sortiranje
			int fromIndex = Math.min(lineNumCaret, lineNumMark);
			int toIndex = Math.max(lineNumCaret, lineNumMark) + 1;

			lines = lines.subList(fromIndex, toIndex);
			
			if(key.equals("ascending")) {
				lines = lines.stream().sorted(comparator).collect(Collectors.toList());
			}else if(key.equals("descending")){
				lines = lines.stream().sorted(comparator.reversed()).collect(Collectors.toList());
			}else {
				lines = lines.stream().distinct().collect(Collectors.toList());
			}
			
			StringBuilder sb = new StringBuilder();
			lines.forEach(l -> sb.append(l).append("\n"));
			
			int startOffset = textArea.getLineStartOffset(fromIndex);
			
			doc.remove(startOffset, sb.length()); 
			doc.insertString(startOffset, sb.toString(), null);
			
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

}
