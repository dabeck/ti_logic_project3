ti_logic_project3
=================

TI Logik Projektaufgabe 3 (WS13/14)

Vorgehensweise:
  1. Formel einlesen - OK
  2. Formel in positive Normalform umwandeln
    1. eliminate all a -> b, a <-> b, -(a & b), -(a | b), -FORALL x (f), -EXISTS x (f).
  3. Formel in Pränex Normalform umwandeln
    1. Formel muss zweimal durchlaufen werden
    2. Beim ersten durchlauf freie und gebundene Variablen in Datenstruktur sammeln und freie umbenennen.
    3. Beim zweiten durchlauf die Äquivalenzen anwenden um PränixNormalform zu erhalten.
  4. Formel skolemisieren
  5. Herbrand Expansion (Satz von Herbrand, endliche Teilmengen)
  6. Gilmore Algorithmus


Das wäre mein Teil als Diskussionsgrundlage. Was haltet ihr davon?
