(function () {
    "use strict";

    function Word(type) {
        this.type = type;
    }

    function VerbFactory(word) {
        if (word.partOfSpeech.type == 'verb') {
            var conj = word.partOfSpeech.conj;
            
            switch (conj) {
                case 'first':
                    return new FirstConjugationVerb(word.stem,
                        word.definition);
                    break;

                case 'second':
                    return new SecondConjugationVerb(word.stem,
                        word.perfect, word.perfectParticiple,
                        word.definition);
                    break;

                default:
                    return null;
            }
        }
    }


    function BaseVerb() {

        var verb = new Word('verb');

        verb.conjugation = "";
        verb.stem = "";
        verb.indicativeFPSPres = "";
        verb.infinitiveAP = "";
        verb.indicativeFPSPerf = "";
        verb.perfectPassivePart = "";
        verb.definition = "";
        verb.getDictionaryEntry = function () {
            return this.indicativeFPSPres + ", " +
            this.infinitiveAP + ", " +
            this.indicativeFPSPerf + ", " +
            this.perfectPassivePart;
        }

        return verb;
    };

    function FirstConjugationVerb(stem, definition) {
        var obj = new BaseVerb();
        obj.conjugation = "first";
        obj.stem = stem;
        obj.indicativeFPSPres = stem + "o";
        obj.infinitiveAP = stem + "are";
        obj.indicativeFPSPerf = stem + "avi";
        obj.perfectPassivePart = stem + "atus";
        obj.definition = definition;

        obj.presentIndicativeActive = new Conjugation(stem,
            'a', 
            [obj.indicativeFPSPres, 's', 't', 'mus', 'tis', 'nt'],
            {
                mood: 'Indicative',
                tense: 'Present',
                voice: 'Active'
            });
            
        return obj;
    }

    function SecondConjugationVerb(stem, perfect, perfectParticiple, definition) {
        var obj = new BaseVerb();
        obj.conjugation = 'second';
        obj.stem = stem;
        obj.indicativeFPSPres = stem + 'eo';
        obj.infinitiveAP = stem + 'ere';
        obj.indicativeFPSPerf = perfect;
        obj.perfectPassivePart = perfectParticiple;
        obj.definition = definition;

        obj.presentIndicativeActive = new Conjugation(stem,
           'e',
           [obj.indicativeFPSPres, 's', 't', 'mus', 'tis', 'nt'],
           {
               mood: 'Indicative',
               tense: 'Present',
               voice: 'Active'
           });

        return obj;

    }

    function Conjugation(stem, themeVowel, endings, options) {
        this.firstPersSing = endings[0];
        this.secondPersSing = stem + themeVowel + endings[1];
        this.thirdPersSing = stem + themeVowel + endings[2];
        this.firstPersPl = stem + themeVowel + endings[3];
        this.secondPersPl = stem + themeVowel + endings[4];
        this.thirdPersPl = stem + themeVowel + endings[5];
        this.mood = options.mood;
        this.tense = options.tense;
        this.voice = options.voice;
    }

    WinJS.Namespace.define("Verbs", {
        FirstConjugationVerb: FirstConjugationVerb,
        SecondConjugationVerb: SecondConjugationVerb,
        VerbFactory: VerbFactory
    });
})();