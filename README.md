# SMP MAintenance TEchnology

Note: sorry to all you guys out there not speaking German - this repository is only available in German. This repository basically provides a maintenance tool that allows to bulk create

Dieser Leitfaden beschreibt SMP Mate (ehemals SMP Provisioning Tool), mit dessen Hilfe zusätzliche Participants im Peppol-Netzwerk provisioniert werden können.
SMP Mate wird als Archiv ausgeliefert, dessen Bestandteile und Verwendung im Folgenden erklärt werden.

## Einleitung

Für den Nachrichtenaustausch im Peppol-Netzwerk müssen die Teilnehmer (im Peppol-Kontext "Participants") in der Komponente SMP und bei den Access Points bekannt 
sein.

* **SMP**: Die Komponente SMP (SMP steht für Service Metadata Publisher) ist der Peppol-eigene Directory Service. In diesem Nutzerverzeichnis, sind **alle** teilnehmenden Participants hinterlegt.
* **Access Points**: Die Access Points kennen jeweils nur eine Untermenge aus der Menge der Participants.
    * **Sender Access Points**: Diese Access Points kennen die teilnehmenden Participants Ihres jeweiligen Landes.
    * **Receiver Access Points**: Diese Access Points kennen die jeweiligen Participants, für die Peppol-Nachrichten, die vom Empfangsprozess abgeholt werden sollen. Beispielsweise holt der Governikus MultiMessenger, per AS4 AccessPoint mit XTA-Connector, Nachrichten von Gruppen von Participants ab, die dem GMM bekannt sind.

Aus den jeweiligen Absendern (Sender, C1), Empfängern (Receiver, C4) und Access Points (C2 und C3) ergibt sich das 4-Corner-Modell des Peppol-Netzwerks.

## Peppol Participants Provisioning

Damit weitere Participants am Nachrichtenaustausch über das PEEPOL-Netzwerk teilnehmen können, müssen diese in den Access-Points und im SMP provisioniert werden.
Alle in einem Access Point bekannten Participants sind auch im SMP bekannt, da die dort provisionierten Participants eine Untermenge der Participants im SMP sind.

### Allgemeine Beschreibung des Verfahrens

Beim Provisionieren mit dem SMP Provisioning Tool werden die folgenden Schritte durchgeführt.

* **Feststellen weiterer Participants**: Das SMP Provisioning Tool übergibt dem System eine CSV-Datei, z.B. `newParticipants.csv`, die neue Participants enthält, die dem Access Point und dem SMP hinzugefügt werden sollen.

Das Provisionieren neuer Participants kann an den Access Points 2 (C2) und 3 (C3) des 4-Corner Modells erfolgen und aktualisiert dabei immer auch den SMP.

### Parameter von SMP Mate

Für die Provisionierung steht eine Kommandozeilen-Applikation in Form einer ausführbaren JAR-Datei zur Verfügung.
Dieser Applikation wird eine Konfigurationsdatei im JSON-Format als Parameter übergeben.
In der Konfigurationsdatei werden unter anderem weitere Dateien referenziert, die für die Durchführung der Provisionierung erforderlich sind.

#### Konfigurationsdaten in der `sampleTask.json`-Datei

Ein Beispiel für die erforderlichen Konfigurationsdaten befindet sich in der Datei `sampleTask.json`, die auf oberster Ebene des ZIP-Archivs zu finden ist.

**Achtung**: Bitte beachten Sie, dass in JSON-Dateien normalerweise keine Kommentare enthalten sein dürfen.
Bitte löschen Sie die Kommentare aus dem JSON-Template, nachdem Sie die Parameter angepasst haben und bevor Sie das SMP Provisioning Tool ausführen.

In der Datei `sampleTask.json` werden die Pfade zu weiteren Eingabe- sowie auch zu Ausgabedateien, die SMP-Konfiguration und eine möglicherweise
benötigte Proxy-Konfiguration definiert.

#### Der Abschnitt "Pfade" in der JSON-Datei

Pfade zu Input-Dateien definiert, die zur Ausführungszeit vorhanden sein müssen. Die folgenden Einträge müssen angepasst werden:

