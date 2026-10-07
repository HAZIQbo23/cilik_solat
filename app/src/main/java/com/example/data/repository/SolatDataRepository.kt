package com.example.data.repository

import com.example.data.model.*

object SolatDataRepository {

    val wudukSteps = listOf(
        WudukStep(
            stepNumber = 1,
            title = "Niat Wuduk",
            arabicText = "نَوَيْتُ رَفْعَ الْحَدَثِ الْأَصْغَرِ لِلَّهِ تَعَالَى",
            transliteration = "Nawaitu raf'al hadatsil ashghari lillahi ta'ala",
            translation = "Sahaja aku mengangkat hadas kecil kerana Allah Ta'ala.",
            kidTips = "Niat di dalam hati apabila air mula menyentuh muka adik."
        ),
        WudukStep(
            stepNumber = 2,
            title = "Membasuh Dua Belah Tangan",
            arabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            transliteration = "Bismillahir Rahmanir Rahim",
            translation = "Dengan nama Allah Yang Maha Pemurah lagi Maha Penyayang.",
            kidTips = "Basuh hingga pergelangan tangan sebanyak 3 kali, celah-celah jari juga dibersihkan ya!",
            isSunat = true
        ),
        WudukStep(
            stepNumber = 3,
            title = "Berkumur-kumur",
            arabicText = "اللَّهُمَّ أَعِنِّي عَلَى ذِكْرِكَ وَشُكْرِكَ",
            transliteration = "Allahumma a'inni 'ala dzikrika wa syukrika",
            translation = "Ya Allah, tolonglah daku untuk mengingati-Mu dan bersyukur kepada-Mu.",
            kidTips = "Kumur mulut sebanyak 3 kali dengan lembut, buang sisa makanan yang tertinggal.",
            isSunat = true
        ),
        WudukStep(
            stepNumber = 4,
            title = "Memasukkan Air ke Hidung (Istinsyaq)",
            arabicText = "اللَّهُمَّ أَرِحْنِي رَائِحَةَ الْجَنَّةِ",
            transliteration = "Allahumma arihni ra'ihatal jannah",
            translation = "Ya Allah, kurniakan aku bau wangian syurga.",
            kidTips = "Sedut sedikit air ke dalam hidung kemudian hembuskan keluar sebanyak 3 kali.",
            isSunat = true
        ),
        WudukStep(
            stepNumber = 5,
            title = "Membasuh Muka (Rukun)",
            arabicText = "نَوَيْتُ الْوُضُوءَ لِلَّهِ تَعَالَى",
            transliteration = "Nawaitul wudhu'a lillahi ta'ala",
            translation = "Sahaja aku berwuduk kerana Allah Ta'ala.",
            kidTips = "Basuh seluruh muka dari anak rambut hingga dagu dan dari anak telinga kanan ke kiri."
        ),
        WudukStep(
            stepNumber = 6,
            title = "Membasuh Dua Belah Tangan Hingga Siku",
            arabicText = "اللَّهُمَّ أَعْطِنِي كِتَابِي بِيَمِينِي",
            transliteration = "Allahumma a'thini kitabi biyamini",
            translation = "Ya Allah, berikanlah buku amalanku dari sebelah kananku.",
            kidTips = "Mulakan tangan kanan dari hujung jari hingga melepasi siku 3 kali, kemudian tangan kiri."
        ),
        WudukStep(
            stepNumber = 7,
            title = "Menyapu Sebahagian Kepala & Telinga",
            arabicText = "اللَّهُمَّ حَرِّمْ شَعْرِي وَبَشَرِي عَلَى النَّارِ",
            transliteration = "Allahumma harrim sya'ri wa basyari 'alan nar",
            translation = "Ya Allah, selamatkan rambut dan kulitku daripada api neraka.",
            kidTips = "Basahkan tangan dan usap sebahagian rambut kepala dan kedua belah telinga dengan cermat."
        ),
        WudukStep(
            stepNumber = 8,
            title = "Membasuh Kaki Hingga Buku Lali & Tertib",
            arabicText = "اللَّهُمَّ ثَبِّتْ قَدَمَيَّ عَلَى الصِّرَاطِ",
            transliteration = "Allahumma tsabbit qadamayya 'alas sirath",
            translation = "Ya Allah, tetapkanlah kedua kakiku di atas titian sirat.",
            kidTips = "Basuh kaki kanan hingga melepasi buku lali dan celah jari 3 kali, kemudian kaki kiri."
        )
    )

