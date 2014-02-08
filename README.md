ti_logic_project3
=================

TI Logik Projektaufgabe 3 (WS13/14)

Vorgehensweise:
  1. Formel einlesen - OK
  2. Formel in positive Normalform umwandeln
    1. eliminate all a -> b, a <-> b, -(a & b), -(a | b), -FORALL x (f), -EXISTS x (f).
  3. Formel in PrÃ¤nex Normalform umwandeln
    1. Formel muss zweimal durchlaufen werden
    2. Beim ersten durchlauf freie und gebundene Variablen in Datenstruktur sammeln und freie umbenennen.
      1. freie Vars in map sammeln und auf sich selbst mappen, falls keien gebundene Version gefunden wird.
      2. gebundene Vars in liste sammeln
    3. Beim zweiten durchlauf die Ã„quivalenzen anwenden um PränixNormalform zu erhalten.
      1. nach Skript
  4. Formel skolemisieren
  5. Herbrand Expansion (Satz von Herbrand, endliche Teilmengen)
  6. Gilmore Algorithmus

Das wäre mein Teil als Diskussionsgrundlage. Was haltet ihr davon?
