A0B36PR2
========

Semestrální práce do předmětu A0B36PR2 

Jedná se o jednoduchý client-server chat s použitím šifrování zpráv upravenou verzí Vigenerovy šifry.
Program se zkládá ze tří částí - hlavní třídy, klienta a serveru. V hlavní třídě nastavíme 
adresu (v případě klienta)a port a tlačítkem spustíme buď klienta, nebo server. Grafické uživatelské 
prostředí je u obou stejné, do pole v horní části napíšeme heslo a potvrdíme tlačítkem Confirm, 
do dolního textového pole píšeme zprávy a odesíláme je buď klávesou Enter, nebo tlačítkem Send. 
Šifrování a dešifrování probíhá automaticky a zprávy se zobrazují do pole veprostřed. 
Jak pro heslo, tak pro text je doporučeno používat pouze malá a velká písmena, znaky .,!?/- a mezeru,
ostatní budou nahrazeny podtržítkem. Je lepší šifrovat znaky z předem stanoveného "balíčku", než řešit
případ kódu např. v azbuce. Tlačítkem Leave ukončíme konverzaci.

Co se týče objektového návrhu, použil jsem pouze 3 třídy. Třída Server vytváří připojení a třída 
Client se k němů dokáže připojit. Hlavní třída umožňuje nastavení adresy, portu a výběr spuštění
klienta nebo serveru. Ke kódování zpráv je použita upravenoá verze Vigenerovy šifry z prvního semestru.

Třída SemetralkaPR2:
- hlavní třída, nastavuje adresu a port, spouští klienta/server
- 2 tlačítka (server/klient), 2 textFieldy (IP adresa, port), 2 labely (Adress (Client), Port)
- obsahuje metody (void):
  - main - hlavní metoda
  - jButton1ActionPerformed - tlačítko spuštění klienta
  - jButton2ActionPerformed - tlačítko spuštění serveru

Třída Client:
- třída pro klienta
- 3 tlačítka (Confirm - potvrzení hesla, Send a Leave), passwordField, 2 textArea (výpis zpráv, prostor ke psaní)
- obsahuje metody (void, pokud není uvedeno jinak):
  - main - spuštění klienta se zadanou IP a portem
  - run - spustí se s novým vláknem, zavolá metodu main
  - jButton1ActionPerformed - tlačítko potvrzení zadaného hesla
  - jButton2ActionPerformed - tlačítko odeslání zprávy
  - jButton3ActionPerformed - tlačítko ukončení
  - jTextArea2KeyPressed - při stisknutí Enteru se text odešle
  - poslat - samotné odeslání textu
  - String prevod - nahrazení neznámých znaků podtržítkem
  - String koduj - kódovaní textu podle klíče
  - String dekoduj - dekódování textu podle klíče
  - odeslat - převede text metodou prevod, vypíše ho, zakóduje ho metodou koduj a odešle metodou poslat
  - inform - vypíše text
  - alert - vyhodí okno se zprávou
  - pripojit - pokouší se připojit přes zadaný port k serveru se zadanou IP
  - nastav - nastaví vstupní a výstupní proud
  - prubeh - příjmá a dekóduje zprávy pomocí metody dekoduj, poté je vypisuje
  - konec - ukončuje připojení
  - spustit - volá metody pripojit, nastav a prubeh
  
Třída Server:
- třída pro klienta
- 3 tlačítka (Confirm - potvrzení hesla, Send a Leave), passwordField, 2 textArea (výpis zpráv, prostor ke psaní)
- obsahuje metody (void, pokud není uvedeno jinak):
  - main - spuštění serveru se zadaným portem
  - run - spustí se s novým vláknem, zavolá metodu main
  - jButton1ActionPerformed - tlačítko potvrzení zadaného hesla
  - jButton2ActionPerformed - tlačítko odeslání zprávy
  - jButton3ActionPerformed - tlačítko ukončení
  - jTextArea2KeyPressed - při stisknutí Enteru se text odešle
  - poslat - samotné odeslání textu
  - String prevod - nahrazení neznámých znaků podtržítkem
  - String koduj - kódovaní textu podle klíče
  - String dekoduj - dekódování textu podle klíče
  - odeslat - převede text metodou prevod, vypíše ho, zakóduje ho metodou koduj a odešle metodou poslat
  - inform - vypíše text
  - alert - vyhodí okno se zprávou
  - cekani - přijmutí klienta
  - nastav - nastaví vstupní a výstupní proud
  - prubeh - příjmá a dekóduje zprávy pomocí metody dekoduj, poté je vypisuje
  - konec - ukončuje připojení
  - spustit - nastaví socket se zadaným portem a čeká na připojení klienta
