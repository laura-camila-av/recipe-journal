# Recipe Journal

A personal recipe diary desktop app I made to keep track of recipes I create and like. This is a work in progress being built in Java with JavaFX. The journal contains collections of recipes, each showing ingredients, quantities, a cover photo, instructions so that when I am time poor or stuck for meal ideas I can use one I've done before.

This project was built specifically to demonstrate object-oriented design in Java: a clean, composed class model sits underneath the UI, and most design decisions below were made deliberately (and sometimes revised) to reflect real OOP principles rather than just "make it work."

## Features

- **Recipe display** with a two-column layout: macros summary + ingredients on the left, cover photo + notes on the right, each independently scrollable
- **Tap-to-edit fields** — click the recipe name or instructions to edit them inline, no separate "edit mode" screen
- **Cover image upload** via a native file picker (`FileChooser`)
- **Persistence** — recipes save to a human-readable JSON file (via Gson) and reload automatically on next launch
- **Automatic macro/calorie calculations**, derived from a single source of truth (see Design Notes below)

## Tech Stack

- **Language:** Java 23
- **UI:** JavaFX
- **Build tool:** Maven
- **Persistence:** Gson (JSON serialization)

## Class Design

The core model is a composed object graph:

```
Profile
 └── has many Collection        (planned — not yet wired into the UI)
      └── has many Recipe
           ├── name, instructions, date, coverPhoto
           └── has many RecipeIngredient
                        ├── has an Ingredient (name, Macros per unit)
                        └── has a Quantity (amount, unit)
```

## Running Locally

1. Clone the repository
   ```
   git clone https://github.com/laura-camila-av/recipe-journal
   cd recipe-journal
   ```

2. Build
   ```
   mvn compile
   ```

3. Run
   ```
   mvn javafx:run
   ```

Requires JDK 23 and Maven installed and on your system PATH.

## Current Status

This is an actively in-progress learning project — the core recipe display, editing, and persistence loop is fully working, but it's a single hardcoded recipe rather than a full multi-recipe app yet.

**Done:**
- Full OOP model layer, composed and tested independently of the UI
- Two-column recipe display with independent scrolling
- Tap-to-edit for recipe name and instructions, wired to the underlying model
- Cover image upload
- Full save/load cycle to a JSON file

**Planned next:**
- Navigation bar and a list/grid of all saved recipes (currently only one hardcoded recipe is shown)
- Editable ingredients table (`TableView`), to replace the current read-only ingredients list
- Editable notes, backed by the model (currently static placeholder text)
- Collections — grouping recipes into named sets
- Visual polish pass (this project has intentionally prioritised functionality over styling so far)

## License

This project is open-source and available under the MIT License.
