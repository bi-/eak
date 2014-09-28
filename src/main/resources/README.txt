Egyetlen összetett alkamazást készítünk a félév során,
amiben időnként kicserélünk és/vagy bővítünk egyes entitásokat és funkciókat.
Az alkalmazás egy olyan rendszert fog modellezni, ami fejlesztési projekteket,
 és hozzájuk tartozó részfeladatokat tud tárolni,
 illetve a felhasználók ezeket képesek különböző mértékben menedzselni.

Az első feladat a következő technológiák használatát igényli: szérializáció,
távoli eljáráshívás RMI-vel, és adatbáziskezelés JDBC-vel.
Az alkalmazás osztályait az eak csomagba, illetve esetlegesen ezen belüli további csomagokba helyezzük el.

A) Készítsünk két szérializálható osztályt, amik a következő adatokat tárolják:

Fejlesztési projekt (eak.Project):

    név (name): sztring, elsődleges kulcs
    leírás (description): sztring, opcionális
    kezdés éve (year): egész szám, kötelező
    a vezető felhasználó neve (leader): sztring, kötelező

Részfeladat (eak.Task):

    sorszám (id): egész szám, elsődleges kulcs
    projekt neve (project): sztring, külső kulcs az előző entitáshoz, kötelező
    leírás (description): sztring, kötelező
    felelős (manager): sztring, kötelező
    fejlesztő (developer): sztring, opcionális
    állapot (status): egy érték a következő felsorolásból (eak.TaskStatus):
        új bejelentés (New)
        fejlesztőhöz hozzárendelt (Assigned)
        megoldott (Resolved)
        lezárt (Closed)

A felhasználókat külön nem tároljuk, csupán a nevük jelenik meg néhol ebben a fázisban.
A külső kulcsot érdemes felvenni az adatbázisban; ebben az esetben egy projekt nem törölhető,
 amíg vannak hozzá részfeladatok, de át lehet nevezni.

B) Készítsünk egy RMI-n keresztül elérhető szolgáltatást, ami az alábbi interfészt valósítja meg
(eak.TaskService). Legyen egy saját, ellenőrzött kivétel típusunk is (eak.TaskException),
 ami a hívások során felmerülő, üzleti logikához köthető hibákat képes kezelni.
 A műveletek egy adatbázist tartanak karban JDBC kapcsolaton át.

    void addProject(eak.Project newProject) throws eak.TaskException;
    // Hozzáad egy új projektet. Ha nem töltöttük ki valamelyik kötelező mezőt,
    // dobjon eak.TaskException kivételt. Ha már létezik ilyen nevű projekt, szintén.

    eak.Task addTask(String project, String description, String manager) throws eak.TaskException;
    // Hozzáad egy új részfeladatot. Ha nem töltöttük ki valamelyik paraméter mezőt, dobjon
    // eak.TaskException kivételt. Ha nem létezik a kapcsolódó nevű projekt, szintén.
    // Az új feladatnak az adatbázis alapján kell új azonosítót osztani. Ezt az
    // értéket a visszaadott objektumnak tartalmaznia kell, az állapota mező értéke
    // pedig legyen "új". A részfeladat fejlesztőjét tekintsük kitöltetlennek.

    java.util.Map<String, Integer> getStatistics();
    // Statisztikát ad vissza a projektekről. A kulcs a projektek neve, az érték
    // pedig a projekthez tartozó "új" állapotú részfeladatok száma.

A program használja a JDK-hoz mellékelt Derby adatbázis-szervert beágyazott módban.
Az adatbázist, és a megfelelő táblákat hozza létre, ha nem létezik.
Az adatbázis neve legyen tasks. Az RMI-hez szükséges Registry-t szintén a folyamaton belül indítsa el a szerver.

C) Készítsünk klienst, ami kipróbálja az elkészített szolgáltatást.
Adjunk hozzá legalább 2-3 projektet, és ezekhez néhány részfeladatot.
Teszteljük az összes hívást, a kivételt kiváltó esetekkel együtt.

Az elkészített program forráskódját egyetlen tömörített állományban kell beadni,
ami összes rész együttes megoldását tartalmazza, a fordításhoz és futtatáshoz szükséges parancsokkal együtt!