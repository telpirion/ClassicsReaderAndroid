package com.ericmschmidt.classicsreader

val xmlString = """<?xml version="1.0"?>
<work>
<header type="text" status="new">
<fileDesc>
<titleStmt>
<title type="work" n="Gal.">De bello Gallico</title>
<title type="sub">Machine readable text</title>
<author n="Caes.">C. Julius Caesar</author>
</titleStmt>

<sourceDesc>
<biblStruct>
<monogr>
<author>C. Julius Caesar</author>
<title>C. Iuli Commentarii
Rerum in Gallia Gestarum VII
A. Hirti Commentarius VII</title>
<editor role="editor">T. Rice Holmes</editor>
<imprint>
<pubPlace>Oxonii</pubPlace>
<publisher>e Typographeo Clarendoniano</publisher>
<date>1914</date>
</imprint>
</monogr>
<series>
<title>Scriptorum Classicorum Bibliotheca Oxoniensis</title>
</series>
</biblStruct>
</sourceDesc>
</fileDesc>
</header>
<text>
<body>
<div1 type="Book" n="1">
<head>COMMENTARIUS PRIMUS</head>
<p>this is a line</p>
</div1>
<div1 type="Book" n="2">
<head>COMMENTARIUS SECONDUS</head>
<p>this is a line in the second book</p>
</div1>
<div1 type="Book" n="2">
<head>COMMENTARIUS TERTIUS</head>
<p>this is a line in the third book</p>
</div1>
</body></text></work>""".trimIndent()