    val doaSelepasWuduk = BacaanSolat(
        id = 99,
        number = 0,
        name = "Doa Selepas Wuduk",
        arabicText = "أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ، اللَّهُمَّ اجْعَلْنِي مِنَ التَّوَّابِينَ وَاجْعَلْنِي مِنَ الْمُتَطَهِّرِينَ",
        transliteration = "Asyhadu an laa ilaha illallah wahdahu la syarika lah, wa asyhadu anna Muhammadan 'abduhu wa rasuluh. Allahummaj'alni minat tawwabina waj'alni minal mutathahhirin.",
        meaning = "Aku bersaksi tiada Tuhan melainkan Allah yang Esa tiada sekutu bagi-Nya, dan aku bersaksi Nabi Muhammad hamba dan pesuruh-Nya. Ya Allah, jadikanlah daku dalam kalangan orang bertaubat dan menyucikan diri.",
        tips = "Menghadap kiblat dan tadah tangan semasa membaca doa selepas wuduk."
    )

    val rukunSolatList = listOf(
        RukunSolat(
            id = 1,
            number = 1,
            name = "Niat",
            category = RukunCategory.QALBI,
            arabicLafaz = "أُصَلِّي فَرْضَ الصُّبْحِ رَكْعَتَيْنِ لِلَّهِ تَعَالَى",
            transliteration = "Usolli fardhas Subhi rak'ataini lillahi ta'ala",
            meaning = "Sahaja aku solat fardu Subuh dua rakaat kerana Allah Ta'ala.",
            kidGuide = "Niat dihadirkan serentak di dalam hati semasa adik melafazkan Takbiratul Ihram.",
            motionTips = "Berdiri tegak, tenang dan fokuskan hati mengingati Allah."
        ),
        RukunSolat(
            id = 2,
            number = 2,
            name = "Takbiratul Ihram",
            category = RukunCategory.QAULI,
            arabicLafaz = "اللَّهُ أَكْبَرُ",
            transliteration = "Allahu Akbar",
            meaning = "Allah Maha Besar.",
            kidGuide = "Angkat kedua-dua tangan setinggi telinga (lelaki) atau paras dada (perempuan) dan sebutkan lafaz Takbir.",
            motionTips = "Tapak tangan menghadap ke arah kiblat dan jari terbuka sederhana."
        ),
        RukunSolat(
            id = 3,
            number = 3,
            name = "Berdiri Betul (Qiyam)",
            category = RukunCategory.FI_LI,
            arabicLafaz = "سُبْحَانَ اللَّهِ وَالْحَمْدُ لِلَّهِ",
            transliteration = "Subhanallah walhamdulillah",
            meaning = "Maha Suci Allah dan segala puji bagi Allah.",
            kidGuide = "Berdiri tegak menghadap kiblat bagi yang mampu. Kedua-dua kaki dibuka sedikit.",
            motionTips = "Letakkan tangan kanan di atas pergelangan tangan kiri di antara pusat dan dada."
        ),
        RukunSolat(
            id = 4,
            number = 4,
            name = "Membaca Surah Al-Fatihah",
            category = RukunCategory.QAULI,
            arabicLafaz = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝ الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ۝ الرَّحْمَٰنِ الرَّحِيمِ ۝ مَالِكِ يَوْمِ الدِّينِ ۝ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ۝ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ۝ صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            transliteration = "Bismillahir Rahmanir Rahim. Alhamdu lillahi Rabbil 'alamin. Ar-Rahmanir Rahim. Maliki yawmid-din. Iyyaka na'budu wa iyyaka nasta'in. Ihdinas-siratal mustaqim. Siratalladhina an'amta 'alayhim ghayril maghdubi 'alayhim waladh-dhallin.",
            meaning = "Dengan nama Allah Yang Maha Pemurah lagi Maha Penyayang. Segala puji bagi Allah Tuhan sekalian alam...",
            kidGuide = "Baca Surah Al-Fatihah dengan sebutan tajwid yang betul pada setiap rakaat solat.",
            motionTips = "Pandang ke tempat sujud dengan khusyuk dan tenang."
        ),
        RukunSolat(
            id = 5,
            number = 5,
            name = "Rukuk serta Thoma'ninah",
            category = RukunCategory.FI_LI,
            arabicLafaz = "سُبْحَانَ رَبِّيَ الْعَظِيمِ وَبِحَمْدِهِ",
            transliteration = "Subhana Rabbiyal 'Azimi wa bihamdih (3x)",
            meaning = "Maha Suci Tuhanku Yang Maha Agung dan dengan segala puji-Nya.",
            kidGuide = "Bongkokkan badan sehingga belakang lurus mendatar. Pegang lutut dengan kedua-dua belah tangan.",
            motionTips = "Thoma'ninah bermaksud berhenti seketika sekadar bacaan 'Subhanallah'."
        ),
        RukunSolat(
            id = 6,
            number = 6,
            name = "I'tidal serta Thoma'ninah",
            category = RukunCategory.FI_LI,
            arabicLafaz = "سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ ، رَبَّنَا وَلَكَ الْحَمْدُ",
            transliteration = "Sami'allahu liman hamidah. Rabbana walakal hamd.",
            meaning = "Allah mendengar pujian orang yang memuji-Nya. Wahai Tuhan kami, bagi-Mu segala puji.",
            kidGuide = "Bangkit dari rukuk kembali berdiri tegak lurus dengan tenang.",
            motionTips = "Lepaskan kedua-dua tangan ke sisi dengan lurus dan tenang."
        ),
        RukunSolat(
            id = 7,
            number = 7,
            name = "Sujud serta Thoma'ninah",
            category = RukunCategory.FI_LI,
            arabicLafaz = "سُبْحَانَ رَبِّيَ الْأَعْلَىٰ وَبِحَمْدِهِ",
            transliteration = "Subhana Rabbiyal A'la wa bihamdih (3x)",
            meaning = "Maha Suci Tuhanku Yang Maha Tinggi dan dengan segala puji-Nya.",
            kidGuide = "Letakkan dahi, kedua tapak tangan, dua lutut dan hujung jari kaki rapat ke lantai.",
            motionTips = "Pastikan dahi tidak berlapik (terbuka terus ke sejadah)."
        ),
        RukunSolat(
            id = 8,
            number = 8,
            name = "Duduk Antara Dua Sujud",
            category = RukunCategory.FI_LI,
            arabicLafaz = "رَبِّ اغْفِرْ لِي وَارْحَمْنِي وَاجْبُرْنِي وَارْفَعْنِي وَارْزُقْنِي وَاهْدِنِي وَعَافِنِي وَاعْفُ عَنِّي",
            transliteration = "Rabbighfirli warhamni wajburni warfa'ni warzuqni wahdini wa'afini wa'fu 'anni",
            meaning = "Wahai Tuhanku, ampunilah daku, kasihanilah daku, cukupkanlah kekuranganku, angkatlah darjatku, kurniakanlah rezeki kepadaku, berilah petunjuk kepadaku, sihatkanlah daku dan maafkanlah daku.",
            kidGuide = "Duduk di atas kaki kiri dan tegakkan kaki kanan (Duduk Iftirasy). Letak tangan atas peha.",
            motionTips = "Minta 8 doa kebaikan kepada Allah dengan penuh rasa rendah diri."
        ),
        RukunSolat(
            id = 9,
            number = 9,
            name = "Duduk Tahiyyat Akhir",
            category = RukunCategory.FI_LI,
            arabicLafaz = "اللَّهُمَّ صَلِّ عَلَىٰ مُحَمَّدٍ",
            transliteration = "Allahumma solli 'ala Muhammad",
            meaning = "Ya Allah, limpahkanlah rahmat ke atas Nabi Muhammad.",
            kidGuide = "Duduk secara Tawarruk (punggung di lantai dan kaki kiri disusupkan di bawah kaki kanan).",
            motionTips = "Duduk dengan sopan dan bersedia untuk bacaan tahiyyat."
        ),
        RukunSolat(
            id = 10,
            number = 10,
            name = "Membaca Tahiyyat Akhir",
            category = RukunCategory.QAULI,
            arabicLafaz = "التَّحِيَّاتُ الْمُبَارَكَاتُ الصَّلَوَاتُ الطَّيِّبَاتُ لِلَّهِ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَىٰ عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا رَسُولُ اللَّهِ",
            transliteration = "At-tahiyyatul mubarakatus salawatut tayyibatu lillah. As-salamu 'alayka ayyuhan Nabiyyu wa rahmatullahi wa barakatuh. As-salamu 'alayna wa 'ala 'ibadillahis salihin. Asyhadu an laa ilaha illallah, wa asyhadu anna Muhammadan Rasulullah.",
            meaning = "Segala ucapan selamat, keberkatan dan kebaikan adalah milik Allah. Salam sejahtera ke atasmu wahai Nabi Muhammad...",
            kidGuide = "Semasa mengucap 'illallah', angkat jari telunjuk kanan sedikit menghadap kiblat.",
            motionTips = "Bacalafaz tahiyyat secara jelas dan tenang."
        ),
        RukunSolat(
            id = 11,
            number = 11,
            name = "Selawat ke atas Nabi SAW",
            category = RukunCategory.QAULI,
            arabicLafaz = "اللَّهُمَّ صَلِّ عَلَىٰ سَيِّدِنَا مُحَمَّدٍ وَعَلَىٰ آلِ سَيِّدِنَا مُحَمَّدٍ",
            transliteration = "Allahumma solli 'ala sayyidina Muhammad wa 'ala aali sayyidina Muhammad",
            meaning = "Ya Allah, limpahkanlah rahmat ke atas junjungan kami Nabi Muhammad dan ke atas keluarga Baginda.",
            kidGuide = "Selawat dibaca sejurus selepas selesai bacaan syahadah tahiyyat akhir.",
            motionTips = "Tanda cinta dan hormat kita kepada junjungan mulia Rasulullah SAW."
        ),
        RukunSolat(
            id = 12,
            number = 12,
            name = "Memberi Salam Pertama",
            category = RukunCategory.QAULI,
            arabicLafaz = "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
            transliteration = "Assalamu'alaikum warahmatullah",
            meaning = "Sejahtera ke atas kamu dan rahmat Allah.",
            kidGuide = "Pusingkan muka ke kanan sehingga pipi kelihatan dari belakang.",
            motionTips = "Salam pertama adalah rukun solat, manakala salam kedua ke kiri adalah sunat."
        ),
        RukunSolat(
            id = 13,
            number = 13,
            name = "Tertib",
            category = RukunCategory.QALBI,
            arabicLafaz = "التَّرْتِيبُ فِي أَرْكَانِ الصَّلَاةِ",
            transliteration = "At-tartib fi arkanis solah",
            meaning = "Melakukan rukun mengikut turutan yang betul.",
            kidGuide = "Melakukan rukun solat dari nombor 1 (Niat) sehingga nombor 12 (Salam) mengikut urutan yang tersusun.",
            motionTips = "Tidak mendahulukan rukun yang di belakang dan tidak melangkau urutan."
        )
    )

