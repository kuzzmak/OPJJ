package demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Glavni2 {

	public static void main(String[] args) throws IOException {

		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}

	public static LSystem createKochCurve2(LSystemBuilderProvider provider) throws IOException {

		Path path = Paths.get("KochCurve2.txt");
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());

		return provider.createLSystemBuilder().configureFromText(lines.toArray(new String[] {})).build();
	}
}
