# Eight_Queens

Eräänlainen Backtracking-ratkaisu seuraavaan ongelmaan:
Kahdeksan kuningattaren ongelma on klassinen kombinatoriikan ongelma, 
jossa tehtävänä on asettaa kahdeksan kuningatarta shakkilaudalle siten, 
etteivät mitkään kaksi kuningatarta uhkaa toisiaan; toisin sanoen mitkään 
kaksi kuningatarta eivät saa olla samalla vaakarivillä, pystylinjalla tai diagonaalilla.

 * HUOM!!! värit eivät toimi kaikissa terminaaleissa (powershell/windows komentokehoite), vscode terminaali sen sijaan toimii,
 * mikäli tämä häiritsee tulee kommentoida/poistaa printer() metodista kaikki ANSI_<color> rivit.
 * 
 * Paranneltu bruteforce/backtracking menetelmä etsimään kaikki vaihtoehdot asettaa
 * kuningattaret pelilaudalle uhkaamatta toisiaan: Pelilaudan kokoa voi
 * muuttaa(minimi 4), maksimi ei ole mutta luonnollisesti mitä isompi pelilauta
 * == tarvitaan tehoja koneesta oletuksena on kahdeksan jolloin pitäisi löytyä
 * 92 eri vaihtoehtoa
 * 
 * https://www.datagenetics.com/blog/august42012/ Sivulta voi tarkistaa toimiiko
 * algoritmi sekä löytyy kaikki eri vaihtoehdot pelilaudan ratkaisuiden määrä
 * koko 
 * 1 x 1 1 
 * 2 x 2 0 
 * 3 x 3 0 
 * 4 x 4 2 
 * 5 x 5 10 
 * 6 x 6 4 
 * 7 x 7 40 
 * 8 x 8 92 
 * 9 x 9 352 
 * 10 x 10 724 
 * 11 x 11 2,680 
 * 12 x 12 14,200 
 * 13 x 13 73,712 
 * 14 x 14 365,596
 



