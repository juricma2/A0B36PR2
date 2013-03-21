A0B36PR2
========

Semestrální práce do předmětu A0B36PR2 

Bude se jednat o program pro komunikaci přes síť s využitím šifrování vzkazů.

K šifrování bude program používat Vigenèrovu šifru, kterou mám připravenou z předchozí semestrálky, 
bude k dispozici jednoduché grafické uživatelské prostředí zobazující šifrovaný a dešifrovaný text,
nějaký formulář na nastavení hesla a asi serveru (řešení síťové komunikace jsem ještě nezkoumal).

Co se týče objektového návrhu, představuji si hlavní třídu řídící program, abstraktní třídu Text, 
její potomky Coded a Decoded a třídu Message, která bude pracovat se samotným vzkazem bez "znalosti" klíče.