* **`csvInput`**: Der Name der Variable `csvInput` darf nicht geändert werden und definiert Pfad und Dateiname der Datei, in der die neuen Participants enthalten sind, beispielsweise `newParticipants.csv`.
    * **`newParticipants.csv`**: Diese Datei enthält alle neue Participants. Die Participants werden in der Datei als Liste der Participant-IDs notiert. Hinweis: die Verwendung von CSV ist für die Vorwärtskompatibilität.
        * `ParticipantId`: Diese ID ist im gesamten Peppol-Netzwerk eindeutig.
* **`csvFailOutput`**: Der Name der Variable `csvFailOutput` darf nicht geändert werden und definiert Pfad und Dateiname einer Datei, die geschrieben wird, wenn einzelne Participants nicht erfolgreich an den SMP-Server übermittelt werden können, beispielsweise `failedParticipants.csv`.
* **`serviceGroupTemplate`**: Der Name der Variable `serviceGroupTemplate` darf nicht geändert werden und definiert Pfad und Dateiname einer Datei die die Vorlage für eine SMP "ServiceGroup" enthält. Der Default-Wert sollte nicht angepasst werden.
* **`serviceMetadata`**: Der Name der Variable `serviceMetadata` darf nicht geändert werden und definiert Pfade und Dateinamen als Vorlage einer oder mehrerer XML-Strukturen sowie der Identifikatoren für Dokumenttyp und Prozess, so, wie sie an den SMP-Server übermittelt werden sollen, beispielsweise `[ { "template": "ServiceMetadata.xml", "documentIdentifier": "urn:oasis:names:specification:ubl:schema:xsd:Tender-2::Tender##urn:www.cenbii.eu:transaction:biitrdm090:ver3.0::2.1", "processIdentifier": "urn:www.cenbii.eu:profile:bii54:ver3.0" } ]`
    * **`template`**: Der Pfad und Dateinamer zur verwendeten XML-Vorlage
    * **`documentIdentifier`**: Der zu verwendende Peppol Dokumenttypidentifikator
    * **`processIdentifier`**: Der zu verwendende Peppol Prozessidentifikator

#### Der Abschnitt "SMP-Konfiguration" in der JSON-Datei

Diese Konfiguration enthält die URL der SMP-REST-Schnittstelle, und Authentifizierungsdaten, wenn diese gefordert sind.
Diese Parameter müssen aus dem Template gelöscht werden, wenn keine Authentifizierungsdaten erforderlich sind.

#### Der Abschnitt "Proxy-Konfiguration" in der JSON-Datei

Diese Konfigurationsdaten sind erforderlich, wenn der SMP-Server hinter einem Proxy betrieben wird.
Diese Daten müssen aus dem Template entfernt werden, wenn kein Proxy konfiguriert werden muss.

#### Der Abschnitt "Options" in der JSON-Datei

Zurzeit steht genau eine Option zur Verfügung.
Die Option `DRY_RUN` sorgt dafür, dass ein Programdurchlauf ausgeführt werden kann, bei dem keine Daten an den SMP-Server übermittelt werden.
Damit kann die Ausführbarkeit des Programms mit seinen aktuellen Parametern getestet werden.
Wenn Ein- und Ausgabedatei mit dem gleichen Pfad/Dateinamen definiert sind, wird diese Datei auch im Modus `DRY_RUN` überschrieben.

## Ausführen von SMP Mate

Die JAR-Datei `smp-mate-<Versionsnummer>-app.jar` kann in jeder beliebigen Umgebung mit dem nachfolgend beschriebenen Aufruf ausgeführt werden.
Dabei muss sichergestellt werden, dass die Pfade und Dateien erreichbar sind, ebenso wie der SMP-Server.

### Aufruf der ausführbaren JAR-Datei

```shell
java – jar smp-mate-<Versionsnummer>-app.jar sampleTask.json
```

# Technisches

## Running

Einfach `java -jar smp-mate-x.y.z-app.jar` ausführen und die Hilfe durchlesen, wobei `x.y.z` für die Versionsnummer steht.

## Building

Voraussetzungen: Java 1.8 oder höher, Apache Maven 3.x

Befehl: `mvn clean install`

Das Ergebnis ist die Datei `target/smp-mate-x.y.z-SNAPSHOT-app.jar` wobei `x.y.z` für die Versionsnummer steht.