    val bacaanSolatList = listOf(
        BacaanSolat(
            id = 1,
            number = 1,
            name = "Takbiratul Ihram",
            arabicText = "اللَّهُ أَكْبَرُ",
            transliteration = "Allahu Akbar",
            meaning = "Allah Maha Besar.",
            tips = "Rukun Qauli yang wajib dilafazkan dengan suara yang didengari diri sendiri."
        ),
        BacaanSolat(
            id = 2,
            number = 2,
            name = "Doa Iftitah (Sunat)",
            arabicText = "اللَّهُ أَكْبَرُ كَبِيرًا، وَالْحَمْدُ لِلَّهِ كَثِيرًا، وَسُبْحَانَ اللَّهِ بُكْرَةً وَأَصِيلًا، وَجَّهْتُ وَجْهِيَ لِلَّذِي فَطَرَ السَّمَاوَاتِ وَالْأَرْضَ حَنِيفًا مُسْلِمًا وَمَا أَنَا مِنَ الْمُشْرِكِينَ",
            transliteration = "Allahu Akbaru kabira, walhamdu lillahi kathira, wa Subhanallahi bukrataw-wa asila. Wajjahtu wajhiya lilladhi fataras samawati wal-ardha hanifam muslimaw-wama ana minal musyrikin.",
            meaning = "Allah Maha Besar sebesar-besarnya. Dan puji-pujian bagi Allah sebanyak-banyaknya...",
            tips = "Dibaca sejurus selepas Takbiratul Ihram pada rakaat pertama sebelum membaca Al-Fatihah."
        ),
        BacaanSolat(
            id = 3,
            number = 3,
            name = "Surah Al-Fatihah",
            arabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝ الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ۝ الرَّحْمَٰنِ الرَّحِيمِ ۝ مَالِكِ يَوْمِ الدِّينِ ۝ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ۝ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ۝ صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
            transliteration = "Bismillahir Rahmanir Rahim. Alhamdu lillahi Rabbil 'alamin. Ar-Rahmanir Rahim. Maliki yawmid-din. Iyyaka na'budu wa iyyaka nasta'in. Ihdinas-siratal mustaqim. Siratalladhina an'amta 'alayhim ghayril maghdubi 'alayhim waladh-dhallin.",
            meaning = "Dengan nama Allah Yang Maha Pemurah lagi Maha Penyayang. Segala puji bagi Allah Tuhan sekalian alam...",
            tips = "Wajib dibaca pada setiap rakaat solat dengan betul dan tertib."
        ),
        BacaanSolat(
            id = 4,
            number = 4,
            name = "Tasbih Rukuk",
            arabicText = "سُبْحَانَ رَبِّيَ الْعَظِيمِ وَبِحَمْدِهِ",
            transliteration = "Subhana Rabbiyal 'Azimi wa bihamdih (3x)",
            meaning = "Maha Suci Tuhanku Yang Maha Agung dan dengan segala puji-Nya.",
            tips = "Dibaca sebanyak 3 kali semasa membongkok rukuk dalam keadaan tenang."
        ),
        BacaanSolat(
            id = 5,
            number = 5,
            name = "Bacaan I'tidal",
            arabicText = "سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ ، رَبَّنَا وَلَكَ الْحَمْدُ",
            transliteration = "Sami'allahu liman hamidah. Rabbana walakal hamd.",
            meaning = "Allah mendengar orang yang memuji-Nya. Wahai Tuhan kami, bagi-Mu segala puji.",
            tips = "Dibaca ketika bangkit daripada rukuk menuju posisi berdiri tegak."
        ),
        BacaanSolat(
            id = 6,
            number = 6,
            name = "Tasbih Sujud",
            arabicText = "سُبْحَانَ رَبِّيَ الْأَعْلَىٰ وَبِحَمْدِهِ",
            transliteration = "Subhana Rabbiyal A'la wa bihamdih (3x)",
            meaning = "Maha Suci Tuhanku Yang Maha Tinggi dan dengan segala puji-Nya.",
            tips = "Dibaca sebanyak 3 kali ketika sujud dengan 7 anggota sujud menyentuh lantai."
        ),
        BacaanSolat(
            id = 7,
            number = 7,
            name = "Duduk Antara Dua Sujud",
            arabicText = "رَبِّ اغْفِرْ لِي وَارْحَمْنِي وَاجْبُرْنِي وَارْفَعْنِي وَارْزُقْنِي وَاهْدِنِي وَعَافِنِي وَاعْفُ عَنِّي",
            transliteration = "Rabbighfirli warhamni wajburni warfa'ni warzuqni wahdini wa'afini wa'fu 'anni",
            meaning = "Wahai Tuhanku! Ampunkanlah daku, kasihanilah daku, cukupkanlah kekuranganku, tinggikanlah darjatku, kurniakanlah rezeki kepadaku...",
            tips = "Doa yang mengandungi 8 permohonan kebaikan hidup di dunia dan akhirat."
        ),
        BacaanSolat(
            id = 8,
            number = 8,
            name = "Tahiyyat Awal",
            arabicText = "التَّحِيَّاتُ الْمُبَارَكَاتُ الصَّلَوَاتُ الطَّيِّبَاتُ لِلَّهِ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَىٰ عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، اللَّهُمَّ صَلِّ عَلَىٰ سَيِّدِنَا مُحَمَّدٍ",
            transliteration = "At-tahiyyatul mubarakatus salawatut tayyibatu lillah. As-salamu 'alayka ayyuhan Nabiyyu wa rahmatullahi wa barakatuh. As-salamu 'alayna wa 'ala 'ibadillahis salihin. Asyhadu an laa ilaha illallah wa asyhadu anna Muhammadan Rasulullah. Allahumma solli 'ala sayyidina Muhammad.",
            meaning = "Segala kehormatan yang berkat, solat dan kebaikan adalah kepunyaan Allah...",
            tips = "Dibaca pada rakaat kedua dalam solat 3 atau 4 rakaat (Zohor, Asar, Maghrib, Isyak)."
        ),
        BacaanSolat(
            id = 9,
            number = 9,
            name = "Tahiyyat Akhir & Selawat Ibrahimiyyah",
            arabicText = "التَّحِيَّاتُ الْمُبَارَكَاتُ الصَّلَوَاتُ الطَّيِّبَاتُ لِلَّهِ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَىٰ عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، اللَّهُمَّ صَلِّ عَلَىٰ سَيِّدِنَا مُحَمَّدٍ وَعَلَىٰ آلِ سَيِّدِنَا مُحَمَّدٍ، كَمَا صَلَّيْتَ عَلَىٰ سَيِّدِنَا إِبْرَاهِيمَ وَعَلَىٰ آلِ سَيِّدِنَا إِبْرَاهِيمَ، وَبَارِكْ عَلَىٰ سَيِّدِنَا مُحَمَّدٍ وَعَلَىٰ آلِ سَيِّدِنَا مُحَمَّدٍ، كَمَا بَارَكْتَ عَلَىٰ سَيِّدِنَا إِبْرَاهِيمَ وَعَلَىٰ آلِ سَيِّدِنَا إِبْرَاهِيمَ، فِي الْعَالَمِينَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
            transliteration = "At-tahiyyatul mubarakatus salawatut tayyibatu lillah... Allahumma solli 'ala sayyidina Muhammad wa 'ala aali sayyidina Muhammad, kama sallayta 'ala sayyidina Ibrahim wa 'ala aali sayyidina Ibrahim...",
            meaning = "Segala ucapan selamat, rahmat dan kebaikan adalah bagi Allah. Ya Allah, berkatilah Nabi Muhammad dan Nabi Ibrahim...",
            tips = "Wajib dibaca pada rakaat terakhir setiap solat fardu mahupun sunat."
        ),
        BacaanSolat(
            id = 10,
            number = 10,
            name = "Lafaz Salam",
            arabicText = "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
            transliteration = "Assalamu'alaikum warahmatullah",
            meaning = "Semoga kesejahteraan dan rahmat Allah tercurah kepada kamu.",
            tips = "Pusing kepala ke kanan untuk salam pertama (wajib) dan ke kiri untuk salam kedua (sunat)."
        ),
        BacaanSolat(
            id = 11,
            number = 11,
            name = "Doa Qunut (Solat Subuh)",
            arabicText = "اللَّهُمَّ اهْدِنِي فِيمَنْ هَدَيْتَ، وَعَافِنِي فِيمَنْ عَافَيْتَ، وَتَوَلَّنِي فِيمَنْ تَوَلَّيْتَ، وَبَارِكْ لِي فِيمَا أَعْطَيْتَ، وَقِنِي شَرَّ مَا قَضَيْتَ، فَإِنَّكَ تَقْضِي وَلَا يُقْضَىٰ عَلَيْكَ",
            transliteration = "Allahummahdini fiman hadayt, wa 'afini fiman 'afayt, wa tawallani fiman tawallayt, wa barik li fima a'thayt, wa qini syarra ma qadhayt...",
            meaning = "Ya Allah, berikanlah aku petunjuk sebagaimana orang yang telah Engkau beri petunjuk...",
            tips = "Sunat Ab'ad dibaca semasa I'tidal rakaat kedua dalam solat Subuh sambil menadah tangan."
        )
    )

