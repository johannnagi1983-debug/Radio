# RadioPlayer – Android-Prototyp

Dunkler, einfacher Android-Prototyp mit den drei angegebenen Streams:
- sunshine live
- Radio BOB!
- Absolut Rock

## Cloud-Build mit Codemagic
1. Lade dieses Projekt in ein GitHub-Repository hoch.
2. Öffne Codemagic und verbinde das Repository.
3. Wähle den Workflow `RadioPlayer Android APK (Debug)` und starte den Build.
4. Nach erfolgreichem Build lade `app-debug.apk` aus dem Bereich Artifacts herunter.

Die Datei `codemagic.yaml` enthält die Build-Konfiguration. Das Projekt benötigt eine Build-Umgebung mit Gradle und Android SDK.

## Prototyp-Umfang
Enthalten: dunkles Design, Senderliste, Wiedergabe starten/stoppen.
Noch nicht enthalten: 3x4-Kachelansicht, frei belegbare Kacheln, gespeicherte Favoriten, Suche/Filter nach Land und Genre, Sleep-Timer, Wecker sowie Anzeige von Interpret/Titel-Metadaten.