val dictionaryXmlString = """<?xml version="1.0"?>
<work>
  <header status="new" type="text">
    <fileDesc>
      <titleStmt>
        <title>An Elementary Latin Dictionary</title>
        <title type="sub">Machine readable text</title>
        <author>Charlton T. Lewis</author>

      <funder>A gift in the name of Carol F. Ross</funder>
      </titleStmt>
      <sourceDesc>
        <biblStruct>
          <monogr>
            <author>Lewis, Charlton, T.</author>
            <title>An Elementary Latin Dictionary</title>
            <imprint>
              <pubPlace>New York, Cincinnati, and Chicago</pubPlace>
              <publisher>American Book Company</publisher>
              <date>1890</date>
            </imprint>
          </monogr>
	  <idno type="ISBN">0199102058</idno>
        </biblStruct>
      </sourceDesc>
    </fileDesc>

</header>
<text>
<body>

<div0 type="alphabetic letter" n="A">
<head lang="la">A</head>
<superEntry type="main"><entry id="n0" type="main" key="A1" n="1"><form><orth extent="full" lang="la"> A. a.</orth></form><gramGrp><itype> as an abbreviation, </itype></gramGrp><sense id="n0.0" level="3" n="1"> for the praenomen Aulus. </sense><sense id="n0.1" level="3" n="2"> for Absolvo, on the voting-tablet of a judge; hence C. calls A littera salutaris. </sense><sense id="n0.2" level="3" n="3"> for Antiquo on a voting-tablet in the Comitia. </sense><sense id="n0.3" level="3" n="4"> <emph>a. d.</emph> for ante diem. </sense><sense id="n0.4" level="3" n="5"> <emph>a.v.c. or a. u. c.</emph> for anno urbis conditae, or ab urbe condit&#257;. </sense><sense id="n0.5" level="3" n="6"> in the Tusculan Disputations of Cicero probably for Aud&#299;tor. </sense></entry>


  <entry id="n1" type="main" key="a2" n="2"><form><orth extent="full" lang="la">&#257;</orth></form> <sense id="n1.0" level="0" n="0">(before consonants), <emph>ab</emph> (before vowels, h, and some consonants, esp. l, n, r, s), <emph>abs</emph> (usu. only before t and q, esp. freq. before the pron. te), old af, <emph>praep.</emph> with <emph>abl.</emph>, denoting separation or departure (opp. ad). </sense><sense id="n1.1" level="1" n="I">I. Lit., in space, <trans><tr>from, away from, out of</tr></trans>. </sense><sense id="n1.2" level="2" n="A">A. With motion: ab urbe proficisci, Cs.: <foreign lang="la">a supero mari Flaminia (est via)</foreign>, leads: <foreign lang="la">Nunc quidem paululum, inquit, a sole</foreign>, a little out of the sun: <foreign lang="la">usque a mari supero Romam proficisci</foreign>, all the way from; with names of cities and small islands, or with domo, home (for the simple <emph>abl</emph>; of motion, away from, not out of, a place); hence, of raising a siege, of the march of soldiers, the setting out of a fleet, etc.: oppidum ab Aene&#257; fugiente a Troi&#257; conditum: ab Alesi&#257;, Cs.: <foreign lang="la">profectus ab Orico cum classe</foreign>, Cs.; with names of persons or with pronouns: cum a vobis discessero: videat forte hic te a patre aliquis exiens, i. e. from his house, T.; (praegn.): a rege munera repudiare, from, sent by, N.-</sense><sense id="n1.3" level="2" n="B">B. Without motion. </sense><sense id="n1.4" level="3" n="1">1. Of separation or distance: abesse a domo paulisper maluit: tum Brutus ab Rom&#257; aberat, S.: <foreign lang="la">hic locus aequo fere spatio ab castris Ariovisti et Caesaris aberat</foreign>, Cs.: <foreign lang="la">a foro longe abesse: procul a castris hostes in collibus constiterunt</foreign>, Cs.: <foreign lang="la">cum esset bellum tam prope a Sicili&#257;; so with</foreign> numerals to express distance: <foreign lang="la">ex eo loco ab milibus passuum octo</foreign>, eight miles distant, Cs.: <foreign lang="la">ab milibus passuum minus duobus castra posuerunt</foreign>, less than two miles off, Cs.; so rarely with substantives: quod tanta machinatio ab tanto spatio instrueretur, so far away, Cs.-</sense><sense id="n1.5" level="3" n="2">2. To denote a side or direction, etc., at, on, in: <foreign lang="la">ab sinistr&#257; parte nudatis castris</foreign>, on the left, Cs.: <foreign lang="la">ab e&#257; parte, qu&#257;</foreign>, etc., on that side, S.: <foreign lang="la">Gallia Celtica attingit ab Sequanis flumen Rhenum</foreign>, on the side of the Sequani, i. e. their country, Cs.: <foreign lang="la">ab decuman&#257; port&#257; castra munita</foreign>, at the main entrance, Cs.: <foreign lang="la">crepuit hinc a Glycerio ostium</foreign>, of the house of G., T.: <foreign lang="la">(cornua) ab labris argento circumcludunt</foreign>, on the edges, Cs.; hence, a fronte, in the van; a latere, on the flank; a tergo, in the rear, behind; a dextro cornu, on the right wing; a medio spatio, half way.-</sense><sense id="n1.6" level="1" n="II">II. Fig. </sense><sense id="n1.7" level="2" n="A">A. Of time. </sense><sense id="n1.8" level="3" n="1">1. Of a point of time, after: <foreign lang="la">Caesar ab decimae legionis cohortatione ad dextrum cornu profectus</foreign>, immediately after, Cs.: <foreign lang="la">ab eo magistratu</foreign>, after this office, S.: <foreign lang="la">recens a volnere Dido</foreign>, fresh from her wound, V.: <foreign lang="la">in Italiam perventum est quinto mense a Carthagine</foreign>, i. e. after leaving, L.: <foreign lang="la">ab his</foreign>, i. e. after these words, hereupon, O.: <foreign lang="la">ab simili</foreign> *ade domo profugus, i. e. after and in consequence of, L.-</sense><sense id="n1.9" level="3" n="2">2. Of a period of time, from, since, after: <foreign lang="la">ab hora terti&#257; bibebatur</foreign>, from the third hour: <foreign lang="la">ab Sull&#257; et Pompeio consulibus</foreign>, since the consulship of: <foreign lang="la">ab incenso Capitolio illum esse vigesumum annum</foreign>, since, S.: <foreign lang="la">augures omnes usque ab Romulo</foreign>, since the time of: <foreign lang="la">iam inde ab infelici pugn&#257; ceciderant animi</foreign>, from (and in consequence of), L.; hence, ab initio, a principio, a primo, at, in, or from the beginning, at first: <foreign lang="la">ab integro</foreign>, anew, afresh: <foreign lang="la">ab ... ad</foreign>, from (a time) ... to: <foreign lang="la">cum ab hor&#257; septim&#257; ad vesperum pugnatum sit</foreign>, Cs.; with nouns or adjectives denoting a time of life: iam inde a pueriti&#257;, T.: <foreign lang="la">a pueriti&#257;: a pueris: iam inde ab incunabulis</foreign>, L.: <foreign lang="la">a parvo</foreign>, from a little child, or childhood, L.: <foreign lang="la">ab parvulis</foreign>, Cs.-</sense><sense id="n1.10" level="2" n="B">B. In other relations. </sense><sense id="n1.11" level="3" n="1">1. To denote separation, deterring, intermitting, distinction, difference, etc., from: <foreign lang="la">quo discessum animi a corpore putent esse mortem: propius abesse ab ortu: alter ab illo</foreign>, next after him, V.: <foreign lang="la">Aiax, heros ab Achille secundus</foreign>, next in rank to, H.: <foreign lang="la">impotentia animi a temperanti&#257; dissidens: alieno a te animo fuit</foreign>, estranged; so with adjj. denoting free, strange, pure, etc.: res familiaris casta a cruore civili: purum ab humano cultu solum, L.: <foreign lang="la">(opoidum) vacuum ab defensoribus</foreign>, Cs.: <foreign lang="la">alqm pudicum servare ab omni facto</foreign>, etc., II.; with substt.: <foreign lang="la">impunitas ab iudicio: ab armis quies dabatur</foreign>, L.; or verbs: <foreign lang="la">haec a custodiis loca vacabant</foreign>, Cs.-</sense><sense id="n1.12" level="3" n="2">2. To denote the agent, by: <foreign lang="la">qui (Mars) saepe spoliantem iam evert</foreign>it et perculit ab abiecto, by the agency of: <foreign lang="la">Laudari me abs te, a laudato viro: si qu</foreign>id ei a Caesare gravius accidisset, at Caesar's hands, Cs.: <foreign lang="la">vetus umor ab igne percaluit solis</foreign>, under, O.: <foreign lang="la">a populo P. imperia perferre</foreign>, Cs.: <foreign lang="la">equo lassus ab indomito</foreign>, H.: <foreign lang="la">volgo occidebantur: per quos et a quibus?</foreign> by whose hands and upon whose orders? factus ab arte decor, artificial, O.: <foreign lang="la">destitutus ab spe</foreign>, L.; (for the sake of the metre): correptus ab ignibus, O.; (poet. with <emph>abl.</emph> of means or instr.): intumuit venter ab und&#257;, O.-Ab with <emph>abl.</emph> of agent for the <emph>dat.</emph>, to avoid ambiguity, or for emphasis: quibus (civibus) est a vobis consulendum: te a me nostrae consuetudinis monendum esse puto.-</sense><sense id="n1.13" level="3" n="3">3. To denote source, origin, extraction, from, of: <foreign lang="la">Turnus ab Arici&#257;</foreign>, L.: <foreign lang="la">si ego me a M. Tull</foreign>io esse dicerem: oriundi ab Sabinis, L.: <foreign lang="la">dulces a fontibus undae</foreign>, V.-With verbs of expecting, fearing, hoping (cf. a parte), from, on the part of: <foreign lang="la">a quo quidem genere, iudices, ego numquam timui: nec ab Romanis vobis ulla est spes</foreign>, you can expect nothing from the Romans, L.; (ellipt.): haec a servorum bello pericula, threatened by: <foreign lang="la">quem metus a praetore Romano stimulabat</foreign>, fear of what the praetor might do, L.-With verbs of paying, etc., solvere, persolvere, dare (pecuniam) ab aliquo, to pay through, by a draft on, etc.: se praetor dedit, a quaestore numeravit, quaestor a mens&#257; public&#257;, by an order on the quaestor: <foreign lang="la">ei legat pecuniam a filio</foreign>, to be paid by his son: <foreign lang="la">scribe decem (milia) a Nerio</foreign>, pay by a draft on Nerius, H.; cognoscere ab aliqu&#257; re, to know or learn by means of something (but ab aliquo, from some one): id se a Gallicis armis atque insignibus cognovisse, Cs.; in giving an etymology: id ab re ... interregnum appellatum, L.-Rarely with verbs of beginning and repeating: coepere a fame mala, L.: <foreign lang="la">a se suisque orsus</foreign>, Ta.-</sense><sense id="n1.14" level="3" n="4">4. With verbs of freeing from, defending, protecting, from, against: <foreign lang="la">ut a proeliis quietem habuerant</foreign>, L.: <foreign lang="la">provincia a calamitate est defendenda: sustinere se a lapsu</foreign>, L.-</sense><sense id="n1.15" level="3" n="5">5. With verbs and adjectives, to define the respect in which, in relation to, with regard to, in respect to, on the part of: <foreign lang="la">orba ab optimatibus contio: mons vastus ab natur&#257; et humano cultu</foreign>, S.: <foreign lang="la">ne ab re sint omissiores</foreign>, too neglectful of money or property, T.: <foreign lang="la">posse a facundi&#257;</foreign>, in the matter of eloquence, T.; cf. with laborare, for the simple <emph>abl</emph>, in, for want of: <foreign lang="la">laborare ab re frumentari&#257;</foreign>, Cs.-</sense><sense id="n1.16" level="3" n="6">6. In stating a motive, from, out of, on account of, in consequence of: <foreign lang="la">patres ab honore appellati</foreign>, L.: <foreign lang="la">inops tum urbs ab longinqu&#257; obsidione</foreign>, L.-</sense><sense id="n1.17" level="3" n="7">7. Indicating a part of the whole, of, out of: <foreign lang="la">scuto ab novissimis uni militi detracto</foreign>, Cs.: <foreign lang="la">a quibus (captivis) ad Senatum missus (Regulus)</foreign>.-</sense><sense id="n1.18" level="3" n="8">8. Marking that to which anything belongs: qui sunt ab e&#257; disciplin&#257;: nostri illi a Platone et Aristotele aiunt.-</sense><sense id="n1.19" level="3" n="9">9. Of a side or party: vide ne hoc totum sit a me, makes for my view: <foreign lang="la">vir ab innocenti&#257; clementissimus</foreign>, in favor of.-<emph>10.</emph> In late prose, of an office: ab epistulis, a secretary, Ta. Note. Ab is not repeated with a following <emph>pron</emph> interrog. or relat.: <foreign lang="la">Arsinon, Stratum, Naupactum ... fateris ab hostibus esse captas. Quibus autem hostibus? Nempe iis, quos</foreign>, etc. It is often separated from the word which it governs: a nullius umquam me tempore aut commodo: a minus bono, S.: <foreign lang="la">a satis miti principio</foreign>, L.-The poets join a and que, making &#257;que; but in good prose que is annexed to the following <emph>abl.</emph> (a meque, abs teque, etc.): aque Chao, V.: <foreign lang="la">aque mero</foreign>, O.-In composition, ab- stands before vowels, and h, b, d, i consonant, l, n, r, s; abs- before c, q, t; b is dropped, leaving as- before p; &#257;- is found in &#257;fu&#299;, &#257;fore (<emph>inf</emph> fut. of absum); and au- in aufer&#333;, aufugi&#333;. </sense></entry>

</superEntry>
<entry id="n2" type="main" key="abactus"><form><orth extent="full" lang="la">ab&#257;ctus</orth></form> <sense id="n2.0" level="0" n="0"><etym lang="la">P. of abigo</etym>, <trans><tr>driven away, driven off</tr></trans>: <foreign lang="la">nox abacta</foreign>, <trans><tr>driven back</tr></trans> (from the pole), i. e. <trans><tr>already turned towards dawn</tr></trans>, <usg>V.</usg>: <foreign lang="la">abacta null&#257; conscienti&#257;</foreign>, <trans><tr>restrained by</tr></trans>, <usg>H.</usg> </sense></entry>


<entry id="n3" type="main" key="abacus"><form><orth extent="full" lang="la">abacus</orth></form><gramGrp><itype> &#299;, </itype><gen>m</gen></gramGrp><sense id="n3.0" level="0" n="0"><trans><tr> a table of precious material for the display of plate</tr></trans>, C.; luv. </sense></entry>


<entry id="n4" type="main" key="abalienatio"><form><orth extent="full" lang="la">abali&#275;n&#257;ti&#333;</orth></form><gramGrp><itype> &#299;nis, </itype><gen>f</gen> </gramGrp><sense id="n4.0" level="0" n="0"><etym lang="la">abalieno</etym>, in law, <trans><tr>a transfer of property, sale, cession</tr></trans>, <usg>C.</usg> </sense></entry>


<entry id="n5" type="main" key="abalieno"><form><orth extent="full" lang="la">ab-ali&#275;n&#333;</orth></form><gramGrp><itype> &#257;v&#299;, &#257;tus, &#257;re, </itype></gramGrp><sense id="n5.0" level="0" n="0"><trans><tr>to convey away, make a former transfer of, sell, alienate</tr></trans>: <foreign lang="la">agros vectigal&#299;s populi</foreign> R.: <foreign lang="la">pecus</foreign>.-Fig., to separate, remove, abstract: <foreign lang="la">ab sensu rerum animos</foreign>, abstracted their thoughts from, L.: <foreign lang="la">deminuti capite, abalienati iure civium</foreign>, deprived of, L.-In partic., to alienate, estrange, make hostile, render disaffected: <foreign lang="la">abalienati scelere istius a nobis reges</foreign>, from us, by his wickedness: <foreign lang="la">aratorum numerum abs te: periurio homines suis rebus</foreign>, N.: <foreign lang="la">totam Africam</foreign>, estrange, N. </sense></entry>
</div0>
</body></text></work>""".trimIndent()