    val solatWaktuList = listOf(
        SolatWaktu(
            id = 1,
            name = "Solat Subuh",
            rakaat = 2,
            timeDescription = "Waktu fajar menyingsing sehingga terbit matahari",
            niatArab = "أُصَلِّي فَرْضَ الصُّبْحِ رَكْعَتَيْنِ أَدَاءً لِلَّهِ تَعَالَى",
            niatRumi = "Usolli fardhas Subhi rak'ataini adaa-an lillahi ta'ala",
            niatMaksud = "Sahaja aku solat fardu Subuh dua rakaat tunai kerana Allah Ta'ala."
        ),
        SolatWaktu(
            id = 2,
            name = "Solat Zohor",
            rakaat = 4,
            timeDescription = "Waktu matahari tergelincir dari tengah langit sehingga bayang sama panjang",
            niatArab = "أُصَلِّي فَرْضَ الظُّهْرِ أَرْبَعَ رَكَعَاتٍ أَدَاءً لِلَّهِ تَعَالَى",
            niatRumi = "Usolli fardhaz Zhuhri arba'a raka'atin adaa-an lillahi ta'ala",
            niatMaksud = "Sahaja aku solat fardu Zohor empat rakaat tunai kerana Allah Ta'ala."
        ),
        SolatWaktu(
            id = 3,
            name = "Solat Asar",
            rakaat = 4,
            timeDescription = "Waktu bayang objek melebihi panjang objek sehingga matahari terbenam",
            niatArab = "أُصَلِّي فَرْضَ الْعَصْرِ أَرْبَعَ رَكَعَاتٍ أَدَاءً لِلَّهِ تَعَالَى",
            niatRumi = "Usolli fardhal 'Asri arba'a raka'atin adaa-an lillahi ta'ala",
            niatMaksud = "Sahaja aku solat fardu Asar empat rakaat tunai kerana Allah Ta'ala."
        ),
        SolatWaktu(
            id = 4,
            name = "Solat Maghrib",
            rakaat = 3,
            timeDescription = "Waktu matahari terbenam sepenuhnya sehingga hilang mega merah",
            niatArab = "أُصَلِّي فَرْضَ الْمَغْرِبِ ثَلَاثَ رَكَعَاتٍ أَدَاءً لِلَّهِ تَعَالَى",
            niatRumi = "Usolli fardhal Maghribi thalatha raka'atin adaa-an lillahi ta'ala",
            niatMaksud = "Sahaja aku solat fardu Maghrib tiga rakaat tunai kerana Allah Ta'ala."
        ),
        SolatWaktu(
            id = 5,
            name = "Solat Isyak",
            rakaat = 4,
            timeDescription = "Waktu hilang mega merah di kaki langit sehingga fajar sadiq",
            niatArab = "أُصَلِّي فَرْضَ الْعِشَاءِ أَرْبَعَ رَكَعَاتٍ أَدَاءً لِلَّهِ تَعَالَى",
            niatRumi = "Usolli fardhal 'Isya-i arba'a raka'atin adaa-an lillahi ta'ala",
            niatMaksud = "Sahaja aku solat fardu Isyak empat rakaat tunai kerana Allah Ta'ala."
        )
    )

