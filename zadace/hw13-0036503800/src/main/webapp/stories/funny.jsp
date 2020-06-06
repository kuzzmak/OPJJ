<%@ page import="java.util.List, java.util.ArrayList, java.util.Arrays, java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- stranica sa smiješnom pričom --%>

<html>
	<% 
		List<String> colors = new ArrayList<>(Arrays.asList("RED", "GREEN", "BLACK"));
		String col = colors.get(new Random().nextInt(colors.size()));
	%>
	<style>
		pre {
			color:<%=col%>;
		}
	</style>
	<body style="background-color:${pickedBgColor};">
		<pre>Mujo na kvizu "Tko želi biti milijunaš" odgovara na pitanje za nekoliko tisuća.

Tarik pročita pitanje koje glasi: 
        
Koji je glavni grad Hrvatske:
	a) Knin
	b) Zagreb
	c) Sisak
	d) Split

Mujo se misli i misli...

Ja bih ipak pozvao pomoć, iskoristio bih džokera "zovi" pa bi pozvao svog prijatelja Sulju.

Uredu, pozvat ćemo vašeg prijatelja Sulju. - odgovara Tarik.

Halo, Suljo? Ovdje Tarik Filipović. Vaš prijatelj je zapeo na jednom pitanju pa izvolite, imate 30 sekundi.

Zdravo Suljo, šta ima? - upita Mujo.

... Kako su ti Fata i djeca, imal' šta novo?

I tako teče priča dok ne istekne 30 sekundi, sirena se oglasi, a Mujo ga ne upita glede pitanja. Tarik upita:

Pa dobro Mujo, zašto nisi pitao Sulju za odgovor?

Ma šta ću ga pitat', znam ja odgvor, al' sam iskoristio priliku da se čujem s njim u Australiji.
		</pre>
		
	</body>
</html>