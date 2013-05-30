A0B36PR2
========

Semestrální práce do předmětu A0B36PR2 

Jedná se o jednoduchý client-server chat s použitím šifrování zpráv upravenou verzí Vigenerovy šifry.
Program se zkládá ze dvou částí - klienta a serveru. Grafické uživatelské prostředí je u obou stejné,
do pole v horní části napíšeme heslo a potvrdíme tlačítkem Confirm, do dolního textového pole píšeme 
zprávy a odesíláme je buď klávesou Enter, nebo tlačítkem Send. Šifrování a dešifrování probíhá
automaticky a zprávy se zobrazují do pole veprostřed. Jak pro heslo, tak pro text je doporučeno
používat pouze malá a velká písmena, znaky .,!?/- a mezeru, ostatní budou nahrazeny podtržítkem.
Tlačítkem Leave ukončíme konverzaci.

Co se týče objektového návrhu, použil jsem pouze 2 třídy. Třída Server vytváří připojení a třída 
Client se k němů dokáže připojit. Jednotlivé metody jsou podobné, dědičnost jsem nepoužil, protože 
mi potom z neznámých důvodů selhávalo síťování.

Ke kódování zpráv jsem použil upravenou verzi Vigenerovy šifry z prvního semestru.