    val quizQuestions = listOf(
        QuizQuestion(
            id = 1,
            questionText = "Apakah rukun solat yang pertama sekali?",
            options = listOf("Niat", "Rukuk", "Sujud"),
            correctIndex = 0,
            explanation = "Betul! Rukun pertama solat adalah Niat di dalam hati."
        ),
        QuizQuestion(
            id = 2,
            questionText = "Berapakah jumlah keseluruhan Rukun Solat?",
            options = listOf("5 Rukun", "10 Rukun", "13 Rukun"),
            correctIndex = 2,
            explanation = "Tepat sekali! Terdapat 13 Rukun Solat yang wajib dipelajari dan diamalkan."
        ),
        QuizQuestion(
            id = 3,
            questionText = "Berapakah bilangan rakaat bagi solat fardu Subuh?",
            options = listOf("2 Rakaat", "3 Rakaat", "4 Rakaat"),
            correctIndex = 0,
            explanation = "Hebat! Solat Subuh mempunyai 2 rakaat sahaja."
        ),
        QuizQuestion(
            id = 4,
            questionText = "Apakah lafaz yang diucapkan ketika Takbiratul Ihram?",
            options = listOf("Subhanallah", "Allahu Akbar", "Alhamdulillah"),
            correctIndex = 1,
            explanation = "Bagus! Lafaz Takbiratul Ihram ialah 'Allahu Akbar' yang bermaksud Allah Maha Besar."
        ),
        QuizQuestion(
            id = 5,
            questionText = "Ketika berwuduk, kita wajib membasuh kedua-dua belah tangan sehingga paras mana?",
            options = listOf("Pergelangan tangan", "Siku", "Bahu"),
            correctIndex = 1,
            explanation = "Pandai! Membasuh tangan sehingga paras siku adalah rukun wuduk."
        ),
        QuizQuestion(
            id = 6,
            questionText = "Apakah surah al-Quran yang wajib dibaca pada setiap rakaat solat?",
            options = listOf("Surah Al-Ikhlas", "Surah Al-Fatihah", "Surah An-Nas"),
            correctIndex = 1,
            explanation = "Cemerlang! Surah Al-Fatihah merupakan rukun qauli yang wajib dibaca dalam setiap rakaat."
        ),
        QuizQuestion(
            id = 7,
            questionText = "Apakah maksud 'Thoma'ninah' di dalam solat?",
            options = listOf("Membaca kuat-kuat", "Berhenti seketika dengan tenang", "Bergerak pantas"),
            correctIndex = 1,
            explanation = "Tepat! Thoma'ninah bermaksud tenang seketika sekadar menyebut 'Subhanallah'."
        ),
        QuizQuestion(
            id = 8,
            questionText = "Apakah ucapan yang dilafazkan semasa memberi salam pertama mengakhiri solat?",
            options = listOf("Assalamu'alaikum warahmatullah", "Bismillah", "Astaghfirullah"),
            correctIndex = 0,
            explanation = "Syabas! Lafaz salam ialah 'Assalamu'alaikum warahmatullah' sambil berpaling ke kanan."
        ),
        QuizQuestion(
            id = 9,
            questionText = "Solat fardu Maghrib mempunyai berapa rakaat?",
            options = listOf("2 Rakaat", "3 Rakaat", "4 Rakaat"),
            correctIndex = 1,
            explanation = "Hebat adik! Solat Maghrib mempunyai 3 rakaat."
        ),
        QuizQuestion(
            id = 10,
            questionText = "Apakah rukun solat yang ke-13 (terakhir)?",
            options = listOf("Tertib", "Membaca Surah Pendek", "Mandi"),
            correctIndex = 0,
            explanation = "Alhamdulillah cemerlang! Rukun ke-13 ialah Tertib (mengikut urutan dengan betul)."
        )
    )

