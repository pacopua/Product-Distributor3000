# Product Distributor 3000

A JavaFX desktop app that works out the best way to place products on supermarket shelves. It uses the *synergy* between each pair of products: items that sell well together should sit next to each other.

This was a group project for **PROP** (Projectes de Programació) at the [Facultat d'Informàtica de Barcelona (UPC)](https://www.fib.upc.edu/).

## Features

- **Product management**: create, edit and delete products and set how strongly each pair is related.
- **Three placement algorithms** to choose from:
  | Algorithm | Type | Notes |
  |-----------|------|-------|
  | Hill Climbing | Approximation | Fast, good for large catalogues |
  | Simulated Annealing | Approximation | Fastest option, can escape local optima |
  | Brute force | Exact | Optimal, but only practical for small inputs |
- **Solution editing**: swap products in a generated layout by hand, with undo.
- **Persistence**: save and load the full state (products, relations, solutions).
- **Keyboard shortcuts**: `Ctrl + <letter>` presses the button whose label starts with that letter.

## Tech stack

Java 22 · JavaFX 17 · Gradle · Gson · JUnit 4 · jlink / jpackage for Windows packaging

## Getting started

### Windows (prebuilt)

- **Installer**: run [`dist/Product Distributor 3000-1.0.msi`](dist/).
- **Portable**: open `dist/Product Distributor 3000/Product Distributor 3000.exe`. No installation needed.

### From source

```bash
cd project_code
./gradlew run     # launch the app
./gradlew test    # run the unit tests
```

To rebuild the Windows installer and executable, run `project_code/build.bat`. It needs [WiX Toolset](https://wixtoolset.org/) 3.0+ and writes its output to `dist/`.

## Repository structure

```
project_code/   Gradle project (source, FXML views, tests)
  └─ src/main/java/edu/upc/prop/clusterxx/
       ├─ domain/   algorithms, products, solutions, controllers
       ├─ data/     persistence
       └─ visual/   JavaFX controllers and cells
docs/           User manual, algorithm studies, UML, Javadoc (Spanish)
dist/           Prebuilt Windows installer and portable executable
```

## Documentation

All the documents are in Spanish:

- [User manual](docs/Manual%20de%20usuario.pdf)
- [Algorithm studies](docs/Estudios.pdf) and the [code used for them](docs/algorithm-studies/)
- [Project documentation](docs/Documentacion.pdf): improvements, who implemented what, UML changes and test sets
- [UML class diagram](docs/UML.svg)
- [Javadoc](docs/javadoc/index.html)

## Authors

- Marcel Alabart Benoit
- Àngel Cantos Girón
- Adrià Cebrián Ruiz
- Biel Llabrés Raurell