    val allBadges = listOf(
        BadgeItem(
            id = "badge_wuduk",
            title = "Jagoan Wuduk",
            description = "Telah mempelajari kesemua 8 langkah wuduk yang suci!",
            iconEmoji = "💧",
            targetType = "wuduk",
            requiredCount = 8
        ),
        BadgeItem(
            id = "badge_solat",
            title = "Pakar Solat",
            description = "Telah menguasai kesemua 13 Rukun Solat dengan sempurna!",
            iconEmoji = "🕌",
            targetType = "rukun",
            requiredCount = 13
        ),
        BadgeItem(
            id = "badge_kuiz",
            title = "Juara Kuiz",
            description = "Berjaya menjawab soalan kuiz dan mengumpul markah tinggi!",
            iconEmoji = "🏆",
            targetType = "quiz",
            requiredCount = 50
        ),
        BadgeItem(
            id = "badge_bacaan",
            title = "Bintang Bacaan",
            description = "Telah mendengar dan mempelajari bacaan-bacaan solat!",
            iconEmoji = "📖",
            targetType = "bacaan",
            requiredCount = 5
        ),
        BadgeItem(
            id = "badge_hebat",
            title = "Solat Cilik Hebat",
            description = "Mengumpul lebih 50 Bintang dalam pengembaraan Cilik Solat!",
            iconEmoji = "⭐",
            targetType = "stars",
            requiredCount = 50
        )
    )
}
